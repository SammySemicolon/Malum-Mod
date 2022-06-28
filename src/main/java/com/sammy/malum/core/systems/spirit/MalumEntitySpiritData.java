package com.sammy.malum.core.systems.spirit;

import com.google.gson.JsonSyntaxException;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MalumEntitySpiritData {
    public static final String SOUL_DATA = "soul_data";
    public static final MalumEntitySpiritData EMPTY = new MalumEntitySpiritData(SpiritTypeRegistry.SACRED_SPIRIT, new ArrayList<>(), null);
    public final MalumSpiritType primaryType;
    public final int totalCount;
    public final ArrayList<SpiritWithCount> dataEntries;
    @Nullable
    public final Ingredient spiritItem;

    public MalumEntitySpiritData(MalumSpiritType primaryType, ArrayList<SpiritWithCount> dataEntries, @Nullable Ingredient spiritItem) {
        this.primaryType = primaryType;
        this.totalCount = dataEntries.stream().mapToInt(d -> d.count).sum();
        this.dataEntries = dataEntries;
        this.spiritItem = spiritItem;
    }

    public ArrayList<Component> createTooltip() {
        return dataEntries.stream().map(SpiritWithCount::getComponent).collect(Collectors.toCollection(ArrayList::new));
    }

    public void saveTo(CompoundTag tag) {
        tag.put(SOUL_DATA, save());
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putString("primaryType", primaryType.identifier);
        tag.putInt("dataAmount", dataEntries.size());
        for (int i = 0; i < dataEntries.size(); i++) {
            CompoundTag dataTag = dataEntries.get(i).save(new CompoundTag());
            tag.put("dataEntry" + i, dataTag);
        }
        if (spiritItem != null)
            tag.putString("spiritItem", spiritItem.toJson().toString());
        return tag;
    }

    public static MalumEntitySpiritData load(CompoundTag tag) {
        CompoundTag nbt = tag.getCompound(SOUL_DATA);


        String type = nbt.getString("primaryType");
        int dataAmount = nbt.getInt("dataAmount");
        if (dataAmount == 0) {
            return EMPTY;
        }
        ArrayList<SpiritWithCount> data = new ArrayList<>();
        for (int i = 0; i < dataAmount; i++) {
            data.add(SpiritWithCount.load(nbt.getCompound("dataEntry" + i)));
        }
        Ingredient spiritItem = null;
        try {
            if (tag.contains("spiritItem", Tag.TAG_STRING)) {
                spiritItem = Ingredient.fromJson(GsonHelper.parse(tag.getString("spiritItem")));
            }
        } catch (JsonSyntaxException ignored) {
            // NO-OP
        }
        return new MalumEntitySpiritData(SpiritHelper.getSpiritType(type), data, spiritItem);
    }

}
