package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SoulVialItem extends BlockItem implements ISoulContainerItem {

    public SoulVialItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel)
        {
            return fetchSoul(pPlayer, pUsedHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResultHolder<ItemStack> interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (!soul.spiritData.equals(MalumEntitySpiritData.EMPTY) && (!stack.hasTag() || (stack.hasTag() && !stack.getOrCreateTag().contains(MalumEntitySpiritData.NBT))))
        {
            ItemStack split = stack.split(1);
            soul.spiritData.saveTo(split.getOrCreateTag());
            soul.discard();
            ItemHelper.giveItemToEntity(split, pPlayer);
            pPlayer.swing(pHand, true);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }
}
