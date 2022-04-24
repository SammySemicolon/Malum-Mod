package com.sammy.malum.common.item.tools.magic;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.ortus.setup.OrtusAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;

public class MagicScytheItem extends MalumScytheItem {

    public final float magicDamage;
    public MagicScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(OrtusAttributes.MAGIC_DAMAGE.get(), new AttributeModifier(OrtusAttributes.UUIDS.get(OrtusAttributes.MAGIC_DAMAGE), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }
}
