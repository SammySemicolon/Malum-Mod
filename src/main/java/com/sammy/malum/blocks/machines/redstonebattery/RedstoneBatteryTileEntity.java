package com.sammy.malum.blocks.machines.redstonebattery;

import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;

import static com.sammy.malum.blocks.machines.redstonebattery.RedstoneBatteryTileEntity.redstoneBatteryFunctionTypeEnum.pulse;
import static com.sammy.malum.blocks.machines.redstonebattery.RedstoneBatteryTileEntity.redstoneBatteryFunctionTypeEnum.constant;

public class RedstoneBatteryTileEntity extends ConfigurableTileEntity implements ITickableTileEntity
{
    public int type;
    public int tickMultiplier;
    public int timer;
    public int powered;
    public int[] cooldown = new int[]{10, 20, 40, 80, 160, 320, 640};
    
    public RedstoneBatteryTileEntity()
    {
        super(ModTileEntities.redstone_battery_tile_entity);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putInt("type", type);
        compound.putInt("tickMultiplier", tickMultiplier);
        compound.putInt("timer", timer);
        compound.putInt("powered", powered);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        type = compound.getInt("type");
        tickMultiplier = compound.getInt("tickMultiplier");
        timer = compound.getInt("timer");
        powered = compound.getInt("powered");
    }
    
    @Override
    public void tick()
    {
        if (timer > 0)
        {
            timer--;
            if (type == pulse.type)
            {
                if (timer == 0)
                {
                    powered = Math.max((tickMultiplier / 5), 5);
                }
            }
            if (type == constant.type)
            {
                powered = 1;
            }
        }
        BlockState state = getBlockState().with(BlockStateProperties.LIT, powered > 0);
        if (!state.equals(getBlockState()))
        {
            world.setBlockState(pos, state);
        }
        if (powered > 0)
        {
            powered--;
        }
    }
    
    public enum redstoneBatteryFunctionTypeEnum
    {
        constant(0), pulse(1);
        
        public final int type;
        
        redstoneBatteryFunctionTypeEnum(int type) { this.type = type;}
    }
}