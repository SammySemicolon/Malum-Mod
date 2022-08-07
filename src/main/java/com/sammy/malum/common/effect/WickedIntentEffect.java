package com.sammy.malum.common.effect;

import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.handlers.MalumAttributeEventHandler;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.setup.content.potion.MalumMobEffectRegistry;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;

import static com.sammy.malum.core.setup.content.item.ItemTagRegistry.GROSS_FOODS;

public class WickedIntentEffect extends MobEffect {
    public WickedIntentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "0cd21cec-758c-456b-9955-06713e732303", 8f, AttributeModifier.Operation.ADDITION);
    }


    public static void removeWickedIntent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.isMagic() || (source instanceof EntityDamageSource entityDamageSource && entityDamageSource.isThorns())) {
            return;
        }
        if (source.getEntity() instanceof LivingEntity livingEntity) {
            if (MalumScytheItem.getScytheItemStack(source, livingEntity).isEmpty()) {
                return;
            }
            MobEffectInstance effect = livingEntity.getEffect(MalumMobEffectRegistry.WICKED_INTENT.get());
            if (effect != null) {
                Level level = livingEntity.level;
                if (livingEntity instanceof Player player) {
                    if (player.getCooldowns().isOnCooldown(ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
                        return;
                    }
                    int pTicks = (effect.amplifier) > 4 ? 160 : 40;
                    player.getCooldowns().addCooldown(ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get(), pTicks);
                }
                livingEntity.removeEffect(effect.getEffect());
                level.playSound(null, livingEntity.blockPosition(), SoundRegistry.HIDDEN_BLADE_STRIKES.get(), SoundSource.PLAYERS, 2.5f, 1 + level.random.nextFloat() * 0.15f);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}