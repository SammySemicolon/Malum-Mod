package com.sammy.malum.data.block;

import com.sammy.malum.*;
import com.supermartijn642.fusion.api.model.*;
import com.supermartijn642.fusion.api.model.data.*;
import com.supermartijn642.fusion.api.predicate.*;
import com.supermartijn642.fusion.api.provider.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;

public class MalumFusionBlockModels extends FusionModelProvider {

    public MalumFusionBlockModels(PackOutput output) {
        super(MalumMod.MALUM, output);
    }

    @Override
    protected void generate() {

        var modelData = ConnectingModelDataBuilder.builder()
                .parent(new ResourceLocation("minecraft", "block/cube_all"))
                .texture("all", MalumMod.malumPath("block/spirited_glass/sacred"))
                .connection(DefaultConnectionPredicates.isSameBlock())
                .build();
        var modelInstance = ModelInstance.of(DefaultModelTypes.CONNECTING, modelData);
        this.addModel(MalumMod.malumPath("block/sacred_spirited_glass"), modelInstance);
    }
}
