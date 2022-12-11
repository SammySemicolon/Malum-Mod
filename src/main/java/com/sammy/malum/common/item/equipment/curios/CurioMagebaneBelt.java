package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioMagebaneBelt(Properties builder) {
        super(builder);
    }

    @Override
    public void onSoulwardAbsorbDamage(LivingHurtEvent event, LivingEntity wardedEntity, ItemStack stack, float soulwardLost, float damageAbsorbed) {
        DamageSource source = event.getSource();
        if (source.getEntity() != null) {
            if (source instanceof EntityDamageSource entityDamageSource) {
                if (!entityDamageSource.isThorns()) {
                    source.getEntity().hurt(DamageSourceRegistry.causeMagebaneDamage(wardedEntity), damageAbsorbed);
                }
            }
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.SOUL_WARD_RECOVERY_SPEED.get(), new AttributeModifier(uuids.computeIfAbsent(0, (i) -> UUID.randomUUID()), "Soul Ward Recovery Rate", 3f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate() {
        return true;
    }
}