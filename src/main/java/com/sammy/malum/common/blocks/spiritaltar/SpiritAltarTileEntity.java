package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.network.packets.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.network.packets.SpiritAltarCraftParticlePacket;
import com.sammy.malum.network.packets.TyrvingParticlePacket;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;
import java.util.Collection;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class SpiritAltarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritAltarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get());

        inventory = new SimpleInventory(1, 64, t-> !(t.getItem() instanceof SpiritItem))
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
                recipe = MalumSpiritAltarRecipes.getRecipe(inventory.getStackInSlot(slot), spiritInventory.nonEmptyStacks());
            }
        };
        extrasInventory = new SimpleInventory(8, 1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        spiritInventory = new SimpleInventory(8, 64, t-> t.getItem() instanceof SpiritItem)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
                recipe = MalumSpiritAltarRecipes.getRecipe(inventory.getStackInSlot(0), spiritInventory.nonEmptyStacks());
            }
        };
    }
    
    public int soundCooldown;
    public int progress;
    public boolean spedUp;
    public int spinUp;
    public float spin;
    public SimpleInventory inventory;
    public SimpleInventory extrasInventory;
    public SimpleInventory spiritInventory;
    public MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe;
    
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
        recipe = MalumSpiritAltarRecipes.getRecipe(inventory.getStackInSlot(0), spiritInventory.nonEmptyStacks());
        super.readData(compound);
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
                ItemStack stack = inventory.getStackInSlot(0);
                progress++;
                int progressCap = spedUp ? 60 : 360;
                if (progress >= progressCap)
                {
                    Vector3d itemPos = itemPos(this);

                    int extras = extrasInventory.nonEmptyItems();
                    if (extras != recipe.extraItemIngredients.size())
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
                                if (recipe.extraItemIngredients.get(extrasInventory.nonEmptyItems()).matches(providedStack))
                                {
                                    extrasInventory.playerInsertItem(world, providedStack.split(1));
                                    world.playSound(null, pos, MalumSounds.ALTAR_CONSUME, SoundCategory.BLOCKS, 1, 0.9f + world.rand.nextFloat() * 0.2f);

                                    Vector3d providedItemPos = tileEntity.providedItemOffset().add(pos.getX(), pos.getY(), pos.getZ());
                                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), SpiritAltarConsumeParticlePacket.fromIngredients(providedStack, recipe.spiritIngredients, providedItemPos.x,providedItemPos.y,providedItemPos.z, itemPos.x,itemPos.y,itemPos.z));
                                    break;
                                }
                            }
                        }
                        return;
                    }
                    for (SpiritIngredient ingredient : recipe.spiritIngredients)
                    {
                        for (int i = 0; i < spiritInventory.slotCount; i++)
                        {
                            ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                            if (ingredient.matches(spiritStack))
                            {
                                spiritStack.shrink(ingredient.count);
                                break;
                            }
                        }
                    }
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), SpiritAltarCraftParticlePacket.fromIngredients(recipe.spiritIngredients, itemPos.x, itemPos.y, itemPos.z));
                    stack.shrink(recipe.inputIngredient.count);
                    ItemEntity entity = new ItemEntity(world, itemPos.x, itemPos.y, itemPos.z, recipe.outputIngredient.getItem());
                    world.addEntity(entity);
                    progress = 0;
                    extrasInventory.clearItems();
                    recipe = MalumSpiritAltarRecipes.getRecipe(stack, spiritInventory.nonEmptyStacks());
                    MalumHelper.updateState(world.getBlockState(pos), world, pos);
                    world.playSound(null, pos, MalumSounds.ALTAR_CRAFT, SoundCategory.BLOCKS, 1, 0.9f + world.rand.nextFloat() * 0.2f);
                    if (recipe == null)
                    {
                        inventory.dumpItems(world, itemPos);
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
        return MalumHelper.pos(tileEntity.getPos()).add(0.5f,1.15f,0.5f);
    }
    public static Vector3d itemOffset(SpiritAltarTileEntity tileEntity, int slot)
    {
        float distance = 1 - Math.min(0.25f, tileEntity.spinUp / 40f) + (float)Math.sin(tileEntity.spin/20f)*0.025f;
        float height = 0.75f + Math.min(0.5f, tileEntity.spinUp / 20f);
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f,height,0.5f), distance,slot, tileEntity.spiritInventory.nonEmptyItems(), (long)tileEntity.spin,360);
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
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritItem spiritSplinterItem = (SpiritItem) item.getItem();
                Color color = spiritSplinterItem.type.color;


                ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(10 - Math.max(0, spinUp-10)/4)
                        .setScale(0.3f, 0)
                        .setColor(color.brighter(), color.darker())
                        .enableNoClip()
                        .repeat(world, x,y,z, 2);

                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(80 - Math.max(0, spinUp-10)*2)
                        .setSpin(0.1f)
                        .setScale(0.2f, 0)
                        .setColor(color, color.darker())
                        .enableNoClip()
                        .repeat(world, x,y,z, 1);

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
                            .repeat(world, x, y, z, 2);

                    float alpha = 0.08f - spiritInventory.slotCount * 0.005f;
                    ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                            .setAlpha(alpha, 0f)
                            .setLifetime(20)
                            .setScale(0.5f, 0)
                            .randomOffset(0.1, 0.1)
                            .randomVelocity(0.02f, 0.02f)
                            .setColor(color, color.darker())
                            .randomVelocity(0.0025f, 0.0025f)
                            .enableNoClip()
                            .repeat(world, itemPos.x,itemPos.y,itemPos.z,2);
                }
            }
        }
    }
}