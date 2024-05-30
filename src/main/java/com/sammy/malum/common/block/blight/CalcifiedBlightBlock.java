package com.sammy.malum.common.block.blight;

import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.block.CustomSoundTypeBlock;
import io.github.fabricators_of_create.porting_lib.extensions.extensions.IShearable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.BLIGHTED_BLOCKS;

public class CalcifiedBlightBlock extends BushBlock implements IShearable, CustomSoundTypeBlock {

    protected static final List<VoxelShape> SHAPES = IntStream.range(0, 4).boxed().map(i -> Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6 + i * 2, 14.0D)).collect(Collectors.toList());
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

    public CalcifiedBlightBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (stack.getItem().equals(ItemRegistry.CALCIFIED_BLIGHT.get())) {
            final int stage = pState.getValue(STAGE);
            if (stage < 3) {
                pLevel.setBlock(pPos, pState.setValue(STAGE, stage + 1), 3);
            } else {
                TallCalcifiedBlightBlock.placeAt(pLevel, BlockRegistry.TALL_CALCIFIED_BLIGHT.get().defaultBlockState(), pPos, 3);
            }
            if (!pPlayer.getAbilities().instabuild) {
                stack.shrink(1);
            }
            SoundType soundtype = getSoundType(pState, pLevel, pPos, pPlayer);
            pLevel.playSound(pPlayer, pPos, SoundRegistry.CALCIFIED_BLIGHT_PLACE.get(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * (1 + stage * 0.1f));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(STAGE));
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
        return this.getSoundType(this.defaultBlockState());
    }
}