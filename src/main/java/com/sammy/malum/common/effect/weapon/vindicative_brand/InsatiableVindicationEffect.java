package com.sammy.malum.common.effect.weapon.vindictive_brand;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;

public class InsatiableVindicationEffect extends MobEffect {

    public InsatiableVindicationEffect() {
        super(MobEffectCategory.BENEFICIAL, 14470143);
        var id = MalumMod.malumPath("insatiable_vindication");
        addAttributeModifier(Attributes.MOVEMENT_SPEED, id, 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, id, 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ARMOR, id, 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, id, 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    }

}