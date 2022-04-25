package com.sammy.malum.client.renderer.entity;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.ortus.renderer.OrtusBoatRenderer;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class MalumBoatRenderer extends OrtusBoatRenderer {
    public MalumBoatRenderer(EntityRendererProvider.Context context, String textureName) {
        super(context, MalumMod.prefix("textures/entity/boat/" + textureName + "_boat.png"));
    }
}