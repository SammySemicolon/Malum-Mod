package com.sammy.malum.common.blockentity.altar;

import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.systems.sound.SimpleBlockEntitySoundInstance;
import net.minecraft.client.Minecraft;

public class AltarSoundInstance extends SimpleBlockEntitySoundInstance<SpiritAltarTileEntity> {
    public AltarSoundInstance(SpiritAltarTileEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.ALTAR_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX()+0.5f;
        this.y = blockEntity.getBlockPos().getY()+0.5f;
        this.z = blockEntity.getBlockPos().getZ()+0.5f;
        this.pitch = 0.8f;
    }

    @Override
    public void tick() {
        if (blockEntity.possibleRecipes.isEmpty())
        {
            stop();
        }
        super.tick();
    }
    public static void playSound(SpiritAltarTileEntity tileEntity)
    {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new AltarSoundInstance(tileEntity, 1,1));
    }
}
