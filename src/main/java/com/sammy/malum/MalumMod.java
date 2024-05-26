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
import com.sammy.malum.common.item.curiosities.curios.runes.madness.RuneTwinnedDurationItem;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.RuneAlimentCleansingItem;
import com.sammy.malum.common.item.curiosities.curios.runes.miracle.RuneFervorItem;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.CurioHarmonyNecklace;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioGruesomeConcentrationRing;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.MalignantConversionReloadListener;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.core.listeners.RitualRecipeReloadListener;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.registry.client.ParticleEmitterRegistry;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.PacketRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.tabs.*;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.entity.events.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.event.common.ExplosionEvents;
import io.github.fabricators_of_create.porting_lib.util.FluidTextUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.*;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.*;
import team.lodestar.lodestone.events.EntityAttributeModificationEvent;
import team.lodestar.lodestone.events.LodestoneInteractionEvent;
import team.lodestar.lodestone.events.LodestoneItemEvent;
import team.lodestar.lodestone.events.LodestoneMobEffectEvents;

import static com.sammy.malum.registry.client.ParticleRegistry.*;
import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static com.sammy.malum.registry.common.ContainerRegistry.*;
import static com.sammy.malum.registry.common.MobEffectRegistry.*;
import static com.sammy.malum.registry.common.SoundRegistry.*;
import static com.sammy.malum.registry.common.block.BlockEntityRegistry.*;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;
import static com.sammy.malum.registry.common.entity.EntityRegistry.*;
import static com.sammy.malum.registry.common.item.EnchantmentRegistry.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeTypeRegistry.*;
import static com.sammy.malum.registry.common.worldgen.FeatureRegistry.*;
import static com.sammy.malum.registry.common.worldgen.StructureRegistry.*;

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

        ConfigRegistry.registerConfig(MalumMod.MALUM, ConfigType.COMMON, CommonConfig.SPEC);

        PacketRegistry.registerNetworkStuff();

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

        ItemRegistry.Common.registerCompost();
        MobEffectRegistry.addPotionRecipes();
        ArmorSkinRegistry.registerItemSkins();

        FarmersDelightCompat.init();


        LodestoneItemEvent.ON_ITEM_TOOLTIP.register(AbstractAugmentItem::addAugmentAttributeTooltip);
        PlayerEvents.BREAK_SPEED.register(InfernalAura::increaseDigSpeed);
        PlayerEvents.BREAK_SPEED.register(RuneFervorItem::increaseDigSpeed);
        EntityAttributeModificationEvent.ADD.register(AttributeRegistry::modifyEntityAttributes);
        LivingEntityEvents.JUMP.register(CorruptedAerialAura::onEntityJump);
        LivingEntityEvents.FALL.register(CorruptedAerialAura::onEntityFall);
        LivingEntityEvents.CHECK_SPAWN.register(SoulDataHandler::markAsSpawnerSpawned);

        EntityEvents.ON_JOIN_WORLD.register(CurioTokenOfGratitude::giveItem);
        EntityEvents.ON_JOIN_WORLD.register(SoulDataHandler::updateAi);
        EntityEvents.ON_JOIN_WORLD.register(ParticleEmitterRegistry::addParticleEmitters);


        MobEntitySetTargetCallback.EVENT.register(SoulDataHandler::preventTargeting);
        LivingEntityEvents.VISIBILITY.register(CurioHarmonyNecklace::preventDetection);
        LivingEntityUseItemEvents.LIVING_USE_ITEM_FINISH.register(CurioGruesomeConcentrationRing::finishEating);
        LivingEntityUseItemEvents.LIVING_USE_ITEM_FINISH.register(CurioVoraciousRing::finishEating);
        LivingHurtEvent.HURT.register(MalumAttributeEventHandler::processAttributes);
        LivingHurtEvent.HURT.register(SoulDataHandler::exposeSoul);
        LivingHurtEvent.HURT.register(WickedIntentEffect::removeWickedIntent);
        LivingHurtEvent.HURT.register(SoulWardHandler::shieldPlayer);
        LivingEntityEvents.DROPS.register(SpiritHarvestHandler::modifyDroppedItems);
        PlayerTickEvents.START.register(SoulWardHandler::recoverSoulWard);
        PlayerTickEvents.START.register(ReserveStaffChargeHandler::recoverStaffCharges);
        PlayerInteractionEvents.LEFT_CLICK_BLOCK.register(this::leftClickBlock);
        ServerLivingEntityEvents.ALLOW_DEATH.register(EsotericReapingHandler::tryCreateReapingDrops);
        ServerLivingEntityEvents.ALLOW_DEATH.register(SpiritHarvestHandler::shatterSoul);
        LivingEntityEvents.LivingTickEvent.TICK.register(MalignantConversionHandler::checkForAttributeChanges);
        LivingEntityEvents.LivingTickEvent.TICK.register(SoulDataHandler::manageSoul);
        LivingEntityEvents.LivingTickEvent.TICK.register(TouchOfDarknessHandler::entityTick);
        ExplosionEvents.DETONATE.register(CurioProspectorBelt::processExplosion);
        ExplosionEvents.DETONATE.register(NitrateExplosion::processExplosion);
        LodestoneInteractionEvent.RIGHT_CLICK_ITEM.register(ReboundEnchantment::onRightClickItem);
        LodestoneMobEffectEvents.APPLICABLE.register(GluttonyEffect::canApplyPotion);
        LodestoneMobEffectEvents.ADDED.register(RuneTwinnedDurationItem::onPotionApplied);
        LodestoneMobEffectEvents.ADDED.register(RuneAlimentCleansingItem::onPotionApplied);
        LodestoneInteractionEvent.ON_ITEM_USE_START.register(CurioVoraciousRing::accelerateEating);
        LodestoneItemEvent.EXPIRE.register(SpiritHarvestHandler::shatterItem);



        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SpiritDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ReapingDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new MalignantConversionReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new RitualRecipeReloadListenerFabricImpl());

        //TODO modBus.addListener(CreativeTabRegistry::populateItemGroups);
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