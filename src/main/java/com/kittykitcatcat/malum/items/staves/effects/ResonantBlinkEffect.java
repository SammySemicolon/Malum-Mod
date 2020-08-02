package com.kittykitcatcat.malum.items.staves.effects;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritDataHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static com.kittykitcatcat.malum.SpiritDataHelper.consumeSpirit;

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
        components.add(new TranslationTextComponent("malum.tooltip.resonantblink.desc"));
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
        Vec3d direction = playerEntity.getLookVec();
        int distance = 8;
        for (int i =distance; i> 0; i--)
        {
            Vec3d newPosition = MalumHelper.tryTeleportPlayer(playerEntity, playerEntity.getPositionVec().add(direction.mul(i,i,i)));
            if (!newPosition.equals(playerEntity.getPositionVec()))
            {
                boolean success = consumeSpirit(playerEntity, stack);
                if (success)
                {
                    playerEntity.teleportKeepLoaded(newPosition.x, newPosition.y, newPosition.z);
                    playerEntity.setMotion(direction);
                    playerEntity.velocityChanged = true;
                }
                return;
            }
        }
    }
}