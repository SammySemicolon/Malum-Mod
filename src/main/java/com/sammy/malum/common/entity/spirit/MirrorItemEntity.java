package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.EntityRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.item.IFloatingGlowItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MirrorItemEntity extends FloatingItemEntity {

    public Direction cachedDirection;
    public Direction direction;
    public BlockPos cachedBlockPos;
    public float desiredMoveTime=1f;

    public MirrorItemEntity(Level level) {
        super(EntityRegistry.MIRROR_ITEM.get(), level);
        maxAge = 4000;
        direction = Direction.NORTH;
        moveTime = 1f;
    }

    public MirrorItemEntity(Level level, Direction direction, ItemStack stack, BlockPos pos) {
        super(EntityRegistry.MIRROR_ITEM.get(), level);
        this.direction = direction;
        setItem(stack);
        setPos(pos.getX() + 0.5f, pos.getY() + 0.25f, pos.getZ() + 0.5f);
        maxAge = 4000;
        moveTime = 1f;
        float multiplier = 0.02f;
        setDeltaMovement(direction.getStepX() * multiplier, direction.getStepY() * multiplier, direction.getStepZ() * multiplier);
    }

    @Override
    public void setItem(ItemStack pStack) {
        if (!(pStack.getItem() instanceof IFloatingGlowItem)) {
            setColor(ColorHelper.brighter(SpiritTypeRegistry.ARCANE_SPIRIT_COLOR, 2), SpiritTypeRegistry.ARCANE_SPIRIT.endColor);
        }
        super.setItem(pStack);
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        SpiritHelper.spawnSpiritParticles(level, x, y, z, 1.5f, Vec3.ZERO, color, endColor);
    }

    @Override
    public void move() {
        if (moveTime < desiredMoveTime)
        {
            moveTime+=0.1f;
        }
        if (moveTime > desiredMoveTime)
        {
            moveTime-=0.1f;
        }
        if (cachedBlockPos != blockPosition() || level.getGameTime() % 10L == 0) {
            cachedBlockPos = blockPosition();
            BlockPos ahead = cachedBlockPos.relative(direction, 1);
            BlockState state = level.getBlockState(ahead);
            if (state.isFaceSturdy(level, ahead, direction)) {
                desiredMoveTime = 0f;
            } else {
                desiredMoveTime = 1f;
            }
        }
        if (cachedDirection != direction) {
            cachedDirection = direction;
        }
        float multiplier = 0.02f * moveTime;
        setDeltaMovement(direction.getStepX() * multiplier, direction.getStepY() * multiplier, direction.getStepZ() * multiplier);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("direction", direction.toString());
        compound.putFloat("desiredMoveTime", desiredMoveTime);
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        direction = Direction.byName(compound.getString("direction"));
        cachedDirection = direction;
        desiredMoveTime = compound.getFloat("desiredMoveTime");
        super.readAdditionalSaveData(compound);
    }
}