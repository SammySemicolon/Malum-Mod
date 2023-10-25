package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.*;
import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.awt.*;
import java.util.function.*;

public class MalumSpiritType {

    public static SpiritTypeBuilder create(String identifier, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        return new SpiritTypeBuilder(identifier, spiritShard, spiritMote);
    }

    public final String identifier;
    public final Supplier<SpiritShardItem> spiritShard;
    public final Supplier<SpiritMoteBlock> spiritMote;

    private final Color primaryColor;
    private final Color secondaryColor;
    private final float mainColorCoefficient;
    private final Easing mainColorEasing;

    private final Color primaryBloomColor;
    private final Color secondaryBloomColor;
    private final float bloomColorCoefficient;
    private final Easing bloomColorEasing;

    private final Color itemColor;

    protected Rarity itemRarity;
    protected Component spiritItemDescription;

    public MalumSpiritType(String identifier, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote,
                           Color primaryColor, Color secondaryColor, float mainColorCoefficient, Easing mainColorEasing,
                           Color primaryBloomColor, Color secondaryBloomColor, float bloomColorCoefficient, Easing bloomColorEasing,
                           Color itemColor) {
        this.identifier = identifier;
        this.spiritShard = spiritShard;
        this.spiritMote = spiritMote;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.mainColorCoefficient = mainColorCoefficient;
        this.mainColorEasing = mainColorEasing;
        this.primaryBloomColor = primaryBloomColor;
        this.secondaryBloomColor = secondaryBloomColor;
        this.bloomColorCoefficient = bloomColorCoefficient;
        this.bloomColorEasing = bloomColorEasing;
        this.itemColor = itemColor;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public Color getItemColor() {
        return itemColor;
    }

    public ColorParticleDataBuilder createMainColorData() {
        return createMainColorData(1f);
    }

    public ColorParticleDataBuilder createMainColorData(float coefficientMultiplier) {
        return ColorParticleData.create(primaryColor, secondaryColor).setCoefficient(mainColorCoefficient*coefficientMultiplier).setEasing(mainColorEasing);
    }

    public ColorParticleDataBuilder createBloomColorData() {
        return createBloomColorData(1f);
    }

    public ColorParticleDataBuilder createBloomColorData(float coefficientMultiplier) {
        return ColorParticleData.create(primaryBloomColor, secondaryBloomColor).setCoefficient(bloomColorCoefficient*coefficientMultiplier).setEasing(bloomColorEasing);
    }

    public Rarity getItemRarity() {
        if (itemRarity == null) {
            TextColor textColor = TextColor.fromRgb(ColorHelper.brighter(primaryColor, 1, 0.85f).getRGB());
            itemRarity = Rarity.create("malum$" + identifier, (style) -> style.withColor(textColor));
        }
        return itemRarity;
    }

    public Component getSpiritShardFlavourTextComponent() {
        if (spiritItemDescription == null) {
            spiritItemDescription = new TranslatableComponent(getSpiritFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(ColorHelper.darker(primaryColor, 1, 0.75f).getRGB()));
        }
        return spiritItemDescription;
    }

    public String getSpiritFlavourText() {
        return "malum.spirit.flavour." + identifier;
    }

    public Component getSpiritJarCounterComponent(int count) {
        return new TextComponent(" " + count + " ").append(new TranslatableComponent(getSpiritDescription())).withStyle(Style.EMPTY.withColor(primaryColor.getRGB()));
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
}