package com.sammy.malum.common.blocks.spiritstorage.jar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

import static com.sammy.malum.MalumHelper.brighter;
import static com.sammy.malum.MalumHelper.darker;

public class SpiritJarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritJarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get());
    }
    
    public MalumSpiritType type;
    public int count;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (type != null)
        {
            compound.putString("spirit", type.identifier);
        }
        if (count != 0)
        {
            compound.putInt("count", count);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("spirit"))
        {
            type = SpiritHelper.figureOutType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            if (type != null)
            {
                double x = getPos().getX() + 0.5f;
                double y = getPos().getY() + 0.5f + Math.sin(world.getGameTime() / 20f) * 0.2f;
                double z = getPos().getZ() + 0.5f;
                Color color = type.color;
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.4f, 0f).setLifetime(20).setScale(0.075f, 0).randomOffset(0.25, 0.25).randomVelocity(0.02f, 0.08f).setColor(brighter(color, 2), darker(color, 3)).randomVelocity(0f, 0.01f).addVelocity(0, 0.01f, 0).enableNoClip().repeat(world, x, y, z, 1);
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.03f, 0f).setLifetime(60).setScale(0.3f, 0).randomOffset(0.2, 0.1).randomVelocity(0.02f, 0.02f).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).addVelocity(0, -0.005f, 0).enableNoClip().repeat(world, x, y, z, 2);
            }
        }
    }
}