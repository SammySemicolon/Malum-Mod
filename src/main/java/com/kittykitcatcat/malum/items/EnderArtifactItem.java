package com.kittykitcatcat.malum.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import static net.minecraft.block.EnderChestBlock.field_220115_d;

public class EnderArtifactItem extends Item
{
    public EnderArtifactItem(Properties builder)
    {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.world.playSound(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 0f, true);
        playerIn.openContainer(new SimpleNamedContainerProvider((p_220114_1_, p_220114_2_, p_220114_3_) -> ChestContainer.createGeneric9X3(p_220114_1_, p_220114_2_, playerIn.getInventoryEnderChest()), field_220115_d));
        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}