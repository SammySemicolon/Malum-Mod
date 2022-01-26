package com.sammy.malum.common.spiritaffinity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector4f;
import com.sammy.malum.common.capability.PlayerDataCapability;
import com.sammy.malum.common.packets.SyncPlayerCapabilityDataPacket;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.AttributeRegistry;
import com.sammy.malum.core.registry.DamageSourceRegistry;
import com.sammy.malum.core.registry.SoundRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.rendering.Shaders;
import com.sammy.malum.core.systems.spirit.MalumSpiritAffinity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.network.PacketDistributor;

import static com.sammy.malum.core.registry.PacketRegistry.INSTANCE;

public class ArcaneAffinity extends MalumSpiritAffinity {
    public ArcaneAffinity() {
        super(SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    public static void recoverSoulWard(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        PlayerDataCapability.getCapability(player).ifPresent(c -> {
            double cap = player.getAttributeValue(AttributeRegistry.SOUL_WARD_CAP);
            if (c.soulWard < cap && c.soulWardProgress <= 0) {
                c.soulWard++;
                if (player.level.isClientSide && !player.isCreative()) {
                    player.playSound(c.soulWard >= cap ? SoundRegistry.SOUL_WARD_CHARGE : SoundRegistry.SOUL_WARD_GROW, 1, Mth.nextFloat(player.getRandom(), 0.6f, 1.4f));
                }
                c.soulWardProgress = getSoulWardCooldown(player);
            }
            else
            {
                c.soulWardProgress--;
            }
            if (c.soulWard > cap)
            {
                c.soulWard = (float) cap;
            }
        });
    }
    public static void consumeSoulWard(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof Player player)
        {
            PlayerDataCapability.getCapability(player).ifPresent(c -> {
                if (c.soulWard > 0)
                {
                    DamageSource source = event.getSource();
                    float amount = event.getAmount();
                    double multiplier = source.isMagic() ? CommonConfig.SOUL_WARD_MAGIC.get() : CommonConfig.SOUL_WARD_PHYSICAL.get();
                    float result = (float) (amount * multiplier);
                    float absorbed = amount - result;
                    double strength = player.getAttributeValue(AttributeRegistry.SOUL_WARD_STRENGTH);
                    if (strength != 0) {
                        c.soulWard -= absorbed / strength;
                    }
                    else {
                        c.soulWard = 0;
                    }
                    c.soulWardProgress = (float) (getSoulWardCooldown(player)*player.getAttributeValue(AttributeRegistry.SOUL_WARD_DAMAGE_PENALTY));
                    if (player instanceof ServerPlayer serverPlayer) {
                        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new SyncPlayerCapabilityDataPacket(c.serializeNBT()));
                    }
                    player.level.playSound(null, player.blockPosition(), SoundRegistry.SOUL_WARD_HIT, SoundSource.PLAYERS,1, Mth.nextFloat(player.getRandom(), 1.5f, 2f));
                    event.setAmount(result);
                    if (source.getEntity() != null) {
                        if (ItemHelper.hasCurioEquipped(player, ItemRegistry.MAGEBANE_BELT)) {
                            if (source instanceof EntityDamageSource entityDamageSource) {
                                if (entityDamageSource.isThorns()) {
                                    return;
                                }
                            }
                            source.getEntity().hurt(DamageSourceRegistry.causeMagebaneDamage(player), absorbed+2);
                        }
                    }
                }
            });
        }
    }
    public static int getSoulWardCooldown(Player player)
    {
        return (int) (CommonConfig.SOUL_WARD_RATE.get() * Math.exp(-0.15*player.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_SPEED)));
    }
    public static class ClientOnly {
        private static final ResourceLocation ICONS_TEXTURE = DataHelper.prefix("textures/gui/icons.png");
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

                    if (armor == 0)
                    {
                        top += 4;
                    }
                    int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    poseStack.pushPose();
                    RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
                    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
                    ShaderInstance shaderInstance = Shaders.distortedTexture.getInstance().get();
                    shaderInstance.safeGetUniform("YFrequency").set(15f);
                    shaderInstance.safeGetUniform("XFrequency").set(15f);
                    shaderInstance.safeGetUniform("Speed").set(550f);
                    shaderInstance.safeGetUniform("Intensity").set(600f);
                    for (int i = 0; i < Math.ceil(c.soulWard/3f); i++) {
                        int row = (int) (Math.ceil(i) / 10f);
                        int x = left + i % 10 * 8;
                        int y = top - row*4 + rowHeight * 2 - 15;
                        int progress = Math.min(3, (int)c.soulWard-i*3);
                        int xTextureOffset = 1 + (3-progress)*15;

                        shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(xTextureOffset/256f, (xTextureOffset+12)/256f, 16/256f, 28/256f));
                        shaderInstance.safeGetUniform("TimeOffset").set(i*150f);

                        RenderUtilities.blit(poseStack, Shaders.distortedTexture, x-2, y-2, 13, 13,xTextureOffset/256f, 16/256f, 13/256f, 13/256f);
                    }
                    poseStack.popPose();
                });
            }
        }
    }
}