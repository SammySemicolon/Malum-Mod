package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.Supplier;

public class MalumSpiritType {
    private final Color color;

    private final Color endColor;
    public final String identifier;

    protected Supplier<Item> splinterItem;
    public MalumSpiritType(String identifier, Color color, RegistryObject<Item> splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = createEndColor(color);
        this.splinterItem = splinterItem;
    }

    public MalumSpiritType(String identifier, Color color, Color endColor, RegistryObject<Item> splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = endColor;
        this.splinterItem = splinterItem;
    }

    public Color getColor() {
        return color;
    }

    public Color getEndColor() {
        return endColor;
    }

    public Component getCountComponent(int count) {
        return new TextComponent(" " + count + " ").append(new TranslatableComponent(getDescription())).withStyle(Style.EMPTY.withColor(color.getRGB()));
    }
    public Component getFlavourComponent() {
        return new TranslatableComponent(getFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(color.getRGB()));
    }

    public String getDescription() {
        return "malum.spirit.description." + identifier;
    }

    public String getFlavourText() {
        return "malum.spirit.flavour." + identifier;
    }

    public Color createEndColor(Color color) {
        return new Color(color.getGreen(), color.getBlue(), color.getRed());
    }

    public MalumSpiritItem getSplinterItem() {
        return (MalumSpiritItem) splinterItem.get();
    }

    public ResourceLocation getOverlayTexture() {
        return MalumMod.prefix("block/totem/" + identifier + "_glow");
    }

    public BlockState getBlockState(boolean isCorrupt, BlockHitResult hit) {
        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
        return base.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY, identifier);
    }
}