package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.curios.CurioPoppetBelt;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

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
    public void effect(LivingDamageEvent event,World world, PlayerEntity playerEntity, LivingEntity target)
    {
    
    }
    @SubscribeEvent
    public static void onHurt(LivingDamageEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.world instanceof ServerWorld)
            {
                if (event.getSource().equals(MalumDamageSources.VOODOO))
                {
                    return;
                }
                ServerWorld world = (ServerWorld) playerEntity.world;
                ArrayList<ItemStack> poppets = new ArrayList<>();
                if (playerEntity.getHeldItemOffhand().getItem() instanceof PoppetItem)
                {
                    poppets.add(playerEntity.getHeldItemOffhand());
                }
                if (playerEntity.getHeldItemMainhand().getItem() instanceof PoppetItem)
                {
                    poppets.add(playerEntity.getHeldItemMainhand());
                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).isPresent())
                {
                    ItemStack belt = CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).get().right;
                    SimpleInventory inventory = CurioPoppetBelt.create(belt);
                    poppets.addAll(inventory.stacks());
                }
                MalumHelper.takeAll(poppets, p -> cast(p).onlyDirect()).forEach(p -> {
                            if (event.getSource().getTrueSource() instanceof LivingEntity)
                            {
                                cast(p).effect(event, world, playerEntity, (LivingEntity) event.getSource().getTrueSource());
                            }
                        }
                );
                ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerEntity.getPosition()).grow(12)).stream().filter(e -> !world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get())).collect(Collectors.toList());
                targets.remove(playerEntity);
                poppets.forEach(p -> cast(p).effect(event, world, playerEntity, targets));
            }
        }
    }
    public static PoppetItem cast(ItemStack stack)
    {
        return ((PoppetItem)(stack.getItem()));
    }
}
