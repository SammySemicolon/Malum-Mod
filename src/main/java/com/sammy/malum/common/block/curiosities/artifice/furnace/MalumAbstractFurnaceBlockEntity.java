package com.sammy.malum.common.block.curiosities.artifice.furnace;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.furnace.MalumAbstractFurnaceRecipe;
import com.sammy.malum.common.recipe.furnace.crystallarium.MalumSizedChanceResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import team.lodestar.lodestone.modules.toolkit.blockentity.IInventoryCapabilityProvider;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeSearch;

import java.util.Optional;

public abstract class MalumAbstractFurnaceBlockEntity<I extends RecipeInput, R extends MalumAbstractFurnaceRecipe<I>> extends LodestoneBlockEntity implements IInventoryCapabilityProvider {
    public int litTime;
    public int litDuration;
    public int cookingProgress;
    public int cookingTotalTime;
    protected final ContainerData dataAccess;
    private final RecipeType<R> recipeType;

    public MalumAbstractFurnaceBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType<R> recipeType) {
        super(type, pos, state);
        this.dataAccess = createDataAccess();
        this.recipeType = recipeType;
    }

    @Override
    public void serverTick(ServerLevel level) {
        boolean shouldSync = false;
        if (this.isLit()) {
            this.litTime--;
        }

        if (this.isLit() || (hasFuel() && hasInput())) {
            if (hasInput()) {
                var optionalRecipeHolder = getValidRecipe(level);
                if (optionalRecipeHolder.isPresent()) {
                    var recipe = optionalRecipeHolder.get();
                    NonNullList<ItemStack> results = rollOutputs(recipe.getFurnaceResults(), recipe.getResultFallback(), level.getRandom());
                    if (!results.isEmpty() && optionalPreValidation(level, results, recipe)) {
                        if (!isLit()) {
                            int newLitTime = this.getBurnDuration(this.getFuelStack());
                            this.litTime = newLitTime;
                            this.litDuration = newLitTime;
                            if (newLitTime > 0) {
                                var stack = this.getFuelStack();
                                var inv = this.inventory();
                                int fuelSlot = inv.getFuelSlot();
                                if (stack.hasCraftingRemainingItem()) {
                                    inv.setStackInSlot(fuelSlot, stack.getCraftingRemainingItem());
                                } else if (!stack.isEmpty()) {
                                    stack.shrink(1);
                                    if (stack.isEmpty()) {
                                        inv.setStackInSlot(fuelSlot, stack.getCraftingRemainingItem());
                                    }
                                }
                                //isLit = true;
                                shouldSync = true;
                            }
                        }

                        if (isLit()) {
                            shouldSync = process(recipe, results, level);
                        } else {
                            this.cookingProgress = 0;
                        }
                    } else {
                        this.cookingProgress = 0;
                    }
                }
            } else {
                this.cookingProgress = 0;
            }
        } else if (this.cookingProgress > 0) {
            this.cookingProgress = Mth.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
        }

        if (shouldSync) {
            this.setDirty();
        }

    }

    protected NonNullList<ItemStack> rollOutputs(NonNullList<MalumSizedChanceResult> results, Optional<ItemStack> fallback, RandomSource random) {
        NonNullList<ItemStack> outputList = NonNullList.create();
        if (results.isEmpty()) return outputList;

        for (MalumSizedChanceResult result : results) {
            float rand = random.nextFloat();
            if (rand < result.chance()) {
                outputList.add(result.result().copy());
            }
        }

        if (outputList.isEmpty() && fallback.isPresent()) {
            outputList.add(fallback.get().copy());
        }

        return outputList;
    }

    protected boolean process(R recipe, NonNullList<ItemStack> results, ServerLevel serverLevel) {
        ++this.cookingProgress;
        this.cookingTotalTime = recipe.getProcessingTime();
        if (this.cookingProgress < this.cookingTotalTime) {
            return false;
        } else {
            this.cookingProgress = 0;
            int resultAmount = results.size();
            int filledProgress = 0;
            for (ItemStack result : results) {
                if (this.inventory().fillOutputSlotsStacked(result, false).isEmpty()) {
                    filledProgress++;
                }
            }
            if (filledProgress < resultAmount) {
                MalumMod.LOGGER.info("Was not able to insert all result items for recipetype {}", recipe.getType());
            }
            this.inventory().shrinkAllInputs();
            onFinishRecipe(recipe, serverLevel);
            return true;
        }
    }

    //TODO add caching for last recipe
    public Optional<R> getValidRecipe(Level level) {
        return hasInput() ? Optional.ofNullable(LodestoneRecipeSearch.search(level, recipeType).findRecipe(getRecipeInput())) : Optional.empty();
    }

    @Override
    public IItemHandler getInventory(Direction direction) { //TODO check if we actually need this interface
        return this.inventory();
    }

    public boolean isLit() {
        return this.litTime > 0;
    }

    public boolean hasFuel() {
        return !getFuelStack().isEmpty();
    }

    public ItemStack getFuelStack() {
        return this.inventory().getStackInSlot(this.inventory().getFuelSlot());
    }

    public boolean hasInput() {
        int[] inputSlots = this.inventory().getInputSlots();
        for(int i : inputSlots) {
            if (this.inventory().getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    protected abstract MalumFurnaceBlockItemStackHandler inventory();

    protected abstract I getRecipeInput();

    protected abstract int getBurnDuration(ItemStack fuel);

    protected boolean optionalPreValidation(ServerLevel level, NonNullList<ItemStack> results, R recipe) {
        return true;
    }

    protected void onFinishRecipe(R recipe, ServerLevel level) {}

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.inventory().save(registries, tag);
        tag.putInt("BurnTime", this.litTime);
        tag.putInt("CookTime", this.cookingProgress);
        tag.putInt("CookTimeTotal", this.cookingTotalTime);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        this.inventory().load(pRegistries, compound);
        super.loadAdditional(compound, pRegistries);
        this.litTime = compound.getInt("BurnTime");
        this.cookingProgress = compound.getInt("CookTime");
        this.cookingTotalTime = compound.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration(this.getFuelStack());
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var tag = new CompoundTag();
        this.inventory().save(registries, tag);
        return tag;
    }

    private ContainerData createDataAccess() {
        return new ContainerData()
        {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MalumAbstractFurnaceBlockEntity.this.litTime;
                    case 1 -> MalumAbstractFurnaceBlockEntity.this.litDuration;
                    case 2 -> MalumAbstractFurnaceBlockEntity.this.cookingProgress;
                    case 3 -> MalumAbstractFurnaceBlockEntity.this.cookingTotalTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> MalumAbstractFurnaceBlockEntity.this.litTime = value;
                    case 1 -> MalumAbstractFurnaceBlockEntity.this.litDuration = value;
                    case 2 -> MalumAbstractFurnaceBlockEntity.this.cookingProgress = value;
                    case 3 -> MalumAbstractFurnaceBlockEntity.this.cookingTotalTime = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }
}
