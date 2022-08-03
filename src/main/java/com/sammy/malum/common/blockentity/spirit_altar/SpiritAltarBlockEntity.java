package com.sammy.malum.common.blockentity.spirit_altar;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.common.packets.particle.block.functional.AltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.block.functional.AltarCraftParticlePacket;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class SpiritAltarBlockEntity extends OrtusBlockEntity {

    private static final int HORIZONTAL_RANGE = 4;
    private static final int VERTICAL_RANGE = 3;

    public float speed;
    public int progress;
    public float spinUp;

    public ArrayList<BlockPos> acceleratorPositions = new ArrayList<>();
    public ArrayList<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public boolean isCrafting;

    public OrtusBlockEntityInventory inventory;
    public OrtusBlockEntityInventory extrasInventory;
    public OrtusBlockEntityInventory spiritInventory;
    public ArrayList<SpiritInfusionRecipe> possibleRecipes = new ArrayList<>();
    public SpiritInfusionRecipe recipe;

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

        inventory = new OrtusBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof MalumSpiritItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        extrasInventory = new OrtusBlockEntityInventory(8, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new OrtusBlockEntityInventory(8, 64, t -> t.getItem() instanceof MalumSpiritItem) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount + 1));
                BlockHelper.updateAndNotifyState(level, worldPosition);
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

            if (!(heldStack.getItem() instanceof MalumSpiritItem)) {
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
                int progressCap = (int) (300 * (1 - Math.log(1+speed)/4f));
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
            spiritSpin += 1 + spinUp / 15f + speed / 8f;
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
                OrtusBlockEntityInventory inventoryForAltar = provider.getInventoryForAltar();
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
        ItemStack outputStack = recipe.output.getStack();
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
        progress *= 0.5f;
        extrasInventory.clear();
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        init();
        recalibrateAccelerators();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 0f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        HashMap<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
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
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            for (IAltarAccelerator accelerator : accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(worldPosition, itemPos);
                }
            }
            if (item.getItem() instanceof MalumSpiritItem spiritSplinterItem) {
                Vec3 offset = getSpiritOffset(i, 0);
                Color color = spiritSplinterItem.type.getColor();
                Color endColor = spiritSplinterItem.type.getEndColor();
                double x = getBlockPos().getX() + offset.x();
                double y = getBlockPos().getY() + offset.y();
                double z = getBlockPos().getZ() + offset.z();
                SpiritHelper.spawnSpiritGlimmerParticles(level, x, y, z, color, endColor);
                if (recipe != null) {
                    Vec3 velocity = new Vec3(x, y, z).subtract(itemPos).normalize().scale(particleVelocityMultiplier);
                    float alpha = 0.07f / spiritInventory.nonEmptyItemAmount;
                    for (IAltarAccelerator accelerator : accelerators) {
                        if (accelerator != null) {
                            accelerator.addParticles(color, endColor, alpha, worldPosition, itemPos);
                        }
                    }
                    ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.15f, 0.25f, 0f)
                            .setLifetime(particleAge)
                            .setScale(0.225f*scaleMultiplier, 0)
                            .randomOffset(0.02f)
                            .randomMotion(0.01f, 0.01f)
                            .setColor(color, endColor)
                            .setColorEasing(Easing.BOUNCE_IN_OUT)
                            .setColorCoefficient(0.8f)
                            .setSpin(0.1f + level.random.nextFloat() * 0.1f)
                            .randomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 1);

                    ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.05f, 0.15f, 0f)
                            .setLifetime(particleAge)
                            .setScale(0.1f*scaleMultiplier, 0)
                            .randomOffset(0.02f)
                            .randomMotion(0.01f, 0.01f)
                            .setColor(endColor, color.darker())
                            .setColorEasing(Easing.BOUNCE_IN_OUT)
                            .setColorCoefficient(0.8f)
                            .setSpin(0.1f + level.random.nextFloat() * 0.1f)
                            .randomMotion(0.0025f, 0.0025f)
                            .addMotion(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 1);

                    ParticleBuilders.create(OrtusParticleRegistry.TWINKLE_PARTICLE)
                            .setAlpha(alpha*0.5f, alpha*3.5f, 0f)
                            .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                            .setLifetime(particleAge)
                            .setScale(0.2f, 0.4f*scaleMultiplier, 0)
                            .setScaleEasing(Easing.QUINTIC_IN, Easing.CUBIC_IN_OUT)
                            .randomOffset(0.1)
                            .randomMotion(0.02f)
                            .setColor(color, endColor)
                            .setColorEasing(Easing.BOUNCE_IN_OUT)
                            .setColorCoefficient(0.5f)
                            .randomMotion(0.0025f, 0.0025f)
                            .enableNoClip()
                            .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                            .repeat(level, itemPos.x, itemPos.y, itemPos.z, 1);
                }
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}