package com.sammy.malum.common.item.equipment.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.UUID;

public class CurioMagebaneBelt extends MalumCurioItem implements IEventResponderItem
{
    private static final UUID MAGIC_RESISTANCE = UUID.fromString("8ad20be5-cc3b-4786-bf53-1dbd969e6477");

    public CurioMagebaneBelt(Properties builder)
    {
        super(builder);
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (event.getSource().isMagic())
        {
            if (event.getSource() instanceof EntityDamageSource source)
            {
                if (source.isThorns())
                {
                    return;
                }
            }
            attacker.hurt(DamageSourceRegistry.causeVoodooDamage(attacked), 2);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack)
    {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        map.put(AttributeRegistry.MAGIC_RESISTANCE, new AttributeModifier(MAGIC_RESISTANCE, "Curio magic resistance", 2f, AttributeModifier.Operation.ADDITION));
        return map;
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }
}