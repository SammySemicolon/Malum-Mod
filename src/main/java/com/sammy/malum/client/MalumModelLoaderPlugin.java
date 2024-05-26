package com.sammy.malum.client;

import com.sammy.malum.MalumMod;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Supplier;

public class MalumModelLoaderPlugin implements ModelLoadingPlugin {
    public static ModelResourceLocation BASE_MODEL;
    public static ResourceLocation REC_MODEL;
    private static ModelResourceLocation GUI_MODEL;

    public MalumModelLoaderPlugin(String name) {
        BASE_MODEL = new ModelResourceLocation(MalumMod.malumPath(name), "inventory");
        REC_MODEL = MalumMod.malumPath("item/" + name);
        GUI_MODEL = new ModelResourceLocation(MalumMod.malumPath(name + "_gui"), "inventory");
    }

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.addModels(GUI_MODEL); // Manually load the GUI model

        pluginContext.modifyModelAfterBake().register((original, context) -> {
            if (context.id().equals(BASE_MODEL) || context.id().equals(REC_MODEL)) {
                BakedModel guiModel = context.baker().bake(GUI_MODEL, context.settings());
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

    /*
    for (RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
            if (item.isPresent() && item.get() instanceof IBigItem) {
                ResourceLocation scytheId = BuiltInRegistries.ITEM.getKey(item.get());
                BigItemRenderer scytheItemRenderer = new BigItemRenderer(scytheId);
                ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(scytheItemRenderer);
                BuiltinItemRendererRegistry.INSTANCE.register(item.get(), scytheItemRenderer);
                ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
                    out.accept(new ModelResourceLocation(scytheId.withPath(scytheId.getPath() + "_gui"), "inventory"));
                    out.accept(new ModelResourceLocation(scytheId.withPath(scytheId.getPath() + "_handheld"), "inventory"));
                });
            }

        }
     */
}
