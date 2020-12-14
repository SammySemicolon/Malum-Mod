package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.recipes.ArcaneCraftingRecipe;
import com.sammy.malum.core.systems.spirits.block.SimpleInventorySpiritRequestTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class ArcaneCraftingTableTileEntity extends SimpleInventorySpiritRequestTileEntity implements ITickableTileEntity
{
    public ArcaneCraftingTableTileEntity()
    {
        super(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get());
        inventory = new SimpleInventory(9, 1)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ArcaneCraftingTableTileEntity.this.markDirty();
                updateContainingBlockInfo();
                updateState(world.getBlockState(pos), world, pos);
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
            inventory.clearItems();
            output(recipe.outputItem, recipe.outputItemCount);
            if (recipe.hasSecondOutput)
            {
                output(recipe.secondOutputItem, recipe.secondOutputItemCount);
            }
        }
    }
    public void output(Item item, int count)
    {
        ItemEntity entity = new ItemEntity(world,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f, new ItemStack(item, count));
        world.addEntity(entity);
    }
}