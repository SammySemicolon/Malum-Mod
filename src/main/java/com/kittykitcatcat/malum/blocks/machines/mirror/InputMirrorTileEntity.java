package com.kittykitcatcat.malum.blocks.machines.mirror;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.blocks.machines.mirror.BasicMirrorBlock.getTileEntityForTransfer;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class InputMirrorTileEntity extends BasicMirrorTileEntity implements ITickableTileEntity
{
    public InputMirrorTileEntity()
    {
        super(ModTileEntities.input_mirror_tile_entity);
    }

    public int currentMirror;
    public List<BlockPos> linkedPositions;
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putInt("currentMirror", currentMirror);
        if (linkedPositions != null && !linkedPositions.isEmpty())
        {
            compound.putInt("mirrorCount", linkedPositions.size());
            for(int i = 0; i < linkedPositions.size(); i++)
            {
                BlockPos pos = linkedPositions.get(i);
                compound.putInt("blockPosX" + i, pos.getX());
                compound.putInt("blockPosY" + i, pos.getY());
                compound.putInt("blockPosZ" + i, pos.getZ());
            }
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        currentMirror = compound.getInt("currentMirror");
        if (compound.contains("mirrorCount"))
        {
            for(int i = 0; i < compound.getInt("mirrorCount"); i++)
            {
                int x = compound.getInt("blockPosX" + i);
                int y = compound.getInt("blockPosY" + i);
                int z = compound.getInt("blockPosZ" + i);
                BlockPos pos = new BlockPos(x,y,z);
                if (linkedPositions == null)
                {
                    linkedPositions = new ArrayList<>();
                }
                linkedPositions.add(pos);
            }
        }
        super.read(compound);
    }

    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            if (transfer)
            {
                if (linkedPositions == null)
                {
                    linkedPositions = new ArrayList<>();
                }
                ItemStack stack = inventory.getStackInSlot(0);

                TileEntity inputTileEntity = getTileEntityForTransfer(world, pos);
                if (inputTileEntity != null)
                {
                    Direction direction = getBlockState().get(HORIZONTAL_FACING);
                    boolean success = MalumHelper.inputStackIntoTE(inputTileEntity, direction, stack);
                    if (success)
                    {
                        cancelNextTransfer = true;
                        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
                    }
                }
                if (stack.isEmpty())
                {
                    if (!linkedPositions.isEmpty())
                    {
                        BlockPos linkedMirrorPos = linkedPositions.get(currentMirror);
                        if (linkedMirrorPos != null)
                        {
                            TileEntity tileEntity = world.getTileEntity(linkedMirrorPos);
                            if (tileEntity != null)
                            {
                                if (tileEntity instanceof OutputMirrorTileEntity)
                                {
                                    OutputMirrorTileEntity mirrorTileEntity = (OutputMirrorTileEntity) tileEntity;
                                    ItemStack mirrorStack = mirrorTileEntity.inventory.getStackInSlot(0);
                                    if (!mirrorStack.isEmpty())
                                    {
                                        boolean success = MalumHelper.inputStackIntoTE(this, mirrorStack);
                                        if (success)
                                        {
                                            mirrorTileEntity.cancelNextTransfer = true;
                                            cancelNextTransfer = true;
                                            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
                                            world.notifyBlockUpdate(tileEntity.getPos(), tileEntity.getBlockState(), tileEntity.getBlockState(), 3);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                currentMirror++;
                if (currentMirror >= linkedPositions.size())
                {
                    currentMirror = 0;
                }
            }
            super.tick();
        }
    }
}