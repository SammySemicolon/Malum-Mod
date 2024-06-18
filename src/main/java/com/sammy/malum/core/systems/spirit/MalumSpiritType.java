package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataBuilder;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MalumSpiritType {

    public static SpiritTypeBuilder create(String identifier, SpiritVisualMotif visualMotif, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        return new SpiritTypeBuilder(identifier, visualMotif, spiritShard, spiritMote);
    }

    public final String identifier;
    public final Supplier<SpiritShardItem> spiritShard;
    public final Supplier<SpiritMoteBlock> spiritMote;

    private final SpiritVisualMotif visualMotif;

    private final Color itemColor;

    protected Rarity itemRarity;
    protected Component spiritItemDescription;

    public MalumSpiritType(String identifier, SpiritVisualMotif visualMotif, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote,
                           Color itemColor) {
        this.identifier = identifier;
        this.spiritShard = spiritShard;
        this.spiritMote = spiritMote;
        this.visualMotif = visualMotif;
        this.itemColor = itemColor;
    }

    public float getAlphaMultiplier() {
        return visualMotif.getAlphaMultiplier();
    }

    public Color getPrimaryColor() {
        return visualMotif.getPrimaryColor();
    }

    public Color getSecondaryColor() {
        return visualMotif.getSecondaryColor();
    }

    public float getColorCoefficient() {
        return visualMotif.getColorCoefficient();
    }

    public ColorParticleDataBuilder createColorData() {
        return createColorData(1f);
    }

    public ColorParticleDataBuilder createColorData(float coefficientMultiplier) {
        return visualMotif.createColorData(coefficientMultiplier);
    }

    public Color getItemColor() {
        return itemColor;
    }

    public TextColor getTextColor(boolean isTooltip) {
        Color color = isTooltip ? ColorHelper.darker(getPrimaryColor(), 1, 0.75f) : ColorHelper.brighter(getPrimaryColor(), 1, 0.85f);
        return TextColor.fromRgb(color.getRGB());
    }

    public Rarity getItemRarity() {
        if (itemRarity == null) {
            itemRarity = Rarity.UNCOMMON;
        }
        return itemRarity;
    }

    public Component getSpiritShardFlavourTextComponent() {
        if (spiritItemDescription == null) {
            spiritItemDescription = Component.translatable(getSpiritFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(getTextColor(true)));
        }
        return spiritItemDescription;
    }

    public String getSpiritFlavourText() {
        return "malum.spirit.flavour." + identifier;
    }

    public Component getSpiritJarCounterComponent(int count) {
        return Component.literal(" " + count + " ").append(Component.translatable(getSpiritDescription())).withStyle(Style.EMPTY.withColor(getPrimaryColor().getRGB()));
    }

    public String getSpiritDescription() {
        return "malum.spirit.description." + identifier;
    }

    public ResourceLocation getTotemGlowTexture() {
        return MalumMod.malumPath("textures/vfx/totem_poles/" + identifier + "_glow.png");
    }

    public BlockState getTotemPoleBlockState(boolean isCorrupt, BlockHitResult hit) {
        Block base = isCorrupt ? BlockRegistry.SOULWOOD_TOTEM_POLE.get() : BlockRegistry.RUNEWOOD_TOTEM_POLE.get();
        return base.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, hit.getDirection()).setValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY, identifier);
    }

    @Environment(EnvType.CLIENT)
    public Consumer<WorldParticleBuilder> applyWorldParticleChanges() {
        return visualMotif::applyWorldParticleChanges;
    }
}
