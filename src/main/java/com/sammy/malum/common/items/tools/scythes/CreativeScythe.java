package com.sammy.malum.common.items.tools.scythes;

import com.sammy.malum.core.systems.essences.EssenceHelper;
import com.sammy.malum.core.systems.essences.EssenceHolderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;

public class CreativeScythe extends ScytheItem
{
    public CreativeScythe()
    {
        super(ItemTier.WOOD, 9996, 999.2f, new Item.Properties().defaultMaxDamage(-1).isImmuneToFire());
    }
    
    @Override
    public void harvest(LivingEntity target, PlayerEntity attacker, ItemStack stack)
    {
        EssenceHelper.harvestEssence(target, attacker, 9999f);
    }
}
