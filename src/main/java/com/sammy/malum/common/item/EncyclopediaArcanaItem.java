package com.sammy.malum.common.item;

import com.sammy.malum.client.screen.codex.ArcanaProgressionScreen;
import com.sammy.malum.client.screen.codex.VoidProgressionScreen;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.visual_effects.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.screen.*;

public class EncyclopediaArcanaItem extends Item implements ParticleEmitterHandler.ItemParticleSupplier {

    public static final String VOID_TAG = "malum:void_touched";

    public static boolean shouldOpenVoidCodex;

    public EncyclopediaArcanaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            if (stack.getTag() == null || !stack.getTag().contains(VOID_TAG)) {
                shouldOpenVoidCodex = false;
            }
            if (shouldOpenVoidCodex) {
                VoidProgressionScreen.openCodexViaItem();
            }
            else {
                ArcanaProgressionScreen.openCodexViaItem(stack.hasTag() && stack.getTag().contains(VOID_TAG));
            }
            player.swing(hand);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        if (stack.hasTag() && stack.getTag().contains(VOID_TAG)) {
            ScreenParticleEffects.spawnVoidEncyclopediaArcanaScreenParticles(target, level, partialTick);
        }
    }
}