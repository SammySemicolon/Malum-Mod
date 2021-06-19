package com.sammy.malum.common.blocks.arcanecompressor;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.itemfocus.ItemFocusTileEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class ArcaneCompressorTileEntity extends SimpleTileEntity implements ITickableTileEntity, RiteOfAssembly.IAssembled
{
    public static final int PRESS_DURATION = 2;
    public boolean active;
    public boolean hasFocus;
    public int progress;
    public float spinUp;

    public int animationProgress;
    public float spin;
    public ArcaneCompressorTileEntity()
    {
        super(MalumTileEntities.ARCANE_COMPRESSOR_TILE_ENTITY.get());
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putBoolean("hasFocus", hasFocus);
        compound.putInt("progress", progress);
        compound.putFloat("spinUp", spinUp);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        hasFocus = compound.getBoolean("hasFocus");
        progress = compound.getInt("progress");
        spinUp = compound.getFloat("spinUp");
        updateFocus(pos.down(2));

        super.readData(compound);
    }

    public void updateFocus(BlockPos pos)
    {
        if (world.getTileEntity(pos) instanceof ItemFocusTileEntity)
        {
            ItemFocusTileEntity tileEntity = (ItemFocusTileEntity) world.getTileEntity(pos);
            hasFocus = tileEntity.recipe != null;
            MalumHelper.updateAndNotifyState(world, this.pos);
        }
    }
    @Override
    public void assemble(RiteOfAssembly assemblyType)
    {
        if (hasFocus)
        {
            MalumHelper.updateAndNotifyState(world, pos);
            active = true;
        }
    }
    @Override
    public void tick()
    {
        if (hasFocus && !active)
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
        if (hasFocus)
        {
            if (animationProgress < 20)
            {
                animationProgress++;
            }
        }
        else if (animationProgress > 0)
        {
            animationProgress--;
        }
    }
}