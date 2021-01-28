package com.sammy.malum.common.items.tools.elementaltools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.tools.ModSwordItem;
import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class SwordOfShiftingSkies extends ModSwordItem
{
    public SwordOfShiftingSkies(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
    }
    
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        MalumHelper.giveAmplifyingEffect(MalumEffects.BLADE_OF_WIND.get(), attacker, 100, 0, 4);
        return super.hitEntity(stack, target, attacker);
    }
}
