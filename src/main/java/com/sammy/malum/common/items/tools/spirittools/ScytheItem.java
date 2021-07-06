package com.sammy.malum.common.items.tools.spirittools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entities.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.items.tools.ModCombatItem;
import com.sammy.malum.common.items.tools.ModSwordItem;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;


public class ScytheItem extends ModCombatItem implements ISpiritTool
{
    public ScytheItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn)
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
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(MalumEntities.SCYTHE_BOOMERANG.get(), worldIn);
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
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) attacker;
            cuttingEdge(playerEntity, target, (float) playerEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        return super.hitEntity(stack, target, attacker);
    }
    
    public void cuttingEdge(PlayerEntity playerEntity, LivingEntity target, float amount)
    {
        float multiplier = 0.4f;
        float damage = 1.0F + (amount * multiplier) + (amount * EnchantmentHelper.getSweepingDamageRatio(playerEntity));
        for (LivingEntity livingentity : playerEntity.world.getEntitiesWithinAABB(LivingEntity.class, target.getBoundingBox().grow(2.0D, 0.25D, 2.0D)))
        {
            if (livingentity.isAlive())
            {
                if (livingentity != playerEntity && livingentity != target && !playerEntity.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && playerEntity.getDistanceSq(livingentity) < 27.0D)
                {
                    livingentity.applyKnockback(0.4F, MathHelper.sin(playerEntity.rotationYaw * ((float) Math.PI / 180F)), (-MathHelper.cos(playerEntity.rotationYaw * ((float) Math.PI / 180F))));
                    livingentity.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), damage);
                }
            }
        }

        playerEntity.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), MalumSounds.SCYTHE_STRIKE, playerEntity.getSoundCategory(), 1.0F, 0.9f + target.world.rand.nextFloat() * 0.2f);
        playerEntity.spawnSweepParticles();
    }
}