package com.sammy.malum.visual_effects.networked.spirit_diode;

import com.sammy.malum.visual_effects.block.SpiritDiodeParticleEffects;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectExtraData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectPositionData;

public class WaveformDiodeCloseParticleEffect extends MalumNetworkedParticleEffectType<NetworkedParticleEffectExtraData> {

    public WaveformDiodeCloseParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, NetworkedParticleEffectExtraData extraData) {
        SpiritDiodeParticleEffects.closeSpiritDiode(positionData, colorData);
    }
}