package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import team.lodestar.lodestone.helpers.render.ColorHelper;

import java.awt.*;
import java.util.function.Supplier;

public class MalumSpiritType {

    public final String identifier;
    protected final Supplier<SpiritShardItem> spiritShard;
    protected final Supplier<SpiritMoteBlock> spiritMote;

    private final Color primaryColor;
    private final Color secondaryColor;

    public MalumSpiritType(String identifier, Color primaryColor, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        this(identifier, primaryColor, new Color(primaryColor.getGreen(), primaryColor.getBlue(), primaryColor.getRed()), spiritShard, spiritMote);
    }

    public MalumSpiritType(String identifier, Color primaryColor, Color secondaryColor, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        this.identifier = identifier;
        this.spiritShard = spiritShard;
        this.spiritMote = spiritMote;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public SpiritShardItem getSpiritShardItem() {
        return spiritShard.get();
    }

    public SpiritMoteBlock getSpiritMoteBlock() {
        return spiritMote.get();
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public Component getSpiritJarCounterComponent(int count) {
        return Component.literal(" " + count + " ").append(Component.translatable(getSpiritDescription())).withStyle(Style.EMPTY.withColor(primaryColor.getRGB()));
    }

    public Component getSpiritShardFlavourTextComponent(ItemStack stack) {
        return Component.translatable(getSpiritFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(ColorHelper.darker(primaryColor, 1, 0.75f).getRGB()));
    }

    public String getSpiritDescription() {
        return "malum.spirit.description." + identifier;
    }

    public String getSpiritFlavourText() {
        return "malum.spirit.flavour." + identifier;
    }

    public ResourceLocation getTotemGlowTexture() {
        return MalumMod.malumPath("vfx/totem_poles/" + identifier + "_glow");
    }

    public BlockState getTotemPoleBlockState(boolean isCorrupt, BlockHitResult hit) {
        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
        return base.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY, identifier);
    }

    public Rarity getItemRarity() {
        TextColor textColor = TextColor.fromRgb(ColorHelper.brighter(primaryColor, 1, 0.85f).getRGB());
        return Rarity.create("malum$" + identifier, (style) -> style.withColor(textColor));
    }
}