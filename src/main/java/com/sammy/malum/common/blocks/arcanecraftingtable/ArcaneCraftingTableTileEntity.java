package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumTileEntities;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class ArcaneCraftingTableTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity
{
    public ArcaneCraftingTableTileEntity()
    {
        super(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get());
        inventory = new SimpleInventory(9, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ArcaneCraftingTableTileEntity.this.markDirty();
                if (!world.isRemote)
                {
                    updateContainingBlockInfo();
                    BlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }
        };
    }
    public int progress = 0;
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        super.readData(compound);
    }
    @Override
    public void tick()
    {
        if (inventory.getStackInSlot(0).isEmpty())
        {
            return;
        }
        
        ArcaneCraftingRecipe recipe = ArcaneCraftingRecipe.getRecipe(inventory.stacks());
        if (recipe != null)
        {
            progress++;
            if (progress >= 20)
            {
                while (recipe != null)
                {
                    ItemStack stack = new ItemStack(recipe.outputItem, recipe.outputItemCount);
                    world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, stack));
                    for (int i = 0; i < inventory.nonEmptyItems(); i++)
                    {
                        inventory.getStackInSlot(i).shrink(recipe.itemStacks.get(i).getCount());
                    }
                    recipe = ArcaneCraftingRecipe.test(inventory.stacks(), recipe);
                }
                progress = 0;
            }
        }
    }
}