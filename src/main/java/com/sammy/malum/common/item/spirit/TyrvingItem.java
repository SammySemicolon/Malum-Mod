package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.packets.particle.entity.MajorEntityEffectParticlePacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.DamageSourceRegistry;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.item.tools.LodestoneSwordItem;

import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class TyrvingItem extends LodestoneSwordItem implements IMalumEventResponderItem {
    public TyrvingItem(Tier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagic()) {
            return;
        }
        if (attacker.level instanceof ServerLevel) {
            float spiritCount = SpiritHelper.getEntitySpiritCount(target) * 2f;
            if (target instanceof Player) {
                spiritCount = 4 * Math.max(1, (1 + target.getArmorValue() / 12f) * (1 + (1 - 1 / (float)target.getArmorValue())) / 12f);
            }

            if (target.isAlive()) {
                target.invulnerableTime = 0;
                target.hurt(DamageSourceRegistry.causeVoodooDamage(attacker), spiritCount);
            }
            attacker.level.playSound(null, target.blockPosition(), SoundRegistry.VOID_SLASH.get(), SoundSource.PLAYERS, 1, 1f + target.level.random.nextFloat() * 0.25f);
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), new MajorEntityEffectParticlePacket(SpiritTypeRegistry.ELDRITCH_SPIRIT.getColor(), target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ()));
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return toolAction.equals(ToolActions.SWORD_DIG);
    }
}