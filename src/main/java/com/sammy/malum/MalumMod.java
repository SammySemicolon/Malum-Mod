package com.sammy.malum;

import com.sammy.malum.common.block.storage.jar.SpiritJarBlock;
import com.sammy.malum.common.effect.GluttonyEffect;
import com.sammy.malum.common.effect.WickedIntentEffect;
import com.sammy.malum.common.effect.aura.CorruptedAerialAura;
import com.sammy.malum.common.effect.aura.InfernalAura;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.entity.nitrate.NitrateExplosion;
import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.curiosities.trinkets.runes.madness.RuneTwinnedDurationItem;
import com.sammy.malum.common.item.curiosities.trinkets.runes.miracle.RuneAlimentCleansingItem;
import com.sammy.malum.common.item.curiosities.trinkets.runes.miracle.RuneFervorItem;
import com.sammy.malum.common.item.curiosities.trinkets.sets.misc.TrinketsHarmonyNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.sets.prospector.TrinketsProspectorBelt;
import com.sammy.malum.common.item.curiosities.trinkets.sets.rotten.TrinketsVoraciousRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.weeping.TrinketsGruesomeConcentrationRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.weeping.TrinketsWatcherNecklace;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.MalignantConversionReloadListener;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.core.listeners.RitualRecipeReloadListener;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.data.worldgen.BiomeModifications;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.PacketRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.entity.events.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.event.common.ExplosionEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.lodestar.lodestone.events.LodestoneInteractionEvent;
import team.lodestar.lodestone.events.LodestoneItemEvent;
import team.lodestar.lodestone.events.LodestoneMobEffectEvents;
import team.lodestar.lodestone.registry.common.LodestoneBlockEntityRegistry;

import static com.sammy.malum.registry.client.ParticleRegistry.PARTICLES;
import static com.sammy.malum.registry.common.AttributeRegistry.ATTRIBUTES;
import static com.sammy.malum.registry.common.ContainerRegistry.CONTAINERS;
import static com.sammy.malum.registry.common.MobEffectRegistry.EFFECTS;
import static com.sammy.malum.registry.common.SoundRegistry.SOUNDS;
import static com.sammy.malum.registry.common.block.BlockEntityRegistry.BLOCK_ENTITY_TYPES;
import static com.sammy.malum.registry.common.block.BlockRegistry.BLOCKS;
import static com.sammy.malum.registry.common.entity.EntityRegistry.ENTITY_TYPES;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.ENCHANTMENTS;
import static com.sammy.malum.registry.common.item.ItemRegistry.ITEMS;
import static com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry.CREATIVE_MODE_TABS;
import static com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry.RECIPE_SERIALIZERS;
import static com.sammy.malum.registry.common.recipe.RecipeTypeRegistry.RECIPE_TYPES;
import static com.sammy.malum.registry.common.worldgen.FeatureRegistry.FEATURE_TYPES;
import static com.sammy.malum.registry.common.worldgen.StructureRegistry.STRUCTURES;

@SuppressWarnings("unused")
public class MalumMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MALUM = "malum";
    public static final RandomSource RANDOM = RandomSource.create();

    public static ResourceLocation malumPath(String path) {
        return new ResourceLocation(MALUM, path);
    }


    @Override
    public void onInitialize() {
        new LodestoneBlockEntityRegistry();
        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.COMMON, CommonConfig.SPEC);

        PacketRegistry.registerNetworkStuff();
        SpiritTypeRegistry.init();
        ENCHANTMENTS.register();
        BLOCKS.register();
        BLOCK_ENTITY_TYPES.register();
        ITEMS.register();
        ENTITY_TYPES.register();
        EFFECTS.register();
        PARTICLES.register();
        SOUNDS.register();
        CONTAINERS.register();
        ATTRIBUTES.register();
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
        FEATURE_TYPES.register();
        STRUCTURES.register();
        CREATIVE_MODE_TABS.register();

        BiomeModifications.init();
        ItemRegistry.Common.registerCompost();
        MobEffectRegistry.addPotionRecipes();
        ArmorSkinRegistry.registerItemSkins();

        FarmersDelightCompat.init();

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(HiddenTagRegistry::hideItems);
        LodestoneItemEvent.ON_ITEM_TOOLTIP.register(AbstractAugmentItem::addAugmentAttributeTooltip);
        PlayerEvents.BREAK_SPEED.register(InfernalAura::increaseDigSpeed);
        PlayerEvents.BREAK_SPEED.register(RuneFervorItem::increaseDigSpeed);
        LivingEntityEvents.JUMP.register(CorruptedAerialAura::onEntityJump);
        LivingEntityEvents.FALL.register(CorruptedAerialAura::onEntityFall);
        LivingEntityEvents.CHECK_SPAWN.register(SoulDataHandler::markAsSpawnerSpawned);

        EntityEvents.ON_JOIN_WORLD.register(CurioTokenOfGratitude::giveItem);
        EntityEvents.ON_JOIN_WORLD.register(SoulDataHandler::updateAi);


        MobEntitySetTargetCallback.EVENT.register(SoulDataHandler::preventTargeting);
        LivingEntityEvents.VISIBILITY.register(TrinketsHarmonyNecklace::preventDetection);
        LivingEntityUseItemEvents.LIVING_USE_ITEM_FINISH.register(TrinketsGruesomeConcentrationRing::finishEating);
        LivingEntityUseItemEvents.LIVING_USE_ITEM_FINISH.register(TrinketsVoraciousRing::finishEating);
        LivingHurtEvent.HURT.register(MalumAttributeEventHandler::processAttributes);
        LivingHurtEvent.HURT.register(SoulDataHandler::exposeSoul);
        LivingHurtEvent.HURT.register(WickedIntentEffect::livingHurt);
        LivingHurtEvent.HURT.register(SoulWardHandler::livingHurt);
        LivingEntityEvents.DROPS.register(SpiritHarvestHandler::primeItemForShatter);
        PlayerTickEvents.START.register(SoulWardHandler::recoverSoulWard);
        PlayerTickEvents.START.register(ReserveStaffChargeHandler::recoverStaffCharges);
        PlayerInteractionEvents.LEFT_CLICK_BLOCK.register(this::leftClickBlock);
        ServerLivingEntityEvents.ALLOW_DEATH.register(EsotericReapingHandler::tryCreateReapingDrops);
        ServerLivingEntityEvents.ALLOW_DEATH.register(SpiritHarvestHandler::shatterSoul);
        LivingEntityEvents.LivingTickEvent.TICK.register(MalignantConversionHandler::checkForAttributeChanges);
        LivingEntityEvents.LivingTickEvent.TICK.register(SoulDataHandler::manageSoul);
        LivingEntityEvents.LivingTickEvent.TICK.register(TouchOfDarknessHandler::entityTick);
        LivingEntityEvents.LivingTickEvent.TICK.register(TrinketsWatcherNecklace::entityTick);
        ExplosionEvents.DETONATE.register(TrinketsProspectorBelt::processExplosion);
        ExplosionEvents.DETONATE.register(NitrateExplosion::processExplosion);
        LodestoneInteractionEvent.RIGHT_CLICK_ITEM.register(ReboundEnchantment::onRightClickItem);
        LodestoneMobEffectEvents.APPLICABLE.register(GluttonyEffect::canApplyPotion);
        LodestoneMobEffectEvents.ADDED.register(RuneTwinnedDurationItem::onPotionApplied);
        LodestoneMobEffectEvents.ADDED.register(RuneAlimentCleansingItem::onPotionApplied);
        LodestoneInteractionEvent.ON_ITEM_USE_START.register(TrinketsVoraciousRing::accelerateEating);
        LodestoneItemEvent.EXPIRE.register(SpiritHarvestHandler::shatterItem);

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SpiritDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ReapingDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new MalignantConversionReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new RitualRecipeReloadListenerFabricImpl());

        CreativeTabRegistry.populateItemGroups();
    }

    private void leftClickBlock(PlayerInteractionEvents.LeftClickBlock leftClickBlock) {
        BlockPos pos = leftClickBlock.getPos();
        Level level = leftClickBlock.getLevel();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof SpiritJarBlock jarBlock) {
            Player player = leftClickBlock.getEntity();
            BlockHitResult target = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (target.getType() == HitResult.Type.BLOCK && target.getBlockPos().equals(pos) && target.getDirection().getAxis() == Direction.Axis.X) {
                if (player.isCreative()) {
                    leftClickBlock.setCanceled(jarBlock.handleAttack(level, pos, player));
                }
            }
        }
    }

    public static class SpiritDataReloadListenerFabricImpl extends SpiritDataReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("spirit_data");
        }
    }

    public static class ReapingDataReloadListenerFabricImpl extends ReapingDataReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("reaping_data");
        }
    }

    public static class MalignantConversionReloadListenerFabricImpl extends MalignantConversionReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("malignant_conversion_data");
        }
    }

    public static class RitualRecipeReloadListenerFabricImpl extends RitualRecipeReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("ritual_recipes");
        }
    }
}