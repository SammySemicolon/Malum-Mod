package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.entity.spirit.SoulEntity;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import com.sammy.ortus.helpers.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public class SoulVialItem extends BlockItem implements ISoulContainerItem {

    public SoulVialItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        if (pStack.hasTag() && pStack.getTag().contains(MalumEntitySpiritData.SOUL_DATA)) {
            return "item.malum.filled_soul_vial";
        }
        return super.getDescriptionId(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            CompoundTag tag = pStack.getTag();
            if (tag.contains(MalumEntitySpiritData.SOUL_DATA)) {
                MalumEntitySpiritData data = MalumEntitySpiritData.load(tag);
                pTooltipComponents.add(new TranslatableComponent("malum.spirit.description.stored_soul").withStyle(ChatFormatting.GRAY));
                pTooltipComponents.addAll(data.createTooltip());
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel instanceof ServerLevel) {
            return fetchSoul(pPlayer, pUsedHand);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getPlayer() != null) {
            InteractionResult result = fetchSoul(pContext.getPlayer(), pContext.getHand()).getResult();
            if (result.equals(InteractionResult.SUCCESS)) {
                return result;
            }
        }
        return super.useOn(pContext);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        ItemStack otherStack = pContext.getPlayer().getItemInHand(pContext.getHand().equals(MAIN_HAND) ? OFF_HAND : MAIN_HAND);
        if (otherStack.getItem() instanceof SoulStaveItem) {
            return false;
        }
        return super.canPlace(pContext, pState);
    }

    @Override
    public InteractionResultHolder<ItemStack> interactWithSoul(Player pPlayer, InteractionHand pHand, SoulEntity soul) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (!soul.spiritData.equals(MalumEntitySpiritData.EMPTY) && (!stack.hasTag() || (stack.hasTag() && !stack.getOrCreateTag().contains(MalumEntitySpiritData.SOUL_DATA)))) {
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