package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.enchantment.*;

import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

import java.util.Optional;

import static team.lodestar.wayward_attributes.tweaks.SweepAttackTweaks.BASE_SWEEP_DAMAGE;
import static team.lodestar.wayward_attributes.tweaks.SweepAttackTweaks.BASE_SWEEP_RADIUS;


public class MalumScytheItem extends LodestoneCombatItem implements IMalumEventResponder, ICustomMeleeDamageTypeItem {

    public MalumScytheItem(Tier tier, float attackDamage, float attackSpeed, float sweepingDamage, float sweepingRadius, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed - 4f,
                properties.mergeAttributes(ItemAttributeModifiers.builder()
                        .add(Attributes.SWEEPING_DAMAGE_RATIO, new AttributeModifier(BASE_SWEEP_DAMAGE, sweepingDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(WaywardAttributeTypes.SWEEPING_DAMAGE_RADIUS, new AttributeModifier(BASE_SWEEP_RADIUS, sweepingRadius, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    public static void enableScytheSweeping(SweepAttackEvent event) {
        Player attacker = event.getEntity();
        if (attacker.getMainHandItem().getItem() instanceof MalumScytheItem) {
            event.setSweeping(canSweep(attacker));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        if (EnchantmentKeys.getEnchantmentLevel(level, EnchantmentKeys.REBOUND, stack) > 0) {
            ReboundHandler.throwScythe(level, player, hand, stack);
            return InteractionResultHolder.success(stack);
        }
        if (EnchantmentKeys.getEnchantmentLevel(level, EnchantmentKeys.ASCENSION, stack) > 0) {
            AscensionHandler.triggerAscension(level, player, hand, stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, hand);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var level = attacker.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (!event.getSource().is(MalumDamageTypes.SCYTHE_MELEE)) {
            return;
        }
        var particle = MalumParticleEffectTypes.SCYTHE_SLASH.createEffect()
                .originatesFrom(attacker)
                .targets(target)
                .color(stack)
                .upwardOffset(-0.4f)
                .forwardOffset(0.8f);
        if (!canSweep(attacker)) {
            SoundPlayer.create(getScytheSound(false)).pitch(0.75f).play(attacker);
            particle.verticalSlashRotation().horizontalOffset(0.6f).spawn(serverLevel);
            return;
        }
        SoundPlayer.create(getScytheSound(true)).play(attacker);
        particle.mirroredRandomly(attacker.getRandom()).spawn(serverLevel);
    }

    public Holder<SoundEvent> getScytheSound(boolean canSweep) {
        return canSweep ? MalumGearSoundEvents.SCYTHE_SWEEP : MalumGearSoundEvents.SCYTHE_CUT;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.SWEEPING_EDGE) || enchantment.is(Enchantments.LOOTING)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    public static void trySweep(LivingEntity attacker, LivingEntity target, float baseDamage) {
        var level = attacker.level();
        var sweeping = attacker.getAttribute(Attributes.SWEEPING_DAMAGE_RATIO);
        if (sweeping == null) {
            return;
        }
        float sweepingRatio = (float) sweeping.getValue();
        float damage = baseDamage * (0.5f + sweepingRatio * 0.33f);
        float radius = 1 + sweepingRatio * 0.25f;
        level.getEntities(attacker, target.getBoundingBox().inflate(radius)).forEach(e -> {
            if (e instanceof LivingEntity sweepTarget) {
                if (sweepTarget.isAlive() && sweepTarget != target) {
                    sweepTarget.hurt((DamageTypeHelper.create(level, MalumDamageTypes.SCYTHE_SWEEP, attacker)), damage);
                    sweepTarget.knockback(0.4F,
                            Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)),
                            (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    public static ScytheDamage getScytheDamage(DamageSource source, LivingEntity attacker) {
        float physicalDamage;
        float magicDamage;
        boolean isBoomerang = false;
        if (source.getDirectEntity() instanceof ScytheBoomerang scytheBoomerang) {
            physicalDamage = scytheBoomerang.damage;
            magicDamage = scytheBoomerang.magicDamage;
            isBoomerang = true;
        } else {
            physicalDamage = (float) (attacker.getAttributeValue(Attributes.ATTACK_DAMAGE));
            magicDamage = (float) (attacker.getAttributeValue(WaywardAttributeTypes.MAGIC_DAMAGE));
        }
        return new ScytheDamage(physicalDamage, magicDamage, isBoomerang);
    }

    public static boolean canSweep(LivingEntity attacker) {
        return !isEnhanced(attacker) && !CurioHelper.hasCurioEquipped(attacker, MalumContent.Gear.NECKLACE_OF_THE_HIDDEN_BLADE.get());
    }

    public static boolean isEnhanced(LivingEntity attacker) {
        return CurioHelper.hasCurioEquipped(attacker, MalumContent.Gear.NECKLACE_OF_THE_NARROW_EDGE.get());
    }

    @Override
    public ResourceKey<DamageType> getDirectDamageType(Player player, ItemStack weapon) {
        return MalumDamageTypes.SCYTHE_MELEE;
    }

    @Override
    public ResourceKey<DamageType> getSweepingDamageType(Player player, ItemStack weapon) {
        return MalumDamageTypes.SCYTHE_SWEEP;
    }

    public record ScytheDamage(float physicalDamage, float magicDamage, boolean isBoomerang) {
    }
}