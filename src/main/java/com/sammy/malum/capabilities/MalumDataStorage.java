package com.sammy.malum.capabilities;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MalumDataStorage implements Capability.IStorage<IMalumData> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IMalumData> capability, IMalumData instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        instance.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void readNBT(Capability<IMalumData> capability, IMalumData instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT)
        {
            instance.loadNBTData((CompoundNBT) nbt);
        }
    }
}
