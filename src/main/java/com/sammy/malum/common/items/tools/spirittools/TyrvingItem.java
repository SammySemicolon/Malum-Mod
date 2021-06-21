package com.sammy.malum.common.items.tools.spirittools;

import com.sammy.malum.common.items.tools.ModSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;

public class TyrvingItem extends ModSwordItem
{
    public final float armorCrushing;
    public TyrvingItem(IItemTier material, float armorCrushing, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.armorCrushing = armorCrushing;
    }
}
