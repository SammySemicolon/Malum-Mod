package com.sammy.malum.client.screen.codex.display.texture.request;

import com.sammy.malum.client.screen.codex.display.texture.RenderedDynamicTexture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Function;

public class ItemTextureRequest extends DynamicTextureRequest {

    private final ItemStack stack;
    private int xOffset, yOffset;

    public static ItemTextureRequest create(ItemLike itemLike, ResourceLocation writeLocation) {
        return create(itemLike.asItem().getDefaultInstance(), writeLocation);
    }

    public static ItemTextureRequest create(ItemStack stack, ResourceLocation writeLocation) {
        return new ItemTextureRequest(writeLocation, stack);
    }

    public static ItemTextureRequest create(ItemLike itemLike, Function<ResourceLocation, ResourceLocation> writeLocation) {
        return create(itemLike.asItem().getDefaultInstance(), writeLocation);
    }

    public static ItemTextureRequest create(ItemStack stack, Function<ResourceLocation, ResourceLocation> writeLocation) {
        var holder = stack.getItem().builtInRegistryHolder();
        var path = holder.key().location();
        return new ItemTextureRequest(writeLocation.apply(path), stack);
    }

    protected ItemTextureRequest(ResourceLocation writeLocation, ItemStack stack) {
        super(writeLocation);
        this.stack = stack;
    }

    public ItemTextureRequest setOffset(int xOffset, int yOffset) {
        return setXOffset(xOffset).setYOffset(yOffset);
    }

    public ItemTextureRequest setXOffset(int xOffset) {
        this.xOffset = xOffset;
        return this;
    }

    public ItemTextureRequest setYOffset(int yOffset) {
        this.yOffset = yOffset;
        return this;
    }

    @Override
    public void drawTexture(RenderedDynamicTexture texture, GuiGraphics guiGraphics) {
        guiGraphics.renderFakeItem(stack, xOffset, yOffset);
    }
}
