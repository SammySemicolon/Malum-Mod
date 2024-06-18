package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.visual_effects.SpiritAltarParticleEffects;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarEatItemParticleEffect;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.*;

public class SpiritAltarBlockEntity extends LodestoneBlockEntity {

    private static final Vec3 ALTAR_ITEM_OFFSET = new Vec3(0.5f, 1.25f, 0.5f);
    private static final int HORIZONTAL_RANGE = 4;
    private static final int VERTICAL_RANGE = 3;

    public float speed = 1f;
    public int progress;
    public float spiritYLevel;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public boolean isCrafting;

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public Map<SpiritInfusionRecipe, AltarCraftingHelper.Ranking> possibleRecipes = new HashMap<>();
    public SpiritInfusionRecipe recipe;

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                SpiritAltarBlockEntity.this.setChanged();
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
                super.onContentsChanged(slot);
            }
        };
        extrasInventory = new MalumBlockEntityInventory(8, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new MalumBlockEntityInventory(SpiritTypeRegistry.SPIRITS.size(), 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                notifyUpdate();
                needsSync = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount + 1));
                BlockHelper.updateAndNotifyState(level, worldPosition);
                SpiritAltarBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemVariant resource, int count) {
                if (!(resource.getItem() instanceof SpiritShardItem spiritItem)) {
                    return false;
                }
                for (int i = 0; i < getSlots().size(); i++) {
                    if (i != slot) {
                        ItemStack stackInSlot = getStackInSlot(i);
                        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == spiritItem)
                            return false;
                    }
                }
                return true;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (progress != 0) {
            compound.putInt("progress", progress);
        }
        if (spiritYLevel != 0) {
            compound.putFloat("spiritYLevel", spiritYLevel);
        }
        if (speed != 0) {
            compound.putFloat("speed", speed);
        }
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (!acceleratorPositions.isEmpty()) {
            compound.putInt("acceleratorAmount", acceleratorPositions.size());
            for (int i = 0; i < acceleratorPositions.size(); i++) {
                BlockHelper.saveBlockPos(compound, acceleratorPositions.get(i), "" + i);
            }
        }

        compound.put("Inventory", inventory.serializeNBT());
        compound.put("spiritInventory", spiritInventory.serializeNBT());
        compound.put("extrasInventory", extrasInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag compound) {
        progress = compound.getInt("progress");
        spiritYLevel = compound.getFloat("spiritYLevel");
        speed = compound.getFloat("speed");
        spiritAmount = compound.getFloat("spiritAmount");

        acceleratorPositions.clear();
        accelerators.clear();
        int amount = compound.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            if (level != null && level.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                acceleratorPositions.add(pos);
                accelerators.add(accelerator);
            }
        }
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        spiritInventory.deserializeNBT(compound.getCompound("spiritInventory"));
        extrasInventory.deserializeNBT(compound.getCompound("extrasInventory"));
        super.load(compound);
    }


    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }

        if (hand.equals(InteractionHand.MAIN_HAND)) {
            recalibrateAccelerators();

            if (!spiritInventory.interact(this, level, player, hand, stack -> stack.getItem() instanceof SpiritShardItem || stack.isEmpty())) {
                inventory.interact(this, level, player, hand, stack -> true);
            }

        }
        return super.onUse(player, hand);
    }

    @Override
    public void init() {
      recalculateRecipes();
        if (level.isClientSide && !possibleRecipes.isEmpty() && !isCrafting) {
            AltarSoundInstance.playSound(this);
        }
    }

    private void recalculateRecipes() {
        ItemStack stack = inventory.getStackInSlot(0);
        Collection<SpiritInfusionRecipe> recipes = DataHelper.getAll(SpiritInfusionRecipe.getRecipes(level), r -> r.doesInputMatch(stack) && r.doSpiritsMatch(spiritInventory.nonEmptyItemStacks));
        possibleRecipes.clear();
        var pedestalItems = AltarCraftingHelper.createPedestalInventoryCapture(
            AltarCraftingHelper.capturePedestals(level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE));
        for (SpiritInfusionRecipe recipe : recipes) {
            AltarCraftingHelper.Ranking ranking = AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, pedestalItems, extrasInventory);
            if (ranking != null)
                possibleRecipes.put(recipe, ranking);
        }
        recipe = possibleRecipes.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);

        if (recipe == null && level != null) {
            extrasInventory.dumpItems(level, worldPosition);
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (!possibleRecipes.isEmpty()) {
            if (spiritYLevel < 30) {
                spiritYLevel++;
            }
            isCrafting = true;
            progress++;
            if (!level.isClientSide) {
                if (level.getGameTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300 / speed);
                if (progress >= progressCap) {
                    boolean success = consume();
                    if (success) {
                        craft();
                    }
                }
            }
        } else {
            if (isCrafting)
                progress = 0;
            isCrafting = false;
            progress++;
            if (spiritYLevel > 0) {
                this.spiritYLevel = Math.max(spiritYLevel - 0.8f, 0);
            }

            int progressCap = (int) (300 / speed);
            if (progress >= progressCap) {
                recalculateRecipes();
                progress = 0;
            }
        }
        if (level.isClientSide) {
            spiritSpin += 1 + spiritYLevel * 0.05f + speed * 0.5f;
            SpiritAltarParticleEffects.passiveSpiritAltarParticles(this);
        }
    }

    public boolean consume() {
        if (recipe == null) {
            return false;
        } else if (recipe.extraItems.isEmpty())
            return true;

        List<IMalumSpecialItemAccessPoint> pedestalItems = AltarCraftingHelper.capturePedestals(level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        ItemStack stack = inventory.getStackInSlot(0);
        AltarCraftingHelper.Ranking reranking = AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, AltarCraftingHelper.createPedestalInventoryCapture(pedestalItems), extrasInventory);
        if (!Objects.equals(reranking, possibleRecipes.get(recipe))) {
            recalculateRecipes();
            return false;
        }
        extrasInventory.updateData();
        extrasInventory.setChanged();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < recipe.extraItems.size()) {
            progress *= 0.8f;
            Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
            for (IMalumSpecialItemAccessPoint provider : altarProviders) {
                LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();

                ItemStack providedStack = inventoryForAltar.getStackInSlot(0);
                IngredientWithCount requestedItem = recipe.extraItems.get(extras);
                boolean matches = requestedItem.matches(providedStack);
                if (!matches) {
                    for (SpiritInfusionRecipe recipe : possibleRecipes) {
                        if (extras < recipe.extraItems.size() && recipe.extraItems.get(extras).matches(providedStack)) {
                            this.recipe = recipe;
                            break;
                        }
                    }
                }
                requestedItem = recipe.extraItems.get(extras);
                matches = requestedItem.matches(providedStack);
                if (matches) {
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), SpiritAltarEatItemParticleEffect.createData(provider.getAccessPointBlockPos(), providedStack));
                    }
                    try (Transaction t = TransferUtil.getTransaction()) {
                        long inserted = extrasInventory.insert(ItemVariant.of(providedStack.split(requestedItem.count)), 64, t);
                        t.commit();
                    }
                    inventoryForAltar.updateData();
                    extrasInventory.setChanged();
                    BlockHelper.updateAndNotifyState(level, provider.getAccessPointBlockPos());
                    break;
                }
            }

            return false;
        }

        return true;
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        if (recipe.useNbtFromInput && inventory.getStackInSlot(0).hasTag()) {
            outputStack.setTag(stack.getTag());
        }
        stack.shrink(recipe.input.count);
        inventory.updateData();
        for (SpiritWithCount spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack)) {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }
        spiritInventory.updateData();
        progress *= 0.75f;
        extrasInventory.clear();
        if (level instanceof ServerLevel serverLevel) {
            ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        }
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        init();
        recalibrateAccelerators();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 1f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        Map<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
        for (IAltarAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canAccelerate()) {
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

    public Vec3 getCentralItemOffset() {
        return ALTAR_ITEM_OFFSET;
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float projectedSpiritSpin = spiritSpin + spiritYLevel * 0.05f + speed * 0.5f;
        float lerpSpiritSpin = spiritSpin + partialTicks * (projectedSpiritSpin - spiritSpin);
        float distance = 1 - getSpinUp(Easing.SINE_OUT) * 0.25f + (float) Math.sin((lerpSpiritSpin % 6.28f) / 20f) * 0.025f;
        float height = 0.75f + getSpinUp(Easing.QUARTIC_OUT) * getSpinUp(Easing.BACK_OUT) * 0.5f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (lerpSpiritSpin), 360);
    }

    public float getSpinUp(Easing easing) {
        if (spiritYLevel > 30) {
            return 1;
        }
        return easing.ease(spiritYLevel / 30f, 0, 1, 1);
    }

    public static Vec3 rotatedCirclePosition(Vec3 pos, float distance, float current, float total, long gameTime, float time, float tickDelta) {
        return rotatedCirclePosition(pos, distance, distance, current, total, gameTime, time, tickDelta);
    }

    public static Vec3 rotatedCirclePosition(Vec3 pos, float distanceX, float distanceZ, float current, float total, long gameTime, float time, float tickDelta) {
        double angle = current / total * (Math.PI * 2);
        angle += (((gameTime % time) + tickDelta) / time) * (Math.PI * 2);
        double dx2 = (distanceX * Math.cos(angle));
        double dz2 = (distanceZ * Math.sin(angle));

        Vec3 vector2f = new Vec3(dx2, 0, dz2);
        double x = vector2f.x * distanceX;
        double z = vector2f.z * distanceZ;
        return pos.add(x, 0, z);
    }
}
