package com.sammy.malum.common.item.tools;

import com.sammy.malum.common.packets.particle.MagicParticlePacket;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class TyrvingItem extends ModSwordItem implements IEventResponderItem
{
    public final float magicAttackDamage;
    public final Supplier<SoundEvent> soundEvent;
    public TyrvingItem(Tier material, float magicAttackDamage, int attackDamage, float attackSpeed, Supplier<SoundEvent> soundEvent, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.magicAttackDamage = magicAttackDamage;
        this.soundEvent = soundEvent;
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagic())
        {
            return;
        }
        if (attacker.level instanceof ServerLevel)
        {
            int spiritCount = SpiritHelper.getEntitySpiritCount(target);
            if (target instanceof Player)
            {
                spiritCount = 2;
            }
            target.invulnerableTime = 0;
            target.hurt(DamageSourceRegistry.causeVoodooDamage(attacker), spiritCount*magicAttackDamage);
            attacker.level.playSound(null, target.blockPosition(), soundEvent.get(), SoundSource.PLAYERS, 1, 1f + target.level.random.nextFloat() * 0.25f);
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), new MagicParticlePacket(SpiritTypeRegistry.ELDRITCH_SPIRIT_COLOR, target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ()));
        }
    }
}
