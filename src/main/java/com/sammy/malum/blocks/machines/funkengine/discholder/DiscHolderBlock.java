package com.sammy.malum.blocks.machines.funkengine.discholder;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import com.sammy.malum.blocks.machines.mirror.BasicMirrorTileEntity;
import com.sammy.malum.blocks.machines.mirror.HolderMirrorTileEntity;
import com.sammy.malum.blocks.machines.mirror.LinkableMirrorTileEntity;
import com.sammy.malum.items.staves.BasicStave;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.sammy.malum.MalumHelper.funkyMultiStackTEHandling;
import static net.minecraft.state.properties.BlockStateProperties.HAS_RECORD;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class DiscHolderBlock extends Block
{
    public DiscHolderBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof DiscHolderTileEntity)
                {
                    DiscHolderTileEntity discHolderTileEntity = (DiscHolderTileEntity) worldIn.getTileEntity(pos);
                    ItemStack stack = player.getHeldItemMainhand();
                    if (discHolderTileEntity.inventory.getStackInSlot(0).isEmpty() && stack.isEmpty())
                    {
                        discHolderTileEntity.shuffle = !discHolderTileEntity.shuffle;
                        float pitch = discHolderTileEntity.shuffle ? 1.0f : 0.5f;
                        MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
                        player.world.notifyBlockUpdate(pos,state,state, 3);
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                    MalumHelper.multiStackTEHandling(player, handIn, stack, discHolderTileEntity.inventory);
                    player.world.notifyBlockUpdate(pos,state,state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new DiscHolderTileEntity();
    }
}