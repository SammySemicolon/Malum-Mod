package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleData;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.recipes.SpiritKilnRecipe;
import com.sammy.malum.core.systems.heat.IHeatTileEntity;
import com.sammy.malum.core.systems.heat.SimpleFuelSystem;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

import static com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreBlock.STATE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@SuppressWarnings("ConstantConditions")
public class SpiritKilnCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity, IHeatTileEntity
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
    
    public int state;
    public SpiritKilnRecipe currentRecipe;
    public float progress;
    public float advancedProgress;
    public ArrayList<BlockPos> blacklistedStands = new ArrayList<>();
    
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        advancedInventory.readData(compound, "advancedInventory");
        powerStorage.readData(compound);
        
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
        compound.putFloat("progress", progress);
        compound.putFloat("advancedProgress", advancedProgress);
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        currentRecipe = SpiritKilnRecipe.getRecipe(stack);
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
        if (world.rand.nextFloat() < 0.05f) // flame around bottom block
        {
            MalumHelper.spawnParticles(world, pos, new SpiritFlameParticleData(0.5f + world.rand.nextFloat() * 0.5f, false), 0.5f);
        }
        Vector3f exhaustPos = smokeParticleOutputPos();
        if (world.rand.nextFloat() < 0.25f) //smoke out of exhaust tubes
        {
            world.addParticle(ParticleTypes.SMOKE, exhaustPos.getX(), exhaustPos.getY(), exhaustPos.getZ(), 0, 0.04f, 0);
        }
    }
    
    public void finishEffects(Vector3f outputPos)
    {
        MalumHelper.makeFancyCircle((ServerWorld) world, outputPos);
        Vector3f furnaceItemPos = new Vector3f(pos.getX(), pos.getY(), pos.getZ());
        furnaceItemPos.add(itemOffset(this));
        MalumHelper.makeFancyCircle((ServerWorld) world, furnaceItemPos);
        
        world.playSound(null, this.pos, MalumSounds.TAINTED_FURNACE_FINISH, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void dumpEffects()
    {
        ((ServerWorld) world).spawnParticle(ParticleTypes.EXPLOSION, pos.getX(), pos.getY(), pos.getZ(), 3, 1, 1, 1, 0);
        ((ServerWorld) world).spawnParticle(new SpiritFlameParticleData(0.75f + world.rand.nextFloat() * 0.75f, true), pos.getX(), pos.getY(), pos.getZ(), 12 + world.rand.nextInt(12), 0.1f, 0.1f, 0.1f, 0.2f);
        ((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), 12 + world.rand.nextInt(12), 0.1f, 0.1f, 0.1f, 0.2f);
        
        world.playSound(null, this.pos, MalumSounds.TAINTED_FURNACE_FAIL, SoundCategory.BLOCKS, 0.4f, 1f);
        world.playSound(null, this.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void itemConsumeEffects(ItemStandTileEntity standTileEntity)
    {
        BlockPos standPos = standTileEntity.getPos();
        Vector3f standItemPos = new Vector3f(standPos.getX(), standPos.getY(), standPos.getZ());
        standItemPos.add(ItemStandTileEntity.itemOffset(standTileEntity));
        MalumHelper.makeFancyCircle((ServerWorld) world, standItemPos);
        world.playSound(null, standPos, MalumSounds.TAINTED_FURNACE_CONSUME, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    }
    
    public void passiveSound()
    {
        if (world.rand.nextFloat() < 0.05f)
        {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
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
    public Vector3f smokeParticleOutputPos()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3f directionVector = new Vector3f(direction.getXOffset() * 0.4f, 0, direction.getZOffset() * 0.4f);
        Vector3f particlePos = MalumHelper.randPos(pos, world.rand, -0.3f, 0.3f);
        Vector3f finalPos = new Vector3f(0, 0, 0);
        if (directionVector.getZ() != 0)
        {
            finalPos = new Vector3f(particlePos.getX() + 0.5f, particlePos.getY() + 2, particlePos.getZ() + 0.5f - directionVector.getZ());
        }
        if (directionVector.getX() != 0)
        {
            finalPos = new Vector3f(particlePos.getX() + 0.5f - directionVector.getX(), particlePos.getY() + 2, particlePos.getZ() + 0.5f);
        }
        return finalPos;
    }
    
    public Vector3f itemOutputPos()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3i directionVector = new Vector3i(direction.getXOffset(), 0, direction.getZOffset());
        return new Vector3f(this.pos.getX() + 0.5f - directionVector.getX(), this.pos.getY() + 1, this.pos.getZ() + 0.5f - directionVector.getZ());
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
            world.playSound(null, this.pos, SoundEvents.ENTITY_IRON_GOLEM_REPAIR, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
            setAndUpdateState(0);
        }
        else
        {
            for (int i = 0; i < 3; i++)
            {
                MalumHelper.spawnParticles(world, pos, new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), 0);
                MalumHelper.spawnParticles(world, pos.up(), new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), 0);
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
        resetCachedValues();
        Vector3f outputPos = itemOutputPos();
        inventory.dumpItems(world, outputPos);
        advancedInventory.dumpItems(world, outputPos);
        dumpEffects();
    }
    
    public void finishProcessing(SpiritKilnRecipe recipe)
    {
        Vector3f outputPos = itemOutputPos();
        ItemEntity entity = new ItemEntity(world, outputPos.getX(), outputPos.getY(), outputPos.getZ(), new ItemStack(recipe.outputItem, recipe.outputItemCount));
        world.addEntity(entity);
        resetCachedValues();
        inventory.getStackInSlot(0).shrink(recipe.inputItemCount);
        advancedInventory.clearItems();
        powerStorage.fuel -= (float) recipe.recipeTime / MalumMod.globalSpeedMultiplier;
        finishEffects(outputPos);
    }
    //endregion
    
    public static Vector3f itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3f directionVector = new Vector3f(direction.getXOffset(), 1.25f, direction.getZOffset());
        return new Vector3f(0.5f + directionVector.getX() * 0.25f, directionVector.getY(), 0.5f + directionVector.getZ() * 0.25f);
    }
}