package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.mod_systems.inventory.SimpleInventory;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlockTileEntity;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

import java.awt.*;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class RuneTableBoundingBlockTileEntity extends BoundingBlockTileEntity implements ITickableTileEntity
{
    public SimpleInventory inventory;
    public RuneTableBoundingBlockTileEntity()
    {
        super(MalumTileEntities.RUNE_TABLE_BOUNDING_BLOCK_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                RuneTableBoundingBlockTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            return;
        }
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
        {
            SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
            Color color = item.type.color;
            Vector3d pos = itemPos(this);
            double x = pos.x;
            double y = pos.y + Math.sin((world.getGameTime() % 360) / 20f) * 0.05f;
            double z = pos.z;
            SpiritHelper.spiritParticles(world, x,y,z, color);
        }
    }
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        super.readData(compound);
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        return super.writeData(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    public static Vector3d itemPos(SimpleTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(itemOffset(tileEntity));
    }
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 1.15f, direction.getZOffset());
        return new Vector3d(0.5f - directionVector.x*0.1f, 1.15f, 0.5f - directionVector.z*0.1f);
    }
}
