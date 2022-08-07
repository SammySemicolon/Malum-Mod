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
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import team.lodestar.lodestone.handlers.ScreenParticleHandler;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.setup.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArcaneAffinity extends MalumSpiritAffinity {
    public ArcaneAffinity() {
        super(SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    public static void recoverSoulWard(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> { //TODO: currently there are rare desync issues due to soul ward recovering separately on server & client. It'd be best if the server did it all on it's own.
            AttributeInstance cap = player.getAttribute(AttributeRegistry.SOUL_WARD_CAP.get());
            if (cap != null) {
                if (c.soulWard < cap.getValue() && c.soulWardProgress <= 0) {
                    c.soulWard++;
                    if (player.level.isClientSide && !player.isCreative()) {
                        player.playSound(c.soulWard >= cap.getValue() ? SoundRegistry.SOUL_WARD_CHARGE.get() : SoundRegistry.SOUL_WARD_GROW.get(), 1, Mth.nextFloat(player.getRandom(), 0.6f, 1.4f));
                    }
                    c.soulWardProgress = getSoulWardCooldown(player);
                } else {
                    c.soulWardProgress--;
                }
                if (c.soulWard > cap.getValue()) {
                    c.soulWard = (float) cap.getValue();
                }
            }
        });
    }

    public static void consumeSoulWard(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        if (event.getEntityLiving() instanceof Player player) {
            if (!player.level.isClientSide) {
                MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                    AttributeInstance instance = player.getAttribute(AttributeRegistry.SOUL_WARD_SHATTER_COOLDOWN.get());
                    if (instance != null) {
                        c.soulWardProgress = (float) (CommonConfig.SOUL_WARD_RATE.getConfigValue() * 6 * Math.exp(-0.15 * instance.getValue()));
                        if (c.soulWard > 0) {
                            DamageSource source = event.getSource();

                            float amount = event.getAmount();
                            float multiplier = source.isMagic() ? CommonConfig.SOUL_WARD_MAGIC.getConfigValue().floatValue() : CommonConfig.SOUL_WARD_PHYSICAL.getConfigValue().floatValue();
                            float result = amount * multiplier;
                            float absorbed = amount - result;
                            double strength = player.getAttributeValue(AttributeRegistry.SOUL_WARD_STRENGTH.get());
                            if (strength != 0) {
                                c.soulWard = (float) Math.max(0, c.soulWard - (absorbed / strength));
                            } else {
                                c.soulWard = 0;
                            }

                            player.level.playSound(null, player.blockPosition(), SoundRegistry.SOUL_WARD_HIT.get(), SoundSource.PLAYERS, 1, Mth.nextFloat(player.getRandom(), 1.5f, 2f));
                            event.setAmount(result);

                            ItemHelper.getEventResponders(player).forEach(s -> {
                                if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                                    eventItem.soulwardDamageAbsorb(event, player, s);
                                }
                            });
                            MalumPlayerDataCapability.syncTrackingAndSelf(player);
                        }
                    }
                });
            }
        }
    }


    public static int getSoulWardCooldown(Player player) {
        return getSoulWardCooldown(player.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_SPEED.get()));
    }
    public static int getSoulWardCooldown(double recoverySpeed) {
        int baseValue = CommonConfig.SOUL_WARD_RATE.getConfigValue();
        if (recoverySpeed == 0) {
            return baseValue;
        }
        float n = 0.6f;
        double exponent = 1 + (Math.pow(recoverySpeed * 0.25f + 1, 1 - n) - 1) / (1 - n);
        return (int) (baseValue * (1 / exponent));
    }

    public static class ClientOnly {
        private static final ResourceLocation DEFAULT_SOUL_WARD = MalumMod.malumPath("textures/gui/soul_ward/default.png");

        public static void renderSoulWard(ForgeIngameGui gui, PoseStack poseStack, int width, int height) {
            Minecraft minecraft = Minecraft.getInstance();
            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                LocalPlayer player = minecraft.player;
                if (!player.isCreative() && !player.isSpectator()) {
                    MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> {
                        if (c.soulWard > 0) {
                            float absorb = Mth.ceil(player.getAbsorptionAmount());
                            float maxHealth = (float) player.getAttribute(Attributes.MAX_HEALTH).getValue();
                            float armor = (float) player.getAttribute(Attributes.ARMOR).getValue();

                            int left = width / 2 - 91;
                            int top = height - gui.left_height;

                            if (armor == 0) {
                                top += 4;
                            }
                            int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                            int rowHeight = Math.max(10 - (healthRows - 2), 3);

                            poseStack.pushPose();
                            RenderSystem.setShaderTexture(0, getSoulWardTexture());
                            RenderSystem.depthMask(true);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                            shaderInstance.safeGetUniform("YFrequency").set(15f);
                            shaderInstance.safeGetUniform("XFrequency").set(15f);
                            shaderInstance.safeGetUniform("Speed").set(550f);
                            shaderInstance.safeGetUniform("Intensity").set(120f);
                            VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                                    .setPosColorTexLightmapDefaultFormat()
                                    .setShader(() -> shaderInstance);

                            int size = 13;
                            for (int i = 0; i < Math.ceil(Math.floor(c.soulWard) / 3f); i++) {
                                int row = (int) (Math.ceil(i) / 10f);
                                int x = left + i % 10 * 8;
                                int y = top - row * 4 + rowHeight * 2 - 15;
                                int progress = Math.min(3, (int) c.soulWard - i * 3);
                                int xTextureOffset = 1 + (3 - progress) * 15;

                                shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(xTextureOffset /45f, (xTextureOffset + size) / 45f, 0, 15 / 45f));
                                shaderInstance.safeGetUniform("TimeOffset").set(i * 150f);

                                builder.setPositionWithWidth(x - 2, y - 2, size, size)
                                        .setUVWithWidth(xTextureOffset, 0, size, size, 45)
                                        .draw(poseStack);

                                if (ScreenParticleHandler.canSpawnParticles) {
                                    ParticleBuilders.create(LodestoneScreenParticleRegistry.WISP)
                                            .setLifetime(20)
                                            .setColor(SpiritTypeRegistry.ARCANE_SPIRIT.getColor().brighter(), SpiritTypeRegistry.ARCANE_SPIRIT.getEndColor())
                                            .setAlphaCoefficient(0.75f)
                                            .setScale(0.2f * progress, 0f)
                                            .setAlpha(0.05f, 0)
                                            .setSpin(Minecraft.getInstance().level.random.nextFloat() * 6.28f)
                                            .setSpinOffset(Minecraft.getInstance().level.random.nextFloat() * 6.28f)
                                            .randomOffset(2)
                                            .randomMotion(0.5f, 0.5f)
                                            .addMotion(0, 0.2f)
                                            .overwriteRenderOrder(ScreenParticle.RenderOrder.BEFORE_UI)
                                            .repeat(x + 5, y + 5, 1);
                                }
                            }
                            shaderInstance.setUniformDefaults();
                            RenderSystem.depthMask(true);
                            RenderSystem.disableBlend();
                            poseStack.popPose();
                        }
                    });
                }
            }
        }
        public static ResourceLocation getSoulWardTexture() {
            return MalumMod.malumPath("textures/gui/soul_ward/default.png");
        }
    }
}