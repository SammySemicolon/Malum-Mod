package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectCommonsHelper;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;
import team.lodestar.lodestone.modules.toolkit.item.*;
import team.lodestar.wayward_attributes.WaywardTags;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;


import static com.sammy.malum.common.effect.gluttony.GluttonyEffect.spawnLocusts;

public class GluttonousBludgeonItem extends LodestoneCombatItem implements IMalumEventResponder, ISpiritAffiliatedItem {

    public GluttonousBludgeonItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed - 4f, properties.mergeAttributes(
                ItemAttributeModifiers.builder()
                        .add(WaywardAttributeTypes.MAGIC_DAMAGE, new AttributeModifier(WaywardAttributeTypes.BASE_MAGIC_DAMAGE, magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }

    @Override
    public SpiritLike getDefiningSpiritType(ItemStack stack) {
        return MalumSpiritTypes.EARTHEN_SPIRIT;
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(TooltipComponentHelper.positiveItemEffect("gluttonous_bludgeon_locusts"));
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.level() instanceof ServerLevel level) {
            var source = event.getSource();
            if (source.is(WaywardTags.DamageTypeTags.CAN_TRIGGER_MAGIC_DAMAGE) || source.is(MalumDamageTypes.INVERTED_HEART_PROPAGATION)) {
                if (!LodestoneEnchantmentEffectCommonsHelper.isChargedAttack(attacker)) {
                    return;
                }
                if (source.is(WaywardTags.DamageTypeTags.CAN_TRIGGER_MAGIC_DAMAGE)) {
                    SoundPlayer.create(MalumGearSoundEvents.GLUTTONOUS_BLUDGEON_SPROUTS).pitch(1f, 1.5f).play(attacker);
                    MalumParticleEffectTypes.BLUDGEON_SLAM.createEffect()
                            .originatesFrom(attacker)
                            .targets(target)
                            .color(stack)
                            .forwardOffset(1.1f).upwardOffset(0.7f)
                            .spawn(level);
                }
                spawnLocusts(attacker, target, 2);
            }
        }
    }
}