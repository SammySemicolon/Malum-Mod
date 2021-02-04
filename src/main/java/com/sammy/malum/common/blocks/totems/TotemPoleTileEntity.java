package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get());
    }
    public MalumSpiritType type;
    public boolean active;
    public int colorPercentage;
    public int expectedColorPercentage;
    public Direction direction;
    public BlockPos corePos;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putInt("activeTime", colorPercentage);
        compound.putInt("expectedActiveTime", expectedColorPercentage);
        if (direction != null)
        {
            compound.putInt("direction", direction.getIndex());
        }
        if (type != null)
        {
            compound.putString("type", type.identifier);
        }
        if (corePos != null)
        {
            compound.putIntArray("corePos", new int[]{corePos.getX(), corePos.getY(), corePos.getZ()});
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        colorPercentage = compound.getInt("activeTime");
        expectedColorPercentage = compound.getInt("expectedActiveTime");
        if (compound.contains("direction"))
        {
            direction = Direction.values()[compound.getInt("direction")];
        }
        if (compound.contains("type"))
        {
            type = SpiritHelper.figureOutType(compound.getString("type"));
        }
        if (compound.contains("corePos"))
        {
            int[] positions = compound.getIntArray("corePos");
            corePos = new BlockPos(positions[0], positions[1], positions[2]);
        }
        super.readData(compound);
    }
    
    @Override
    public void remove()
    {
        if (world.getTileEntity(corePos) instanceof TotemCoreTileEntity)
        {
            ((TotemCoreTileEntity) world.getTileEntity(corePos)).reset(true);
        }
        super.remove();
    }
    
    @Override
    public void tick()
    {
        if (colorPercentage < expectedColorPercentage)
        {
            colorPercentage++;
        }
        if (colorPercentage > expectedColorPercentage)
        {
            colorPercentage--;
        }
        if (active)
        {
            passiveEffects();
        }
    }
    public void start(BlockPos pos)
    {
        expectedColorPercentage = 10;
        corePos = pos;
    }
    public void activate()
    {
        active = true;
        expectedColorPercentage = 20;
        MalumHelper.updateState(world, pos);
    }
    public void passiveEffects()
    {
        Color color = type.color;
        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.75f, 0f).setLifetime(20).setScale(0.075f, 0).setColor(color, color).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 2);
    
    }
}