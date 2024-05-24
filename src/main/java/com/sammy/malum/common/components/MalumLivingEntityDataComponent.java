package com.sammy.malum.common.components;

import com.sammy.malum.core.handlers.MalignantConversionHandler;
import com.sammy.malum.core.handlers.SoulDataHandler;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MalumLivingEntityDataComponent implements AutoSyncedComponent {

    private LivingEntity livingEntity;
    public SoulDataHandler soulData = new SoulDataHandler();
    public MalignantConversionHandler malignantConversionHandler = new MalignantConversionHandler();
    public TouchOfDarknessHandler touchOfDarknessHandler = new TouchOfDarknessHandler();

    public List<ItemStack> soulsToApplyToDrops;
    public UUID killerUUID;

    public MalumLivingEntityDataComponent(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("soulData")) {
            soulData.deserializeNBT(tag.getCompound("soulData"));
        }
        if (tag.contains("darknessAfflictionData")) {
            touchOfDarknessHandler.deserializeNBT(tag.getCompound("darknessAfflictionData"));
        }

        if (tag.contains("soulsToApplyToDrops", Tag.TAG_LIST)) {
            soulsToApplyToDrops = new ArrayList<>();
            ListTag souls = tag.getList("soulsToApplyToDrops", Tag.TAG_COMPOUND);
            for (int i = 0; i < souls.size(); i++) {
                soulsToApplyToDrops.add(ItemStack.of(souls.getCompound(i)));
            }
        } else {
            soulsToApplyToDrops = null;
        }

        if (tag.hasUUID("killerUUID")) {
            killerUUID = tag.getUUID("killerUUID");
        } else {
            killerUUID = null;
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.put("soulData", soulData.serializeNBT());
        tag.put("darknessAfflictionData", touchOfDarknessHandler.serializeNBT());

        if (soulsToApplyToDrops != null) {
            ListTag souls = new ListTag();
            for (ItemStack soul : soulsToApplyToDrops) {
                souls.add(soul.serializeNBT());
            }
            tag.put("soulsToApplyToDrops", souls);
        }
        if (killerUUID != null) {
            tag.putUUID("killerUUID", killerUUID);
        }
    }
}
