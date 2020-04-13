package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.network.packets.BonkPacket;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStartPacket;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import com.kittykitcatcat.malum.particles.souleruptionparticle.SoulEruptionParticleData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class BonkItem extends SwordItem
{
    public BonkItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof PlayerEntity)
        {
            target.addVelocity(attacker.getLookVec().x * 2, 0.2, attacker.getLookVec().z * 2);
            attacker.world.playSound((PlayerEntity) attacker, attacker.getPosition(), ModSounds.bonk, SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(attacker.world.rand, 1.5f, 2.0f));
            attacker.world.addParticle(new BonkParticleData(), target.getPositionVec().getX() + 0.5 - attacker.getLookVec().getX() / 4, target.getPosYEye(), target.getPositionVec().getZ() + 0.5 - attacker.getLookVec().getZ() / 4, 0, 0, 0);
            INSTANCE.send(
                    PacketDistributor.TRACKING_CHUNK.with(() -> attacker.world.getChunkAt(attacker.getPosition())),
                    new BonkPacket(target.getPositionVec().getX() + 0.5 - attacker.getLookVec().getX() / 4, target.getPosYEye(), target.getPositionVec().getZ() + 0.5 - attacker.getLookVec().getZ() / 4));
        }
        return super.hitEntity(stack, target, attacker);
    }
}

