package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.tool.ToolAction;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import io.github.fabricators_of_create.porting_lib.tool.extensions.ItemStackExtensions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import team.lodestar.lodestone.systems.item.tools.LodestoneSwordItem;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem, ItemStackExtensions {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        final Level level = attacker.level();
        if (level.isClientSide) {
            return;
        }
        if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            return;
        }
        float damage = SpiritHarvestHandler.getSpiritData(target).map(d -> d.totalSpirits).orElse(0) * 2f;
        if (target instanceof Player) {
            damage = 4 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
        }

        if (target.isAlive()) {
            target.invulnerableTime = 0;
            target.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO, attacker), damage);
        }

        SoundHelper.playSound(attacker, SoundRegistry.TYRVING_SLASH.get(), 1, RandomHelper.randomBetween(attacker.getRandom(), 1f, 1.5f));
        ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.TYRVING_SLASH)
                .setSpiritType(SpiritTypeRegistry.WICKED_SPIRIT)
                .setVerticalSlashAngle()
                .spawnForwardSlashingParticle(attacker);
    }

    @Override
    public boolean canPerformAction(ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }
}