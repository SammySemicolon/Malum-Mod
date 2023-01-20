package com.sammy.malum.common.blockentity.spirit_altar;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import team.lodestar.lodestone.systems.sound.LodestoneBlockEntitySoundInstance;

public class AltarSoundInstance extends LodestoneBlockEntitySoundInstance<SpiritAltarBlockEntity> {
    public AltarSoundInstance(SpiritAltarBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.ALTAR_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
        this.pitch = 0.8f;
    }

    @Override
    public void tick() {
        if (blockEntity.possibleRecipes.isEmpty()) {
            stop();
        }
        super.tick();
    }

    public static void playSound(SpiritAltarBlockEntity tileEntity) {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new AltarSoundInstance(tileEntity, 1, 1));
    }
}