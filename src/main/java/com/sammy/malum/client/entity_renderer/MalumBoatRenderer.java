package com.sammy.malum.client.entity_renderer;

import com.sammy.malum.MalumHelper;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;

public class MalumBoatRenderer extends BoatRenderer
{
    private final ResourceLocation boatTexture;
    public MalumBoatRenderer(EntityRendererManager renderManagerIn, String textureName)
    {
        super(renderManagerIn);
        boatTexture = MalumHelper.prefix("textures/entity/boat/" + textureName + "_boat.png");
    }

    @Override
    public ResourceLocation getTextureLocation(BoatEntity entity)
    {
        return boatTexture;
    }
}
