package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.tile.SpiritJarTileEntity;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.stream.Stream;

public class SpiritJarBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Stream.of(Block.makeCuboidShape(2.5, 0.5, 2.5, 13.5, 13.5, 13.5), Block.makeCuboidShape(3.5, 14.5, 3.5, 12.5, 16.5, 12.5), Block.makeCuboidShape(4.5, 13.5, 4.5, 11.5, 14.5, 11.5), Block.makeCuboidShape(5.5, 0, 5.5, 10.5, 1, 10.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if (worldIn instanceof ServerWorld)
        {
            spawnAdditionalDrops(state, (ServerWorld) worldIn, pos, player.getActiveItemStack());
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack)
    {
        if (worldIn.getTileEntity(pos) instanceof SpiritJarTileEntity)
        {
            SpiritJarTileEntity tileEntity = (SpiritJarTileEntity) worldIn.getTileEntity(pos);
            while (tileEntity.count > 0)
            {
                int stackCount = Math.min(tileEntity.count, 64);
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,new ItemStack(tileEntity.type.splinterItem(), stackCount)));
                tileEntity.count -= stackCount;
            }
        }
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack heldItem = player.getHeldItem(handIn);
        if (worldIn.getTileEntity(pos) instanceof SpiritJarTileEntity)
        {
            SpiritJarTileEntity tileEntity = (SpiritJarTileEntity) worldIn.getTileEntity(pos);
            if (heldItem.getItem() instanceof MalumSpiritItem)
            {
                MalumSpiritItem spiritSplinterItem = (MalumSpiritItem) heldItem.getItem();
                if (tileEntity.type == null)
                {
                    tileEntity.type = spiritSplinterItem.type;
                    tileEntity.count = heldItem.getCount();
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                    particles(worldIn,hit, tileEntity.type);
                    return ActionResultType.SUCCESS;
                }
                else if (tileEntity.type.equals(spiritSplinterItem.type))
                {
                    tileEntity.count += heldItem.getCount();
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                    particles(worldIn,hit, tileEntity.type);
                    return ActionResultType.SUCCESS;
                }
            }
            else if (tileEntity.type != null)
            {
                int max = player.isSneaking() ? 64 : 1;
                int count = Math.min(tileEntity.count, max);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(tileEntity.type.splinterItem(), count));
                tileEntity.count -= count;
                particles(worldIn,hit, tileEntity.type);
                if (tileEntity.count == 0)
                {
                    tileEntity.type = null;
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    public void particles(World world, BlockRayTraceResult hit, MalumSpiritType type)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = type.color;
            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.4f, 0f)
                    .setLifetime(20)
                    .setScale(0.3f, 0)
                    .setSpin(0.2f)
                    .randomOffset(0.1f, 0.1f)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .repeat(world, hit.getHitVec().getX(), hit.getHitVec().y, hit.getHitVec().z, 10);
        }
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritJarTileEntity();
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
        super.fillStateContainer(builder);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return getDefaultState().with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }
}
