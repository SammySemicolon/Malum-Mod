package com.sammy.malum.common.block.curiosities.totem;

import com.sammy.malum.*;
import com.sammy.malum.common.data.listener.rite.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.sound.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;

import javax.annotation.*;
import java.util.*;

public class TotemBaseBlockEntity extends LodestoneBlockEntity {

    private static final int INTERVAL = 20;

    public enum TotemBaseState {
        INACTIVE,
        ASSEMBLING,
        ACTIVE
    }

    protected TotemBaseState state = TotemBaseState.INACTIVE;
    protected SpiritRiteType rite;
    protected Direction totemDirection;

    protected int totemHeight;
    protected int timer;
    protected int timerPause;

    public TotemBaseBlockEntity(LodestoneBlockEntityType<? extends TotemBaseBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.TOTEM_BASE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putInt("state", state.ordinal());
        if (rite != null) {
            rite.save(compound);
        }
        if (totemDirection != null) {
            compound.putInt("direction", totemDirection.ordinal());
        }
        compound.putInt("totemHeight", totemHeight);
        compound.putInt("timer", timer);
        compound.putInt("timerPause", timerPause);
        super.saveAdditional(compound, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        if (compound.contains("state")) {
            state = TotemBaseState.values()[compound.getInt("state")];
        }
        rite = SpiritRiteType.load(compound).orElse(null);

        if (compound.contains("direction")) {
            totemDirection = Direction.values()[compound.getInt("direction")];
        }
        totemHeight = compound.getInt("totemHeight");
        timer = compound.getInt("timer");
        timerPause = compound.getInt("timerPause");
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public void serverTick(ServerLevel level) {
        switch (state) {
            case ACTIVE -> updateRite(level);
            case ASSEMBLING -> {
                timer--;
                if (timer <= 0) {
                    var polePos = worldPosition.above(totemHeight + 1);
                    if (level.getBlockEntity(polePos) instanceof TotemPoleBlockEntity pole) {
                        timer = INTERVAL;
                        addTotemPole(level, pole);
                    } else {
                        var rite = SpiritRiteTypeReloadListener.DATA.findMatching(level, this);
                        if (rite == null) {
                            setState(level, TotemBaseState.INACTIVE);
                            return;
                        }
                        this.rite = rite;
                        setTotemPoleState(level, TotemPoleBlockEntity.TotemPoleState.ACTIVE);
                        setState(level, TotemBaseState.ACTIVE);
                    }
                }
            }
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack pStack, InteractionHand pHand) {
        if (state.equals(TotemBaseState.ASSEMBLING)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (getFirstTotemPole().isEmpty()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (level instanceof ServerLevel serverLevel) {
            if (state.equals(TotemBaseState.ACTIVE)) {
                setState(serverLevel, TotemBaseState.INACTIVE);
            } else {
                setState(serverLevel, TotemBaseState.ASSEMBLING);
            }
            BlockStateHelper.updateState(level, worldPosition);
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (level instanceof ServerLevel serverLevel) {
            setState(serverLevel, TotemBaseState.INACTIVE);
        }
    }

    public void receiveSparkUpdate() {
        timerPause = 100;
    }

    public TotemBaseState getState() {
        return state;
    }

    public SpiritRiteType getRite() {
        return rite;
    }

    public int getTotemHeight() {
        return totemHeight;
    }

    public Direction getTotemDirection() {
        if (totemDirection != null) {
            return totemDirection;
        }
        BlockState above = level.getBlockState(getBlockPos().above());
        if (above.getBlock() instanceof TotemPoleBlock) {
            totemDirection = above.getValue(TotemPoleBlock.FACING);
            return totemDirection;
        }
        MalumMod.LOGGER.warn("Totem Base at {} has no totem pole above it, defaulting to north direction", worldPosition);
        return Direction.NORTH;
    }

    public void updateRite(ServerLevel level) {
        if (timerPause > 0) {
            timerPause--;
            return;
        }
        if (timer > 0) {
            timer--;
        }
        if (canTriggerRite()) {
            triggerRiteEffect(level);
            notifyObservers();
        }
    }

    public boolean canTriggerRite() {
        return timer == 0;
    }

    public void triggerRiteEffect(ServerLevel level) {
        if (rite == null) {
            return;
        }
        timer = rite.getEffect().getCooldown();
        rite.triggerRiteEffect(level, this);
    }

    public void addTotemPole(ServerLevel level, TotemPoleBlockEntity pole) {
        totemHeight++;
        pole.bind(level,this, totemHeight);
        BlockStateHelper.updateState(level, worldPosition);
    }

    public void setState(ServerLevel level, TotemBaseState newState) {
        if (state.equals(newState)) {
            return;
        }
        if (newState.equals(TotemBaseState.INACTIVE)) {
            playSound(MalumSoundEvents.TOTEM_CANCELLED.get());
            setTotemPoleState(level, TotemPoleBlockEntity.TotemPoleState.INACTIVE);
            totemHeight = 0;
            rite = null;
        }
        if (newState.equals(TotemBaseState.ACTIVE)) {
            playSound(MalumSoundEvents.TOTEM_ACTIVATED.get());
        }
        this.state = newState;
        this.timer = 0;
        BlockStateHelper.updateAndNotifyState(level, worldPosition);
    }

    public void setTotemPoleState(ServerLevel level, TotemPoleBlockEntity.TotemPoleState state) {
        for (TotemPoleBlockEntity totemPole : getTotemPoles(level)) {
            totemPole.setState(state);
        }
    }

    public List<SpiritArcanaType> getSpirits(ServerLevel level) {
        return getTotemPoles(level).stream().map(TotemPoleBlockEntity::getSpirit).toList();
    }

    public List<TotemPoleBlockEntity> getTotemPoles(ServerLevel level) {
        List<TotemPoleBlockEntity> totemPoles = new ArrayList<>();
        BlockPos.MutableBlockPos mutable = getBlockPos().mutable();
        for (int i = 0; i < totemHeight; i++) {
            mutable.move(Direction.UP);
            if (level.getBlockEntity(mutable) instanceof TotemPoleBlockEntity totemPole) {
                totemPoles.add(totemPole);
            }
        }
        return totemPoles;
    }

    public boolean isActiveOrAssembling() {
        return !state.equals(TotemBaseState.INACTIVE);
    }

    public Optional<TotemPoleBlockEntity> getFirstTotemPole() {
        if (level.getBlockEntity(worldPosition.above()) instanceof TotemPoleBlockEntity totemPole) {
            return Optional.of(totemPole);
        }
        return Optional.empty();
    }
}