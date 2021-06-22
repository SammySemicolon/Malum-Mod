package com.sammy.malum.common.blocks.itemfocus;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.ActivatorRite;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import com.sammy.malum.core.modcontent.MalumTotemRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;


public class ItemFocusTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity, ActivatorRite.IAssembled
{
    public int progress;
    public int spin;
    public MalumTotemRecipes.MalumTotemRecipe recipe;

    public ItemFocusTileEntity()
    {
        super(MalumTileEntities.ITEM_FOCUS_TILE_ENTITY.get());
        inventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemFocusTileEntity.this.markDirty();
                updateContainingBlockInfo();
                recipe = MalumTotemRecipes.getRecipe(stacks());
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("progress", progress);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        super.readData(compound);
        recipe = MalumTotemRecipes.getRecipe(inventory.stacks());
    }

    public static Vector3d itemOffset(ItemFocusTileEntity tileEntity, int slot)
    {
        int nonEmpty = tileEntity.inventory.nonEmptyItems();
        if (nonEmpty == 1)
        {
            return new Vector3d(0.5, 1.5, 0.5);
        }
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f, 1.5f, 0.5f), 0.5f, slot, nonEmpty, (long) tileEntity.spin, 360);
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }

    public void passiveParticles()
    {
        spin += 1;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritItem)
            {
                Vector3d offset = itemOffset(this, i);
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritItem spiritSplinterItem = (SpiritItem) item.getItem();
                Color color = spiritSplinterItem.type.color;
                SpiritHelper.spiritParticles(world, x, y, z, color);
            }
        }
    }

    @Override
    public void assemble(MalumSpiritType assemblyType)
    {
        MalumTotemRecipes.MalumTotemRecipe recipe = MalumTotemRecipes.getRecipe(inventory.stacks());
        if (recipe != null)
        {
            if (assemblyType.equals(recipe.assemblyType))
            {
                do
                {
                    ItemEntity entity = new ItemEntity(world, getPos().getX() + 0.5f, getPos().getY() + 1.25f, getPos().getZ() + 0.5f, recipe.outputIngredient.getItem());
                    world.addEntity(entity);

                    for (ItemIngredient ingredient : recipe.itemIngredients)
                    {
                        for (int i = 0; i < inventory.slotCount; i++)
                        {
                            ItemStack stack = inventory.getStackInSlot(i);
                            if (ingredient.matches(stack))
                            {
                                stack.shrink(ingredient.count);
                                break;
                            }
                        }
                    }
                }
                while (recipe.matches(inventory.stacks()));
            }
        }
    }
}