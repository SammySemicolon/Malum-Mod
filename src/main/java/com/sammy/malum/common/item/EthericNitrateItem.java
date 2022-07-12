package com.sammy.malum.common.item;

import com.sammy.malum.common.entity.nitrate.EthericNoduleEntity;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.setup.OrtusScreenParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.ParticleRenderTypes;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.ortus.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
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

import java.awt.*;
import java.util.Random;

import static net.minecraft.util.Mth.nextFloat;

public class EthericNitrateItem extends Item implements ItemParticleEmitter {

    public static final Color FIRST_COLOR = SpiritTypeRegistry.INFERNAL_SPIRIT.getColor();
    public static final Color SECOND_COLOR = new Color(225, 54, 46);

    public EthericNitrateItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isClientSide) {
            EthericNoduleEntity bombEntity = new EthericNoduleEntity(playerIn, worldIn);
            int angle = handIn == InteractionHand.MAIN_HAND ? 225 : 90;
            Vec3 pos = playerIn.position().add(playerIn.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - playerIn.yHeadRot)), playerIn.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - playerIn.yHeadRot)));
            float pitch = -10.0F;
            bombEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), pitch, 1.25F, 0.9F);
            bombEntity.setPos(pos);
            worldIn.addFreshEntity(bombEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }


    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float gameTime = level.getGameTime() + Minecraft.getInstance().timer.partialTick;
        Color firstColor = FIRST_COLOR.brighter();
        Color secondColor = SECOND_COLOR;
        double scale = 1.5f + Math.sin(gameTime * 0.1f) * 0.125f + Math.sin((gameTime - 100) * 0.05f) * -0.25f;
        ParticleBuilders.create(OrtusScreenParticleRegistry.STAR)
                .setAlpha(0.06f, 0f)
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
                .centerOnStack(stack, 1, 4)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f - 0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}