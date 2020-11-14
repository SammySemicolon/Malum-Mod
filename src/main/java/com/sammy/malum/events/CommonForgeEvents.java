package com.sammy.malum.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.capabilities.IMalumData;
import com.sammy.malum.capabilities.MalumDataProvider;
import com.sammy.malum.events.customevents.SpiritHarvestEvent;
import com.sammy.malum.events.customevents.SpiritIntegrityUpdateEvent;
import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModSounds;
import com.sammy.malum.items.BowofLostSouls;
import com.sammy.malum.items.equipment.armor.ItemSpiritHunterArmor;
import com.sammy.malum.items.equipment.armor.ItemSpiritedSteelBattleArmor;
import com.sammy.malum.items.equipment.armor.ItemUmbraSteelBattleArmor;
import com.sammy.malum.items.equipment.curios.*;
import com.sammy.malum.items.staves.BasicStave;
import com.sammy.malum.items.tools.ModBusterSwordItem;
import com.sammy.malum.network.packets.HuskChangePacket;
import com.sammy.malum.network.packets.SpiritHarvestFailurePacket;
import com.sammy.malum.particles.particletypes.charm.HeartParticleData;
import com.sammy.malum.particles.particletypes.skull.SkullParticleData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumHelper.addDrop;
import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.MalumMod.random;
import static com.sammy.malum.SpiritDataHelper.*;
import static com.sammy.malum.SpiritDataHelper.getSpirit;
import static com.sammy.malum.capabilities.MalumDataProvider.*;
import static com.sammy.malum.init.ModFeatures.CONFIGURED_COAL_ORE;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.util.SoundCategory.PLAYERS;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents
{
    //region worldgen
    @SubscribeEvent
    public static void init(BiomeLoadingEvent e)
    {
        e.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> CONFIGURED_COAL_ORE);
    }
    //endregion
    
    //region capabilities
    @SubscribeEvent
    public static void onAttatchCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof LivingEntity)
        {
            event.addCapability(new ResourceLocation(MalumMod.MODID, "malum_data"), new MalumDataProvider());
        }
    }
    
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (!event.isWasDeath()) { return; }
        LazyOptional<IMalumData> capability = event.getOriginal().getCapability(MalumDataProvider.CAPABILITY);
        capability.ifPresent(oldStore -> event.getOriginal().getCapability(MalumDataProvider.CAPABILITY).ifPresent(newStore -> newStore.copy(oldStore)));
    }
    //endregion
    
    //region husks
    @SubscribeEvent
    public static void syncHuskData(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof LivingEntity)
        {
            if (event.getEntityLiving().world instanceof ServerWorld)
            {
                INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getEntityLiving()), new HuskChangePacket(event.getTarget().getEntityId(), getDread((LivingEntity) event.getTarget()), getCharm((LivingEntity) event.getTarget())));
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
                if (lacksSpirit(livingEntity))
                {
                    if (getSpiritOwner(livingEntity) != null)
                    {
                        UUID spiritOwner = getSpiritOwner(livingEntity);
                        if (getSpiritOwner(target) != null)
                        {
                            if (spiritOwner.equals(getSpiritOwner(target)))
                            {
                                ((MobEntity) livingEntity).setAttackTarget(null);
                            }
                        }
                        if (event.getTarget() instanceof PlayerEntity)
                        {
                            PlayerEntity playerEntity = (PlayerEntity) target;
                            if (playerEntity.getUniqueID().equals(getSpiritOwner(livingEntity)))
                            {
                                ((MobEntity) livingEntity).setAttackTarget(null);
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
            if (lacksSpirit(livingEntity))
            {
                if (getDread(livingEntity))
                {
                    Vector3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, random, 0.5f);
                    if (random.nextInt(4) == 0)
                    {
                        world.addParticle(new SkullParticleData(0.25f), pos.x, pos.y, pos.z, 0, 0.04, 0);
                    }
                }
                if (getCharm(livingEntity))
                {
                    Vector3d pos = MalumHelper.randExtendedPosofEntity(livingEntity, random, 0.5f);
                    if (random.nextInt(4) == 0)
                    {
                        world.addParticle(new HeartParticleData(0.25f), pos.x, pos.y, pos.z, 0, 0.04, 0);
                    }
                }
            }
        }
    }
    //endregion
    
    //region curios
    @SubscribeEvent
    public static void goodLuckCharmEffect(LootingLevelEvent event)
    {
        if (event.getDamageSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getDamageSource().getTrueSource();
            CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioGoodLuckCharm, playerEntity).ifPresent(triple -> {
                boolean success = consumeSpirit(playerEntity, triple.right);
                if (success)
                {
                    event.setLootingLevel(event.getLootingLevel() + 4);
                }
            });
        }
    }
    
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
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioVampireNecklace, playerEntity).ifPresent(triple -> {
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
    public static void sinisterMaskEffect(PotionEvent.PotionApplicableEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioSinisterMask, playerEntity).isPresent())
            {
                if (!event.getPotionEffect().getPotion().isBeneficial())
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioSinisterMask, playerEntity).ifPresent(triple -> {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            event.setResult(Event.Result.DENY);
                            playerEntity.addPotionEffect(new EffectInstance(Effects.STRENGTH, event.getPotionEffect().getDuration(), event.getPotionEffect().getAmplifier()));
                        }
                    });
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void vacantAegisEffect(SpiritHarvestEvent.Post event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioVacantAegis, playerEntity).isPresent())
        {
            playerEntity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 200, 1));
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
                if (lacksSpirit(event.getEntityLiving()))
                {
                    CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioEnchantedLectern, playerEntity).ifPresent(triple -> {
                        boolean success = consumeSpirit(playerEntity, triple.right);
                        if (success)
                        {
                            event.setDroppedExperience(event.getDroppedExperience() * 2);
                        }
                    });
                    event.setDroppedExperience(event.getDroppedExperience() * 2);
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
            event.extraSpirits += 1;
        }
        if (ItemUmbraSteelBattleArmor.hasArmorSet(playerEntity))
        {
            event.extraSpirits += 2;
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
            event.extraSpirits += 1;
        }
    }
    
    @SubscribeEvent
    public static void seerOfMiraclesEffect(SpiritHarvestEvent.Pre event)
    {
        PlayerEntity playerEntity = event.playerEntity;
        if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioMiraclePearl, playerEntity).isPresent())
        {
            event.extraSpirits += 1;
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
        CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioJesterHat, playerEntity).ifPresent(triple -> {
            boolean success = consumeSpirit(playerEntity, triple.right);
            if (success)
            {
                setSpiritOwner(event.target, playerEntity.getUniqueID());
            }
        });
    }
    //endregion
    
    //region items
    @SubscribeEvent
    public static void busterSwordSpecialAttack(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (playerEntity.swingingHand != null)
            {
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem() instanceof ModBusterSwordItem)
                {
                    if (!playerEntity.getCooldownTracker().hasCooldown(stack.getItem()))
                    {
                        LivingEntity target = event.getEntityLiving();
                        int cooldown = 100;
                        
                        if (CuriosApi.getCuriosHelper().findEquippedCurio(curio -> curio.getItem() instanceof CurioGildedGauntlet, playerEntity).isPresent())
                        {
                            cooldown = 40;
                        }
                        playerEntity.getCooldownTracker().setCooldown(stack.getItem(), cooldown);
                        if (stack.getItem().equals(ModItems.breaker_blade))
                        {
                            event.setAmount(event.getAmount() + target.getTotalArmorValue());
                        }
                        event.setAmount(event.getAmount() * 2f);
                        playerEntity.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.4f, 0.8f);
                        playerEntity.playSound(SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.PLAYERS, 1.2f, 0.8f);
                        playerEntity.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0f, 0.8f);
                        target.addVelocity(playerEntity.getLookVec().x / 2, 0.1, playerEntity.getLookVec().z / 2);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public static void busterSwordDropHeads(LivingDropsEvent event)
    {
        if (event.getEntity() instanceof LivingEntity)
        {
            if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem() instanceof ModBusterSwordItem)
                {
                    float chance = (10 + event.getLootingLevel() * 10) / 100f;
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
    
    //region spirit harvesting
    public static boolean tryCancel(PlayerEntity playerEntity, ItemStack stack)
    {
        if (playerEntity instanceof ServerPlayerEntity)
        {
            if (getHarvestTarget(playerEntity) == null)
            {
                cancel(playerEntity, stack);
                return true;
            }
            else
            {
                Entity entity = ((ServerPlayerEntity) playerEntity).getServerWorld().getEntityByUuid(getHarvestTarget(playerEntity));
                if (entity instanceof LivingEntity)
                {
                    boolean isAlive = entity.isAlive();
                    if (!isAlive || SpiritDataHelper.lacksSpirit((LivingEntity) entity))
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
        setHarvestTarget(playerEntity, null);
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
        setHarvestTarget(playerEntity, null);
        harvestSpirit(playerEntity, stack, cachedTarget, getSpirit(cachedTarget), 1);
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
            if (player.world.getGameTime() % 20L == 0L) //dont do it too often
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
                        if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                        {
                            LivingEntity entity = findEntityHarvestViable(player, 0.6f);
                            if (entity != null)
                            {
                                setHarvestTarget(player, entity.getUniqueID());
                            }
                        }
                    }
                    else
                    {
                        if (getHarvestTarget(player) != null)
                        {
                            setHarvestTarget(player, null);
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
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                {
                    UUID cachedTarget = getHarvestTarget(player);
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
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = event.getItem();
            if (stack.getItem() instanceof BasicStave)
            {
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0)
                {
                    
                    if (tryCancel(player, event.getItem()))
                    {
                        player.resetActiveHand();
                        event.setCanceled(true);
                    }
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
            ItemStack stack = event.getItem();
            if (stack.getItem() instanceof BasicStave) {
                if (((BasicStave) stack.getItem()).getOption(stack).option == 0) {
                    if (tryCancel(player, stack)) {
                        event.setCanceled(true);
                    }
                    int time = 100;
                    
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(curio -> curio.getItem() instanceof CurioNetherborneCapacitor, player).isPresent()) {
                        time = 50;
                    }
                    if ((stack.getUseDuration() - player.getItemInUseCount()) > time) {
                        if (!tryCancel(player, stack)) {
                            UUID cachedTarget = getHarvestTarget(player);
                            if (cachedTarget != null) {
                                if (player.world instanceof ServerWorld) {
                                    Entity entity = ((ServerWorld) player.world).getEntityByUuid(cachedTarget);
                                    if (entity instanceof LivingEntity) {
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
    }
    
    @SubscribeEvent
    public static void stopHarvest(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof ServerPlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (event.getItem().getItem() instanceof BasicStave)
            {
                cancel(player, player.getActiveItemStack());
            }
        }
    }
    
    public static boolean isEntityValid(PlayerEntity playerEntity, LivingEntity livingEntity)
    {
        if (livingEntity.getHealth() > playerEntity.getHealth() && !playerEntity.isCreative())
        {
            if (!CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioNetherborneCapacitor, playerEntity).isPresent())
            {
                return false;
            }
        }
        return !lacksSpirit(livingEntity);
    }
    
    public static LivingEntity findEntityHarvestViable(PlayerEntity player, float maxAngle)
    {
        World world = player.world;
        Vector3d pos = player.getPositionVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - 10, pos.y - 10, pos.z - 10, pos.x + 10, pos.y + 10, pos.z + 10)).stream().filter(e -> e instanceof LivingEntity && isEntityValid(player, (LivingEntity) e)).collect(Collectors.toList());
        List<Entity> finalList = new ArrayList<>();
        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                Vector3d vecA = player.getLookVec().normalize();
                Vector3d vecB = (entity.getPositionVec().subtract(player.getPositionVec())).normalize();
                double angle = 2 * Math.atan((vecA.subtract(vecB)).length() / (vecA.add(vecB)).length());
                if (angle <= maxAngle && angle >= -maxAngle)
                {
                    finalList.add(entity);
                }
            }
        }
        if (!finalList.isEmpty())
        {
            return (LivingEntity) MalumHelper.getClosestEntity(finalList, player.getPositionVec());
        }
        return null;
    }
    //endregion
    
}