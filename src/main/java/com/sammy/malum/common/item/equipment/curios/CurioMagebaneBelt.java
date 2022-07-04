package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.helpers.CurioHelper;
import com.sammy.ortus.setup.OrtusAttributeRegistry;
import com.sammy.ortus.setup.OrtusScreenParticleRegistry;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.ortus.systems.rendering.particle.screen.emitter.ItemParticleEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.awt.*;
import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioMagebaneBelt(Properties builder) {
        super(builder);
    }

    @Override
    public void soulwardDamageAbsorb(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack) {
        DamageSource source = event.getSource();
        if (source.getEntity() != null) {
            if (source instanceof EntityDamageSource entityDamageSource) {
                if (entityDamageSource.isThorns()) {
                    return;
                }
                source.getEntity().hurt(DamageSourceRegistry.causeMagebaneDamage(wardedEntity), 6);
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SOUL_WARD_CAP.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Soul Ward Capacity", 3f, AttributeModifier.Operation.ADDITION));
        map.put(AttributeRegistry.SOUL_WARD_RECOVERY_SPEED.get(), new AttributeModifier(uuids.computeIfAbsent(1, (i) -> UUID.randomUUID()), "Soul Ward Recovery Rate", 1f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}