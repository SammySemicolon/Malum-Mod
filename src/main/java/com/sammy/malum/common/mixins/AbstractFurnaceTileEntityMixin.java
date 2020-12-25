package com.sammy.malum.common.mixins;

import com.sammy.malum.MalumMod;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(AbstractFurnaceTileEntity.class)
public abstract class AbstractFurnaceTileEntityMixin extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity
{
    protected AbstractFurnaceTileEntityMixin(TileEntityType<?> typeIn)
    {
        super(typeIn);
    }
    
    @Inject(at = @At("HEAD"), method = "smelt(Lnet/minecraft/item/crafting/IRecipe;)V")
    private void smelt(CallbackInfo info, IRecipe<?> recipe)
    {
        MalumMod.LOGGER.info("homo");
    }
}