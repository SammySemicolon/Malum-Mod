package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import team.lodestar.lodestone.helpers.ColorHelper;

public class AqueousAura extends MobEffect {
    public AqueousAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AQUEOUS_SPIRIT.getPrimaryColor()));
        addAttributeModifier(ForgeMod.ENTITY_REACH.get(), "738bd9e4-23d8-46b0-b8ba-45a2016eec74", 1f, AttributeModifier.Operation.ADDITION);
    }

    public static AABB growBoundingBox(Player player, AABB original) {
        MobEffectInstance effect = player.getEffect(MobEffectRegistry.POSEIDONS_GRASP.get());
        if (effect != null) {
            original = original.inflate((effect.amplifier + 1) * 1.5f);
        }
        return original;
    }


    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}