package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.data.listener.spirit.EntitySpiritDropData;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.toolkit.item.tools.LodestoneSwordItem;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.modules.toolkit.worldevent.WorldEventHandler;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.WaywardTags;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponder, ISpiritAffiliatedItem {

    public TyrvingItem(Tier tier, float attackDamage, float attackSpeed, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.SWEEPING_EDGE)) {
            return false;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("soul_based_damage"));
    }

    @Override
    public SpiritLike getDefiningSpiritType(ItemStack stack) {
        return MalumSpiritTypes.WICKED_SPIRIT;
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.level() instanceof ServerLevel level) {
            if (!event.getSource().is(WaywardTags.DamageTypeTags.CAN_TRIGGER_MAGIC_DAMAGE)) {
                return;
            }
            float magicDamage = EntitySpiritDropData.getSpiritData(target).map(EntitySpiritDropData::getTotalSpirits).orElse(0);
            if (target instanceof Player) {
                magicDamage = 2 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
            }
            if (target.isAlive()) {
                target.invulnerableTime = 0;
                target.hurt(DamageTypeHelper.create(level, MalumDamageTypes.TYRVING, attacker), magicDamage);
            }
            if (target.isAlive()) {
                WorldEventHandler.addWorldEvent(level,
                        new DelayedDamageWorldEvent(target)
                                .setAttacker(attacker)
                                .setMagicDamageType(MalumDamageTypes.TYRVING)
                                .setDamageData(0, magicDamage, 3));
            }

            SoundPlayer.create(MalumGearSoundEvents.TYRVING_SLASH).pitch(1f, 1.5f).play(attacker);
            MalumParticleEffectTypes.TYRVING_SLASH.createEffect()
                    .originatesFrom(attacker).targets(target)
                    .verticalSlashRotation()
                    .color(stack)
                    .horizontalOffset(0.2f).forwardOffset(0.8f).upwardOffset(-0.2f)
                    .spawn(level);
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return itemAbility.equals(ItemAbilities.SWORD_DIG);
    }
}