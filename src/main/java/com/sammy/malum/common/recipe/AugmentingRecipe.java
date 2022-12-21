package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.common.blockentity.spirit_altar.IAltarProvider;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistryEntry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class AugmentingRecipe extends ILodestoneRecipe {
    public static final String NAME = "augmenting";

    private final ResourceLocation id;

    public final Ingredient targetItem;
    public final Ingredient augment;
    public final CompoundTag tagAugment;

    public AugmentingRecipe(ResourceLocation id, Ingredient targetItem, Ingredient augment, CompoundTag tagAugment) {
        this.id = id;
        this.targetItem = targetItem;
        this.augment = augment;
        this.tagAugment = tagAugment;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.AUGMENTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.AUGMENTING.get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.targetItem.test(input);
    }

    public boolean doesAugmentMatch(ItemStack input) {
        return this.augment.test(input);
    }

    public static AugmentingRecipe getRecipe(Level level, ItemStack stack, ItemStack augment) {
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doesAugmentMatch(augment));
    }

    public static AugmentingRecipe getRecipe(Level level, Predicate<AugmentingRecipe> predicate) {
        List<AugmentingRecipe> recipes = getRecipes(level);
        for (AugmentingRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<AugmentingRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.AUGMENTING.get());
    }

    public static InteractionResult performAugmentation(IAltarProvider altarProvider, Player player, InteractionHand hand) {
        LodestoneBlockEntityInventory inventory = altarProvider.getInventoryForAltar();
        Level level = player.level;
        ItemStack target = inventory.getStackInSlot(0);
        if (!target.isEmpty()) {
            ItemStack applied = player.getItemInHand(hand);
            AugmentingRecipe recipe = AugmentingRecipe.getRecipe(level, target, applied);
            if (recipe != null) {
                if (!level.isClientSide) {
                    Random random = level.random;
                    CompoundTag tag = target.getOrCreateTag();
                    CompoundTag modifiedTag = tag.copy().merge(recipe.tagAugment);
                    if (tag.equals(modifiedTag)) {
                        return InteractionResult.FAIL;
                    }
                    target.setTag(modifiedTag);
                    Vec3 pos = altarProvider.getItemPosForAltar();
                    ItemEntity pEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), target);
                    pEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(altarProvider.getBlockPosForAltar())), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), pos));
                    level.playSound(null, altarProvider.getBlockPosForAltar(), SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);

                    level.addFreshEntity(pEntity);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    if (!player.isCreative()) {
                        applied.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AugmentingRecipe> {

        @Override
        public AugmentingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient targetItem = Ingredient.fromJson(json.get("targetItem"));
            Ingredient input = Ingredient.fromJson(json.get("augment"));

            CompoundTag tagAugment = CraftingHelper.getNBT(json.get("tagAugment"));
            return new AugmentingRecipe(recipeId, targetItem, input, tagAugment);
        }

        @Nullable
        @Override
        public AugmentingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient targetItem = Ingredient.fromNetwork(buffer);
            Ingredient input = Ingredient.fromNetwork(buffer);
            CompoundTag tagAugment = buffer.readNbt();
            return new AugmentingRecipe(recipeId, targetItem, input, tagAugment);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AugmentingRecipe recipe) {
            recipe.targetItem.toNetwork(buffer);
            recipe.augment.toNetwork(buffer);
            buffer.writeNbt(recipe.tagAugment);
        }
    }
}