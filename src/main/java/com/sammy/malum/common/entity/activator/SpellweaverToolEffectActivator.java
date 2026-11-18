package com.sammy.malum.common.entity.activator;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.spiritrite.effect.aerial.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.magic.*;
import com.sammy.malum.registry.common.sound.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.level.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.sound.SoundPlayer;

import java.util.*;

public class SpellweaverToolEffectActivator extends FloatingEntity {

    protected static final EntityDataAccessor<SpiritArcanaType> DATA_SPIRIT_GLOW = SynchedEntityData.defineId(SpellweaverToolEffectActivator.class, MalumEntityDataSerializers.SPIRIT_ARCANA.get());

    public static SpellweaverToolEffectActivator BREAKER_ENTITY;
    protected ItemStack tool = ItemStack.EMPTY;
    protected UUID owner;
    protected float speed;

    protected List<ItemEntity> carriedItems = new ArrayList<>();
    protected List<BlockPos> backupPositions = new ArrayList<>();
    protected int carriedExperience;

    public SpellweaverToolEffectActivator(Level level) {
        super(MalumEntityTypes.SPELLWEAVER_TOOL_EFFECT_ACTIVATOR.get(), level);
        maxAge = 4000;
    }

    public SpellweaverToolEffectActivator(Level level, ItemStack tool, UUID owner, float speed, BlockPos targetPos, Vec3 position, Vec3 velocity) {
        this(level);
        this.tool = tool;
        this.owner = owner;
        this.speed = speed;
        setDestination(new FloatingItemDestinationData(targetPos));
        setPos(position);
        setDeltaMovement(velocity);
        maxAge = 800;
    }

    public void addBackupPositions(List<BlockPos> backupPositions) {
        this.backupPositions.addAll(backupPositions);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_SPIRIT_GLOW, MalumSpiritTypes.ARCANE_SPIRIT.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        var spirit = getSpiritType();
        if (spirit != null) {
            spirit.save(pCompound);
        }

        if (tool != null) {
            CompoundTag toolTag = new CompoundTag();
            tool.save(registryAccess(), toolTag);
            pCompound.put("tool", toolTag);
        }
        pCompound.putUUID("ownerUUID", owner);
        pCompound.putFloat("speed", speed);

        if (!carriedItems.isEmpty()) {
            pCompound.putInt("carriedItemCount", carriedItems.size());
            for (int i = 0; i < carriedItems.size(); i++) {
                var entity = carriedItems.get(i);
                var carriedTag = new CompoundTag();
                entity.save(carriedTag);
                pCompound.put("carriedItem_" + i, carriedTag);
            }
        }

        pCompound.putInt("backupPositionCount", backupPositions.size());
        for (int i = 0; i < backupPositions.size(); i++) {
            pCompound.put("backupPosition_" + i, NBTHelper.saveBlockPos(backupPositions.get(i)));
        }
        pCompound.putInt("carriedExperience", carriedExperience);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setSpirit(SpiritArcanaType.load(pCompound).orElse(MalumSpiritTypes.ARCANE_SPIRIT.get()));

        CompoundTag toolTag = pCompound.getCompound("tool");
        tool = ItemStack.parse(registryAccess(), toolTag).orElse(ItemStack.EMPTY);

        carriedItems.clear();
        for (int i = 0; i < pCompound.getInt("carriedItemCount"); i++) {
            var carriedTag = pCompound.getCompound("carriedItem_" + i);
            var carriedItem = new ItemEntity(level(), 0, 0, 0, ItemStack.EMPTY);
            carriedItem.load(carriedTag);
            carriedItems.add(carriedItem);
        }
        owner = pCompound.getUUID("ownerUUID");
        speed = pCompound.getFloat("speed");

        backupPositions.clear();
        for (int i = 0; i < pCompound.getInt("backupPositionCount"); i++) {
            var posTag = pCompound.getCompound("backupPosition_" + i);
            backupPositions.add(NBTHelper.readBlockPos(posTag));
        }
        carriedExperience = pCompound.getInt("carriedExperience");

    }
    @Override
    public boolean mayInteract(Level level, BlockPos pos) {
        return false;
    }

    @Override
    public boolean shouldVanishAfterCollection(ServerLevel level) {
        return destination.getTargetLocation().left().isPresent();
    }

    @Override
    public void collect(ServerLevel level) {
        var sound = destination.getTargetLocation().map(
                u -> MalumGearSoundEvents.SPELLWOVEN_SPRITE_RETURNS,
                b -> MalumGearSoundEvents.SPELLWOVEN_SPRITE_HARVESTS
        );
        SoundPlayer.create(sound).volume(0.5f).play(this);
        playSound(sound.get(), 0.5f, 1f);
        destination.getTargetLocation().ifRight(pos -> {
            var state = level.getBlockState(pos);
            if (state.isEmpty()) {
                discard();
                return;
            }
            var harvestToolStack = BlockGravityRiteEffect.getToolForState(state);
            if (harvestToolStack.isEmpty()) {
                discard();
                return;
            }
            var breaker = level.getEntity(owner);
            if (breaker == null) {
                discard();
                return;
            }
            BREAKER_ENTITY = this;
            var blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
            level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
            boolean removed;
            if (breaker instanceof Player player) {
                state = state.getBlock().playerWillDestroy(level, pos, state, player);
                removed = state.onDestroyedByPlayer(level, pos, player, true, level.getFluidState(pos));
                if (removed) {
                    state.getBlock().destroy(level, pos, state);
                }
            } else {
                removed = level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
            if (removed) {
                Block.dropResources(state, level, pos, blockentity, breaker, tool);
                carriedExperience = EnchantmentHelper.processBlockExperience(level, tool, state.getExpDrop(level, pos, blockentity, breaker, tool));
            }
            destination = new FloatingItemDestinationData(owner);
            BREAKER_ENTITY = null;
            if (speed < 1) {
                movementWindUp = 0;
            }
            else {
                movementWindUp = Mth.ceil(movementWindUp / speed);
            }
        }).ifLeft(uuid -> {
            var entity = level.getEntity(uuid);
            var position = entity != null ? entity.position().add(0, entity.getBbHeight() / 2f, 0) : getOffsetPosition();
            for (var itemEntity : carriedItems) {
                itemEntity.setPickUpDelay(0);
                itemEntity.setPos(position);
                itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(0, 1, 0));
                level.addFreshEntity(itemEntity);
            }
            if (carriedExperience > 0) {
                ExperienceOrb.award(level, position, carriedExperience);
            }
        });
    }

    public static void redirectDrops(BlockDropsEvent event) {
        if (BREAKER_ENTITY != null) {
            BREAKER_ENTITY.carriedItems.addAll(event.getDrops());
            event.setCanceled(true);
        }
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel level) {
            if (tool.isEmpty()) {
                discard();
                return;
            }
            if (!backupPositions.isEmpty()) {
                var optional = destination.getTargetLocation().right();
                if (optional.isPresent()) {
                    var pos = optional.get();
                    var state = level.getBlockState(pos);
                    if (state.isEmpty()) {
                        var roll = random.nextInt(backupPositions.size());
                        var newTarget = backupPositions.get(roll);
                        setDestination(new FloatingItemDestinationData(newTarget));
                        backupPositions.remove(roll);
                    }
                }
            }

            float windUpDuration = getWindUpDuration();
            float delta = Mth.clamp(movementWindUp / windUpDuration, 0, 1);
            var length = getDeltaMovement().length();
            var disharmony = 0.25f * (1 - delta) / Math.max(speed, 0.1f);
            var addedOffset = new Vec3(
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony)
            );
            var newMovement = getDeltaMovement().add(addedOffset).normalize().scale(length);
            setDeltaMovement(newMovement);
        }
        super.tick();
        if (level().isClientSide) {
            var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), getOffsetPosition(), getSpiritType());
            lightSpecs.getBuilder().modifyScaleData(d -> d.multiplyValue(0.75f)).multiplyLifetime(0.75f);
            lightSpecs.getBloomBuilder().multiplyLifetime(0.5f);
            lightSpecs.spawnParticles();
        }
    }

    @Override
    public int getWindUpDuration() {
        return Mth.clamp(Mth.floor(40 - speed * 10), 10, 40);
    }

    @Override
    public float getFriction() {
        return 0.9f;
    }

    @Override
    public float getMovementSpeed(float windUp, float distance) {
        return (0.4f + Easing.EXPO_OUT.lerp(windUp, 0, 1.6f + speed * 0.4f));
    }

    @Override
    public float getMovementEasing(float windUp, float distance) {
        return 0.1f + Easing.EXPO_IN.lerp(windUp, 0, 0.4f);
    }

    public SpiritArcanaType getSpiritType() {
        return getEntityData().get(DATA_SPIRIT_GLOW);
    }

    public void setSpirit(SpiritArcanaType spirit) {
        getEntityData().set(DATA_SPIRIT_GLOW, spirit);
    }

    public UUID getOwner() {
        return owner;
    }
}