package com.sammy.malum.visual_effects.networked.banner;

import com.sammy.malum.common.block.building.banner.soulwoven.SoulwovenBannerBlockEntity;
import com.sammy.malum.visual_effects.block.SoulwovenBannerParticleEffects;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.systems.network.particle.*;

public class SoulwovenBannerRemoveParticleEffect extends MalumNetworkedParticleEffectType<NetworkedParticleEffectExtraData> {

    public SoulwovenBannerRemoveParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void act(Level level, RandomSource random, NetworkedParticleEffectPositionData positionData, MalumNetworkedParticleEffectColorData colorData, NetworkedParticleEffectExtraData extraData) {
        if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SoulwovenBannerBlockEntity banner)) {
            return;
        }
        SoulwovenBannerParticleEffects.removeBannerGlow(level, colorData, banner);
    }
}