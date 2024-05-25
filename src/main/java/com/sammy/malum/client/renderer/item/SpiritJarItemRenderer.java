package com.sammy.malum.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpiritJarItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final SpiritJarBlockEntity jar = new SpiritJarBlockEntity(BlockPos.ZERO, BlockRegistry.SPIRIT_JAR.get().defaultBlockState());

    public SpiritJarItemRenderer() {

    }

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof SpiritJarItem) {
            if (stack.hasTag() && stack.getTag().contains("spirit")) {
                MalumSpiritType spirit = SpiritHarvestHandler.getSpiritType(stack.getTag().getString("spirit"));
                int count = stack.getTag().getInt("count");
                jar.type = spirit;
                jar.count = count;
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(jar, matrices, vertexConsumers, light, overlay);
            }
        }
    }
}
