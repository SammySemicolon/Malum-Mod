package com.sammy.malum.common.block.curiosities.ritual_plinth;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.*;

public class RitualPlinthBlockEntity extends LodestoneBlockEntity {
    public RitualPlinthBlockEntity(BlockEntityType<? extends RitualPlinthBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RitualPlinthBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RITUAL_PLINTH.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void onBreak(@Nullable Player player) {
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        return super.onUse(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            RitualPlinthParticleEffects.passiveRitualPlinthParticles(this);
        }
    }

    public Vec3 getParticlePositionPosition(Direction direction) {
        final BlockPos blockPos = getBlockPos();
        float x = blockPos.getX() + 0.5f + direction.getStepX()*0.7f;
        float y = blockPos.getY() + 0.85f;
        float z = blockPos.getZ() + 0.5f + direction.getStepZ()*0.7f;
        return new Vec3(x, y, z);
    }
}
