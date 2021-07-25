package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.network.packets.particle.totem.TotemPoleParticlePacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock extends Block
{
    public TotemPoleBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TotemPoleTileEntity();
    }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (MalumHelper.areWeOnClient(worldIn))
        {
            return ActionResultType.SUCCESS;
        }
        if (worldIn.getTileEntity(pos) instanceof TotemPoleTileEntity)
        {
            if (player.getHeldItem(handIn).getItem() instanceof AxeItem)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) worldIn.getTileEntity(pos);
                if (totemPoleTileEntity.type != null)
                {
                    worldIn.setBlockState(pos, MalumBlocks.RUNEWOOD_LOG.get().getDefaultState());
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->worldIn.getChunkAt(pos)), new TotemPoleParticlePacket(totemPoleTileEntity.type.identifier, pos.getX(),pos.getY(),pos.getZ(), false));
                    worldIn.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS,1,1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
