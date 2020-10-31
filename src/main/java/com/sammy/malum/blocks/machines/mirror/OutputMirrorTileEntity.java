package com.sammy.malum.blocks.machines.mirror;

import com.sammy.malum.init.ModTileEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static net.minecraft.item.ItemStack.EMPTY;

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
                globalLogic();
            }
            transferCooldown--;
        }
    }
}