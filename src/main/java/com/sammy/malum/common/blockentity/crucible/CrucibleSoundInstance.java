package com.sammy.malum.common.blockentity.crucible;

import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.systems.sound.SimpleBlockEntitySoundInstance;
import net.minecraft.client.Minecraft;

public class CrucibleSoundInstance extends SimpleBlockEntitySoundInstance<SpiritCrucibleCoreBlockEntity> {
    public CrucibleSoundInstance(SpiritCrucibleCoreBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.CRUCIBLE_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX()+0.5f;
        this.y = blockEntity.getBlockPos().getY()+0.5f;
        this.z = blockEntity.getBlockPos().getZ()+0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.recipe == null)
        {
            stop();
        }
        super.tick();
    }
    public static void playSound(SpiritCrucibleCoreBlockEntity tileEntity)
    {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new CrucibleSoundInstance(tileEntity, 1,1));
    }
}
