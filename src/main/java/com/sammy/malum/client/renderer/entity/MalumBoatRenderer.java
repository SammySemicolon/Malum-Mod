package com.sammy.malum.client.renderer.entity;

import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import team.lodestar.lodestone.systems.entityrenderer.LodestoneBoatRenderer;

public class MalumBoatRenderer extends LodestoneBoatRenderer {
    public MalumBoatRenderer(EntityRendererProvider.Context context, String textureName) {
        super(context, MalumMod.malumPath("textures/entity/boat/" + textureName + "_boat.png"));
    }
}