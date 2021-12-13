package com.sammy.malum.common.item.tools;

import com.sammy.malum.core.registry.misc.DamageSourceRegistry;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.ELDRITCH_SPIRIT;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.WICKED_SPIRIT;
import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class TyrvingItem extends ModSwordItem implements IEventResponderItem
{
    public final float magicAttackDamage;
    public final Supplier<SoundEvent> soundEvent;
    public TyrvingItem(IItemTier material, float magicAttackDamage, int attackDamage, float attackSpeed, Supplier<SoundEvent> soundEvent, Properties properties)
    {
        super(material, attackDamage, attackSpeed, properties);
        this.magicAttackDamage = magicAttackDamage;
        this.soundEvent = soundEvent;
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().isMagicDamage())
        {
            return;
        }
        if (attacker.world instanceof ServerWorld)
        {
            int spiritCount = SpiritHelper.getEntitySpiritCount(target);
            if (target instanceof PlayerEntity)
            {
                spiritCount = 2;
            }
            target.hurtResistantTime = 0;
            target.attackEntityFrom(DamageSourceRegistry.causeVoodooDamage(attacker), spiritCount*magicAttackDamage);
            attacker.world.playSound(null, target.getPosition(), soundEvent.get(), SoundCategory.PLAYERS, 1, 1f + target.world.rand.nextFloat() * 0.25f);
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), BurstParticlePacket.fromSpirits(target.getPosX(), target.getPosY() + target.getHeight() / 2, target.getPosZ(), WICKED_SPIRIT, ELDRITCH_SPIRIT));
        }
    }
}
