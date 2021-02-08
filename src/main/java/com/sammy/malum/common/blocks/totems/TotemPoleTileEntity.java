package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumBlocks;
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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.particles.ParticleTypes.SMOKE;

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
        if (active)
        {
            compound.putBoolean("active", true);
        }
        if (colorPercentage != 0)
        {
            compound.putInt("colorPercentage", colorPercentage);
        }
        if (expectedColorPercentage != 0)
        {
            compound.putInt("expectedColorPercentage", expectedColorPercentage);
        }
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
        colorPercentage = compound.getInt("colorPercentage");
        expectedColorPercentage = compound.getInt("expectedColorPercentage");
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
        if (corePos != null)
        {
            if (world.getTileEntity(corePos) instanceof TotemCoreTileEntity)
            {
                TotemCoreTileEntity tileEntity = (TotemCoreTileEntity) world.getTileEntity(corePos);
                if (tileEntity.state != 0)
                {
                    tileEntity.reset();
                }
            }
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
            passiveParticles(pos, world, type.color);
        }
    }
    
    public void setup(Direction direction, MalumSpiritType type)
    {
        colorPercentage = 15;
        this.type = type;
        this.direction = direction;
        MalumHelper.updateState(world, this.pos);
        particles(pos, world, type.color, 8);
        smoke(pos,world, 4);
    }
    public void scan(BlockPos pos)
    {
        expectedColorPercentage = 10;
        corePos = pos;
        MalumHelper.updateState(world, this.pos);
        particles(this.pos, world, type.color, 8);
    }
    
    public void deactivate()
    {
        expectedColorPercentage = 0;
        colorPercentage = 20;
        active = false;
        MalumHelper.updateState(world, pos);
        badParticles(pos, world, type.color, 2);
        smoke(pos, world, 6);
    }
    
    public void activate()
    {
        active = true;
        expectedColorPercentage = 20;
        MalumHelper.updateState(world, pos);
        particles(pos, world, type.color, 8);
    }
    
    public void reset()
    {
        world.setBlockState(pos, MalumBlocks.RUNEWOOD_LOG.get().getDefaultState());
        smoke(pos, world, 8);
        badParticles(pos, world, type.color, 8);
    }
    
    public static void smoke(BlockPos pos, World world, int count)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            for (int j = 0; j < count; j++)
            {
                ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                particlePositions.forEach(p -> world.addParticle(SMOKE, p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
            }
        }
    }
    
    public static void badParticles(BlockPos pos, World world, Color color, float countMultiplier)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.brighter(), MalumHelper.darker(color, 2)).randomVelocity(0f, 0.01f).enableNoClip().evenlyRepeatEdges(world, pos, (int) (1 * countMultiplier));
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(80).setScale(0.4f, 0).setColor(color.darker(), MalumHelper.darker(color, 3)).randomVelocity(0.0025f, 0.0025f).enableNoClip().evenlyRepeatEdges(world, pos, (int) (2 * countMultiplier));
        }
    }
    public static void particles(BlockPos pos, World world, Color color, float countMultiplier)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.01f).enableNoClip().evenlyRepeatEdges(world, pos, (int) (1 * countMultiplier));
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(80).setScale(0.4f, 0).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).enableNoClip().evenlyRepeatEdges(world, pos, (int) (2 * countMultiplier));
        }
    }
    
    public static void passiveParticles(BlockPos pos, World world, Color color)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            if (world.rand.nextFloat() <= 0.4f)
            {
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 1);
            }
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(80).setScale(0.4f, 0).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).enableNoClip().repeatEdges(world, pos, 2);
        }
    }
}