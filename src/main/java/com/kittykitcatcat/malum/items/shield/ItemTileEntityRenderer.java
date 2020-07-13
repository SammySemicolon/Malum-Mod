package com.kittykitcatcat.malum.items.shield;

import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.models.ModelSkullBulwarkShield;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.MalumMod.MODID;

@OnlyIn(Dist.CLIENT)
public class ItemTileEntityRenderer extends ItemStackTileEntityRenderer
{

    private final ModelSkullBulwarkShield skull_shield = new ModelSkullBulwarkShield();

    public static final ResourceLocation skull_shield_texture = new ResourceLocation(MODID,"entity/shield/skull_bulwark");

    @Override
    public void render(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight, int packedOverlay)
    {
        Item item = itemStack.getItem();
        if (item instanceof ModShieldItem)
        {
            matrixStack.push();
            matrixStack.scale(1.0F, -1.0F, -1.0F);
            if (item == ModItems.skull_bulwark)
            {
                Material material = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, skull_shield_texture);
                IVertexBuilder vertexBuilder = material.getSprite().wrapBuffer(ItemRenderer.getBuffer(renderTypeBuffer, skull_shield.getRenderType(material.getAtlasLocation()), false, itemStack.hasEffect()));
                skull_shield.render(matrixStack, vertexBuilder, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            matrixStack.pop();
        }
    }

}