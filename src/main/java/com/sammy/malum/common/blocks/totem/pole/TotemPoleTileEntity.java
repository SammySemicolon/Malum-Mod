package com.sammy.malum.common.blocks.totem.pole;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get());
    }

    public MalumSpiritType type;
    public int desiredColor;
    public int currentColor;
    @Override
    public void tick()
    {
        if (currentColor > desiredColor)
        {
            currentColor--;
        }
        if (currentColor < desiredColor)
        {
            currentColor++;
        }
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (type != null)
        {
            compound.putString("type", type.identifier);
        }
        compound.putInt("desiredColor", desiredColor);
        compound.putInt("currentColor", currentColor);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("type"))
        {
            type = SpiritHelper.figureOutType(compound.getString("type"));
        }
        desiredColor = compound.getInt("desiredColor");
        currentColor = compound.getInt("currentColor");
        super.readData(compound);
    }
    public void create(MalumSpiritType type)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_ENGRAVE, SoundCategory.BLOCKS,1,1);
        this.type = type;
        this.currentColor = 10;
    }
    public void riteStarting(int height)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS,1,1 + 0.2f * height);
        this.desiredColor = 10;
        MalumHelper.updateState(world,pos);
    }
    public void riteComplete(int height)
    {
        this.desiredColor = 20;
        MalumHelper.updateState(world,pos);
    }
    public void riteEnding()
    {
        this.desiredColor = 0;
        MalumHelper.updateState(world,pos);
    }
}
