package com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ArtificeBlockConnectionData;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.SequencedBlockConnectionArray;
import net.minecraft.world.phys.AABB;

public record WindTunnelEntityInfluenceData(SequencedBlockConnectionArray array, AABB area, float strength) {

    public WindTunnelEntityInfluenceData(ArtificeBlockConnectionData data, float strength) {
        this(data.getArray(), data.getDefinedArea(), strength);
    }

    public static final Codec<WindTunnelEntityInfluenceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SequencedBlockConnectionArray.CODEC.fieldOf("array").forGetter(WindTunnelEntityInfluenceData::array),
            ArtificeBlockConnectionData.AABB_CODEC.fieldOf("area").forGetter(WindTunnelEntityInfluenceData::area),
            Codec.FLOAT.fieldOf("strength").forGetter(WindTunnelEntityInfluenceData::strength)
    ).apply(instance, WindTunnelEntityInfluenceData::new));
}
