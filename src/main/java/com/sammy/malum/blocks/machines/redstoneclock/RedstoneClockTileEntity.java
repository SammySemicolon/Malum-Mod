package com.sammy.malum.blocks.machines.redstoneclock;

import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;

import static com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity.redstoneClockFunctionTypeEnum.pulse;
import static com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity.redstoneClockFunctionTypeEnum.toggle;

public class RedstoneClockTileEntity extends ConfigurableTileEntity implements ITickableTileEntity
{
    public int type;
    public int tickMultiplier;
    public int timer;
    public int powered;
    public int[] cooldown = new int[]{10, 20, 40, 80, 160, 320, 640};
    
    public RedstoneClockTileEntity()
    {
        super(ModTileEntities.redstone_clock_tile_entity);
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
        timer++;
        if (timer >= cooldown[tickMultiplier])
        {
            if (type == pulse.type)
            {
                powered = Math.max((tickMultiplier / 5), 5);
                timer = 0;
            }
            if (type == toggle.type)
            {
                timer -= cooldown[tickMultiplier] * 2;
                powered = 1;
            }
        }
        if (powered > 0)
        {
            if (type == pulse.type)
            {
                powered--;
            }
            if (type == toggle.type)
            {
                if (timer >= 0)
                {
                    powered = 0;
                }
            }
        }
        BlockState state = getBlockState().with(BlockStateProperties.LIT, powered > 0);
        if (!state.equals(getBlockState()))
        {
            world.setBlockState(pos, state);
        }
    }
    
    public enum redstoneClockFunctionTypeEnum
    {
        toggle(0), pulse(1);
        
        public final int type;
        
        redstoneClockFunctionTypeEnum(int type) { this.type = type;}
    }
}