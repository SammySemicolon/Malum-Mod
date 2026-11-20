package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.artifice.waveform.wavebanker.WaveBankerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.modules.core.easing.Easing;

public class WaveBankerRenderer extends SpiritDiodeRenderer<WaveBankerBlockEntity> {

    public WaveBankerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/waveform_artifice/wavebanker_overlay.png"), "malum.waveform_artifice.wavebanker");
    }

    @Override
    public float getGlowDelta(WaveBankerBlockEntity blockEntityIn, float delta) {
        if (delta > 0.5f) {
            return 1;
        }
        return Easing.QUARTIC_OUT.ease(delta*2);
    }
}
