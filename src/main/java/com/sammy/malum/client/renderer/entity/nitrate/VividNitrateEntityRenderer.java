package com.sammy.malum.client.renderer.entity.nitrate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.renderer.entity.nitrate.*;
import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.client.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.malumPath;

public class VividNitrateEntityRenderer extends AbstractNitrateEntityRenderer<VividNitrateEntity> {

    public static final Function<Float, Color> COLOR_FUNCTION = f -> VividNitrateEntity.COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(Minecraft.getInstance().level, f));

    public VividNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context, COLOR_FUNCTION, COLOR_FUNCTION);
    }
}
