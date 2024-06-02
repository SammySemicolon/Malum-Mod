package com.sammy.malum.mixin;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SimpleCookingSerializer.class)
abstract class SimpleCookingSerializerMixin {
    @ModifyArgs(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/world/item/crafting/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/GsonHelper;getAsString(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;"))
    private void malum$getString(Args args) {
        JsonObject root = args.get(0);
        String originalKey = args.get(1);

        if (root.get(originalKey) instanceof JsonObject result) {
            args.set(0, result);
            args.set(1, "item");
        }
    }

    @ModifyArgs(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/world/item/crafting/AbstractCookingRecipe;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/SimpleCookingSerializer$CookieBaker;create(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Lnet/minecraft/world/item/crafting/CookingBookCategory;Lnet/minecraft/world/item/crafting/Ingredient;Lnet/minecraft/world/item/ItemStack;FI)Lnet/minecraft/world/item/crafting/AbstractCookingRecipe;"))
    private void malum$create(Args args, ResourceLocation identifier, JsonObject jsonObject) {
        if (jsonObject.get("result").isJsonObject()) {
            ItemStack stack = args.get(4);
            stack.setCount(jsonObject.getAsJsonObject("result").get("count").getAsInt());
        }
    }
}