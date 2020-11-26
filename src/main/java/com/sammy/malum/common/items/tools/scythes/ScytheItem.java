package com.sammy.malum.common.items.tools.scythes;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.entities.SpiritEssenceEntity;
import com.sammy.malum.common.items.tools.ModSwordItem;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.systems.essences.EssenceHelper;
import com.sammy.malum.core.systems.events.EventSubscriberItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;

public class ScytheItem extends ModSwordItem implements EventSubscriberItem
{
    public ScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn)
    {
        super(tier, attackDamageIn, attackSpeedIn - 0.8f, builderIn);
    }
    
    @Override
    public boolean onEntityKill(ItemStack stack, PlayerEntity player, LivingEntity entity, boolean run)
    {
        if (run)
        {
            harvest(entity, player, stack);
        }
        return true;
    }
    
    public void harvest(LivingEntity target, PlayerEntity attacker, ItemStack stack)
    {
        SpiritEssenceEntity essenceEntity = new SpiritEssenceEntity(MalumEntities.SPIRIT_ESSENCE.get(), attacker.world);
        essenceEntity.multiplier = 0.5f;
        essenceEntity.spirits.add(Pair.of(EssenceHelper.identifier(target), 1));
        essenceEntity.ownerUUID = attacker.getUniqueID();
        essenceEntity.setPosition(target.getPositionVec().x, target.getPositionVec().y + target.getHeight() / 2f, target.getPositionVec().z);
        attacker.world.addEntity(essenceEntity);
    }
}