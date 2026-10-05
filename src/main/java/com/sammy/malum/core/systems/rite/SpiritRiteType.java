package com.sammy.malum.core.systems.rite;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.client.screen.codex.pages.BookPage;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlock;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.data.listener.rite.*;
import com.sammy.malum.core.helpers.TooltipComponentHelper;
import com.sammy.malum.core.systems.rite.effect.*;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class SpiritRiteType {

    public static final Codec<SpiritRiteType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(SpiritRiteType::getId),
            SpiritArcanaType.HOLDER_CODEC.listOf().fieldOf("pattern").forGetter(SpiritRiteType::getPattern),
            SpiritRiteEffect.CODEC.getHolderCodec().fieldOf("effect").forGetter(SpiritRiteType::getEffectHolder),
            Codec.BOOL.fieldOf("is_soulwood").forGetter(SpiritRiteType::isSoulwood)
    ).apply(instance, SpiritRiteType::new));

    protected final ResourceLocation id;
    protected final List<Holder<SpiritArcanaType>> pattern;
    protected final Holder<SpiritRiteEffect> effect;
    protected final boolean isSoulwood;

    private List<Component> detailedDescription;

    public SpiritRiteType(ResourceLocation id, List<Holder<SpiritArcanaType>> pattern, Holder<SpiritRiteEffect> effect, boolean isSoulwood) {
        this.id = id;
        this.pattern = pattern;
        this.effect = effect;
        this.isSoulwood = isSoulwood;
    }

    public ResourceLocation getId() {
        return id;
    }

    public List<Holder<SpiritArcanaType>> getPattern() {
        return pattern;
    }

    public Holder<SpiritRiteEffect> getEffectHolder() {
        return effect;
    }

    public SpiritRiteEffect getEffect() {
        return effect.value();
    }

    public SpiritArcanaType getIdentifyingSpirit() {
        return getPattern().getLast().value();
    }

    public boolean isSoulwood() {
        return isSoulwood;
    }

    public void triggerRiteEffect(ServerLevel level, TotemBaseBlockEntity totemBase) {
        var params = RiteEffectConfig.builder()
                .setTotemHeight(totemBase.getTotemHeight())
                .setTotemDirection(totemBase.getTotemDirection())
                .build();
        getEffect().triggerRiteEffect(level, totemBase.getBlockPos(), getIdentifyingSpirit(), params);
    }

    public boolean matches(ServerLevel level, TotemBaseBlockEntity totemBase) {
        var totemSpirits = totemBase.getSpirits(level);
        var state = totemBase.getBlockState();

        if (state.getBlock() instanceof TotemBaseBlock<?> block
                && block.corrupted != isSoulwood) {
            return false;
        }

        if (totemSpirits.size() != pattern.size()) {
            return false;
        }

        for (int i = 0; i < totemSpirits.size(); i++) {
            var spirit = pattern.get(i);
            var totemSpirit = totemSpirits.get(i);

            if (!totemSpirit.matches(spirit.value())) {
                return false;
            }
        }

        return true;
    }

    public List<Component> getDetailedDescription() {
        if (detailedDescription == null) {
            ArrayList<Component> tooltip = new ArrayList<>();
            var color = getIdentifyingSpirit().getTextData().createStyle(0.9f);
            var title = Component.translatable(getLangKey()).withStyle(color);
            var tags = getTags();
            var effectDetails = getEffectDetails();

            tooltip.add(title);
            tooltip.add(tags);
            tooltip.addAll(effectDetails);
            detailedDescription = ImmutableList.copyOf(tooltip);
        }

        return detailedDescription;
    }

    public List<Component> getEffectDetails() {
        MutableComponent effect = Component.translatable(getEffectLangKey());
        String text = effect.getString();

        String[] parts = text.split("\n");
        return Arrays.stream(parts).map(p -> TooltipComponentHelper.riteEffect(p, this)).toList();
    }

    public MutableComponent getTags() {
        MutableComponent component = Component.empty();
        List<SpiritRiteEffectTag> tags = new ArrayList<>(getEffect().getTags());
        tags.addFirst(isSoulwood() ? SpiritRiteEffectTag.SOULWOOD : SpiritRiteEffectTag.RUNEWOOD);
        Iterator<SpiritRiteEffectTag> iterator = tags.iterator();

        while (iterator.hasNext()) {
            SpiritRiteEffectTag tag = iterator.next();
            component.append(Component.translatable(tag.getLangKey()));
            if (iterator.hasNext()) {
                component.append(Component.literal(", "));
            }
        }
        return component.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_GRAY);
    }

    public ResourceLocation getRegistryName() {
        return id;
    }

    public String getLangKey() {
        return id.getNamespace() + ".gui.rite." + getName();
    }

    public String getEffectLangKey() {
        return getLangKey() + ".effect";
    }

    public String getCodexEntryLangKey() {
        return BookPage.textKey(getName());
    }

    public String getName() {
        return id.getPath();
    }

    public ResourceLocation getIcon() {
        return id.withPath(s -> "textures/vfx/rite/" + s).withSuffix(".png");
    }

    public final void save(CompoundTag tag) {
        save(tag, "rite");
    }

    public final void save(CompoundTag tag, String name) {
        tag.putString(name, id.toString());
    }

    public static Optional<SpiritRiteType> load(CompoundTag tag) {
        if (!tag.contains("rite")) {
            return Optional.empty();
        }

        var id = ResourceLocation.tryParse(tag.getString("rite"));
        var rite = SpiritRiteTypeReloadListener.DATA.get(id);
        return Optional.ofNullable(rite);
    }
}