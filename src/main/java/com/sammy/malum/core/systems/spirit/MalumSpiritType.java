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

import java.awt.*;
import java.util.function.*;

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
        return new TextComponent(" " + count + " ").append(new TranslatableComponent(getSpiritDescription())).withStyle(Style.EMPTY.withColor(primaryColor.getRGB()));
    }

    public Component getSpiritShardFlavourTextComponent(ItemStack stack) {
        return new TranslatableComponent(getSpiritFlavourText()).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(ColorHelper.darker(primaryColor, 1, 0.75f).getRGB()));
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