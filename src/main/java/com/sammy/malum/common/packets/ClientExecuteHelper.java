package com.sammy.malum.common.packets;

import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClientExecuteHelper {

    @Environment(EnvType.CLIENT)
    public static void generic(Minecraft client, String id, PositionEffectData positionData, @Nullable ColorEffectData colorData, @Nullable NBTEffectData nbtData) {
        client.execute(() -> {

            ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.EFFECT_TYPES.get(id);
            if (particleEffectType == null) {
                throw new RuntimeException("This shouldn't be happening.");
            }
            ParticleEffectType.ParticleEffectActor particleEffectActor = particleEffectType.get().get();
            particleEffectActor.act(client.level, client.level.random, positionData, colorData, nbtData);
        });
    }
}
