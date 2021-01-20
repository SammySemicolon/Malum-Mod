package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.extractionfocus.ExtractionFocusBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes.MalumSpiritKilnRecipe;
import com.sammy.malum.core.systems.fuel.IFuelTileEntity;
import com.sammy.malum.core.systems.fuel.SimpleFuelSystem;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.network.packets.ParticlePacket;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreBlock.STATE;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@SuppressWarnings("ConstantConditions")
public class SpiritKilnCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity, IFuelTileEntity
{
    public SpiritKilnCoreTileEntity()
    {
        super(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        advancedInventory = new SimpleInventory(3, 1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        powerStorage = new SimpleFuelSystem(this, 64);
    }
    
    @Override
    public SimpleFuelSystem getSystem()
    {
        return powerStorage;
    }
    
    public boolean isPowered()
    {
        return getBlockState().get(STATE) == 2;
    }
    
    public SimpleInventory inventory;
    public SimpleInventory advancedInventory;
    public SimpleFuelSystem powerStorage;
    public BlockPos extractionFocus;
    
    public int state;
    public MalumSpiritKilnRecipe currentRecipe;
    public float progress;
    public float advancedProgress;
    public ArrayList<BlockPos> blacklistedStands = new ArrayList<>();
    
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        advancedInventory.readData(compound, "advancedInventory");
        powerStorage.readData(compound);
        extractionFocus = NBTUtil.readBlockPos(compound);
        progress = compound.getFloat("progress");
        advancedProgress = compound.getFloat("advancedProgress");
        
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        advancedInventory.writeData(compound, "advancedInventory");
        powerStorage.writeData(compound);
        if (extractionFocus != null)
        {
            NBTUtil.writeBlockPos(extractionFocus);
        }
        compound.putFloat("progress", progress);
        compound.putFloat("advancedProgress", advancedProgress);
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        currentRecipe = MalumSpiritKilnRecipes.getRecipe(stack);
        state = getBlockState().get(STATE);
        if (MalumHelper.areWeOnClient(world))
        {
            if (state != 1 && powerStorage.fuel > 0 && currentRecipe != null)
            {
                passiveParticles();
            }
        }
        if (MalumHelper.areWeOnServer(world))
        {
            if (state != 1)
            {
                maintainState();
            }
            if (currentRecipe != null)
            {
                if (powerStorage.fuel > 0)
                {
                    passiveSound();
                    if (state != 1)
                    {
                        simpleProcessing();
                    }
                }
            }
            else
            {
                if (progress > 0 || advancedProgress > 0 || !blacklistedStands.isEmpty())
                {
                    resetCachedValues();
                }
            }
        }
    }
    
    //region effect helpers
    public void passiveParticles()
    {
        Color color = MalumConstants.faded();
        if (world.rand.nextFloat() < 0.2f) // flame around bottom block
        {
            ParticleManager.create(MalumParticles.SPIRIT_FLAME)
                    .setAlpha(1.0f, 0f).setScale(0.65f, 0)
                    .setColor(color, color)
                    .randomOffset(0.15, 0.025).randomVelocity(0f, 0.01f)
                    .addVelocity(0, 0.02f, 0)
                    .enableGravity()
                    .repeatEdges(world, pos, 2);
        }
        Vector3d exhaustPos = smokeParticleOutputPos();
        if (world.rand.nextFloat() < 0.25f) //smoke out of exhaust tubes
        {
            world.addParticle(ParticleTypes.SMOKE, exhaustPos.getX(), exhaustPos.getY(), exhaustPos.getZ(), 0, 0.04f, 0);
        }
        Vector3d itemOffset = itemOffset(this);
        Vector3d furnaceItemPos = new Vector3d(pos.getX() + itemOffset.x,pos.getY() + itemOffset.y-0.15f,pos.getZ() + itemOffset.z);
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setScale(0.08f, 0).setLifetime(20)
                    .randomOffset(0.15, 0.025).randomVelocity(0.025f, 0.01f)
                    .addVelocity(0, 0.02f, 0)
                    .setColor(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getRed()/255f, (color.getGreen() * 0.5f)/255f, (color.getBlue() * 0.5f)/255f)
                    .setSpin(0.4f)
                    .repeat(world, furnaceItemPos.x,furnaceItemPos.y,furnaceItemPos.z, 5);
    
        ParticleManager.create(MalumParticles.SPIRIT_FLAME)
                .setAlpha(1.0f, 0).setScale(0.3f, 0)
                .randomOffset(0.25, 0.025).randomVelocity(0.01f, 0.01f)
                .setColor(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getRed()/255f, (color.getGreen() * 0.5f)/255f, (color.getBlue() * 0.5f)/255f)
                .addVelocity(0, 0.01f, 0)
                .repeat(world, furnaceItemPos.x,furnaceItemPos.y,furnaceItemPos.z, 2);
    }
    
    public void finishEffects(Vector3d outputPos)
    {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()-> world.getChunkAt(pos)), new ParticlePacket(2, outputPos.getX(),outputPos.getY(),outputPos.getZ()));
    
        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_FINISH, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void dumpEffects()
    {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()-> world.getChunkAt(pos)), new ParticlePacket(1, pos.getX(),pos.getY(),pos.getZ()));
    
        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_FAIL, SoundCategory.BLOCKS, 0.4f, 1f);
        world.playSound(null, this.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void itemConsumeEffects(ItemStandTileEntity standTileEntity)
    {
        BlockPos standPos = standTileEntity.getPos();
        Vector3d itemOffset = ItemStandTileEntity.itemOffset(standTileEntity);
        Vector3d standItemPos = new Vector3d(standPos.getX() + itemOffset.x, standPos.getY() + itemOffset.y, standPos.getZ() + itemOffset.z);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()-> world.getChunkAt(pos)), new ParticlePacket(2, standItemPos.getX(),standItemPos.getY(),standItemPos.getZ()));
    
        world.playSound(null, standPos, MalumSounds.SPIRIT_KILN_CONSUME, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void passiveSound()
    {
        if (world.rand.nextFloat() < 0.05f)
        {
            world.playSound(null,pos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
    //endregion
    
    //region state helpers
    public void maintainState()
    {
        if (powerStorage.fuel > 0 && currentRecipe != null)
        {
            if (!isPowered())
            {
                setAndUpdateState(2);
            }
        }
        else
        {
            if (isPowered())
            {
                setAndUpdateState(0);
            }
        }
    }
    
    public void setAndUpdateState(int value)
    {
        world.setBlockState(pos, world.getBlockState(pos).with(STATE, value));
        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(STATE, value));
        
        MalumHelper.updateState(world.getBlockState(pos), world, pos);
        MalumHelper.updateState(world.getBlockState(pos.up()), world, pos.up());
    }
    //endregion
    
    //region vector helpers
    public Vector3d smokeParticleOutputPos()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset() * 0.4f, 0, direction.getZOffset() * 0.4f);
        Vector3d particlePos = MalumHelper.randPos(pos, world.rand, -0.3f, 0.3f);
        Vector3d finalPos = new Vector3d(0, 0, 0);
        if (directionVector.getZ() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f, particlePos.getY() + 2, particlePos.getZ() + 0.5f - directionVector.getZ());
        }
        if (directionVector.getX() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f - directionVector.getX(), particlePos.getY() + 2, particlePos.getZ() + 0.5f);
        }
        return finalPos;
    }
    
    public Vector3d itemOutputPos()
    {
        extractionFocus = MalumHelper.extractionFocus(world,pos.up());
        if (extractionFocus != null)
        {
            if (world.getBlockState(extractionFocus).getBlock() instanceof ExtractionFocusBlock)
            {
                return new Vector3d(extractionFocus.getX() + 0.5f, extractionFocus.getY() + 0.5f, extractionFocus.getZ() + 0.5f);
            }
        }
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3i directionVector = new Vector3i(direction.getXOffset(), 0, direction.getZOffset());
        return new Vector3d(this.pos.getX() + 0.5f - directionVector.getX(), this.pos.getY() + 1.5f, this.pos.getZ() + 0.5f - directionVector.getZ());
    }
    //endregion
    
    //region logic helpers
    public void resetCachedValues()
    {
        progress = 0;
        advancedProgress = 0;
        blacklistedStands.clear();
        if (state == 1)
        {
            MalumHelper.updateState(world.getBlockState(pos), world, pos);
        }
    }
    
    public void repairKiln()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_REPAIR, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
            setAndUpdateState(0);
        }
        else
        {
            for (int i = 0; i < 3; i++)
            {
                ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
                ArrayList<Vector3d> particlePositionsUp = MalumHelper.blockOutlinePositions(world, pos.up());
                particlePositionsUp.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
            }
        }
    }
    
    public void simpleProcessing()
    {
        MalumHelper.updateState(getBlockState(), world, pos);
        progress += powerStorage.speed;
        if (progress >= currentRecipe.recipeTime)
        {
            if (currentRecipe.isAdvanced())
            {
                progress = currentRecipe.recipeTime;
                advancedProgress += powerStorage.speed;
                if (advancedProgress >= MalumMod.globalSpeedMultiplier / 4f)
                {
                    advancedProcessing();
                }
            }
            else
            {
                finishProcessing(currentRecipe);
            }
        }
    }
    
    public void advancedProcessing()
    {
        ArrayList<BlockPos> stands = MalumHelper.itemStands(world, pos, Direction.UP, Direction.DOWN);
        boolean success = finishAdvancedProcessing(stands);
        if (success)
        {
            return;
        }
        stands.removeAll(blacklistedStands);
        if (stands.isEmpty())
        {
            return;
        }
        for (BlockPos pos : stands)
        {
            if (world.getTileEntity(pos) instanceof ItemStandTileEntity)
            {
                ItemStandTileEntity standTileEntity = (ItemStandTileEntity) world.getTileEntity(pos);
                ItemStack standStack = standTileEntity.inventory.getStackInSlot(0);
                if (!standStack.isEmpty())
                {
                    int firstEmpty = advancedInventory.firstEmptyItem();
                    if (firstEmpty != -1)
                    {
                        advancedInventory.setStackInSlot(firstEmpty, standStack.split(1));
                        advancedProgress = 0;
                        blacklistedStands.add(pos);
                        MalumHelper.updateState(world, pos);
                        
                        itemConsumeEffects(standTileEntity);
                        break;
                    }
                }
                else
                {
                    blacklistedStands.add(pos);
                }
            }
        }
    }
    
    public boolean finishAdvancedProcessing(ArrayList<BlockPos> stands)
    {
        if (stands.size() == blacklistedStands.size())
        {
            ArrayList<Item> advancedItems = advancedInventory.items();
            
            if (currentRecipe.hasAlternatives)
            {
                MalumSpiritKilnRecipe maybeRightRecipe = MalumSpiritKilnRecipes.getPreciseRecipe(inventory.getStackInSlot(0), MalumHelper.toArrayList(advancedItems.stream().filter(i -> !i.getDefaultInstance().isEmpty())));
                if (maybeRightRecipe != null)
                {
                    currentRecipe = maybeRightRecipe;
                }
            }
            if (advancedItems.stream().filter(i -> !i.getDefaultInstance().isEmpty()).anyMatch(i -> !currentRecipe.extraItems.contains(i))) //any item in the inventory, ISN'T present in the recipe.
            {
                dumpAllItems();
                return false;
            }
            if (MalumHelper.hasDuplicate(advancedItems.stream().filter(i -> !i.getDefaultInstance().isEmpty()).toArray())) //any item in the inventory present more than once
            {
                dumpAllItems();
                return false;
            }
            if (advancedItems.containsAll(currentRecipe.extraItems))
            {
                finishProcessing(currentRecipe);
            }
            else
            {
                dumpAllItems();
            }
            return true;
        }
        return false;
    }
    
    public void dumpAllItems()
    {
        setAndUpdateState(1);
        powerStorage.wipe();
        Vector3d outputPos = itemOutputPos();
        inventory.dumpItems(world, outputPos);
        advancedInventory.dumpItems(world, outputPos);
        dumpEffects();
    }
    
    public void finishProcessing(MalumSpiritKilnRecipe recipe)
    {
        Vector3d outputPos = itemOutputPos();
        ItemEntity entity = new ItemEntity(world, outputPos.getX(), outputPos.getY(), outputPos.getZ(), new ItemStack(recipe.outputItem, recipe.outputItemCount));
        world.addEntity(entity);
        resetCachedValues();
        inventory.getStackInSlot(0).shrink(recipe.inputItemCount);
        advancedInventory.clearItems();
        powerStorage.fuel -= (float) recipe.recipeTime / MalumMod.globalSpeedMultiplier;
        finishEffects(outputPos);
    }
    //endregion
    
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 1.25, direction.getZOffset());
        return new Vector3d(0.5 + directionVector.getX() * 0.25, directionVector.getY(), 0.5 + directionVector.getZ() * 0.25);
    }
}