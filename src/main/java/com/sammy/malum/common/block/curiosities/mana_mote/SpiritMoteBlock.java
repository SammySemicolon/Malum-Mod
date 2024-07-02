package com.sammy.malum.common.block.curiosities.mana_mote;

import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.SpiritMoteParticleEffects;
import io.github.fabricators_of_create.porting_lib.block.CustomDestroyEffectsBlock;
import io.github.fabricators_of_create.porting_lib.block.CustomHitEffectsBlock;
import io.github.fabricators_of_create.porting_lib.block.CustomSoundTypeBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.HitResult;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.*;

public class SpiritMoteBlock extends LodestoneEntityBlock<MoteOfManaBlockEntity> implements CustomSoundTypeBlock, CustomDestroyEffectsBlock, CustomHitEffectsBlock {

    public static final SpiritTypeProperty SPIRIT_TYPE = SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY;

    public SpiritMoteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SPIRIT_TYPE, "sacred"));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPIRIT_TYPE);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
        return this.getSoundType(this.defaultBlockState());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
        return true;
    }

    @Override
    public boolean addDestroyEffects(BlockState state, ClientLevel level, BlockPos pos, ParticleEngine engine) {
        if (state.getBlock() instanceof SpiritMoteBlock) {
            SpiritMoteParticleEffects.destroy(level, pos, state, SpiritHarvestHandler.getSpiritType(state.getValue(SpiritMoteBlock.SPIRIT_TYPE)));
        }
        return true;
    }
}
