package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData.SpiritDataEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

public class MalumSpiritRepairData {

    public final float durabilityPercentage;
    public final Ingredient repairIngredient;
    public final ArrayList<RepairDataEntry> repairData;
    public final ArrayList<SpiritDataEntry> spiritData;

    public MalumSpiritRepairData(float durabilityPercentage, Ingredient repairIngredient, ArrayList<RepairDataEntry> repairData, ArrayList<SpiritDataEntry> spiritData) {
        this.durabilityPercentage = durabilityPercentage;
        this.repairIngredient = repairIngredient;
        this.repairData = repairData;
        this.spiritData = spiritData;
    }

    public static class RepairDataEntry {
        public final Item targetItem;
        public final int extraCount;

        public RepairDataEntry(Item targetItem, int extraCount) {
            this.targetItem = targetItem;
            this.extraCount = extraCount;
        }
    }
}