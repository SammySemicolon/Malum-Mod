package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;

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
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                updateState(state, world, pos);
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
            if (progress == 20)
            {
                ItemStack stack = new ItemStack(recipe.outputItem, recipe.outputItemCount);
                ArrayList<ItemStack> stacks = inventory.stacks();
                while (inventory.nonEmptyItems() != 0)
                {
                    world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, stack));
                    recipe = ArcaneCraftingRecipe.test(stacks, recipe);
                    if (recipe == null)
                    {
                        for (ItemStack currentStack : stacks)
                        {
                            world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, currentStack));
                        }
                        return;
                    }
                    for (int i = 0; i < recipe.itemStacks.size(); i++)
                    {
                        int finalI = i;
                        ArcaneCraftingRecipe finalRecipe = recipe;
                        //this is incredibly dumb but it works... I'm not fixing it!
                        ItemStack shrinkStack = stacks.stream().filter(s -> s.getItem().equals(finalRecipe.itemStacks.get(finalI).getItem())).findFirst().get();
                        shrinkStack.shrink(recipe.itemStacks.stream().filter(s -> s.getItem().equals(finalRecipe.itemStacks.get(finalI).getItem())).findFirst().get().getCount());
                    }
                }
                progress = 0;
            }
            else
            {
                if (progress == 0)
                {
                    world.playSound(null, pos, MalumSounds.ARCANE_CRAFT, SoundCategory.BLOCKS, 1, 1);
                }
                progress++;
            }
        }
    }
}