package com.sammy.malum.common.block.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.network.PacketDistributor;

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
    public TileEntity createTileEntity(BlockState state, IBlockReader Level)
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
    public ActionResultType use(BlockState state, Level LevelIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit)
    {
        if (MalumHelper.areWeOnClient(LevelIn) || handIn == Hand.OFF_HAND)
        {
            return ActionResultType.FAIL;
        }
        if (LevelIn.getBlockEntity(pos) instanceof TotemPoleTileEntity)
        {
            if (player.getItemInHand(handIn).getItem() instanceof AxeItem)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) LevelIn.getBlockEntity(pos);
                if (totemPoleTileEntity.type != null)
                {
                    LevelIn.setBlockAndUpdate(pos, logBlock.get().defaultBlockState());
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->LevelIn.getChunkAt(pos)), new BlockParticlePacket(totemPoleTileEntity.type.color, pos.getX(),pos.getY(),pos.getZ()));
                    LevelIn.playSound(null, pos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS,1,1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.use(state, LevelIn, pos, player, handIn, hit);
    }
}
