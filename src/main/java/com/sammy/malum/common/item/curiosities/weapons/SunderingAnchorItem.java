package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.*;
import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.attack.sundering_anchor.SunderingAnchorSlashParticleEffect;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.server.level.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.modules.toolkit.worldevent.WorldEventHandler;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.WaywardTags;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;


import java.util.*;

public class SunderingAnchorItem extends LodestoneCombatItem implements IMalumEventResponderBody, ISpiritAffiliatedItem {

    public static final List<SpiritLike> SPIRITS = List.of(MalumSpiritTypes.INFERNAL_SPIRIT, MalumSpiritTypes.SACRED_SPIRIT, MalumSpiritTypes.AQUEOUS_SPIRIT, MalumSpiritTypes.EARTHEN_SPIRIT);

    public static SpiritLike getSunderingAnchorSpirit() {
        return SPIRITS.get(MalumMod.RANDOM.nextInt(SPIRITS.size()-1));
    }

    public SunderingAnchorItem(Tier tier, float magicDamage, LodestoneItemProperties properties) {
        super(tier, -2f, -2f, properties
                .component(DataComponents.TOOL, createToolProperties(tier, MalumTags.Blocks.FD_MINEABLE_WITH_KNIFE))
                .mergeAttributes(
                        ItemAttributeModifiers.builder()
                                .add(WaywardAttributeTypes.MAGIC_DAMAGE, new AttributeModifier(WaywardAttributeTypes.BASE_MAGIC_DAMAGE, magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                                .build()));
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("sundering_anchor_damage_split"));
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("sundering_anchor_hatred"));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.BREACH)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public SpiritLike getDefiningSpiritType(ItemStack stack) {
        return getSunderingAnchorSpirit();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var stack = player.getItemInHand(usedHand);
        if (EntityHelper.pick(player) instanceof BlockHitResult blockHitResult) {
            if (level.getBlockState(blockHitResult.getBlockPos()).is(MalumTags.Blocks.SUNDERING_ANCHOR_PLEASE_BE_A_KNIFE)) {
                return InteractionResultHolder.pass(stack);
            }
        }
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(stack);
        }
        if (player instanceof ServerPlayer serverPlayer) {
            int slot = usedHand == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
            float physicalDamage = (float) player.getAttributes().getValue(Attributes.ATTACK_DAMAGE);
            float magicDamage = (float) player.getAttributes().getValue(WaywardAttributeTypes.MAGIC_DAMAGE);
            Vec3 pos = getProjectileSpawnPos(player, usedHand, 0.5f, 0.5f);
            SunderingAnchorProjectile entity = new SunderingAnchorProjectile(level, pos.x, pos.y, pos.z);
            entity.setData(player, physicalDamage, magicDamage, slot);
            entity.setItem(stack);

            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 2.5f, 0F);
            level.addFreshEntity(entity);
            SoundPlayer.create(MalumGearSoundEvents.SUNDERING_ANCHOR_THROW).volume(0.5f).pitch(1.5f, 2f).play(player);
            TemporarilyDisabledItem.disable(serverPlayer, slot, MalumContent.SOUL_OF_THE_ANCHOR);
            applyCooldown(stack, player);
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.level() instanceof ServerLevel level) {
            var source = event.getSource();
            var random = level.random;

            if (source.is(WaywardTags.DamageTypeTags.IS_MAGIC)) {
                applyHatred(target);
            }
            if (source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
                int slashCount = 3 + Mth.floor(random.nextFloat() * 3);
                float splitDamage = event.getNewDamage() / slashCount;
                if (target.isAlive()) {
                    for (int i = 0; i < slashCount; i++) {
                        WorldEventHandler.addWorldEvent(level,
                                new DelayedDamageWorldEvent(target)
                                        .setAttacker(attacker)
                                        .setDamageData(0, splitDamage, i * 2)
                                        .setPhysicalDamageType(MalumDamageTypes.SUNDERING_ANCHOR_PHYSICAL_COMBO)
                                        .setMagicDamageType(MalumDamageTypes.SUNDERING_ANCHOR_MAGIC_COMBO)
                                        .setSound(MalumGearSoundEvents.SUNDERING_ANCHOR_EXTRA_SWING, 1.2f, 1.4f, 0.7f));
                    }
                }
                event.setNewDamage(splitDamage);
                SoundPlayer.create(MalumGearSoundEvents.SUNDERING_ANCHOR_SWING).volume(1f).pitch(0.8f, 1.2f).play(attacker);
                MalumParticleEffectTypes.SUNDERING_ANCHOR_SLASH.createEffect()
                        .originatesFrom(attacker)
                        .forwardOffset(1.5f)
                        .horizontalOffset(0.5f)
                        .upwardOffset(0.2f)
                        .color(MalumNetworkedParticleEffectColorData.fromSpirits(SPIRITS))
                        .customData(new SunderingAnchorSlashParticleEffect.SunderingAnchorSlashEffectData(slashCount))
                        .randomSlashRotation(random)
                        .spawn(level);
            }
        }
    }

    public static void applyCooldown(ItemStack stack, Player player) {
        applyCooldown(stack, player, 1);
    }

    public static void applyCooldown(ItemStack stack, Player player, float scalar) {
        if (!player.isCreative()) {
            int cooldown = (int) (200 * scalar);
            player.getCooldowns().addCooldown(stack.getItem(), cooldown);
        }
    }
    public static void applyHatred(LivingEntity target) {
        var hatred = MalumMobEffects.HATRED;
        var effect = target.getEffect(hatred);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(hatred, 120, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 1, 49);
            EntityHelper.extendEffect(effect, target, 60, 3000);
        }
    }
    public static Tool createToolProperties(Tier tier, TagKey<Block> blocks) {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(net.minecraft.world.level.block.Blocks.COBWEB), 15.0F),
                Tool.Rule.overrideSpeed(net.minecraft.tags.BlockTags.SWORD_EFFICIENT, 1.5F),
                Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(blocks, tier.getSpeed())), 1.0F, 2);
    }

    public Vec3 getProjectileSpawnPos(LivingEntity player, InteractionHand hand, float distance, float spread) {
        int angle = hand == InteractionHand.MAIN_HAND ? 225 : 90;
        double radians = Math.toRadians(angle - player.yHeadRot);
        return player.position().add(player.getLookAngle().scale(distance)).add(spread * Math.sin(radians), player.getBbHeight() * 0.9f, spread * Math.cos(radians));
    }
}
