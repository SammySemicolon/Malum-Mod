package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;

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
    public void tick()
    {
        if (inventory.getStackInSlot(0).isEmpty())
        {
            return;
        }
        
        ArcaneCraftingRecipe recipe = ArcaneCraftingRecipe.getRecipe(inventory.stacks());
        if (recipe != null)
        {
            if (progress == 28)
            {
                ItemStack stack = new ItemStack(recipe.outputItem, recipe.outputItemCount);
                world.addEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 1.25f, pos.getZ() + 0.5f, stack));
                for (int i = 0; i < recipe.itemStacks.size(); i++)
                {
                    int finalI = i;
                    ItemStack shrinkStack = inventory.stacks().stream().filter(s -> s.getItem().equals(recipe.itemStacks.get(finalI).getItem())).findFirst().get();
                    shrinkStack.shrink(recipe.itemStacks.stream().filter(s -> s.getItem().equals(recipe.itemStacks.get(finalI).getItem())).findFirst().get().getCount());
                }
                world.playSound(null, pos, MalumSounds.ARCANE_CRAFT_FINISH, SoundCategory.BLOCKS, 1, 1.5f+ MalumMod.RANDOM.nextFloat() * 0.2f);
    
                progress = 8;
            }
            else
            {
                if (progress == 0)
                {
                    world.playSound(null, pos, MalumSounds.ARCANE_CRAFT_START, SoundCategory.BLOCKS, 1, 1);
                }
                progress++;
            }
        }
        else if (progress > 0)
        {
            progress--;
        }
    }
}