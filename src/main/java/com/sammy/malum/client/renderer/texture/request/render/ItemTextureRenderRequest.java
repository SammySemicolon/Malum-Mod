package com.sammy.malum.client.renderer.texture.request.render;

import com.sammy.malum.client.renderer.texture.DynamicTextureRenderer;
import com.sammy.malum.client.renderer.texture.LodestoneDynamicTexture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Function;

public class ItemTextureRenderRequest extends DynamicTextureRenderRequest {

    private final ItemStack stack;
    private int xOffset, yOffset;

    public static ItemTextureRenderRequest create(ItemLike itemLike, ResourceLocation writeLocation) {
        return create(itemLike.asItem().getDefaultInstance(), writeLocation);
    }

    public static ItemTextureRenderRequest create(ItemStack stack, ResourceLocation writeLocation) {
        return new ItemTextureRenderRequest(writeLocation, stack);
    }

    public static ItemTextureRenderRequest create(ItemLike itemLike, Function<ResourceLocation, ResourceLocation> writeLocation) {
        return create(itemLike.asItem().getDefaultInstance(), writeLocation);
    }

    public static ItemTextureRenderRequest create(ItemStack stack, Function<ResourceLocation, ResourceLocation> writeLocation) {
        var holder = stack.getItem().builtInRegistryHolder();
        var path = holder.key().location();
        return new ItemTextureRenderRequest(writeLocation.apply(path), stack);
    }

    protected ItemTextureRenderRequest(ResourceLocation writeLocation, ItemStack stack) {
        super(writeLocation);
        this.stack = stack;
    }

    public ItemTextureRenderRequest setOffset(int xOffset, int yOffset) {
        return setXOffset(xOffset).setYOffset(yOffset);
    }

    public ItemTextureRenderRequest setXOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public ItemTextureRenderRequest setYOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    @Override
    public void drawTexture(LodestoneDynamicTexture texture, GuiGraphics guiGraphics) {
        guiGraphics.renderFakeItem(stack, xOffset, yOffset);
    }

    @Override
    public void modify(DynamicTextureRenderer builder) {
        if (stack.getItem() instanceof BlockItem) {
            builder.setTextureSize(64);
            builder.setScale(4);
        }
    }
}
