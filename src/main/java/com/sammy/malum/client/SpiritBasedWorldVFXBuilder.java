package com.sammy.malum.client;

import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.core.systems.spirit.SpiritLike;
import com.sammy.malum.core.systems.spirit.umbral.UmbralSpiritArcanaType;
import net.minecraft.client.renderer.RenderType;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.builder.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;

public class SpiritBasedWorldVFXBuilder extends WorldVFXBuilder {

    public static SpiritBasedWorldVFXBuilder create(SpiritLike spirit) {
        return new SpiritBasedWorldVFXBuilder(spirit.getSpirit());
    }

    public static SpiritBasedWorldVFXBuilder create(SpiritArcanaType spiritType) {
        return new SpiritBasedWorldVFXBuilder(spiritType);
    }

    public final SpiritArcanaType spiritType;

    public SpiritBasedWorldVFXBuilder(SpiritArcanaType spiritType) {
        this.spiritType = spiritType;
    }

    @Override
    public WorldVFXBuilder setRenderType(RenderType renderType) {
        if (spiritType instanceof UmbralSpiritArcanaType && renderType instanceof LodestoneRenderType lodestoneRenderType) {
            var umbral = lodestoneRenderType.copy("umbral", UniformData.LUMITRANSPARENT, b -> b.setTransparencyState(StateShards.NORMAL_TRANSPARENCY));
            return super.setRenderType(umbral);
        }
        return super.setRenderType(renderType);
    }

    @Override
    public WorldVFXBuilder setAlpha(float a) {
        return super.setAlpha(a * spiritType.getAlphaMultiplier());
    }
}