package com.kittykitcatcat.malum.blocks;


import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.network.NetworkManager;
import com.kittykitcatcat.malum.network.packets.BloodCutPacket;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Random;

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
                        }
                        else
                        {
                            newState = Blocks.AIR.getDefaultState();
                        }
                        worldIn.setBlockState(pos,newState, 3);
                        worldIn.notifyBlockUpdate(pos, state, newState, 3);

                        NetworkManager.INSTANCE.send(
                                PacketDistributor.TRACKING_CHUNK.with(() -> worldIn.getChunkAt(pos)),
                                new BloodCutPacket(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, state.get(CUT)));
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }

    @Override
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand)
    {
        super.animateTick(stateIn, world, pos, rand);
        if (world.isRemote)
        {
            if (stateIn.get(CUT) != 0)
            {
                if (world.getBlockState(pos.down()).isAir())
                {
                    if (MathHelper.nextInt(world.rand, 0, 8 - stateIn.get(CUT)) == 0)
                    {
                        for (int i = 0; i <= stateIn.get(CUT); i++)
                        {
                            Vec3d velocity = MalumHelper.randVelocity(world, -0.2f, -0.1f);
                            Vec3d particlePos = MalumHelper.randPos(pos, world, -0.5f, 0.5f);
                            world.addParticle(new BloodParticleData(),
                                    particlePos.x,pos.getY()-0.1f,particlePos.z, 0f,velocity.y,0f);
                        }
                    }
                }
            }
        }
    }
}