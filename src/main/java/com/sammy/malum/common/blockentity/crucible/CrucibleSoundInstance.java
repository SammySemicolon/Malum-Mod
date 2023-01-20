package com.sammy.malum.common.blockentity.crucible;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import team.lodestar.lodestone.systems.sound.LodestoneBlockEntitySoundInstance;

public class CrucibleSoundInstance extends LodestoneBlockEntitySoundInstance<SpiritCrucibleCoreBlockEntity> {
    public CrucibleSoundInstance(SpiritCrucibleCoreBlockEntity blockEntity, float volume, float pitch) {
        super(blockEntity, SoundRegistry.CRUCIBLE_LOOP.get(), volume, pitch);
        this.x = blockEntity.getBlockPos().getX() + 0.5f;
        this.y = blockEntity.getBlockPos().getY() + 0.5f;
        this.z = blockEntity.getBlockPos().getZ() + 0.5f;
    }

    @Override
    public void tick() {
        if (blockEntity.focusingRecipe != null || blockEntity.repairRecipe != null) {
            super.tick();
            return;
        }
        stop();
    }

    public static void playSound(SpiritCrucibleCoreBlockEntity tileEntity) {
        Minecraft.getInstance().getSoundManager().queueTickingSound(new CrucibleSoundInstance(tileEntity, 1, 1));
    }
}