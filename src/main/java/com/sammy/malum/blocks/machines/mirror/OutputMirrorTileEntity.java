package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.init.ModTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

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
            if (transferCooldown <= 0)
            {
                TileEntity attachedTileEntity = getAttachedTileEntity(world, pos);
                if (attachedTileEntity != null)
                {
                    boolean success = transferItem(attachedTileEntity, this, transferAmounts[transferAmount]);
                    if (!success)
                    {
                        transferCooldown = 10;
                    }
                }
            }
            transferCooldown--;
        }
    }
}