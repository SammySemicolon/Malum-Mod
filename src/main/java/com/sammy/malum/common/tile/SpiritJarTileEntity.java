package com.sammy.malum.common.tile;

import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class SpiritJarTileEntity extends SimpleBlockEntity
{
    public SpiritJarTileEntity(BlockPos pos, BlockState state)
    {
        super(TileEntityRegistry.SPIRIT_JAR_TILE_ENTITY.get(), pos, state);
    }
    
    public MalumSpiritType type;
    public int count;
    
    @Override
    public CompoundTag save(CompoundTag compound)
    {
        if (type != null)
        {
            compound.putString("spirit", type.identifier);
        }
        if (count != 0)
        {
            compound.putInt("count", count);
        }
        return super.save(compound);
    }
    
    @Override
    public void load(CompoundTag compound)
    {
        if (compound.contains("spirit"))
        {
            type = SpiritHelper.getSpiritType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.load(compound);
    }

    public void tick()
    {
        if (level.isClientSide)
        {
            if (type != null)
            {
                double x = getBlockPos().getX() + 0.5f;
                double y = getBlockPos().getY() + 0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f;
                double z = getBlockPos().getZ() + 0.5f;
                Color color = type.color;
                SpiritHelper.spawnSpiritParticles(level, x,y,z, color);
            }
        }
    }
}