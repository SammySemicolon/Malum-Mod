package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;

import static com.kittykitcatcat.malum.blocks.machines.mirror.BasicMirrorBlock.getTileEntityForTransfer;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class InputMirrorTileEntity extends BasicMirrorTileEntity implements ITickableTileEntity
{
    public InputMirrorTileEntity()
    {
        super(ModTileEntities.input_mirror_tile_entity);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            if (transfer)
            {
                ItemStack stack = inventory.getStackInSlot(0);
                TileEntity inputTileEntity = getTileEntityForTransfer(world, pos);
                if (inputTileEntity != null)
                {
                    Direction direction = getBlockState().get(HORIZONTAL_FACING);
                    MalumHelper.inputStackIntoTE(inputTileEntity, direction, stack);
                    world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                }
            }
            super.tick();
        }
    }
}