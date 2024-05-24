package com.sammy.malum;

import com.sammy.malum.common.block.storage.jar.SpiritJarBlock;
import com.sammy.malum.common.effect.WickedIntentEffect;
import com.sammy.malum.common.effect.aura.CorruptedAerialAura;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.entity.nitrate.NitrateExplosion;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.curiosities.curios.sets.misc.CurioHarmonyNecklace;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioGruesomeConcentrationRing;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.PacketRegistry;
import com.sammy.malum.registry.common.item.tabs.*;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.entity.events.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.event.common.ExplosionEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.*;
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
import team.lodestar.lodestone.events.LodestoneInteractionEvent;

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

        FarmersDelightCompat.init();

        LivingEntityEvents.JUMP.register(CorruptedAerialAura::onEntityJump);
        LivingEntityEvents.FALL.register(CorruptedAerialAura::onEntityFall);
        LivingEntityEvents.CHECK_SPAWN.register(SoulDataHandler::markAsSpawnerSpawned);
        EntityEvents.ON_JOIN_WORLD.register(CurioTokenOfGratitude::giveItem);
        EntityEvents.ON_JOIN_WORLD.register(SoulDataHandler::updateAi);
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

        modBus.addListener(CreativeTabRegistry::populateItemGroups);
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
}