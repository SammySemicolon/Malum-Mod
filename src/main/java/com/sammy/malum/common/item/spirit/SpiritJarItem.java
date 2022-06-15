package com.sammy.malum.common.item.spirit;

import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if (pStack.hasTag() && pStack.getTag().contains("spirit")) {
            MalumSpiritType spirit = SpiritHelper.getSpiritType(pStack.getTag().getString("spirit"));
            int count = pStack.getTag().getInt("count");
            pTooltip.add(new TranslatableComponent("malum.spirit.description.stored_spirit").withStyle(ChatFormatting.GRAY));
            pTooltip.add(spirit.getCountComponent(count));
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> new SpiritJarItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer.get();
            }
        });
    }
}