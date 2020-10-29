package com.sammy.malum.items.staves.effects;

import com.sammy.malum.SpiritDataHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.ClientHandler.makeGenericSpiritDependantTooltip;

@Nullable
public class MatingEffect extends ModEffect
{
    @Override
    public int durability()
    {
        return 5;
    }
    
    @Override
    public String spirit()
    {
        return "minecraft:skeleton";
    }
    
    @Override
    public int cooldown()
    {
        return 100;
    }
    
    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(makeGenericSpiritDependantTooltip("malum.tooltip.mating.effect", SpiritDataHelper.getName(spirit())));
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
        World world = playerEntity.world;
        BlockPos pos = playerEntity.getPosition();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(playerEntity, new AxisAlignedBB(pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10, pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10));
        if (!list.isEmpty())
        {
            boolean success = SpiritDataHelper.consumeSpirit(playerEntity, stack);
            if (success)
            {
                for (Entity entity : list)
                {
                    if (entity instanceof AnimalEntity)
                    {
                        AnimalEntity animalEntity = (AnimalEntity) entity;
                        animalEntity.setInLove(playerEntity);
                    }
                }
            }
        }
    }
}