package com.sammy.malum.common.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MalumItemDataComponent implements AutoSyncedComponent {

    private final ItemEntity itemEntity;
    public List<ItemStack> soulsToDrop;
    public UUID attackerForSouls;
    public float totalSoulCount;

    public MalumItemDataComponent(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("soulsToDrop", Tag.TAG_LIST)) {
            soulsToDrop = new ArrayList<>();
            ListTag souls = tag.getList("soulsToDrop", Tag.TAG_COMPOUND);
            for (int i = 0; i < souls.size(); i++) {
                soulsToDrop.add(ItemStack.of(souls.getCompound(i)));
            }
        } else {
            soulsToDrop = null;
        }

        if (tag.hasUUID("attacker")) {
            attackerForSouls = tag.getUUID("attacker");
        } else {
            attackerForSouls = null;
        }

        totalSoulCount = tag.getFloat("soulCount");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        if (soulsToDrop != null) {
            ListTag souls = new ListTag();
            for (ItemStack soul : soulsToDrop) {
                souls.add(soul.serializeNBT());
            }
            tag.put("soulsToDrop", souls);
        }
        if (attackerForSouls != null) {
            tag.putUUID("attacker", attackerForSouls);
        }
        tag.putFloat("soulCount", totalSoulCount);
    }
}
