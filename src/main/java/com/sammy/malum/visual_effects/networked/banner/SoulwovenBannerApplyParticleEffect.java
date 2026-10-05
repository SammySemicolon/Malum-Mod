package com.sammy.malum.visual_effects.networked.banner;

import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import com.sammy.malum.visual_effects.block.SoulwovenBannerParticleEffects;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectExtraData;
import team.lodestar.lodestone.systems.network.particle.NetworkedParticleEffectPositionData;

public class SoulwovenBannerApplyParticleEffect extends MalumNetworkedParticleEffectType<NetworkedParticleEffectExtraData> {

    public SoulwovenBannerApplyParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, NetworkedParticleEffectExtraData extraData) {
        if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SoulwovenBannerBlockEntity banner)) {
            return;
        }
        SoulwovenBannerParticleEffects.applyBannerGlow(level, colorData, banner);
    }
}