package com.sammy.malum.common.item.tools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;


public class ScytheItem extends ModCombatItem implements IEventResponderItem
{
    public ScytheItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn)
    {
        super(tier, attackDamageIn+4, attackSpeedIn - 3.2f, builderIn.addToolType(ToolType.HOE, 3));
    }
    
    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.REBOUND.get(), itemstack) > 0)
        {
            if (MalumHelper.areWeOnServer(worldIn))
            {
                playerIn.setHeldItem(handIn, ItemStack.EMPTY);
                double baseDamage = playerIn.getAttributeManager().getAttributeValue(Attributes.ATTACK_DAMAGE);
                float multiplier = 1.2f;
                double damage = 1.0F + baseDamage * multiplier;
    
                int slot = handIn == Hand.OFF_HAND ? playerIn.inventory.getSizeInventory() - 1 : playerIn.inventory.currentItem;
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(worldIn);
                entity.setPosition(playerIn.getPositionVec().x, playerIn.getPositionVec().y + playerIn.getHeight() / 2f, playerIn.getPositionVec().z);
    
                entity.setData((float) damage, playerIn.getUniqueID(), slot, itemstack);
                entity.getDataManager().set(ScytheBoomerangEntity.SCYTHE, itemstack);
    
                entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0F);
                worldIn.addEntity(entity);
            }
            playerIn.addStat(Stats.ITEM_USED.get(this));
    
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (MalumHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE))
        {
            return;
        }
        float damage = 1f + event.getAmount() * EnchantmentHelper.getSweepingDamageRatio(attacker);
        target.world.getEntitiesWithinAABBExcludingEntity(attacker, target.getBoundingBox().grow(1)).forEach(e ->
        {
            if (e instanceof LivingEntity)
            {
                e.attackEntityFrom(DamageSource.causeMobDamage(attacker), damage);
                ((LivingEntity) e).applyKnockback(0.4F, MathHelper.sin(attacker.rotationYaw * ((float) Math.PI / 180F)), (-MathHelper.cos(attacker.rotationYaw * ((float) Math.PI / 180F))));
            }
        });
        if (attacker instanceof PlayerEntity)
        {
            ((PlayerEntity) attacker).spawnSweepParticles();
        }
        attacker.world.playSound(null, attacker.getPosX(), attacker.getPosY(), attacker.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, attacker.getSoundCategory(), 1,1);
    }

    @Override
    public void deathEvent(LivingEntity attacker, LivingEntity target, ItemStack stack) {
        SpiritHelper.playerSummonSpirits(target, attacker, stack);
    }
}