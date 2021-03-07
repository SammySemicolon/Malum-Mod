package com.sammy.malum.common.events;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.tools.TyrvingSwordItem;
import com.sammy.malum.common.items.equipment.poppets.PoppetItem;
import com.sammy.malum.common.items.equipment.poppets.PoppetOfUndying;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.systems.inventory.ItemInventory;
import com.sammy.malum.network.packets.ParticlePacket;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.UUID;

import static com.sammy.malum.common.items.equipment.poppets.PoppetItem.cast;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraftforge.eventbus.api.Event.Result.DENY;

@Mod.EventBusSubscriber
public class Events
{
    @SubscribeEvent
    public static void giveCattoHisTreat(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (MalumHelper.areWeOnServer(playerEntity.world))
            {
                if (playerEntity.getUniqueID().equals(UUID.fromString("0ca54301-6170-4c44-b3e0-b8afa6b81ed2")))
                {
                    if (!MalumHelper.findEquippedCurio(s -> s.getItem().equals(MalumItems.FLUFFY_TAIL.get()), playerEntity).isPresent())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, MalumItems.FLUFFY_TAIL.get().getDefaultInstance());
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void tyrvingHurt(LivingHurtEvent event)
    {
        if (event.getSource().equals(MalumDamageSources.VOODOO))
        {
            return;
        }
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (playerEntity.swingingHand != null)
            {
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem() instanceof TyrvingSwordItem)
                {
                    LivingEntity entity = event.getEntityLiving();
                    float amount = (event.getAmount() / 8) * (8 + entity.getTotalArmorValue());
                    event.setAmount(event.getAmount() / 2);
                    event.getEntity().attackEntityFrom(MalumDamageSources.VOODOO, amount / 2f);
                    playerEntity.world.playSound(null, entity.getPosition(), MalumSounds.TYRVING_HIT, SoundCategory.PLAYERS, 1, 1f + playerEntity.world.rand.nextFloat() * 0.25f);
                    if (playerEntity.world instanceof ServerWorld)
                    {
                        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(event::getEntityLiving), new ParticlePacket(0, entity.getPosX(), entity.getPosY() + entity.getHeight() / 2, entity.getPosZ()));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            if (!event.getSource().canHarmInCreative())
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                ArrayList<Pair<ItemStack, ItemInventory>> poppets = PoppetItem.poppets(playerEntity, i -> i.getItem() instanceof PoppetOfUndying);
                if (poppets.isEmpty())
                {
                    return;
                }
                ItemStack poppetOfUndying = poppets.get(0).getFirst();
                if (!poppetOfUndying.isEmpty())
                {
                    if (playerEntity instanceof ServerPlayerEntity)
                    {
                        ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerEntity;
                        serverplayerentity.addStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                        CriteriaTriggers.USED_TOTEM.trigger(serverplayerentity, poppetOfUndying);
                    }
                    playerEntity.setHealth(1.0F);
                    playerEntity.clearActivePotions();
                    playerEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                    playerEntity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                    playerEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                    playerEntity.world.setEntityState(playerEntity, (byte) 35);
                    if (poppets.get(0).getSecond() != null)
                    {
                        ItemInventory inventory = poppets.get(0).getSecond();
                        for (int i = 0; i < inventory.slotCount; i++)
                        {
                            ItemStack stack = inventory.getStackInSlot(i);
                            if (ItemStack.areItemStacksEqual(stack, poppetOfUndying))
                            {
                                inventory.setStackInSlot(i, ItemStack.EMPTY);
                                break;
                            }
                        }
                    }
                    else
                    {
                        poppetOfUndying.shrink(1);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
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
                ArrayList<Pair<ItemStack, ItemInventory>> poppets = PoppetItem.poppets(playerEntity, i -> i.getItem() instanceof PoppetItem);
                if (poppets.isEmpty())
                {
                    return;
                }
                poppets.forEach(p -> {
                    if (event.isCanceled())
                    {
                        return;
                    }
                    LivingEntity target = null;
                    if (event.getSource().getTrueSource() instanceof LivingEntity)
                    {
                        target = (LivingEntity) event.getSource().getTrueSource();
                    }
                    cast(p.getFirst()).effect(p.getFirst(), event, world, playerEntity, target, p.getSecond());
                });
            }
        }
    }
    
    @SubscribeEvent
    public static void knockBack(LivingKnockBackEvent event)
    {
        if (event.getEntityLiving().getActivePotionEffect(MalumEffects.WARDING.get()) != null)
        {
            event.setStrength(0);
        }
    }
}