package com.kittykitcatcat.malum.blocks.special;


import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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
                if (player.getHeldItem(handIn) != ItemStack.EMPTY)
                {
                    if (player.getHeldItem(handIn).getItem() instanceof SwordItem)
                    {
                        BlockState newState;
                        if (state.get(CUT) < 7)
                        {
                            newState = state.with(CUT, state.get(CUT)+1);
                            worldIn.playSound(null, pos, SoundEvents.BLOCK_CORAL_BLOCK_PLACE, SoundCategory.BLOCKS, 1,1);
                        }
                        else
                        {
                            newState = Blocks.AIR.getDefaultState();
                            for (int i = 0; i <= 20; i++)
                            {
                                Vec3d velocity = MalumHelper.randVelocity(worldIn.rand, -0.3f, 0.3f);
                                Vec3d particlePos = MalumHelper.randPos(pos, worldIn.rand, -0.5f, 0.5f);
                                worldIn.addParticle(new BloodParticleData(), particlePos.getX(),particlePos.getY(),particlePos.getZ(), velocity.getX(),velocity.getY(),velocity.getZ());
                            }
                            worldIn.playSound(null, pos, SoundEvents.BLOCK_CORAL_BLOCK_BREAK, SoundCategory.BLOCKS, 1,1);
                        }
                        worldIn.setBlockState(pos,newState, 3);
                        worldIn.notifyBlockUpdate(pos, state, newState, 3);
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }
}