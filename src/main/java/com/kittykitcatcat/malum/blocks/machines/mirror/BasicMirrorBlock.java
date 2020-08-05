package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.items.staves.BasicStave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import static net.minecraft.state.properties.AttachFace.CEILING;
import static net.minecraft.state.properties.AttachFace.FLOOR;


public class BasicMirrorBlock extends HorizontalFaceBlock
{
    public mirrorTypeEnum type;

    public BasicMirrorBlock(Properties properties, mirrorTypeEnum type)
    {
        super(properties);
        this.type = type;
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(FACE, AttachFace.WALL));
    }

    public enum mirrorTypeEnum
    {
        basic(0),
        input(1),
        output(2);

        public final int type;

        mirrorTypeEnum(int type) { this.type = type;}
    }

    public static TileEntity getTileEntityForTransfer(World world, BlockPos pos)
    {
        Direction direction = world.getBlockState(pos).get(HORIZONTAL_FACING);
        Vec3i directionVector = direction.getDirectionVec();
        if (world.getBlockState(pos).get(FACE).equals(CEILING))
        {
            directionVector = new Vec3i(0, -1, 0);
        }
        if (world.getBlockState(pos).get(FACE).equals(FLOOR))
        {
            directionVector = new Vec3i(0, 1, 0);
        }
        if (!(world.getTileEntity(pos.subtract(directionVector)) instanceof BasicMirrorTileEntity))
        {
            return world.getTileEntity(pos.subtract(directionVector));
        }
        return null;
    }

    protected static final VoxelShape AABB_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape AABB_WEST = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_DOWN = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape AABB_UP = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return true;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (state.get(FACE))
        {
            case FLOOR:
                return AABB_DOWN;
            case WALL:
                switch (direction)
                {
                    case EAST:
                        return AABB_EAST;
                    case WEST:
                        return AABB_WEST;
                    case SOUTH:
                        return AABB_SOUTH;
                    case NORTH:
                    default:
                        return AABB_NORTH;
                }
            case CEILING:
            default:
                return AABB_UP;
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HORIZONTAL_FACING, FACE);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        switch (type)
        {
            case basic:
            {
                return new BasicMirrorTileEntity();
            }
            case input:
            {
                return new InputMirrorTileEntity();
            }
            case output:
            {
                return new OutputMirrorTileEntity();
            }
        }
        return new BasicMirrorTileEntity();
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!isMoving)
        {
            if (worldIn.getTileEntity(pos) instanceof BasicMirrorTileEntity)
            {
                BasicMirrorTileEntity tileEntity = (BasicMirrorTileEntity) worldIn.getTileEntity(pos);
                if (tileEntity.inventory.getStackInSlot(0) != ItemStack.EMPTY)
                {
                    Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, tileEntity.inventory.getStackInSlot(0));
                    worldIn.addEntity(entity);
                }
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                ItemStack stack = player.getHeldItem(handIn);
                if (worldIn.getTileEntity(pos) instanceof BasicMirrorTileEntity)
                {
                    BasicMirrorTileEntity mirrorTileEntity = (BasicMirrorTileEntity) worldIn.getTileEntity(pos);

                    if (stack.getItem() instanceof BasicStave)
                    {
                        CompoundNBT nbt = stack.getOrCreateTag();
                        if (mirrorTileEntity instanceof InputMirrorTileEntity)
                        {
                            nbt.putInt("blockPosX", pos.getX());
                            nbt.putInt("blockPosY", pos.getY());
                            nbt.putInt("blockPosZ", pos.getZ());
                        }
                        if (nbt.contains("blockPosX") && mirrorTileEntity instanceof OutputMirrorTileEntity)
                        {
                            BlockPos linkedPos = new BlockPos(nbt.getInt("blockPosX"),nbt.getInt("blockPosY"),nbt.getInt("blockPosZ"));
                            if (worldIn.getTileEntity(linkedPos) instanceof InputMirrorTileEntity)
                            {
                                InputMirrorTileEntity inputMirrorTileEntity = (InputMirrorTileEntity) worldIn.getTileEntity(linkedPos);
                                inputMirrorTileEntity.linkedPositions.add(pos);
                            }
                        }
                        return ActionResultType.SUCCESS;
                    }

                    boolean success = MalumHelper.basicItemTEHandling(player, handIn, stack, mirrorTileEntity.inventory, 0);
                    if (success)
                    {
                        mirrorTileEntity.cancelNextTransfer = true;
                        player.world.notifyBlockUpdate(mirrorTileEntity.getPos(), mirrorTileEntity.getBlockState(), mirrorTileEntity.getBlockState(), 3);
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}