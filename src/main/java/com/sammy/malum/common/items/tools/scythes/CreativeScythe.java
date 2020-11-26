package com.sammy.malum.common.items.tools.scythes;

import com.sammy.malum.core.systems.essences.EssenceHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;

public class CreativeScythe extends ScytheItem
{
    public CreativeScythe()
    {
        super(ItemTier.WOOD, 9996, 999.2f, new Item.Properties().defaultMaxDamage(-1).isImmuneToFire());
    }
}
