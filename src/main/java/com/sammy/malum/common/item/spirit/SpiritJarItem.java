package com.sammy.malum.common.item.spirit;

import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SpiritJarItem extends BlockItem {
    public SpiritJarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        if (pStack.hasTag() && pStack.getTag().contains("spirit")) {
            return "item.malum.filled_spirit_jar";
        }
        return super.getDescriptionId(pStack);
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        if (pStack.hasTag() && pStack.getTag().contains("spirit")) {
            MalumSpiritType spirit = SpiritHarvestHandler.getSpiritType(pStack.getTag().getString("spirit"));
            return spirit.getItemRarity();
        }
        return super.getRarity(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (pStack.hasTag() && pStack.getTag().contains("spirit")) {
            MalumSpiritType spirit = SpiritHarvestHandler.getSpiritType(pStack.getTag().getString("spirit"));
            int count = pStack.getTag().getInt("count");
            pTooltip.add(Component.translatable("malum.spirit.description.stored_spirit").withStyle(ChatFormatting.GRAY));
            pTooltip.add(spirit.getSpiritJarCounterComponent(count));
        }
    }
}
