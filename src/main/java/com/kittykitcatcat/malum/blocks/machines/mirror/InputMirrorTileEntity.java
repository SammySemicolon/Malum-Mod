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
        super.tick();
        if (transferCooldown <= 0)
        {
            ItemStack stack = inventory.getStackInSlot(0);
            if (stack != ItemStack.EMPTY)
            {
                TileEntity inputTileEntity = getTileEntityForTransfer(world, pos);
                if (inputTileEntity != null)
                {
                    Direction direction = getBlockState().get(HORIZONTAL_FACING);
                    MalumHelper.inputStackIntoTE(inputTileEntity, direction, stack);
                    if (inputTileEntity instanceof BasicMirrorTileEntity)
                    {
                        ((BasicMirrorTileEntity) inputTileEntity).transferCooldown = 10;
                    }
                    world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                    transferCooldown = 10;
                }
            }
        }
    }
}