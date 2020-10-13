package com.sammy.malum.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.capabilities.MalumDataProvider;
import com.sammy.malum.items.BowofLostSouls;
import com.sammy.malum.items.armor.ItemSpiritHunterArmor;
import com.sammy.malum.items.armor.ItemSpiritedSteelBattleArmor;
import com.sammy.malum.items.armor.ItemUmbraSteelBattleArmor;
import com.sammy.malum.items.staves.BasicStave;
import com.sammy.malum.items.tools.ModBusterSwordItem;
import com.sammy.malum.network.packets.HuskChangePacket;
import com.sammy.malum.particles.skull.SkullParticleData;
import com.sammy.malum.items.curios.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import static com.sammy.malum.MalumHelper.addDrop;
import static com.sammy.malum.MalumMod.random;
import static com.sammy.malum.SpiritDataHelper.consumeSpirit;
import static com.sammy.malum.capabilities.MalumDataProvider.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber()
public class RuntimeEvents
{
    //region HUSKS
    @SubscribeEvent
    public static void syncHuskData(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof LivingEntity)
        {
            if (event.getEntityLiving().world instanceof ServerWorld)
            {
                INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntityLiving()),
                        new HuskChangePacket(event.getTarget().getEntityId(), getHusk((LivingEntity) event.getTarget())));
            }
        }
    }
    
    @SubscribeEvent
    public static void cancelHuskTargetEvent(LivingSetAttackTargetEvent event)
    {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof MobEntity)
        {
            LivingEntity target = event.getTarget();
            if (target != null)
            {
                if (getHusk(livingEntity))
                {
                    if (getRogueOwner(livingEntity) != null)
                    {
                        if (getRogueOwner(target).equals(getRogueOwner(livingEntity)))
                        {
                            ((MobEntity) livingEntity).setAttackTarget(null);
                        }
                        if (event.getTarget() instanceof PlayerEntity)
                        {
                            PlayerEntity playerEntity = (PlayerEntity)target;
                            if (target.getUniqueID().equals(getRogueOwner(livingEntity)))
                            {
                                ((MobEntity)livingEntity).setAttackTarget(null);
                            }
                        }
                    }
                    else
                    {
                        ((MobEntity) livingEntity).setAttackTarget(null);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void handleHuskLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() != null)
        {
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.getEntityWorld();
            if (getHusk(livingEntity))
            {
                Vector3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, random, 0.5f);
                if (random.nextInt(4) == 0)
                {
                    world.addParticle(new SkullParticleData(0.25f), pos.x, pos.y, pos.z, 0, 0.04, 0);
                }
            }
        }
    }
    //endregion
    
    //region CURIOS
    @SubscribeEvent
    public static void necroticCatalystEffect(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioNecroticCatalyst, playerEntity).isPresent())
            {
                if (playerEntity.getFoodStats().needFood())
                {
                    if (random.nextDouble() < 0.2f)
                    {
                        CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioNecroticCatalyst, playerEntity).ifPresent(triple -> {
                            boolean success = consumeSpirit(playerEntity, triple.right);
                            if (success)
                            {
                                playerEntity.getFoodStats().addStats(1, 1);
                            }
                        });
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void vampireNecklaceEffect(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioVampireNecklace, playerEntity).isPresent())
            {
                if (random.nextDouble() < 0.2f || event.getEntityLiving().getHealth() <= 0)
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioVampireNecklace, playerEntity).ifPresent(triple ->
                    {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            playerEntity.heal(2);
                        }
                    });
                }
            }
        }
    }
    @SubscribeEvent
    public static void etherealBulwarkEffect(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioEtherealBulwark, playerEntity).isPresent())
            {
                if (playerEntity.getActiveItemStack().getItem() instanceof BasicStave)
                {
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void vacantAegisEffect(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioVacantAegis, playerEntity).isPresent())
            {
                if (event.getSource().getTrueSource() instanceof LivingEntity)
                {
                    if (getHusk((LivingEntity) event.getSource().getTrueSource()))
                    {
                        event.setAmount(event.getAmount() * 0.7f);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void enchantedLecternEffect(LivingExperienceDropEvent event)
    {
        if (event.getAttackingPlayer() != null)
        {
            PlayerEntity playerEntity = event.getAttackingPlayer();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).isPresent())
            {
                if (getHusk(event.getEntityLiving()))
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).ifPresent(triple ->
                    {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            event.setDroppedExperience(event.getDroppedExperience()*2);
                        }
                    });
                    event.setDroppedExperience(event.getDroppedExperience()*2);
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void armorSetbonusEffects(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (ItemSpiritHunterArmor.hasArmorSet(playerEntity))
        {
            event.extraSpirits +=1;
        }
        if (ItemUmbraSteelBattleArmor.hasArmorSet(playerEntity))
        {
            event.extraSpirits +=2;
        }
    }
    @SubscribeEvent
    public static void armorSetbonusEffects(SpiritHarvestEvent.Post event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (ItemSpiritedSteelBattleArmor.hasArmorSet(playerEntity))
        {
            playerEntity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 100, 1));
        }
        if (ItemUmbraSteelBattleArmor.hasArmorSet(playerEntity))
        {
            //make umbral trace
        }
    }
    @SubscribeEvent
    public static void armorSetbonusEffects(SpiritIntegrityUpdateEvent.Fill event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (ItemSpiritHunterArmor.hasArmorSet(playerEntity) || ItemUmbraSteelBattleArmor.hasArmorSet(playerEntity))
        {
            event.integrityChange *= 1.25f;
        }
    }
    
    @SubscribeEvent
    public static void barkNecklaceEffect(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioSpiritwoodNecklace, playerEntity).isPresent())
        {
            event.extraSpirits +=1;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            event.extraSpirits +=1;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritIntegrityUpdateEvent.Fill event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            event.integrityChange *= 1.5f;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritIntegrityUpdateEvent.Decrease event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            if (MathHelper.nextInt(random, 0, 3) == 0)
            {
                event.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public static void jesterHatEffect(SpiritHarvestEvent.Post event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioJesterHat, playerEntity).isPresent())
        {
            setRogueOwner(event.target, playerEntity.getUniqueID());
        }
    }
    //endregion
    
    //region ITEMS
    @SubscribeEvent
    public static void busterSwordDropHeads(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            if (event.isRecentlyHit())
            {
                if (event.getSource().getTrueSource() instanceof PlayerEntity)
                {
                    PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
                    ItemStack stack = playerEntity.getActiveItemStack();
                    if (stack.getItem() instanceof ModBusterSwordItem)
                    {
                        float chance = 40 + event.getLootingLevel() * 10 / 100f;
                        LivingEntity entity = event.getEntityLiving();
                        if (random.nextFloat() <= chance)
                        {
                            if (entity instanceof AbstractSkeletonEntity)
                            {
                                addDrop(event, new ItemStack(event.getEntity() instanceof WitherSkeletonEntity ? Items.WITHER_SKELETON_SKULL : Items.SKELETON_SKULL));
                            }
                            if (entity instanceof ZombieEntity && !(entity instanceof ZombifiedPiglinEntity))
                            {
                                addDrop(event, new ItemStack(Items.ZOMBIE_HEAD));
                            }
                            if (entity instanceof CreeperEntity)
                            {
                                addDrop(event, new ItemStack(Items.CREEPER_HEAD));
                            }
                            if (entity instanceof PlayerEntity)
                            {
                                ItemStack head = new ItemStack(Items.PLAYER_HEAD);
                                head.getOrCreateTag().putString("SkullOwner", ((PlayerEntity) entity).getGameProfile().getName());
                                addDrop(event, stack);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void autofireBowofLostSouls(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = playerEntity.getActiveItemStack();
            if (stack.getItem() instanceof BowofLostSouls)
            {
                if ((stack.getUseDuration() - playerEntity.getItemInUseCount()) / BowofLostSouls.getDrawTime(stack) >= 1.1f)
                {
                    boolean success = consumeSpirit(playerEntity, stack);
                    if (success)
                    {
                        stack.onPlayerStoppedUsing(playerEntity.world, playerEntity, stack.getUseDuration() - playerEntity.getItemInUseCount());
                        playerEntity.resetActiveHand();
                    }
                }
            }
        }
    }
    //endregion
}