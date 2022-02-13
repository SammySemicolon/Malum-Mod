package com.sammy.malum.common.item.tools.magic;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.common.item.tools.ModSwordItem;
import com.sammy.malum.core.setup.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;

import java.util.UUID;

public class MagicSwordItem extends ModSwordItem {
    public final float magicDamage;

    public MagicSwordItem(Tier material, int attackDamage, float attackSpeed, float magicDamage, Properties properties) {
        super(material, attackDamage, attackSpeed, properties.durability(material.getUses()));
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(AttributeRegistry.MAGIC_DAMAGE.get(), new AttributeModifier(UUID.fromString("d1d17de1-c944-4cdb-971e-f9c4ce260cfe"), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }
}
