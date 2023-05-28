package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.client.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.packets.particle.curiosities.altar.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public class SpiritAltarBlockEntity extends LodestoneBlockEntity {

    private static final int HORIZONTAL_RANGE = 4;
    private static final int VERTICAL_RANGE = 3;

    public float speed = 1f;
    public int progress;
    public float spinUp;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public boolean isCrafting;

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public List<SpiritInfusionRecipe> possibleRecipes = new ArrayList<>();
    public SpiritInfusionRecipe recipe;

    public LazyOptional<IItemHandler> internalInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, extrasInventory, spiritInventory));
    public LazyOptional<IItemHandler> exposedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, spiritInventory));

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

        inventory = new LodestoneBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        extrasInventory = new LodestoneBlockEntityInventory(8, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new LodestoneBlockEntityInventory(SpiritTypeRegistry.SPIRITS.size(), 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount + 1));
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (!(stack.getItem() instanceof SpiritShardItem spiritItem))
                    return false;

                for (int i = 0; i < getSlots(); i++) {
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
        if (spinUp != 0) {
            compound.putFloat("spinUp", spinUp);
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

        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
        extrasInventory.save(compound, "extrasInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        progress = compound.getInt("progress");
        spinUp = compound.getFloat("spinUp");
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
        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");
        extrasInventory.load(compound, "extrasInventory");
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
            ItemStack heldStack = player.getMainHandItem();
            recalibrateAccelerators();

            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty()) {
                    return InteractionResult.SUCCESS;
                }
            }
            spiritInventory.interact(level, player, hand);
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void init() {
        ItemStack stack = inventory.getStackInSlot(0);
        possibleRecipes = new ArrayList<>(DataHelper.getAll(SpiritInfusionRecipe.getRecipes(level), r -> r.doesInputMatch(stack) && r.doSpiritsMatch(spiritInventory.nonEmptyItemStacks)));
        recipe = SpiritInfusionRecipe.getRecipe(level, stack, spiritInventory.nonEmptyItemStacks);
        if (level.isClientSide && !possibleRecipes.isEmpty() && !isCrafting) {
            AltarSoundInstance.playSound(this);
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (!possibleRecipes.isEmpty()) {
            if (spinUp < 30) {
                spinUp++;
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
            isCrafting = false;
            progress = 0;
            if (spinUp > 0) {
                float spinUp = 0.1f + Easing.QUAD_OUT.ease(Math.min(1, this.spinUp/10f), 0, 1, 1)*1.4f;
                this.spinUp -= spinUp;
            }
        }
        if (level.isClientSide) {
            spiritSpin += 1 + spinUp / 15f + speed / 2f;
            passiveParticles();
        }
    }

    public static Vec3 getItemPos(SpiritAltarBlockEntity blockEntity) {
        return BlockHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.25f, 0.5f);
    }

    public Vec3 getSpiritOffset(int slot, float partialTicks) {
        float distance = 1 - getSpinUp(Easing.SINE_OUT) * 0.25f + (float) Math.sin((spiritSpin + partialTicks) / 20f) * 0.025f;
        float height = 0.75f + getSpinUp(Easing.QUARTIC_OUT) * getSpinUp(Easing.BACK_OUT) * 0.5f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public float getSpinUp(Easing easing) {
        if (spinUp > 30) {
            return 1;
        }
        return easing.ease(spinUp / 30f, 0, 1, 1);
    }
    public boolean consume() {
        Vec3 itemPos = getItemPos(this);
        if (recipe.extraItems.isEmpty()) {
            return true;
        }
        extrasInventory.updateData();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < recipe.extraItems.size()) {
            progress *= 0.8f;
            Collection<IAltarProvider> altarProviders = BlockHelper.getBlockEntities(IAltarProvider.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
            for (IAltarProvider provider : altarProviders) {
                LodestoneBlockEntityInventory inventoryForAltar = provider.getInventoryForAltar();
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
                    level.playSound(null, provider.getBlockPosForAltar(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    Vec3 providedItemPos = provider.getItemPosForAltar();
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(provider.getBlockPosForAltar())), new AltarConsumeParticlePacket(providedStack, recipe.spirits.stream().map(s -> s.type.identifier).collect(Collectors.toList()), providedItemPos.x, providedItemPos.y, providedItemPos.z, itemPos.x, itemPos.y, itemPos.z));
                    extrasInventory.insertItem(level, providedStack.split(requestedItem.count));
                    inventoryForAltar.updateData();
                    BlockHelper.updateAndNotifyState(level, provider.getBlockPosForAltar());
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
        Vec3 itemPos = getItemPos(this);
        if (inventory.getStackInSlot(0).hasTag()) {
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
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarCraftParticlePacket(recipe.spirits.stream().map(s -> s.type.identifier).collect(Collectors.toList()), itemPos));
        progress *= 0.75f;
        extrasInventory.clear();
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

    public void passiveParticles() {
        Vec3 itemPos = getItemPos(this);
        float particleVelocityMultiplier = -0.015f;
        int particleAge = 40;
        float scaleMultiplier = 1f;
        if (recipe != null) {
            if (recipe.spirits.size() > 1) {
                int amount = (recipe.spirits.size() - 2);
                particleVelocityMultiplier -= amount * 0.005f;
                particleAge -= amount * 5;
                scaleMultiplier += amount * 0.02f;
            }
        }
        int spiritCount = spiritInventory.nonEmptyItemAmount;
        Item currentItem = spiritInventory.getStackInSlot(0).getItem();
        if (spiritCount > 1) {
            float duration = 30f * spiritCount;
            float gameTime = (getLevel().getGameTime() % duration) / 30f;
            currentItem = spiritInventory.getStackInSlot(Mth.floor(gameTime)).getItem();
        }
        if (!(currentItem instanceof SpiritShardItem spiritItem)) {
            return;
        }
        MalumSpiritType activeSpiritType = spiritItem.type;
        for (IAltarAccelerator accelerator : accelerators) {
            if (accelerator != null) {
                accelerator.addParticles(this, activeSpiritType, itemPos);
            }
        }
        if (recipe != null) {
            Color firstColor = activeSpiritType.getPrimaryColor();
            Color secondColor = activeSpiritType.getSecondaryColor();
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.035f, 0.22f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.QUARTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0.4f * scaleMultiplier, 0).setEasing(Easing.QUINTIC_IN, Easing.CUBIC_IN_OUT).build())
                    .setLifetime(particleAge)
                    .setRandomOffset(0.1)
                    .setRandomMotion(0.02f)
                    .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.BOUNCE_IN_OUT).setCoefficient(0.5f).build())
                    .setRandomMotion(0.0025f, 0.0025f)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, itemPos.x, itemPos.y, itemPos.z, 1);
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = getSpiritOffset(spiritsRendered++, 0);
                Color color = spiritSplinterItem.type.getPrimaryColor();
                Color endColor = spiritSplinterItem.type.getSecondaryColor();
                double x = getBlockPos().getX() + offset.x();
                double y = getBlockPos().getY() + offset.y();
                double z = getBlockPos().getZ() + offset.z();
                ParticleEffects.spawnSpiritGlimmerParticles(level, x, y, z, color, endColor);
                if (recipe != null) {
                    Vec3 velocity = new Vec3(x, y, z).subtract(itemPos).normalize().scale(particleVelocityMultiplier);
                    WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                            .setTransparencyData(GenericParticleData.create(0.15f, 0.25f, 0f).build())
                            .setColorData(ColorParticleData.create(color, endColor).setEasing(Easing.BOUNCE_IN_OUT).setCoefficient(0.8f).build())
                            .setScaleData(GenericParticleData.create(0.225f*scaleMultiplier, 0).build())
                            .setSpinData(SpinParticleData.create(0.1f + level.random.nextFloat() * 0.1f).build())
                            .setLifetime(particleAge)
                            .setRandomOffset(0.02f)
                            .setRandomMotion(0.01f, 0.01f)
                            .setRandomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 1);

                    WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                            .setTransparencyData(GenericParticleData.create(0.05f, 0.15f, 0f).build())
                            .setScaleData(GenericParticleData.create(0.1f*scaleMultiplier, 0).build())
                            .setLifetime(particleAge)
                            .setRandomOffset(0.02f)
                            .setRandomMotion(0.01f, 0.01f)
                            .setColorData(ColorParticleData.create(endColor, color.darker()).setEasing(Easing.BOUNCE_IN_OUT).setCoefficient(0.8f).build())
                            .setSpinData(SpinParticleData.create(0.1f + level.random.nextFloat() * 0.1f).build())
                            .setRandomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 1);
                }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null)
                return internalInventory.cast();
            return exposedInventory.cast();
        }
        return super.getCapability(cap, side);
    }
}
