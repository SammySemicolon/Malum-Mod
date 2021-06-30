package com.sammy.malum.common.items.tools.spirittools;

import com.sammy.malum.common.items.tools.ModSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class TyrvingItem extends ModSwordItem
{
    public final float armorCrushing;
    public final Supplier<SoundEvent> soundEvent;
    public TyrvingItem(IItemTier material, float armorCrushing, int attackDamage, float attackSpeed, Supplier<SoundEvent> soundEvent, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.armorCrushing = armorCrushing;
        this.soundEvent = soundEvent;
    }
}
