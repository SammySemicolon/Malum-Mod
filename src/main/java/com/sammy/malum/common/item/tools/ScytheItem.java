package com.sammy.malum.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.IItemTier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.Level.Level;
import net.minecraft.Level.server.ServerLevel;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nonnull;
import java.util.UUID;


import net.minecraft.item.Item.Properties;

public class ScytheItem extends ModCombatItem implements IEventResponderItem {
    private final float magicDamageBoost;

    public ScytheItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, float magicDamageBoost, Properties builderIn) {
        super(tier, builderIn.addToolType(ToolType.HOE, 3));
        this.magicDamageBoost = magicDamageBoost;
        createAttributes(tier, attackDamageIn + 4, attackSpeedIn - 3.2f);
    }

    @Override
    public void putExtraAttributes(ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder) {
        if (magicDamageBoost != 0) {
            attributeBuilder.put(AttributeRegistry.MAGIC_PROFICIENCY, new AttributeModifier(UUID.fromString("d1d17de1-c944-4cdb-971e-f9c4ce260cfe"), "Weapon magic proficiency", magicDamageBoost, AttributeModifier.Operation.ADDITION));
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(Level LevelIn, Player playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.REBOUND.get(), itemstack) > 0) {
            if (MalumHelper.areWeOnServer(LevelIn)) {
                playerIn.setItemInHand(handIn, ItemStack.EMPTY);
                double baseDamage = playerIn.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
                float multiplier = 1.2f;
                double damage = 1.0F + baseDamage * multiplier;

                int slot = handIn == Hand.OFF_HAND ? playerIn.inventory.getContainerSize() - 1 : playerIn.inventory.selected;
                ScytheBoomerangEntity entity = new ScytheBoomerangEntity(LevelIn);
                entity.setPos(playerIn.position().x, playerIn.position().y + playerIn.getBbHeight() / 2f, playerIn.position().z);

                entity.setData((float) damage, playerIn.getUUID(), slot, itemstack);
                entity.getEntityData().set(ScytheBoomerangEntity.SCYTHE, itemstack);

                entity.shoot(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 0F);
                LevelIn.addFreshEntity(entity);
            }
            playerIn.awardStat(Stats.ITEM_USED.get(this));

            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        return super.use(LevelIn, playerIn, handIn);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player) {
            SoundEvent sound;
            if (MalumHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE)) {
                spawnSweepParticles((Player) attacker, ParticleRegistry.SCYTHE_CUT_ATTACK_PARTICLE.get());
                sound = SoundRegistry.SCYTHE_CUT;
            } else {
                spawnSweepParticles((Player) attacker, ParticleRegistry.SCYTHE_SWEEP_ATTACK_PARTICLE.get());
                sound = SoundEvents.PLAYER_ATTACK_SWEEP;
            }
            attacker.level.playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundSource(), 1, 1);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (MalumHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE)) {
            return;
        }
        float damage = event.getAmount() * (0.5f+EnchantmentHelper.getSweepingDamageRatio(attacker));
        target.level.getEntities(attacker, target.getBoundingBox().inflate(1)).forEach(e ->
        {
            if (e instanceof LivingEntity) {
                e.hurt(DamageSource.mobAttack(attacker), damage);
                ((LivingEntity) e).knockback(0.4F, MathHelper.sin(attacker.yRot * ((float) Math.PI / 180F)), (-MathHelper.cos(attacker.yRot * ((float) Math.PI / 180F))));
            }
        });
    }

    public void spawnSweepParticles(Player player, BasicParticleType type) {
        double d0 = (-MathHelper.sin(player.yRot * ((float) Math.PI / 180F)));
        double d1 = MathHelper.cos(player.yRot * ((float) Math.PI / 180F));
        if (player.level instanceof ServerLevel) {
            ((ServerLevel) player.level).sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }
}