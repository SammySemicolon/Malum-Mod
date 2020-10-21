package com.sammy.malum.blocks.machines.ignisfurnace;

import com.sammy.malum.blocks.utility.BasicTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.minecraft.item.crafting.IRecipeType.SMELTING;

public class IgnisFurnaceTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public IgnisFurnaceTileEntity()
    {
        super(ModTileEntities.ignis_furnace_tile_entity);
    }
    public int burnProgress;
    
    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        
        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
        @Override
        protected void onContentsChanged(int slot)
        {
            IgnisFurnaceTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
    public final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putInt("burnProgress", burnProgress);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnProgress = compound.getInt("burnProgress");
    }
    
    
    @Override
    public void tick()
    {
        burnProgress++;
        if (burnProgress > 200)
        {
            ItemStack inputItem = inventory.getStackInSlot(0);
            List<FurnaceRecipe> recipes = world.getRecipeManager().getRecipesForType(SMELTING).stream().filter(c -> c.getIngredients().get(0).test(inputItem)).collect(Collectors.toList());
            Optional<FurnaceRecipe> recipeOptional = recipes.stream().findFirst();
            if (recipeOptional.isPresent())
            {
                FurnaceRecipe furnaceRecipe = recipeOptional.get();
                Ingredient ingredient = furnaceRecipe.getIngredients().get(0);
                if (ingredient.test(inputItem))
                {
                    output(furnaceRecipe.getRecipeOutput().copy(),pos);
                }
                burnProgress = 0;
            }
        }
    }
}