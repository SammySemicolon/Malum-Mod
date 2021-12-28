package com.sammy.malum.client.renderer.entity;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class MalumBoatRenderer extends BoatRenderer {
    private final ResourceLocation boatTexture;
    private final BoatModel boatModel;

    public MalumBoatRenderer(EntityRendererProvider.Context context, String textureName) {
        super(context);
        boatTexture = DataHelper.prefix("textures/entity/boat/" + textureName + "_boat.png");
        boatModel = new BoatModel(BoatModel.createBodyModel().bakeRoot());
    }

    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
        return Pair.of(boatTexture, boatModel);
    }
}