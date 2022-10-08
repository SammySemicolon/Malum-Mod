package com.sammy.malum.common.item.nitrate;

import com.sammy.malum.common.entity.nitrate.AbstractNitrateEntity;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.core.setup.content.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;
import team.lodestar.lodestone.systems.rendering.particle.screen.emitter.ItemParticleEmitter;

import java.awt.*;
import java.util.function.Function;

public class EthericNitrateItem extends AbstractNitrateItem {

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties, p -> new EthericNitrateEntity(p, p.level));
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float partialTick = Minecraft.getInstance().timer.partialTick;
        float gameTime = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = ColorHelper.brighter(EthericNitrateEntity.FIRST_COLOR, 2);
        Color secondColor = EthericNitrateEntity.SECOND_COLOR;
        double scale = 1.5f + Math.sin(gameTime * 0.1f) * 0.125f + Math.sin((gameTime - 100) * 0.05f) * -0.5f;
        ParticleBuilders.create(LodestoneScreenParticleRegistry.STAR)
                .setAlpha(0.04f, 0f)
                .setLifetime(7)
                .setScale((float) scale, 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, -1, 4)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}