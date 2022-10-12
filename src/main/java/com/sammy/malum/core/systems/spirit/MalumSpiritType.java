package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.helpers.ColorHelper;

import java.awt.*;
import java.util.function.Supplier;

public class MalumSpiritType {

    //TODO: we'd probably want some sorta builder class for this.

    private final Color color;

    private final Color endColor;
    public final String identifier;

    protected Supplier<Item> splinterItem;

    public final Rarity rarity;

    public MalumSpiritType(String identifier, Color color, RegistryObject<Item> splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = createEndColor(color);
        this.splinterItem = splinterItem;
        this.rarity = createRarity(identifier, color);
    }

    public MalumSpiritType(String identifier, Color color, Color endColor, RegistryObject<Item> splinterItem) {
        this.identifier = identifier;
        this.color = color;
        this.endColor = endColor;
        this.splinterItem = splinterItem;
        this.rarity = createRarity(identifier, color);
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

    public Component getFlavourComponent(ItemStack stack) {
        return new TranslatableComponent(getFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(ColorHelper.darker(color, 1, 0.75f).getRGB()));
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
        return MalumMod.malumPath("spirit/" + identifier + "_glow");
    }

    public BlockState getBlockState(boolean isCorrupt, BlockHitResult hit) {
        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
        return base.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY, identifier);
    }

    private static Rarity createRarity(String identifier, Color color) {
        final TextColor textColor = TextColor.fromRgb(ColorHelper.brighter(color, 1, 0.85f).getRGB());
        return Rarity.create("malum$" + identifier, (style) -> style.withColor(textColor));
    }
}