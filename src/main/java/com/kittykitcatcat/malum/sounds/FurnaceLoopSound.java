package com.kittykitcatcat.malum.sounds;

import com.kittykitcatcat.malum.init.ModSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;

public class FurnaceLoopSound extends TickableSound
{
    private final TileEntity tileEntity;

    public FurnaceLoopSound(TileEntity tileEntity)
    {
        super(ModSounds.furnace_loop, SoundCategory.BLOCKS);
        this.tileEntity = tileEntity;
        this.repeat = true;

        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.x = (float) tileEntity.getPos().getX();
        this.y = (float) tileEntity.getPos().getY();
        this.z = (float) tileEntity.getPos().getZ();
    }

    public void tick()
    {
        if (this.tileEntity.isRemoved())
        {
            this.donePlaying = true;
        }
    }
}