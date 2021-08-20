package com.sammy.malum.common.item.tools.spirittools;

import com.sammy.malum.common.item.tools.ModSwordItem;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.init.MalumSpiritTypes.ELDRITCH_SPIRIT;
import static com.sammy.malum.core.init.MalumSpiritTypes.WICKED_SPIRIT;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class TyrvingItem extends ModSwordItem
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
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker.world instanceof ServerWorld)
        {
            int spiritCount = SpiritHelper.totalEntitySpirits(target);
            if (target instanceof PlayerEntity)
            {
                spiritCount = 2;
            }
            SpiritHelper.causeVoodooDamage(attacker, target, spiritCount*magicAttackDamage);
            attacker.world.playSound(null, target.getPosition(), soundEvent.get(), SoundCategory.PLAYERS, 1, 1f + target.world.rand.nextFloat() * 0.25f);
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target), BurstParticlePacket.fromSpirits(target.getPosX(), target.getPosY() + target.getHeight() / 2, target.getPosZ(), WICKED_SPIRIT, ELDRITCH_SPIRIT));
        }
        return super.hitEntity(stack, target, attacker);
    }
}
