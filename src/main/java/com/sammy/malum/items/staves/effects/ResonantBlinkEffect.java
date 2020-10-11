package com.sammy.malum.items.staves.effects;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.particles.lensmagic.LensMagicParticleData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static com.sammy.malum.MalumMod.random;
import static com.sammy.malum.SpiritDataHelper.consumeSpirit;
import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

@Nullable
public class ResonantBlinkEffect extends ModEffect
{
    @Override
    public int durability()
    {
        return 50;
    }

    @Override
    public String spirit()
    {
        return "minecraft:enderman";
    }

    @Override
    public int cooldown()
    {
        return 5;
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.resonant_blink.effect", SpiritDataHelper.getName(spirit())));
        return components;
    }

    @Override
    public effectTypeEnum type()
    {
        return effectTypeEnum.rightClick;
    }

    @Override
    public void effect(PlayerEntity playerEntity, ItemStack stack)
    {
        super.effect(playerEntity, stack);
        Vector3d direction = playerEntity.getLookVec();
        int distance = 12;
        for (int i = distance; i > 0; i--)
        {
            Vector3d newPosition = MalumHelper.tryTeleportPlayer(playerEntity, playerEntity.getPositionVec().add(direction.mul(i, i, i)));
            if (!newPosition.equals(playerEntity.getPositionVec()))
            {
                boolean success = consumeSpirit(playerEntity, stack);
                if (success)
                {
                    makeVisuals(playerEntity, playerEntity.getPositionVec(), 0.3f);
                    playerEntity.teleportKeepLoaded(newPosition.x, newPosition.y, newPosition.z);
                    makeVisuals(playerEntity, newPosition,-0.2f);
                    playerEntity.setMotion(direction.mul(0.25,0.25,0.25));
                    playerEntity.velocityChanged = true;
                }
                return;
            }
        }
    }

    public static void makeVisuals(PlayerEntity playerEntity,Vector3d pos, float velocityMultiplier)
    {
        playerEntity.world.playSound(null, playerEntity.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 0.8f + random.nextFloat());

        for (int i = 0; i < 20; i++)
        {
            Vector3d particlePos = MalumHelper.randSimulatedExtendedPosofEntity(playerEntity, pos, random, 1.25f);
            Vector3d particleVelocity = particlePos.subtract(pos).normalize().mul(velocityMultiplier, velocityMultiplier, velocityMultiplier);
            playerEntity.world.addParticle(new LensMagicParticleData(0.15f + random.nextFloat() * 0.2f), particlePos.x, particlePos.y, particlePos.z, particleVelocity.x, particleVelocity.y, particleVelocity.z);
        }
    }
}