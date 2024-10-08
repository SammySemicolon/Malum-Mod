package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class MagicScytheItem extends MalumScytheItem implements ISpiritAffiliatedItem {

    public final float magicDamage;

    public MagicScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(), new AttributeModifier(LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    @Override
    public MalumSpiritType getDefiningSpiritType() {
        return SpiritTypeRegistry.WICKED_SPIRIT;
    }
}
