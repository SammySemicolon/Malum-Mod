package com.sammy.malum.client;

import com.sammy.malum.MalumMod;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class MalumModelLoaderPlugin implements ModelLoadingPlugin {
    private static final ModelResourceLocation CREATIVE_BASE_MODEL = createModel("creative_scythe");
    private static final ModelResourceLocation CREATIVE_GUI_MODEL = createGuiModel("creative_scythe");

    private static final ModelResourceLocation CRUDE_BASE_MODEL = createModel("crude_scythe");
    private static final ModelResourceLocation CRUDE_GUI_MODEL = createGuiModel("crude_scythe");

    private static final ModelResourceLocation SOUL_BASE_MODEL = createModel("soul_stained_steel_scythe");
    private static final ModelResourceLocation SOUL_GUI_MODEL = createGuiModel("soul_stained_steel_scythe");

    private static final ModelResourceLocation WORLD_BASE_MODEL = createModel("weight_of_worlds");
    private static final ModelResourceLocation WORLD_GUI_MODEL = createGuiModel("weight_of_worlds");

    private static final Map<ModelResourceLocation, ModelResourceLocation> modelPairs = Map.of(
            CREATIVE_BASE_MODEL, CREATIVE_GUI_MODEL,
            CRUDE_BASE_MODEL, CRUDE_GUI_MODEL,
            SOUL_BASE_MODEL, SOUL_GUI_MODEL,
            WORLD_BASE_MODEL, WORLD_GUI_MODEL
    );

    public MalumModelLoaderPlugin() {
    }

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        modelPairs.values().forEach(pluginContext::addModels);

        pluginContext.modifyModelAfterBake().register((original, context) -> {
            ModelResourceLocation guiModelLocation = modelPairs.get(context.id());
            if (guiModelLocation != null) {
                BakedModel guiModel = context.baker().bake(guiModelLocation, context.settings());
                return new MalumBakedModel(original, guiModel);
            }
            return original;
        });
    }

    private static class MalumBakedModel extends ForwardingBakedModel {
        private static final Set<ItemDisplayContext> ITEM_GUI_CONTEXTS = EnumSet.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED);

        private final BakedModel guiModel;

        public MalumBakedModel(BakedModel heldModel, BakedModel guiModel) {
            this.wrapped = heldModel;
            this.guiModel = guiModel;
        }

        @Override
        public void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
            if (ITEM_GUI_CONTEXTS.contains(context.itemTransformationMode())) {
                guiModel.emitItemQuads(stack, randomSupplier, context);
                return;
            }
            super.emitItemQuads(stack, randomSupplier, context);
        }

        @Override
        public boolean isVanillaAdapter() {
            return false;
        }
    }

    private static ModelResourceLocation createModel(String baseName) {
        return new ModelResourceLocation(MalumMod.malumPath(baseName), "inventory");
    }

    private static ModelResourceLocation createGuiModel(String baseName) {
        return new ModelResourceLocation(MalumMod.malumPath(baseName + "_gui"), "inventory");
    }
}
