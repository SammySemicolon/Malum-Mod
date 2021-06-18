package com.sammy.malum.common.blocks.arcanecompressor;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.RiteOfAssembly;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumCompressingRecipes;
import com.sammy.malum.core.modcontent.MalumCompressingRecipes.ArcaneCompressorRecipe;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class ArcaneCompressorTileEntity extends SimpleTileEntity implements ITickableTileEntity, RiteOfAssembly.IAssembled
{
    public static final int PRESS_DURATION = 2;
    public boolean active;
    public int progress;
    public float spin;
    public float spinUp;
    public ArcaneCompressorRecipe recipe;
    public ArcaneCompressorTileEntity()
    {
        super(MalumTileEntities.ARCANE_COMPRESSOR_TILE_ENTITY.get());
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("progress", progress);
        compound.putBoolean("active", active);
        compound.putFloat("spinUp", spinUp);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        active = compound.getBoolean("active");
        spinUp = compound.getFloat("spinUp");
        super.readData(compound);
    }

    @Override
    public void assemble()
    {
        MalumHelper.updateAndNotifyState(world, pos);

        active = true;
    }
    @Override
    public void tick()
    {
        if (recipe != null && !active)
        {
            if (spinUp < 1)
            {
                spinUp += 0.05f;
            }
        }
        else
        {
            if (spinUp > 0)
            {
                spinUp -= 0.05f;
            }
        }
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }
    public void passiveParticles()
    {
        if (!active)
        {
            spin += 1 + spinUp;
        }
    }
}