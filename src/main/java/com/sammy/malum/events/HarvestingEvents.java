package com.sammy.malum.events;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.init.ModSounds;
import com.sammy.malum.items.curios.CurioBandOfFriendship;
import com.sammy.malum.items.curios.CurioGoodLuckCharm;
import com.sammy.malum.items.curios.CurioNetherborneCapacitor;
import com.sammy.malum.items.staves.BasicStave;
import com.sammy.malum.network.packets.HuskChangePacket;
import com.sammy.malum.network.packets.SpiritHarvestFailurePacket;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

import static com.sammy.malum.SpiritDataHelper.*;
import static com.sammy.malum.capabilities.MalumDataProvider.*;
import static com.sammy.malum.items.staves.BasicStave.getEnum;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.util.SoundCategory.PLAYERS;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class HarvestingEvents
{
    public static SimpleSound sound;
    public static boolean tryCancel(PlayerEntity playerEntity, ItemStack stack)
    {
        if (playerEntity instanceof ServerPlayerEntity)
        {
            if (getCachedTarget(playerEntity) == null)
            {
                cancel(playerEntity, stack);
                return true;
            }
            else
            {
                Entity entity = ((ServerPlayerEntity) playerEntity).getServerWorld().getEntityByUuid(getCachedTarget(playerEntity));
                if (entity instanceof LivingEntity)
                {
                    boolean isAlive = entity.isAlive();
                    if (!isAlive || SpiritDataHelper.hasSpirit((LivingEntity) entity))
                    {
                        cancel(playerEntity, stack);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void cancel(PlayerEntity playerEntity, ItemStack stack)
    {
        playerEntity.swingArm(playerEntity.getActiveHand());
        setCachedTarget(playerEntity, null);
        playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_failure, PLAYERS, 1f, 1);
        if (playerEntity instanceof ServerPlayerEntity)
        {
            INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity), new SpiritHarvestFailurePacket());
        }
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 10);
        playerEntity.resetActiveHand();
    }
    public static void success(PlayerEntity playerEntity, ItemStack stack, LivingEntity cachedTarget)
    {
        playerEntity.world.playSound(null, playerEntity.getPosition(), ModSounds.spirit_harvest_success, PLAYERS, 1f, 1);
        if (CuriosApi.getCuriosHelper().findEquippedCurio(band -> band.getItem() instanceof CurioBandOfFriendship, playerEntity).isPresent())
        {
            setCharm(cachedTarget, true);
        }
        else
        {
            setDread(cachedTarget, true);
        }
        setCachedTarget(playerEntity, null);
        harvestSpirit(playerEntity, cachedTarget, getSpirit(cachedTarget), 1);
        playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), 40);
        playerEntity.resetActiveHand();
    }
    @SubscribeEvent
    public static void updateCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.world.getGameTime() % 4L == 0L) //dont do it too often
            {
                if (!player.isHandActive()) //we dont wanna update the target if the player is harvesting a spirit
                {
                    ItemStack stack = player.getHeldItemMainhand();
                    if (!(stack.getItem() instanceof BasicStave))
                    {
                        stack = player.getHeldItemOffhand();
                    }
                    if (stack.getItem() instanceof BasicStave)
                    {
                        if (getEnum(stack).equals(BasicStave.staveOptionEnum.spiritHarvest))
                        {
                            LivingEntity entity = BasicStave.findEntity(player);
                            if (entity != null)
                            {
                                setCachedTarget(player, entity.getUniqueID());
                            }
                        }
                    }
                    else
                    {
                        if (getCachedTarget(player) != null)
                        {
                            setCachedTarget(player, null);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void previewCachedTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getHeldItemMainhand();
            if (!(stack.getItem() instanceof BasicStave))
            {
                stack = player.getHeldItemOffhand();
            }
            if (stack.getItem() instanceof BasicStave)
            {
                if (getEnum(stack).equals(BasicStave.staveOptionEnum.spiritHarvest))
                {
                    UUID cachedTarget = getCachedTarget(player);
                    if (cachedTarget != null)
                    {
                        if (player.world instanceof ServerWorld)
                        {
                            Entity entity = ((ServerWorld) player.world).getEntityByUuid(cachedTarget);
                            if (entity instanceof LivingEntity)
                            {
                                ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void harvestStart(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getActiveItemStack();
            if (getEnum(stack).equals(BasicStave.staveOptionEnum.spiritHarvest))
            {
                if (tryCancel(player, player.getActiveItemStack()))
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void harvestTick(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getActiveItemStack();
            if (getEnum(stack).equals(BasicStave.staveOptionEnum.spiritHarvest))
            {
                if (tryCancel(player, stack))
                {
                    event.setCanceled(true);
                }
                int time = 100;
        
                if (CuriosApi.getCuriosHelper().findEquippedCurio(curio -> curio.getItem() instanceof CurioNetherborneCapacitor, player).isPresent())
                {
                    time = 50;
                }
                if ((stack.getUseDuration() - player.getItemInUseCount()) > time)
                {
                    if (!tryCancel(player, stack))
                    {
                        UUID cachedTarget = getCachedTarget(player);
                        if (cachedTarget != null)
                        {
                            if (player.world instanceof ServerWorld)
                            {
                                Entity entity = ((ServerWorld) player.world).getEntityByUuid(cachedTarget);
                                if (entity instanceof LivingEntity)
                                {
                                    success(player, stack, (LivingEntity) entity);
                                    event.setCanceled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void stopHarvest(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getActiveItemStack().getItem() instanceof BasicStave)
            {
                cancel(player, player.getActiveItemStack());
            }
        }
    }
}