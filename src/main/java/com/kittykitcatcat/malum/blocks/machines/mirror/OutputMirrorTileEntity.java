package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import static com.kittykitcatcat.malum.blocks.machines.mirror.BasicMirrorBlock.getTileEntityForTransfer;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class OutputMirrorTileEntity extends BasicMirrorTileEntity implements ITickableTileEntity
{
    public OutputMirrorTileEntity()
    {
        super(ModTileEntities.output_mirror_tile_entity);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            super.tick();
            if (transferCooldown <= 0)
            {
                ItemStack stack = inventory.getStackInSlot(0);
                if (stack == ItemStack.EMPTY)
                {
                    TileEntity inputTileEntity = getTileEntityForTransfer(world, pos);
                    if (inputTileEntity != null)
                    {
                        Direction direction = getBlockState().get(HORIZONTAL_FACING);
                        IItemHandler inventory = inputTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).orElseThrow(null);
                        for (int j = 0; j < inventory.getSlots(); j++)
                        {
                            if (!inventory.getStackInSlot(j).isEmpty())
                            {
                                MalumHelper.inputStackIntoTE(this, direction.getOpposite(), inventory.getStackInSlot(j));
                                if (inputTileEntity instanceof BasicMirrorTileEntity)
                                {
                                    ((BasicMirrorTileEntity) inputTileEntity).transferCooldown = 10;
                                }
                                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                                transferCooldown = 10;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}