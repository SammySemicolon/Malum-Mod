package com.sammy.malum.common.blocks.totem;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TotemBaseTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemBaseTileEntity()
    {
        super(MalumTileEntities.TOTEM_BASE_TILE_ENTITY.get());
    }

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (rite != null)
        {
            compound.putString("rite", rite.identifier);
        }
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++)
        {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        rite = MalumRites.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        for (int i = 0; i < size; i++)
        {
            spirits.add(SpiritHelper.figureOutType(compound.getString("spirit_"+1)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        super.readData(compound);
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            if (rite != null)
            {
                progress++;
                if (progress >= rite.defaultInterval())
                {
                    rite.executeRite(pos);
                    progress = 0;
                }
            }
            else if (active)
            {
                progress++;
                if (progress >= 20)
                {
                    height++;
                    progress = 0;
                    BlockPos polePos = pos.up(height);
                    if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
                    {
                        TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
                        if (tileEntity.type != null)
                        {
                            spirits.add(tileEntity.type);
                            tileEntity.riteStarting(height);
                        }
                    }
                    else
                    {
                        MalumRiteType rite = MalumRites.getRite(spirits);
                        if (rite == null)
                        {
                            riteEnding();
                        }
                        else
                        {
                            riteComplete(rite);
                        }
                    }
                }
            }
        }
    }
    public ArrayList<TotemPoleTileEntity> poles()
    {
        ArrayList<TotemPoleTileEntity> poles = new ArrayList<>();
        for (int i = 1; i <= height; i++)
        {
            if (world.getTileEntity(pos.up(i)) instanceof TotemPoleTileEntity)
            {
                poles.add((TotemPoleTileEntity) world.getTileEntity(pos.up(i)));
            }
        }
        return poles;
    }
    public void reset()
    {
        poles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        MalumHelper.updateState(world,pos);
    }
    public void riteStarting()
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS,1,1);
        active = true;
        MalumHelper.updateState(world,pos);
    }
    public void riteComplete(MalumRiteType rite)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGED, SoundCategory.BLOCKS,1,1);
        poles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant)
        {
            rite.executeRite(pos);
            reset();
            return;
        }
        this.rite = rite;
        MalumHelper.updateState(world,pos);
    }
    public void riteEnding()
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS,1,0.5f);
        reset();
    }
}
