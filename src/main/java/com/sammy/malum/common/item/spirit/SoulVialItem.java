package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.core.systems.item.ISoulContainerItem;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
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
                pTooltipComponents.add(Component.translatable("malum.spirit.description.stored_soul").withStyle(ChatFormatting.GRAY));
                pTooltipComponents.addAll(data.createTooltip());
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        ItemStack otherStack = pContext.getPlayer().getItemInHand(pContext.getHand().equals(MAIN_HAND) ? OFF_HAND : MAIN_HAND);
        if (otherStack.getItem() instanceof SoulStaveItem) {
            return false;
        }
        return super.canPlace(pContext, pState);
    }
}