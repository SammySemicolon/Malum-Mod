package com.sammy.malum.common.item.curiosities;

import com.google.common.collect.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;

public class MagicKnifeItem extends MalumKnifeItem {
    public final float magicDamage;

    public MagicKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(), new AttributeModifier(LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE), "Weapon magic damage", magicDamage, AttributeModifier.Operation.ADDITION));
        return builder;
    }
}
