package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.handlers.item_events.IEventResponderBody;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectCommonsHelper;
import team.lodestar.lodestone.modules.toolkit.item.tools.LodestoneAxeItem;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.WaywardTags;

import static com.sammy.malum.common.item.curiosities.weapons.scythe.EdgeOfDeliveranceItem.triggerMalignantCrit;

public class WeightOfWorldsItem extends LodestoneAxeItem implements IEventResponderBody {

    public WeightOfWorldsItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.BREACH)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("weight_of_worlds_crit"));
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("weight_of_worlds_kill"));
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        attacker.addEffect(new MobEffectInstance(MalumMobEffects.GRIM_CERTAINTY, 200));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!(attacker.level() instanceof ServerLevel level)) {
            return;
        }
        if (!LodestoneEnchantmentEffectCommonsHelper.isChargedAttack(attacker)) {
            return;
        }
        var source = event.getSource();
        if (!source.is(DamageTypeTags.IS_PLAYER_ATTACK) && !source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
            return;
        }
        MalumNetworkedWeaponParticleEffectType<?> particleEffectType = MalumParticleEffectTypes.SCYTHE_SLASH;
        var effectType = MalumMobEffects.GRIM_CERTAINTY;
        if (attacker.hasEffect(effectType) || level.random.nextFloat() < 0.25f) {
            if (triggerMalignantCrit(event.getContainer(), attacker, target)) {
                particleEffectType = MalumParticleEffectTypes.WEIGHT_OF_WORLDS_CRIT;
                attacker.removeEffect(effectType);
            }
        } else {
            //We want only the crit to be present in case of exterior triggers such as Inverted Heart Propagation
            //Regular swing animations are still tied to the melee attack only
            if (!source.is(DamageTypeTags.IS_PLAYER_ATTACK)) {
                return;
            }
        }
        var effectBuilder = particleEffectType.createEffect()
                .originatesFrom(attacker).targets(target)
                .verticalSlashRotation()
                .horizontalOffset(0.4f)
                .forwardOffset(0.8f);
        if (source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
            effectBuilder.tiedToTarget().horizontalOffset(0.2f).horizontalDeviation(Easing.SINE_IN_OUT.asWeighedRandom(attacker.getRandom(), -0.5f, 0.5f)).forwardOffset(-0.8f);
        }
        effectBuilder.spawn(level);
        SoundPlayer.create(MalumGearSoundEvents.WEIGHT_OF_WORLDS_CUT).volume(2f).pitch(0.75f).play(target, SoundSource.PLAYERS);
    }
}