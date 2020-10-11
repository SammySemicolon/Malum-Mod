package com.sammy.malum.blocks.special;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import static com.sammy.malum.MalumMod.random;

public class FleshBlock extends Block
{
    public FleshBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(CUT, 0));
    }

    public static final IntegerProperty CUT = IntegerProperty.create("cut", 0, 7);

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_)
    {
        p_206840_1_.add(CUT);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (player.getHeldItem(handIn).getItem() instanceof SwordItem)
                {
                    int currentCut = state.get(CUT);
                    if (currentCut < 7)
                    {
                        currentCut++;
                    }
                    if (worldIn instanceof ServerWorld)
                    {
                        for (int i = 0; i < currentCut * 3; i++)
                        {
                            Vector3d position = new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(MathHelper.nextDouble(random, 0,1),MathHelper.nextDouble(random, 0,1),MathHelper.nextDouble(random, 0,1));
                            ((ServerWorld) worldIn).spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, state), position.x,position.y,position.z, currentCut * 3, 0,0,0, 0.4f);
                        }
                    }
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_CORAL_BLOCK_PLACE, SoundCategory.BLOCKS, 1 + random.nextFloat() * 0.2f, 1 + random.nextFloat() * 0.2f);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_CORAL_BLOCK_BREAK, SoundCategory.BLOCKS, 1 + random.nextFloat() * 0.2f, 1 + random.nextFloat() * 0.2f);
                    worldIn.playSound(null, pos, SoundEvents.BLOCK_CORAL_BLOCK_HIT, SoundCategory.BLOCKS, 1 + random.nextFloat() * 0.2f, 1 + random.nextFloat() * 0.2f);
                    BlockState newState = state.with(CUT,currentCut);
                    worldIn.setBlockState(pos, newState, 3);
                    worldIn.notifyBlockUpdate(pos, state, newState, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }
}