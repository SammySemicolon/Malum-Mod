package com.sammy.malum.common.blocks.infernalkiln;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes.MalumSpiritKilnRecipe;
import com.sammy.malum.core.systems.fuel.IFuelTileEntity;
import com.sammy.malum.core.systems.fuel.SimpleFuelSystem;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

import java.util.HashMap;

import static com.sammy.malum.common.blocks.infernalkiln.SpiritKilnCoreBlock.STATE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.util.Direction.*;

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
                SpiritKilnCoreTileEntity.this.currentRecipe = MalumSpiritKilnRecipes.getRecipe(this.getStackInSlot(0));
            }
        };
        sideInventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        workInventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        outputInventory = new SimpleInventory(1, 64)
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
    public boolean isDamaged()
    {
        return getBlockState().get(STATE) == 1;
    }
    
    public SimpleInventory inventory;
    public SimpleInventory sideInventory;
    public SimpleInventory workInventory;
    public SimpleInventory outputInventory;
    public SimpleFuelSystem powerStorage;
    
    public HashMap<Direction, Integer> slotToDirection = new HashMap<>();
    public BlockPos extractionFocus;
    
    public MalumSpiritKilnRecipe currentRecipe;
    public float progress;
    public float advancedProgress;
    public int currentItem;

    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        sideInventory.readData(compound, "sideInventory");
        workInventory.readData(compound, "workInventory");
        outputInventory.readData(compound, "outputInventory");
        
        powerStorage.readData(compound);
        extractionFocus = NBTUtil.readBlockPos(compound);
        progress = compound.getFloat("progress");
        advancedProgress = compound.getFloat("advancedProgress");
        currentItem = compound.getInt("currentItem");
        setupDirections(getBlockState());
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        sideInventory.writeData(compound, "sideInventory");
        workInventory.writeData(compound, "workInventory");
        outputInventory.writeData(compound, "outputInventory");
        
        powerStorage.writeData(compound);
        if (extractionFocus != null)
        {
            NBTUtil.writeBlockPos(extractionFocus);
        }
        compound.putFloat("progress", progress);
        compound.putFloat("advancedProgress", advancedProgress);
        compound.putInt("currentItem", currentItem);
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        if (MalumHelper.areWeOnServer(world))
        {
            if (isPowered())
            {
                if (currentRecipe != null)
                {
                    if (powerStorage.fuel > 0)
                    {
                        passiveSound();
                    }
                    else
                    {
                        return;
                    }
                    progress++;
                    if (progress > currentRecipe.recipeTime)
                    {
                        if (advancedProgress == 0)
                        {
                            int nonEmptyItems = workInventory.nonEmptyItems();
                            if (currentRecipe.items.size() == nonEmptyItems)
                            {
                                currentRecipe = MalumSpiritKilnRecipes.getPreciseRecipe(stack, workInventory.nonEmptyStacks());
                                if (currentRecipe != null)
                                {
                                    finishCrafting();
                                }
                                else
                                {
                                    damageKiln();
                                }
                            }
                            else
                            {
                                advancedProgress = 60;
                                for (int i = 0; i < 3; i++)
                                {
                                    MalumItemIngredient ingredient = currentRecipe.items.get(nonEmptyItems);
                                    ItemStack splinter = sideInventory.getStackInSlot(i);
                                    if (ingredient.matches(splinter))
                                    {
                                        workInventory.insertItem(splinter.split(ingredient.count), nonEmptyItems);
                                        currentItem = i;
                                        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_CONSUME, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
                                        return;
                                    }
                                }
                                damageKiln();
                            }
                        }
                        else
                        {
                            advancedProgress--;
                        }
                    }
                }
                else
                {
                    damageKiln();
                }
            }
        }
    }
    public void setupDirections(BlockState state)
    {
        switch (state.get(HORIZONTAL_FACING))
        {
            case NORTH:
            {
                slotToDirection.put(EAST, 0);
                slotToDirection.put(SOUTH, 1);
                slotToDirection.put(WEST, 2);
                break;
            }
            case SOUTH:
            {
                slotToDirection.put(WEST, 0);
                slotToDirection.put(NORTH, 1);
                slotToDirection.put(EAST, 2);
                break;
            }
            case EAST:
            {
                slotToDirection.put(SOUTH, 0);
                slotToDirection.put(WEST, 1);
                slotToDirection.put(NORTH, 2);
                break;
            }
            case WEST:
            {
                slotToDirection.put(NORTH, 0);
                slotToDirection.put(EAST, 1);
                slotToDirection.put(SOUTH, 2);
            }
        }
    }
    public void passiveSound()
    {
        if (world.rand.nextFloat() < 0.05f)
        {
            world.playSound(null,pos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
    //endregion
    public Vector3d dumpPos()
    {
        Vector3i directionVec = getBlockState().get(HORIZONTAL_FACING).getDirectionVec();
        return MalumHelper.pos(getPos()).add(0.5,1.5,0.5).add(directionVec.getX(), 0, directionVec.getZ());
    }
    public void outputItem(ItemStack stack)
    {
        outputInventory.insertItem(stack,0);
    }
    public void finishCrafting()
    {
        progress = -20;
        powerStorage.fuel -= currentRecipe.recipeTime / MalumConstants.globalSpeedMultiplier;
        inventory.getStackInSlot(0).shrink(currentRecipe.inputIngredient.count);
        workInventory.clearItems();
        outputItem(currentRecipe.outputIngredient.random());
        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_FINISH, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
    
        setAndUpdateState(2);
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
    public void activateKiln()
    {
        setAndUpdateState(2);
    }
    public void damageKiln()
    {
        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_FAIL, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
        progress = 0;
        powerStorage.wipe();
        Vector3d pos = dumpPos();
        inventory.dumpItems(world, pos);
        sideInventory.dumpItems(world, pos);
        outputInventory.dumpItems(world, pos);
        workInventory.clearItems();
        setAndUpdateState(1);
    }
    public void repairKiln()
    {
        world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_REPAIR, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
        setAndUpdateState(0);
    }
    
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 1.25, direction.getZOffset());
        return new Vector3d(0.5 + directionVector.getX() * 0.25, directionVector.getY(), 0.5 + directionVector.getZ() * 0.25);
    }
}