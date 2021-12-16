package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

import net.minecraft.block.AbstractBlock.Properties;

public class TotemPoleBlock extends Block
{
    public final Supplier<? extends Block> logBlock;
    public final boolean corrupted;
    public TotemPoleBlock(Properties properties, Supplier<? extends Block> logBlock, boolean corrupted)
    {
        super(properties.lootFrom(logBlock));
        this.logBlock = logBlock;
        this.corrupted = corrupted;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader Level, BlockPos pos, Player player) {
        return logBlock.get().getCloneItemStack(Level, pos, state);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new TotemPoleTileEntity();
    }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (level.isClientSide || handIn == InteractionHand.OFF_HAND)
        {
            return InteractionResult.FAIL;
        }
        if (level.getBlockEntity(pos) instanceof TotemPoleTileEntity)
        {
            if (player.getItemInHand(handIn).getItem() instanceof AxeItem)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) level.getBlockEntity(pos);
                if (totemPoleTileEntity.type != null)
                {
                    level.setBlockAndUpdate(pos, logBlock.get().defaultBlockState());
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->level.getChunkAt(pos)), new BlockParticlePacket(totemPoleTileEntity.type.color, pos.getX(),pos.getY(),pos.getZ()));
                    level.playSound(null, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS,1,1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
}
