package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.tool.ToolAction;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.tools.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            return;
        }
        final Level level = attacker.level();
        if (!level.isClientSide) {
            float damage = SpiritHarvestHandler.getSpiritData(target).map(d -> d.totalSpirits).orElse(0) * 2f;
            if (target instanceof Player) {
                damage = 4 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float) target.getArmorValue())) / 12f);
            }

            if (target.isAlive()) {
                target.invulnerableTime = 0;
                target.hurt(DamageTypeRegistry.create(level, DamageTypeRegistry.VOODOO, attacker), damage);
            }
            level.playSound(null, target.blockPosition(), SoundRegistry.VOID_SLASH.get(), SoundSource.PLAYERS, 1, 1f + level.random.nextFloat() * 0.25f);
            MALUM_CHANNEL.sendToClientsTracking(new MajorEntityEffectParticlePacket(SpiritTypeRegistry.ELDRITCH_SPIRIT.getPrimaryColor(), target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ()), target);
        }
    }



    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }
}