package com.sammy.malum.client.renderer.entity;

import com.sammy.malum.MalumMod;
import com.sammy.ortus.systems.entityrenderer.OrtusBoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MalumBoatRenderer extends OrtusBoatRenderer {
    public MalumBoatRenderer(EntityRendererProvider.Context context, String textureName) {
        super(context, MalumMod.prefix("textures/entity/boat/" + textureName + "_boat.png"));
    }
}