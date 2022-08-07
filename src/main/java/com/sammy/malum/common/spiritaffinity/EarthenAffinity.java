package com.sammy.malum.common.spiritaffinity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import team.lodestar.lodestone.setup.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EarthenAffinity extends MalumSpiritAffinity {
    public EarthenAffinity() {
        super(SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    public static void recoverHeartOfStone(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
            AttributeInstance cap = player.getAttribute(AttributeRegistry.HEART_OF_STONE_CAP.get());
            if (cap != null) {
                if (c.heartOfStone < cap.getValue() && c.heartOfStoneProgress <= 0) {
                    float hungerCost = getHeartOfStoneHungerCost(player);
                    FoodData data = player.getFoodData();
                    if (data.getFoodLevel() > hungerCost) {
                        data.addExhaustion(hungerCost);
                        c.heartOfStone++;
                        c.heartOfStoneProgress = getHeartOfStoneCooldown(player);
                        if (player.level.isClientSide && !player.isCreative()) {
                            player.playSound(SoundRegistry.HEART_OF_STONE_GROW.get(), 1, Mth.nextFloat(player.getRandom(), 0.4f, 1.2f));
                        }
                    }
                } else {
                    c.heartOfStoneProgress--;
                }
                if (c.heartOfStone > cap.getValue()) {
                    c.heartOfStone = (float) cap.getValue();
                }
            }
        });
    }

    public static void consumeHeartOfStone(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        if (event.getEntityLiving() instanceof Player player) {
            if (!player.level.isClientSide) {
                MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                    if (c.heartOfStone > 0) {
                        float absorbed = Math.min(event.getAmount(), c.heartOfStone);
                        AttributeInstance strength = player.getAttribute(AttributeRegistry.HEART_OF_STONE_STRENGTH.get());
                        if (strength != null) {
                            if (strength.getValue() != 0) {
                                c.heartOfStone = (float) Math.max(0, c.heartOfStone - (absorbed / strength.getValue()));
                            } else {
                                c.heartOfStone = 0;
                            }
                            c.heartOfStoneProgress = (float) (getHeartOfStoneCooldown(player) * 2);
                            player.level.playSound(null, player.blockPosition(), SoundRegistry.HEART_OF_STONE_HIT.get(), SoundSource.PLAYERS, 1, Mth.nextFloat(player.getRandom(), 1.5f, 2f));
                            event.setAmount(event.getAmount() - absorbed);
                            MalumPlayerDataCapability.syncTrackingAndSelf(player);
                        }
                    }
                });
            }
        }
    }

    public static int getHeartOfStoneHungerCost(Player player) {
        return (int) (CommonConfig.HEART_OF_STONE_COST.getConfigValue() * player.getAttributeValue(AttributeRegistry.HEART_OF_STONE_COST.get()));
    }

    public static int getHeartOfStoneCooldown(Player player) {
        return (int) (CommonConfig.HEART_OF_STONE_RATE.getConfigValue() * Math.exp(-0.2 * player.getAttributeValue(AttributeRegistry.HEART_OF_STONE_RECOVERY_SPEED.get())));
    }

    public static class ClientOnly {
        private static final ResourceLocation ICONS_TEXTURE = MalumMod.malumPath("textures/gui/icons.png");

        public static void renderHeartOfStone(RenderGameOverlayEvent.Post event) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && !player.isCreative() && !player.isSpectator()) {
                MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                    PoseStack poseStack = event.getMatrixStack();

                    float absorb = Mth.ceil(player.getAbsorptionAmount());
                    float maxHealth = (float) player.getAttribute(Attributes.MAX_HEALTH).getValue();

                    int left = event.getWindow().getGuiScaledWidth() / 2 - 91;
                    int top = event.getWindow().getGuiScaledHeight() - ((ForgeIngameGui) Minecraft.getInstance().gui).left_height;

                    int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    poseStack.pushPose();
                    RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                    ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                    shaderInstance.safeGetUniform("YFrequency").set(35f);
                    shaderInstance.safeGetUniform("XFrequency").set(25f);
                    shaderInstance.safeGetUniform("Speed").set(1000f);
                    shaderInstance.safeGetUniform("Intensity").set(500f);
                    VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                            .setPosColorTexLightmapDefaultFormat()
                            .setShader(() -> shaderInstance);
                    int size = 13;
                    for (int i = 0; i < Math.ceil(c.heartOfStone / 3f); i++) {
                        int row = (int) (Math.ceil(i) / 10f);
                        int x = left + i % 10 * 8;
                        int y = top - row * 3 + rowHeight * 2 - 5;
                        int progress = (int) Math.min(3, c.heartOfStone - i * 3);
                        int xTextureOffset = 1 + (3 - progress) * 15;

                        shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(xTextureOffset / 256f, (xTextureOffset + 12) / 256f, 1 / 256f, 12 / 256f));
                        shaderInstance.safeGetUniform("TimeOffset").set(i * 250f);

                        builder.setPositionWithWidth(x - 2, y - 2, size, size)
                                .setUVWithWidth(xTextureOffset, 1, size, size, 256f)
                                .draw(poseStack);
                    }
                    shaderInstance.setUniformDefaults();
                    poseStack.popPose();
                });
            }
        }
    }
}