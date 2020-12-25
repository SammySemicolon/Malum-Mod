package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.recipes.TaintTransfusion;
import com.sammy.malum.core.recipes.TaintedFurnaceRecipe;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

import java.util.ArrayList;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TaintedFurnaceCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    public TaintedFurnaceCoreTileEntity()
    {
        super(MalumTileEntities.TAINTED_FURNACE_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                TaintedFurnaceCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                updateState(world.getBlockState(pos), world, pos);
            }
        };
        advancedInventory = new SimpleInventory(3, 1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                TaintedFurnaceCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    //persistent data
    public SimpleInventory inventory;
    public SimpleInventory advancedInventory;
    public int fuel;
    public int progress;
    public int advancedProgress;
    public ArrayList<BlockPos> blacklistedStands = new ArrayList<>();
    
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        advancedInventory.readData(compound, "advancedInventory");
        fuel = compound.getInt("fuel");
        progress = compound.getInt("progress");
        advancedProgress = compound.getInt("advancedProgress");
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        advancedInventory.writeData(compound, "advancedInventory");
        compound.putInt("fuel", fuel);
        compound.putInt("progress", progress);
        compound.putInt("advancedProgress", advancedProgress);
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        
        ItemStack stack = inventory.getStackInSlot(0);
        TaintedFurnaceRecipe recipe = TaintedFurnaceRecipe.getRecipe(stack);
        if (recipe != null)
        {
            if (fuel > 0)
            {
                if (world.rand.nextDouble() < 0.05D)
                {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }
                progress++;
                if (progress >= recipe.recipeTime)
                {
                    if (recipe.isAdvanced())
                    {
                        progress = recipe.recipeTime;
                        advancedProgress++;
                        if (advancedProgress >= recipe.recipeTime / 4)
                        {
                            ArrayList<BlockPos> stands = MalumHelper.itemStands(world, pos, Direction.UP, Direction.DOWN);
                            if (stands.size() == blacklistedStands.size())
                            {
                                if (advancedInventory.items().containsAll(recipe.extraItems))
                                {
                                    output(recipe);
                                }
                                else
                                {
                                    dump();
                                }
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
                                        if (!advancedInventory.items().contains(standStack.getItem()))
                                        {
                                            int firstEmpty = advancedInventory.firstEmptyItem();
                                            if (firstEmpty != -1)
                                            {
                                                if (MalumHelper.areWeOnServer(world))
                                                {
                                                    world.playSound(null, this.pos, MalumSounds.TAINTED_FURNACE_CONSUME, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
                                                }
                                                advancedInventory.setStackInSlot(firstEmpty, standStack.split(1));
                                                advancedProgress = 0;
                                                fuel--;
                                                blacklistedStands.add(pos);
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        blacklistedStands.add(pos);
                                        break;
                                    }
                                }
                            }
                        }
                        return;
                    }
                    output(recipe);
                }
            }
        }
        else
        {
            progress = 0;
            advancedProgress = 0;
            blacklistedStands.clear();
        }
    }
    
    public Vector3f behindFurnace()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3i directionVector = new Vector3i(direction.getXOffset(), 0, direction.getZOffset());
        return new Vector3f(this.pos.getX() + 0.5f - directionVector.getX(), this.pos.getY() + 1, this.pos.getZ() + 0.5f - directionVector.getZ());
    }
    
    public void dump()
    {
        ArrayList<BlockPos> stands = MalumHelper.itemStands(world, pos, Direction.UP, Direction.DOWN);
        for (BlockPos pos : stands)
        {
            if (world.getTileEntity(pos) instanceof ItemStandTileEntity)
            {
                ItemStandTileEntity standTileEntity = (ItemStandTileEntity) world.getTileEntity(pos);
                standTileEntity.inventory.dumpItems(world, new Vector3f(standTileEntity.getPos().getX() + 0.5f, standTileEntity.getPos().getY() + 0.5f, standTileEntity.getPos().getZ() + 0.5f));
            }
        }
        fuel -= MalumMod.globalCharcoalToFuelRatio;
        inventory.dumpItems(world, behindFurnace());
        advancedInventory.dumpItems(world, behindFurnace());
        blacklistedStands.clear();
        if (MalumHelper.areWeOnServer(world))
        {
            world.playSound(null, this.pos, MalumSounds.TAINTED_FURNACE_FAIL, SoundCategory.BLOCKS, 0.4f, 1.5f - world.rand.nextFloat());
            world.playSound(null, this.pos, MalumSounds.TAINTED_ROCK_BREAK, SoundCategory.BLOCKS, 0.4f, 0.5f);
            world.playSound(null, this.pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.2f, 1f);
        }
    }
    
    public void output(TaintedFurnaceRecipe recipe)
    {
        Vector3f pos = behindFurnace();
        ItemEntity entity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(recipe.outputItem, recipe.outputItemCount));
        world.addEntity(entity);
        progress = 0;
        advancedProgress = 0;
        fuel -= Math.max(recipe.recipeTime / TaintedFurnaceRecipe.globalSpeedMultiplier, 1);
        inventory.getStackInSlot(0).shrink(recipe.inputItemCount);
        advancedInventory.clearItems();
        blacklistedStands.clear();
        if (MalumHelper.areWeOnServer(world))
        {
            world.playSound(null, this.pos, MalumSounds.TAINTED_FURNACE_FINISH, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
            world.playSound(null, this.pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.2f, 1.5f);
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