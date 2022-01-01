package com.sammy.malum.common.spiritaffinity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ArcaneAffinity extends MalumSpiritAffinity {
    public ArcaneAffinity() {
        super(SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientOnly {
        private static final ResourceLocation ICONS_TEXTURE = DataHelper.prefix("textures/gui/icons.png");

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void renderSoulWard(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !player.isCreative()) {
                PlayerDataCapability.getCapability(player).ifPresent(c -> {
                    PoseStack poseStack = event.getMatrixStack();
                    poseStack.pushPose();

                    float absorb = Mth.ceil(player.getAbsorptionAmount());
                    float maxHealth = (float) player.getAttribute(Attributes.MAX_HEALTH).getValue();
                    float armor = (float) player.getAttribute(Attributes.ARMOR).getValue();

                    int left = event.getWindow().getGuiScaledWidth() / 2 - 91;
                    int top = event.getWindow().getGuiScaledHeight() - ((ForgeIngameGui) Minecraft.getInstance().gui).left_height;

                    int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    RenderSystem.setShaderTexture(0, ICONS_TEXTURE);

                    if (armor == 0)
                    {
                        top += 10;
                    }
                    for (int i = 0; i < Math.ceil(c.soulWard/4f); i++) {
                        int x = left + i % 10 * 8;
                        int y = top + rowHeight * 2 - 22;
                        int progress = Math.min(4, c.soulWard-i*4);
                        int xTextureOffset = 1 + (4-progress)*11;
                        poseStack.pushPose();
                        minecraft.gui.blit(poseStack, x, y, xTextureOffset, 12, 9, 9);
                        poseStack.popPose();
                    }
                    poseStack.popPose();
                });
            }
        }
    }
}