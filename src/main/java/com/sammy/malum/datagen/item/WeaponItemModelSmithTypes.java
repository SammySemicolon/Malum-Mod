package com.sammy.malum.datagen.item;

import com.sammy.malum.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.modules.datagen.*;
import team.lodestar.lodestone.modules.datagen.smith.itemmodel.*;

import java.util.function.*;

import static team.lodestar.lodestone.modules.datagen.ItemModelSmithTypes.*;

public class WeaponItemModelSmithTypes {

    protected static final ResourceLocation LARGE_HANDHELD = MalumMod.malumPath("item/handheld_large");
    protected static final ResourceLocation LARGE_GENERATED = MalumMod.malumPath("item/generated_large");
    protected static final ResourceLocation GREATSWORD = MalumMod.malumPath("item/greatsword");

    protected static final Function<LargeItemParams, Consumer<ItemModelSmithResult>> LARGE_ITEM = params -> result -> {
        var provider = result.provider();
        var existingFileHelper = provider.existingFileHelper;
        var separateTransforms = result.addSeparateTransformData();
        var firstPersonModel = ItemModelSmith.parentedItem(params.largeItemModel, true)
                .addModelPathAffix(params.modelPathAffix + "_huge")
                .addTextureNameAffix(params.texturePathAffix + "_huge")
                .act(provider, result.item());
        var guiModel = ItemModelSmithTypes.GENERATED_ITEM
                .addModelPathAffix(params.modelPathAffix + "_gui")
                .addTextureNameAffix(params.texturePathAffix)
                .act(provider, result.item());
        var reparent = firstPersonModel.parentedToThis(existingFileHelper);
        var guiReparent = guiModel.parentedToThis(existingFileHelper);
        separateTransforms.perspective(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, reparent);
        separateTransforms.perspective(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, reparent);
        separateTransforms.perspective(ItemDisplayContext.FIRST_PERSON_LEFT_HAND, reparent);
        separateTransforms.perspective(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND, reparent);
        separateTransforms.base(guiReparent);
    };

    public static ItemModelSmith LARGE_GENERATED_ITEM = ItemModelSmith.parentedItem(GENERATED, false).modifyResult(LARGE_ITEM.apply(create(LARGE_GENERATED)));
    public static ItemModelSmith LARGE_HANDHELD_ITEM = ItemModelSmith.parentedItem(HANDHELD, false).modifyResult(LARGE_ITEM.apply(create(LARGE_HANDHELD)));
    public static ItemModelSmith GREATSWORD_ITEM = ItemModelSmith.parentedItem(HANDHELD, false).modifyResult(LARGE_ITEM.apply(create(GREATSWORD)));

    public static ItemModelSmith VINDICTIVE_BRAND = new ItemModelSmith((item, provider) -> {
        var sealed = GREATSWORD_ITEM.act(provider, item);
        var affix = "_unsealed";
        var config = create(GREATSWORD).addModelPathAffix(affix).addTexturePathAffix(affix);
        var unsealed = ItemModelSmith.parentedItem(HANDHELD, false).modifyResult(LARGE_ITEM.apply(config)).addModelPathAffix(affix).addTextureNameAffix(affix).act(provider, item);
        var reparent = unsealed.parentedToThis(provider.existingFileHelper);
        return sealed.builder().override()
                .predicate(MalumMod.malumPath("unsealed"), 1)
                .model(reparent)
                .end();
    });

    public static ItemModelSmith SOUL_OF_AN_ITEM = new ItemModelSmith((item, provider) -> provider.getBuilder(provider.getItemName(item)))
            .modifyResult(result -> {
                var provider = result.provider();
                var existingFileHelper = provider.existingFileHelper;
                var separateTransforms = result.addSeparateTransformData();
                var guiModel = ItemModelSmithTypes.GENERATED_ITEM.addModelPathAffix("_gui").act(provider, result.item());
                var reparent = guiModel.parentedToThis(existingFileHelper);
                separateTransforms.perspective(ItemDisplayContext.GUI, reparent);
                separateTransforms.perspective(ItemDisplayContext.FIXED, reparent);
                separateTransforms.base(provider.getBuilder("item/air"));
            });

    public static LargeItemParams create(ResourceLocation largeItemModel) {
        return new LargeItemParams(largeItemModel);
    }

    public static class LargeItemParams {
        protected final ResourceLocation largeItemModel;
        protected String modelPathAffix = "";
        protected String texturePathAffix = "";

        public LargeItemParams(ResourceLocation largeItemModel) {
            this.largeItemModel = largeItemModel;
        }

        public LargeItemParams addModelPathAffix(String prefixForTheAffix) {
            modelPathAffix += prefixForTheAffix;
            return this;
        }

        public LargeItemParams addTexturePathAffix(String prefixForTheAffix) {
            texturePathAffix += prefixForTheAffix;
            return this;
        }
    }
}