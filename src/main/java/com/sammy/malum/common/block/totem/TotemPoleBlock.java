package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock extends Block
{
    public final Supplier<? extends Block> logBlock;
    public final boolean corrupted;
    public TotemPoleBlock(Properties properties, Supplier<? extends Block> logBlock, boolean corrupted)
    {
        super(properties.lootFrom(logBlock));
        this.logBlock = logBlock;
        this.corrupted = corrupted;
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return logBlock.get().getItem(world, pos, state);
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
        if (MalumHelper.areWeOnClient(worldIn) || handIn == Hand.OFF_HAND)
        {
            return ActionResultType.FAIL;
        }
        if (worldIn.getTileEntity(pos) instanceof TotemPoleTileEntity)
        {
            if (player.getHeldItem(handIn).getItem() instanceof AxeItem)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) worldIn.getTileEntity(pos);
                if (totemPoleTileEntity.type != null)
                {
                    worldIn.setBlockState(pos, logBlock.get().getDefaultState());
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->worldIn.getChunkAt(pos)), new BlockParticlePacket(totemPoleTileEntity.type.color, pos.getX(),pos.getY(),pos.getZ()));
                    worldIn.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS,1,1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
