package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.utility.ConfigurableBlock;
import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import com.sammy.malum.items.MirrorBlockItem;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import static com.sammy.malum.MalumHelper.machineOption;

public class BasicMirrorBlock extends HorizontalFaceBlock implements ConfigurableBlock
{
    protected static final VoxelShape AABB_NORTH = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_SOUTH = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    protected static final VoxelShape AABB_WEST = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_EAST = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    protected static final VoxelShape AABB_DOWN = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    protected static final VoxelShape AABB_UP = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    public mirrorTypeEnum type;
    public BasicMirrorBlock(Properties properties, mirrorTypeEnum type)
    {
        super(properties);
        this.type = type;
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(FACE, AttachFace.WALL));
    }

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
                return new HolderMirrorTileEntity();
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
        return null; //if this EVER runs you're in some deep shit, it shouldn't though
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
    public int options()
    {
        return 2;
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.getTileEntity(pos) instanceof LinkableMirrorTileEntity)
        {
            ItemStack stack = player.getHeldItem(handIn);
            if (stack.getItem() instanceof MirrorBlockItem)
            {
                CompoundNBT nbt = stack.getOrCreateTag();
                nbt.putInt("blockPosX", pos.getX());
                nbt.putInt("blockPosY", pos.getY());
                nbt.putInt("blockPosZ", pos.getZ());
                nbt.putBoolean("linked", true);
                player.sendStatusMessage(new StringTextComponent("YOOO"), true);
                return ActionResultType.SUCCESS;
            }
        }
        return activateBlock(state, worldIn, pos, player, handIn, hit);
    }
    
    @Override
    public ActionResultType blockInteraction(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);
        if (worldIn.getTileEntity(pos) instanceof BasicMirrorTileEntity)
        {
            BasicMirrorTileEntity mirrorTileEntity = (BasicMirrorTileEntity) worldIn.getTileEntity(pos);
            boolean success = MalumHelper.basicItemTEHandling(player, handIn, stack, mirrorTileEntity.inventory, 0);
            if (success)
            {
                player.world.notifyBlockUpdate(pos, state, state, 3);
                player.swingArm(handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
    
    @Override
    public void configureTileEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, ConfigurableTileEntity tileEntity, int option, boolean isSneaking)
    {
        if (tileEntity instanceof BasicMirrorTileEntity)
        {
            BasicMirrorTileEntity mirrorTileEntity = (BasicMirrorTileEntity) tileEntity;
            if (mirrorTileEntity instanceof HolderMirrorTileEntity)
            {
                return;
            }
            int change = isSneaking ? -1 : 1;
            float pitch = 0f;
            int finalOption = machineOption(option, options());
            switch (finalOption)
            {
                case 1:
                {
                    break;
                }
                case 0:
                {
                    mirrorTileEntity.transferAmount = machineOption(change + mirrorTileEntity.transferAmount, mirrorTileEntity.transferAmounts.length);
                    pitch = (float) mirrorTileEntity.transferAmount / mirrorTileEntity.transferAmounts.length;
                    break;
                }
            }
            MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
            MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
            player.world.notifyBlockUpdate(pos, state, state, 3);
            player.swingArm(handIn);
        }
    }
    
    public enum mirrorTypeEnum
    {
        basic(0), input(1), output(2);
        
        public final int type;
        
        mirrorTypeEnum(int type) { this.type = type;}
    }
}