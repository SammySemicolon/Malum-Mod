package com.sammy.malum.common.entity.mob.cultist.evangelist;

import com.sammy.malum.common.entity.mob.cultist.evangelist.EvangelistHeavyStanceData.HeavyStanceState;
import com.sammy.malum.common.entity.mob.cultist.CultistAnimationState;
import com.sammy.malum.common.entity.mob.cultist.CultistMonster;
import com.sammy.malum.common.entity.mob.cultist.IAltarBlessingRecipient;
import com.sammy.malum.common.entity.mob.cultist.ICherubFriend;
import com.sammy.malum.common.entity.mob.cultist.evangelist.goal.EvangelistHeavyStanceAttackGoal;
import com.sammy.malum.common.entity.mob.cultist.evangelist.goal.EvangelistMeleeAttackGoal;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.wayward_attributes.core.registry.WaywardAttributeTypes;

public class EvangelistCultist extends CultistMonster implements IAltarBlessingRecipient, ICherubFriend {

    public static final int HEAVY_STANCE_NEEDED_PROGRESS = 2;
    public static final float HEAVY_STANCE_CHANCE = 0.75f;
    public static final int HEAVY_STANCE_SWINGS = 3;

    public static final byte MELEE_SWING_ANIMATION = 11;
    public static final byte PARRY_ANIMATION = 12;

    public static final byte ENTER_HEAVY_STANCE_ANIMATION = 13;
    public static final byte PARRY_INTO_HEAVY_STANCE_ANIMATION = 14;
    public static final byte HEAVY_MELEE_SWING_ANIMATION = 16;
    public static final byte HEAVY_MELEE_ENDING_SWING_ANIMATION = 17;

    public CultistAnimationState idleAnimationState = new CultistAnimationState(this);

    public CultistAnimationState meleeAttackAnimationState = new CultistAnimationState(this);
    public CultistAnimationState parryAnimationState = new CultistAnimationState(this);

    public HeavyStanceAnimationSet heavyStanceAnimationSet = new HeavyStanceAnimationSet(this);

    public EvangelistHeavyStanceData heavyStanceData;

    public EvangelistCultist(Level level) {
        super(MalumCultistEntityTypes.EVANGELIST.get(), MalumCultistSoundEvents.EVANGELIST, level);
        heavyStanceData = new EvangelistHeavyStanceData(this);
    }

    @Override
    protected void registerGoals() {
        var targeting = new NearestAttackableTargetGoal<>(this, Player.class, true);

        var heavyStanceAttackGoal = new EvangelistHeavyStanceAttackGoal(this, 2.25f);
        var meleeAttackGoal = new EvangelistMeleeAttackGoal(this, 1f);

        var randomStroll = new WaterAvoidingRandomStrollGoal(this, 0.5f);
        var lookAtPlayer = new LookAtPlayerGoal(this, Player.class, 24.0F);
        var randomLookAround = new RandomLookAroundGoal(this);

        targetSelector.addGoal(0, targeting);

        goalSelector.addGoal(0, heavyStanceAttackGoal);
        goalSelector.addGoal(1, meleeAttackGoal);

        goalSelector.addGoal(3, randomStroll);
        goalSelector.addGoal(4, lookAtPlayer);
        goalSelector.addGoal(5, randomLookAround);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (heavyStanceAnimationSet.acceptEvent(id)) {
            return;
        }
        switch (id) {
            case MELEE_SWING_ANIMATION -> startAnimation(meleeAttackAnimationState);
            case PARRY_ANIMATION -> startAnimation(parryAnimationState);

            default -> super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        heavyStanceData.save(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        heavyStanceData.load(compound);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.ATTACK_DAMAGE, -5.0)
                .add(WaywardAttributeTypes.MAGIC_DAMAGE, 2.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(WaywardAttributeTypes.MAGIC_RESISTANCE, 0.75)
                .add(Attributes.ARMOR, 20.0)
                .add(Attributes.STEP_HEIGHT, 1);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        heavyStanceData.updateValues();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        heavyStanceAnimationSet.animateIdleOrFallback(idleAnimationState);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        boolean success = super.doHurtTarget(target);
        if (success) {
            heavyStanceData.workTowardsHeavyStance();
        }
        return success;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean success = super.hurt(source, amount);
        if (success && random.nextFloat() < 0.25f) {
            heavyStanceData.workTowardsHeavyStance();
        }
        return success;
    }

    @Override
    public int getCherubCapacity() {
        return heavyStanceData.is(HeavyStanceState.ACTIVE) ? 5 : 3;
    }

    @Override
    public CherubPriority getCherubPriority() {
        return heavyStanceData.is(HeavyStanceState.ACTIVE) ? CherubPriority.HIGHEST : CherubPriority.STANDARD;
    }

    @Override
    public Vec3 getCherubHoverOffset(int cherub) {
        float delta = ((tickCount + cherub * 60) % 300) / 300f;
        float angle = delta * 6.28f;
        float offset = getBbWidth()*1.25f;
        float x = Mth.sin(angle) * offset;
        float y = getBbHeight() + 0.75f + 0.25f * (cherub+1);
        float z = Mth.cos(angle) * offset;
        return new Vec3(x, y, z);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        setItemSlot(EquipmentSlot.MAINHAND, MalumContent.Enscription.SHAPED_SLAB.get().getDefaultInstance());
        enchantSpawnedWeapon(level, random, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        return getAttackBoundingBox().inflate(0.5f).intersects(entity.getHitbox());
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {

    }
}