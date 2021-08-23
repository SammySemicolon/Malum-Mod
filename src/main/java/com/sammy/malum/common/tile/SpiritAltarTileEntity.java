package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.mod_systems.recipe.IngredientWithCount;
import com.sammy.malum.core.mod_systems.recipe.ItemWithCount;
import com.sammy.malum.core.mod_systems.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntityInventory;
import com.sammy.malum.core.mod_systems.particle.ParticleManager;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import com.sammy.malum.network.packets.particle.altar.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.network.packets.particle.altar.SpiritAltarCraftParticlePacket;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Collection;
import java.util.Random;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class SpiritAltarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public int soundCooldown;
    public int progress;
    public boolean spedUp;
    public int spinUp;
    public float spin;
    public SimpleTileEntityInventory inventory;
    public SimpleTileEntityInventory extrasInventory;
    public SimpleTileEntityInventory spiritInventory;
    public SpiritInfusionRecipe recipe;

    public SpiritAltarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get());

        inventory = new SimpleTileEntityInventory(1, 64, t-> !(t.getItem() instanceof SpiritItem))
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
                recipe = SpiritInfusionRecipe.getRecipeForAltar(world, inventory.getStackInSlot(0), spiritInventory.nonEmptyStacks());
            }
        };
        extrasInventory = new SimpleTileEntityInventory(8, 1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
        spiritInventory = new SimpleTileEntityInventory(8, 64, t-> t.getItem() instanceof SpiritItem)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
                recipe = SpiritInfusionRecipe.getRecipeForAltar(world, inventory.getStackInSlot(0), spiritInventory.nonEmptyStacks());
            }
        };
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (progress != 0)
        {
            compound.putInt("progress", progress);
        }
        if (spinUp != 0)
        {
            compound.putInt("spinUp", spinUp);
        }
        if (spedUp)
        {
            compound.putByte("spedUp", (byte) 0);
        }
        
        inventory.writeData(compound);
        spiritInventory.writeData(compound, "spiritInventory");
        extrasInventory.writeData(compound, "extrasInventory");
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        spinUp = compound.getInt("spinUp");
        if (compound.contains("spedUp"))
        {
            spedUp = true;
        }
        inventory.readData(compound);
        spiritInventory.readData(compound, "spiritInventory");
        extrasInventory.readData(compound, "extrasInventory");
        recipe = SpiritInfusionRecipe.getRecipeForAltar(world, inventory.getStackInSlot(0), spiritInventory.nonEmptyStacks());
    }
    
    @Override
    public void tick()
    {
        if (soundCooldown > 0)
        {
            soundCooldown--;
        }
        if (recipe != null)
        {
            int spinCap = spedUp ? 30 : 10;
            if (spinUp < spinCap)
            {
                spinUp++;
            }
            if (MalumHelper.areWeOnServer(world))
            {
                if (soundCooldown == 0)
                {
                    world.playSound(null, pos, MalumSounds.ALTAR_LOOP, SoundCategory.BLOCKS, 1, 1f);
                    soundCooldown = 180;
                }
                progress++;
                int progressCap = spedUp ? 60 : 360;
                if (progress >= progressCap)
                {
                    boolean success = consume();
                    if (success)
                    {
                        craft();
                    }
                }
            }
        }
        else
        {
            progress = 0;
            if (spinUp > 0)
            {
                spinUp--;
            }
            spedUp = false;
        }
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }
    public static Vector3d itemPos(SpiritAltarTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(tileEntity.itemOffset());
    }
    public Vector3d itemOffset()
    {
        return new Vector3d(0.5f, 1.25f, 0.5f);
    }
    public static Vector3d itemOffset(SpiritAltarTileEntity tileEntity, int slot)
    {
        float distance = 1 - Math.min(0.25f, tileEntity.spinUp / 40f) + (float)Math.sin(tileEntity.spin/20f)*0.025f;
        float height = 0.75f + Math.min(0.5f, tileEntity.spinUp / 20f);
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f,height,0.5f), distance,slot, tileEntity.spiritInventory.nonEmptyItems(), (long)tileEntity.spin,360);
    }
    public boolean consume()
    {
        Vector3d itemPos = itemPos(this);
        int extras = extrasInventory.nonEmptyItems();
        if (extras != recipe.extraItems.size())
        {
            progress *= 0.5f;
            int horizontal = 4;
            int vertical = 2;
            Collection<BlockPos> nearbyBlocks = MalumHelper.getBlocks(pos, horizontal, vertical, horizontal);
            for (BlockPos pos : nearbyBlocks)
            {
                if (world.getTileEntity(pos) instanceof IAltarProvider)
                {
                    IAltarProvider tileEntity = (IAltarProvider) world.getTileEntity(pos);
                    ItemStack providedStack = tileEntity.providedInventory().getStackInSlot(0);
                    IngredientWithCount requestedItem = recipe.extraItems.get(extras);
                    if (requestedItem.matches(providedStack))
                    {
                        world.playSound(null, pos, MalumSounds.ALTAR_CONSUME, SoundCategory.BLOCKS, 1, 0.9f + world.rand.nextFloat() * 0.2f);
                        Vector3d providedItemPos = tileEntity.providedItemPos();
                        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), SpiritAltarConsumeParticlePacket.fromSpirits(providedStack, recipe.spirits(), providedItemPos.x,providedItemPos.y,providedItemPos.z, itemPos.x,itemPos.y,itemPos.z));
                        extrasInventory.playerInsertItem(world, providedStack.split(requestedItem.count));
                        MalumHelper.updateAndNotifyState(world, pos);
                        MalumHelper.updateAndNotifyState(world, this.pos);
                        break;
                    }
                }
            }
            return false;
        }
        return true;
    }
    public void craft()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        Vector3d itemPos = itemPos(this);
        ItemStack outputStack = recipe.output.stack();
        if (inventory.getStackInSlot(0).hasTag())
        {
            outputStack.setTag(stack.getTag());
        }

        stack.shrink(recipe.input.count);
        for (ItemWithCount spirit : recipe.spirits)
        {
            for (int i = 0; i < spiritInventory.slotCount; i++)
            {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack))
                {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }

        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), SpiritAltarCraftParticlePacket.fromSpirits(recipe.spirits(), itemPos.x, itemPos.y, itemPos.z));
        progress = 0;
        extrasInventory.clearItems();
        recipe = SpiritInfusionRecipe.getRecipeForAltar(world, stack, spiritInventory.nonEmptyStacks());
        world.playSound(null, pos, MalumSounds.ALTAR_CRAFT, SoundCategory.BLOCKS, 1, 0.9f + world.rand.nextFloat() * 0.2f);
        world.addEntity(new ItemEntity(world, itemPos.x, itemPos.y, itemPos.z, outputStack));

        MalumHelper.updateAndNotifyState(world, pos);
    }
    public void passiveParticles()
    {
        Vector3d itemPos = itemPos(this);
        spin += 1+ spinUp / 5f;
        for (int i = 0; i < spiritInventory.slotCount; i++)
        {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritItem)
            {
                Vector3d offset = itemOffset(this, i);
                Random rand = world.rand;
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritItem spiritSplinterItem = (SpiritItem) item.getItem();
                Color color = spiritSplinterItem.type.color;
                SpiritHelper.spiritParticles(world, x,y,z, color);


                if (recipe != null)
                {
                    Vector3d velocity = new Vector3d(x, y, z).subtract(itemPos).normalize().scale(-0.03f);
                    ParticleManager.create(MalumParticles.WISP_PARTICLE)
                            .setAlpha(0.15f, 0f)
                            .setLifetime(40)
                            .setScale(0.2f, 0)
                            .randomOffset(0.02f)
                            .randomVelocity(0.01f, 0.01f)
                            .setColor(color, color.darker())
                            .randomVelocity(0.0025f, 0.0025f)
                            .addVelocity(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(world, x, y, z, spedUp ? 4 : 2);

                    float alpha = 0.08f / spiritInventory.nonEmptyItems();
                    ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                            .setAlpha(alpha, 0f)
                            .setLifetime(20)
                            .setScale(0.5f, 0)
                            .randomOffset(0.1, 0.1)
                            .randomVelocity(0.02f, 0.02f)
                            .setColor(color, color.darker())
                            .randomVelocity(0.0025f, 0.0025f)
                            .enableNoClip()
                            .repeat(world, itemPos.x,itemPos.y,itemPos.z,spedUp ? 4 : 2);
                }
            }
        }
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}