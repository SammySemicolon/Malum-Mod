package com.sammy.malum.data;

import com.sammy.malum.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraftforge.common.data.*;

import java.util.concurrent.*;

public class MalumCuriosThings extends top.theillusivec4.curios.api.CuriosDataProvider {

    public MalumCuriosThings(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(MalumMod.MALUM, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {
        createSlot("ring")
                .size(2)
                .addCosmetic(true);
        createSlot("necklace")
                .size(1)
                .addCosmetic(true);
        createSlot("belt")
                .size(1)
                .addCosmetic(true);
        createSlot("rune")
                .size(0);

        createEntities("malum_entities")
                .addPlayer()
                .addSlots("ring", "necklace", "belt", "rune");
    }
}
