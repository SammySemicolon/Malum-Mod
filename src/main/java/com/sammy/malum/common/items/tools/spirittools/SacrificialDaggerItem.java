package com.sammy.malum.common.items.tools.spirittools;

import com.sammy.malum.common.items.tools.ModCombatItem;
import com.sammy.malum.common.items.tools.ModSwordItem;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class SacrificialDaggerItem extends ModCombatItem implements ISpiritTool
{
    public SacrificialDaggerItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage+3, attackSpeed-2.4f, properties);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        attacker.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SCYTHE_STRIKE, attacker.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);

        return super.hitEntity(stack, target, attacker);
    }
}
