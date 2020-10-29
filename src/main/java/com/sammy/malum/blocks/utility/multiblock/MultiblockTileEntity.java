package com.sammy.malum.blocks.utility.multiblock;

import com.sammy.malum.blocks.utility.BasicTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sammy.malum.MalumHelper.inputStackIntoTE;
import static com.sammy.malum.MalumHelper.vectorFromBlockPos;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public abstract class MultiblockTileEntity extends BasicTileEntity implements ITickableTileEntity
{
    public MultiblockTileEntity(TileEntityType type)
    {
        super(type);
    }
    
    public List<BlockPos> positions;
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if (positions != null && !positions.isEmpty())
        {
            compound.putInt("blockCount", positions.size());
            for (int i = 0; i < positions.size(); i++)
            {
                BlockPos pos = positions.get(i);
                compound.putInt("blockPosX" + i, pos.getX());
                compound.putInt("blockPosY" + i, pos.getY());
                compound.putInt("blockPosZ" + i, pos.getZ());
            }
        }
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains("blockCount"))
        {
            for (int i = 0; i < compound.getInt("blockCount"); i++)
            {
                int x = compound.getInt("blockPosX" + i);
                int y = compound.getInt("blockPosY" + i);
                int z = compound.getInt("blockPosZ" + i);
                BlockPos pos = new BlockPos(x, y, z);
                if (positions == null)
                {
                    positions = new ArrayList<>();
                }
                positions.add(pos);
            }
        }
    }
    
    @Override
    public void remove()
    {
        if (positions != null)
        {
            for (BlockPos pos : positions)
            {
                TileEntity tileEntity = world.getTileEntity(pos);
                if (tileEntity instanceof BoundingBlockTileEntity)
                {
                    world.removeBlock(pos, true);
                }
            }
        }
        super.remove();
    }
    
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, BlockPos pos)
    {
        return getCapability(cap);
    }
    
    @Override
    public void tick()
    {
    }
}