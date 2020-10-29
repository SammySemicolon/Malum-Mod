package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.init.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class InputMirrorTileEntity extends LinkableMirrorTileEntity implements ITickableTileEntity
{
    public InputMirrorTileEntity()
    {
        super(ModTileEntities.input_mirror_tile_entity);
    }
    
    @Override
    public void link(BlockPos linkPos)
    {
        super.link(linkPos);
        if (world.getTileEntity(linkPos) instanceof OutputMirrorTileEntity)
        {
            if (linkedPositions == null)
            {
                linkedPositions = new ArrayList<>();
            }
            linkedPositions.add(linkPos);
        }
    }
    
    @Override
    public void tick()
    {
        if (!world.isRemote)
        {
            if (transferCooldown <= 0)
            {
                TileEntity attachedTileEntity = getAttachedTileEntity(world, pos);
                if (attachedTileEntity != null)
                {
                    boolean success = transferItem(this, attachedTileEntity, transferAmounts[transferAmount]);
                    if (!success)
                    {
                        transferCooldown = 10;
                    }
                }
                if (linkedPositions != null && !linkedPositions.isEmpty())
                {
                    for (int i = 0; i < linkedPositions.size(); i++)
                    {
                        BasicMirrorTileEntity linkedMirror = getCurrentMirrorTileEntity();
                        if (linkedMirror != null)
                        {
                            boolean success = transferItem(linkedMirror, this, transferAmounts[transferAmount]);
                            if (!success)
                            {
                                transferCooldown = 10;
                            }
                        }
                    }
                }
            }
            transferCooldown--;
        }
    }
}