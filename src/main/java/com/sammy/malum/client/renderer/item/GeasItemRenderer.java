package com.sammy.malum.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.*;
import com.sammy.malum.client.screen.codex.helper.CodexRenderHelper;
import com.sammy.malum.common.data.component.GeasDataComponent;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GeasItemRenderer extends BlockEntityWithoutLevelRenderer {

    public GeasItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        if (ctx.equals(ItemDisplayContext.GUI)) {
            if (stack.has(MalumDataComponents.GEAS_EFFECT)) {
                GeasDataComponent.getStoredGeasEffect(stack).ifPresent(c -> {
                    var geas = c.geasEffectType();
                    poseStack.popPose();
                    poseStack.pushPose();
                    poseStack.mulPose(Axis.ZP.rotation(3.14f));
                    poseStack.mulPose(Axis.YN.rotation(3.14f));
                    poseStack.translate(-.5, -.5, -.5);
                    poseStack.scale(0.0625f, 0.0625f, 0.0625f);
                    CodexRenderHelper.renderGeasIcon(geas.getIcon(), poseStack, geas, 0, 0);
                });
            }
        }
    }
}
