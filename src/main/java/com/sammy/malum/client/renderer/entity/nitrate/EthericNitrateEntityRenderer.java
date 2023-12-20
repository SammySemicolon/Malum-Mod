package com.sammy.malum.client.renderer.entity.nitrate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.renderer.entity.nitrate.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;

import java.awt.*;

import static com.sammy.malum.MalumMod.malumPath;

public class EthericNitrateEntityRenderer extends AbstractNitrateEntityRenderer<EthericNitrateEntity> {

    public EthericNitrateEntityRenderer(EntityRendererProvider.Context context) {
        super(context, EthericNitrateEntity.FIRST_COLOR, EthericNitrateEntity.SECOND_COLOR);
    }
}
