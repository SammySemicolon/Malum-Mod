package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class PoppetItem extends Item
{
    public PoppetItem(Properties properties)
    {
        super(properties);
    }
    public boolean onlyDirect()
    {
        return false;
    }
    public void effect(LivingDamageEvent event,World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets)
    {
        targets.forEach(t -> effect(event,world,playerEntity,t));
    }
    public void effect(LivingDamageEvent event,World world, PlayerEntity playerEntity, Entity target)
    {
    
    }
    @SubscribeEvent
    public static void onHurt(LivingDamageEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            World world = playerEntity.world;
            ArrayList<PoppetItem> poppets = new ArrayList<>();
            if (playerEntity.getHeldItemOffhand().getItem() instanceof PoppetItem)
            {
                poppets.add((PoppetItem) playerEntity.getHeldItemOffhand().getItem());
            }
            if (playerEntity.getHeldItemMainhand().getItem() instanceof PoppetItem)
            {
                poppets.add((PoppetItem) playerEntity.getHeldItemMainhand().getItem());
            }
            MalumHelper.takeAll(poppets, PoppetItem::onlyDirect).forEach(p ->
                    p.effect(event,world,playerEntity,event.getSource().getTrueSource()));
            ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerEntity.getPosition()).grow(16)).stream().filter(e -> !world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get())).collect(Collectors.toList());
            targets.remove(playerEntity);
            poppets.forEach(p -> p.effect(event,world, playerEntity,targets));
        }
    }
}
