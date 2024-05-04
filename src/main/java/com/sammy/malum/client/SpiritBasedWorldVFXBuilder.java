package com.sammy.malum.client;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.UmbralSpiritType;
import net.minecraft.client.renderer.RenderType;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderType;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;

public class SpiritBasedWorldVFXBuilder extends VFXBuilders.WorldVFXBuilder {

    public static SpiritBasedWorldVFXBuilder create(MalumSpiritType spiritType) {
        return new SpiritBasedWorldVFXBuilder(spiritType);
    }

    public final MalumSpiritType spiritType;

    public SpiritBasedWorldVFXBuilder(MalumSpiritType spiritType) {
        this.spiritType = spiritType;
    }

    @Override
    public VFXBuilders.WorldVFXBuilder setRenderType(RenderType renderType) {
        if (spiritType instanceof UmbralSpiritType && renderType instanceof LodestoneRenderType lodestoneRenderType) {
            if (!LodestoneRenderTypeRegistry.COPIES.containsKey(Pair.of(spiritType, lodestoneRenderType))) {
                LodestoneRenderTypeRegistry.addRenderTypeModifier(b -> b.setTransparencyState(StateShards.NORMAL_TRANSPARENCY));
            }
            LodestoneRenderType umbralRenderType = LodestoneRenderTypeRegistry.copyAndStore(spiritType, lodestoneRenderType);
            if (!RenderHandler.UNIFORM_HANDLERS.containsKey(umbralRenderType)) {
                LodestoneRenderTypeRegistry.applyUniformChanges(umbralRenderType, ShaderUniformHandler.LUMITRANSPARENT);
            }
            return super.setRenderType(umbralRenderType);
        }
        return super.setRenderType(renderType);
    }

    @Override
    public VFXBuilders.WorldVFXBuilder setAlpha(float a) {
        return super.setAlpha(a * spiritType.getAlphaMultiplier());
    }
}