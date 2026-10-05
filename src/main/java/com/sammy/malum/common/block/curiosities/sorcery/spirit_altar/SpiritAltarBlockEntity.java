package com.sammy.malum.common.block.curiosities.sorcery.spirit_altar;

import com.sammy.malum.common.block.MalumBlockItemStackHandler;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.block.storage.MalumItemHolderItemDisplayData;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.SpiritBasedRecipeInput;
import com.sammy.malum.registry.common.MalumParticleEffectTypes;
import com.sammy.malum.registry.common.sound.MalumSoundEvents;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import com.sammy.malum.registry.common.recipe.MalumRecipeTypes;
import com.sammy.malum.visual_effects.block.SpiritAltarParticleEffects;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarEatItemParticleEffect;
import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.toolkit.inventory.ItemStackMultiHandler;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeSearch;

import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("NullableProblems")
public class SpiritAltarBlockEntity extends LodestoneBlockEntity implements IInventoryCapabilityProvider {

    public static final int HORIZONTAL_RANGE = 4;
    public static final int VERTICAL_RANGE = 3;

    public float speed = 1f;
    public int progress;
    public int idleProgress;
    public boolean isCrafting;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<IAltarAccelerator> accelerators = new ArrayList<>();

    public MalumBlockItemStackHandler inventory;
    public MalumBlockItemStackHandler extrasInventory;
    public MalumBlockItemStackHandler spiritInventory;
    public ItemStackMultiHandler inventoryHandler;

    public Map<SpiritInfusionRecipe, AltarCraftingHelper.Ranking> possibleRecipes = new HashMap<>();
    public SpiritInfusionRecipe recipe;

    public SpiritAltarBlockEntity(LodestoneBlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = MalumBlockItemStackHandler.create(this, 1).noSpirits().onContentsChanged(this::recalculateRecipes).build();
        extrasInventory = MalumBlockItemStackHandler.create(this, 32).noSpirits().build();
        spiritInventory = MalumBlockItemStackHandler.create(this, 9).onlySpirits().onContentsChanged(this::recalculateRecipes).build();

        inventory.attachDisplayData(MalumItemHolderItemDisplayData::new);
        spiritInventory.attachDisplayData(SpiritAltarSpiritDisplayData::new);
        inventoryHandler = new ItemStackMultiHandler(inventory, spiritInventory);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.SPIRIT_ALTAR.get(), pos, state);
    }

    @Override
    public IItemHandler getInventory(Direction direction) {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("speed", speed);
        tag.putInt("progress", progress);
        tag.putInt("idleProgress", idleProgress);
        tag.putBoolean("isCrafting", isCrafting);

        var acceleratorData = new CompoundTag();
        acceleratorData.putInt("acceleratorAmount", acceleratorPositions.size());
        for (int i = 0; i < acceleratorPositions.size(); i++) {
            acceleratorData.put("acceleratorPosition_" + i, NBTHelper.saveBlockPos(acceleratorPositions.get(i)));
        }
        tag.put("acceleratorData", acceleratorData);

        inventory.save(registries, tag);
        spiritInventory.save(registries, tag, "spiritInventory");
        extrasInventory.save(registries, tag, "extrasInventory");
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        speed = compound.getFloat("speed");
        progress = compound.getInt("progress");
        idleProgress = compound.getInt("idleProgress");
        isCrafting = compound.getBoolean("isCrafting");

        acceleratorPositions.clear();
        accelerators.clear();
        var acceleratorData = compound.getCompound("acceleratorData");
        int amount = acceleratorData.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = NBTHelper.readBlockPos(acceleratorData.getCompound("acceleratorPosition_" + i));
            if (pos != null) {
                if (level != null && level.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                    acceleratorPositions.add(pos);
                    accelerators.add(accelerator);
                }
            }
        }
        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");
        extrasInventory.load(pRegistries, compound, "extrasInventory");

        if (level != null) {
            recalculateRecipes();
            recalibrateAccelerators();
            if (level.isClientSide && isCrafting) {
                AltarSoundInstance.playSound(this);
            }
        }
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        if (inventoryHandler.interact(serverLevel, player, hand)) {
            return ItemInteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
    }

    @Override
    public void serverTick(ServerLevel level) {
        var primeItem = inventory.getStackInSlot(0);
        if (!primeItem.isEmpty()) {
            idleProgress++;
            int progressCap = (int) (20 / speed);
            if (idleProgress >= progressCap) {
                recalculateRecipes();
                idleProgress = 0;
            }
        }
        progress = isCrafting ? progress + 1 : progress;
        if ((!isCrafting && recipe != null) || (isCrafting && recipe == null)) {
            isCrafting = !isCrafting;
            setDirty();
        }
        if (isCrafting) {
            if (level.getGameTime() % 20L == 0) {
                boolean canAccelerate = accelerators.stream().allMatch(iAltarAccelerator -> iAltarAccelerator.canAccelerate(this));
                if (!canAccelerate) {
                    recalibrateAccelerators();
                }
            }
            int progressCap = (int) (300 / speed);
            if (progress >= progressCap) {
                boolean success = consume(level);
                if (success) {
                    craft(level);
                }
            }
        }
    }

    @Override
    public void clientTick(Level level) {
        SpiritAltarParticleEffects.passiveSpiritAltarParticles(this);
    }

    @Override
    public void commonTick(Level level) {
        if (possibleRecipes.isEmpty()) {
            isCrafting = false;
            progress = 0;
        }
    }

    private void recalculateRecipes() {
        boolean hadRecipe = recipe != null;
        inventory.updateCaches();
        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            var input = new SpiritBasedRecipeInput(inventory, spiritInventory);
            var recipes = LodestoneRecipeSearch.search(level, MalumRecipeTypes.SPIRIT_INFUSION::get).findRecipes(input);
            possibleRecipes.clear();
            IItemHandlerModifiable pedestalItems = AltarCraftingHelper.createPedestalInventoryCapture(AltarCraftingHelper.capturePedestals(level, worldPosition));
            for (SpiritInfusionRecipe recipe : recipes) {
                possibleRecipes.put(recipe, AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, pedestalItems, extrasInventory));
            }
            recipe = possibleRecipes.entrySet().stream().filter(it -> it.getValue() != null).max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        } else {
            recipe = null;
            possibleRecipes.clear();
        }

        if (hadRecipe && recipe == null && level != null) {
            extrasInventory.dumpItems(level, worldPosition);
        }
        if (recipe != null) {
            isCrafting = true;
        }
    }

    public boolean consume(ServerLevel level) {
        if (recipe == null) {
            return false;
        } else if (recipe.extraInputs.isEmpty())
            return true;

        List<IMalumSpecialItemAccessPoint> pedestalItems = AltarCraftingHelper.capturePedestals(level, worldPosition);
        ItemStack stack = inventory.getStackInSlot(0);
        AltarCraftingHelper.Ranking reranking = AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, AltarCraftingHelper.createPedestalInventoryCapture(pedestalItems), extrasInventory);
        if (!Objects.equals(reranking, possibleRecipes.get(recipe))) {
            recalculateRecipes();
            return false;
        }

        SizedIngredient nextIngredient = AltarCraftingHelper.getNextIngredientToTake(recipe, extrasInventory);
        if (nextIngredient != null) {
            for (IMalumSpecialItemAccessPoint provider : pedestalItems) {
                var providerInventory = provider.getSuppliedInventory();
                var providedStack = providerInventory.extractItem(0, nextIngredient.count(), true);

                if (nextIngredient.ingredient().test(providedStack)) {
                    level.playSound(null, provider.getAccessPointBlockPos(), MalumSoundEvents.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 1.1f + level.random.nextFloat() * 0.5f);
                    MalumParticleEffectTypes.SPIRIT_ALTAR_EATS_ITEM
                            .createEffect(worldPosition)
                            .color(MalumNetworkedParticleEffectColorData.fromSpirits(recipe.spirits))
                            .customData(new SpiritAltarEatItemParticleEffect.SpiritAltarEatItemEffectData(provider.getAccessPointBlockPos(), providedStack))
                            .spawn(level);
                    var extractedStack = providerInventory.extractItem(0, nextIngredient.count(), false);
                    extrasInventory.insertItem(level, extractedStack);
                    break;
                }
            }
            progress = (int) (progress * 0.8f);
            if (extrasInventory.isEmpty()) {
                return false;
            }
            return AltarCraftingHelper.extractIngredient(extrasInventory, nextIngredient.ingredient(), nextIngredient.count(), true).isEmpty();
        }
        return true;
    }

    public void craft(ServerLevel level) {
        var stack = inventory.getStackInSlot(0);
        var outputStack = recipe.getOutput(level, stack);
        var itemPos = inventory.getDisplayData().getDisplayCenter(0);
        progress -= (int) (progress * 0.2f);
        stack.shrink(recipe.input.count());
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));

        spiritInventory.spendSpiritsOnRecipe(recipe.spirits);
        extrasInventory.clear();
        recalibrateAccelerators();
        accelerators.forEach(a -> a.completeSpiritInfusion(level, this));

        MalumParticleEffectTypes.SPIRIT_ALTAR_CRAFTS
                .createEffect(worldPosition)
                .color(MalumNetworkedParticleEffectColorData.fromSpirits(recipe.spirits))
                .spawn(level);
        level.playSound(null, worldPosition, MalumSoundEvents.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        recalculateRecipes();
        notifyObservers();
        setDirty();
    }

    public void recalibrateAccelerators() {
        speed = 1f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockEntityHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        Map<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
        for (IAltarAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canAccelerate(this)) {
                int max = accelerator.getAcceleratorType().maximumEntries();
                int amount = entries.computeIfAbsent(accelerator.getAcceleratorType(), (a) -> 0);
                if (amount < max) {
                    accelerators.add(accelerator);
                    acceleratorPositions.add(((BlockEntity) accelerator).getBlockPos());
                    speed += accelerator.getAcceleration();
                    entries.replace(accelerator.getAcceleratorType(), amount + 1);
                }
            }
        }
    }
}