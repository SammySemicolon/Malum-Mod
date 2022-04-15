package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.item.tools.ModSwordItem;
import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.damage.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.systems.easing.Easing;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.systems.rendering.particle.ParticleBuilders;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;

import static com.sammy.malum.core.setup.server.PacketRegistry.INSTANCE;

public class TyrvingItem extends ModSwordItem implements IEventResponderItem, ItemParticleEmitter {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagic()) {
            return;
        }
        if (attacker.level instanceof ServerLevel) {
            float spiritCount = SpiritHelper.getEntitySpiritCount(target) * 2f;
            if (target instanceof Player) {
                spiritCount = 4;
            }

            if (target.isAlive()) {
                target.invulnerableTime = 0;
                target.hurt(DamageSourceRegistry.causeVoodooDamage(attacker), spiritCount);
            }
            attacker.level.playSound(null, target.blockPosition(), SoundRegistry.VOID_SLASH.get(), SoundSource.PLAYERS, 1, 1f + target.level.random.nextFloat() * 0.25f);
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), new MagicParticlePacket(SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ()));
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }


    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + Minecraft.getInstance().timer.partialTick;
        Color firstColor = SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR;
        Color secondColor = ColorHelper.darker(SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR, 2);
        ParticleBuilders.create(ScreenParticleRegistry.STAR)
                .setAlpha(0.035f, 0f)
                .setLifetime(8)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.15f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, -2, 2)
                .repeat(x, y, 1)
                .setScale((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.15f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1)
                .setScale((float) (0.9f - Math.sin(gameTime * 0.1f) * 0.175f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.8f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);

        gameTime += 31.4f;
        ParticleBuilders.create(ScreenParticleRegistry.SPARKLE)
                .setAlpha(0.035f, 0f)
                .setLifetime(8)
                .setScale((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCurveMultiplier(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, 3, -3)
                .repeat(x, y, 1)
                .setScale((float) (0.85f - Math.sin(gameTime * 0.075f) * 0.15f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1)
                .setScale((float) (0.95f - Math.sin(gameTime * 0.1f) * 0.175f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.8f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}