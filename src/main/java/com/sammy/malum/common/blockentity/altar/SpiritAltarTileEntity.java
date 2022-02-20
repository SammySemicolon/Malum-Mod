package com.sammy.malum.common.blockentity.altar;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.common.packets.particle.altar.SpiritAltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.altar.SpiritAltarCraftParticlePacket;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.ParticleRegistry;
import com.sammy.malum.core.setup.SoundRegistry;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.sammy.malum.core.setup.PacketRegistry.INSTANCE;

public class SpiritAltarTileEntity extends SimpleBlockEntity {

    private static final int HORIZONTAL_RANGE = 4;
    private static final int VERTICAL_RANGE = 2;

    public int soundCooldown;
    public float speed;
    public int progress;
    public int spinUp;

    public ArrayList<BlockPos> acceleratorPositions = new ArrayList<>();
    public ArrayList<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;

    public boolean updateRecipe;
    public SimpleBlockEntityInventory inventory;
    public SimpleBlockEntityInventory extrasInventory;
    public SimpleBlockEntityInventory spiritInventory;
    public ArrayList<SpiritInfusionRecipe> possibleRecipes = new ArrayList<>();
    public SpiritInfusionRecipe recipe;

    public SpiritAltarTileEntity(BlockEntityType<? extends SpiritAltarTileEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public SpiritAltarTileEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

        inventory = new SimpleBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof MalumSpiritItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                updateRecipe = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        extrasInventory = new SimpleBlockEntityInventory(8, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new SimpleBlockEntityInventory(8, 64, t -> t.getItem() instanceof MalumSpiritItem) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                updateRecipe = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount+1));
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
            compound.putInt("spinUp", spinUp);
        }
        if (speed != 0) {
            compound.putFloat("speed", speed);
        }
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }

        if (!acceleratorPositions.isEmpty())
        {
            compound.putInt("acceleratorAmount", acceleratorPositions.size());
            for (int i = 0; i < acceleratorPositions.size(); i++)
            {
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
        spinUp = compound.getInt("spinUp");
        speed = compound.getFloat("speed");

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

        spiritAmount = compound.getFloat("spiritAmount");
        updateRecipe = true;
        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");
        extrasInventory.load(compound, "extrasInventory");
        super.load(compound);
    }


    @Override
    public void onBreak() {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            recalibrateAccelerators();

            if (!(heldStack.getItem() instanceof MalumSpiritItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty())
                {
                    return InteractionResult.SUCCESS;
                }
            }
            spiritInventory.interact(level, player, hand);
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void tick() {
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (updateRecipe)
        {
            ItemStack stack = inventory.getStackInSlot(0);
            possibleRecipes = new ArrayList<>(DataHelper.getAll(SpiritInfusionRecipe.getRecipes(level), r -> r.doesInputMatch(stack) && r.doSpiritsMatch(spiritInventory.getNonEmptyItemStacks())));
            recipe = SpiritInfusionRecipe.getRecipe(level, stack, spiritInventory.getNonEmptyItemStacks());
            updateRecipe = false;
        }
        if (soundCooldown > 0) {
            soundCooldown--;
        }
        if (!possibleRecipes.isEmpty()) {
            if (soundCooldown == 0){
                level.playSound(null, worldPosition, SoundRegistry.ALTAR_LOOP, SoundSource.BLOCKS, 1, 1f);
                soundCooldown = 180;
            }
            if (spinUp < 10) {
                spinUp++;
            }
            if (!level.isClientSide) {
                progress++;
                if (level.getGameTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300*Math.exp(-0.15*speed));
                if (progress >= progressCap) {
                    boolean success = consume();
                    if (success) {
                        craft();
                    }
                }
            }
        } else {
            progress = 0;
            if (spinUp > 0) {
                spinUp--;
            }
        }
        if (level.isClientSide) {
            spiritSpin += 1 + spinUp / 5f;
            passiveParticles();
        }
    }

    public static Vec3 itemPos(SpiritAltarTileEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.25f, 0.5f);
    }

    public static Vec3 spiritOffset(SpiritAltarTileEntity blockEntity, int slot) {
        float distance = 1 - Math.min(0.25f, blockEntity.spinUp / 40f) + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
        float height = 0.75f + Math.min(0.5f, blockEntity.spinUp / 20f);
        return DataHelper.rotatedCirclePosition(new Vec3(0.5f, height, 0.5f), distance, slot, blockEntity.spiritAmount, (long) blockEntity.spiritSpin, 360);
    }

    public boolean consume() {
        Vec3 itemPos = itemPos(this);
        if (recipe.extraItems.isEmpty())
        {
            return true;
        }
        extrasInventory.updateData();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < recipe.extraItems.size()) {
            progress *= 0.75f;
            Collection<BlockPos> nearbyBlocks = BlockHelper.getBlocks(worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
            for (BlockPos pos : nearbyBlocks) {
                if (level.getBlockEntity(pos) instanceof IAltarProvider blockEntity) {
                    ItemStack providedStack = blockEntity.providedInventory().getStackInSlot(0);
                    IngredientWithCount requestedItem = recipe.extraItems.get(extras);
                    boolean matches = requestedItem.matches(providedStack);
                    if (!matches) {
                        for (SpiritInfusionRecipe recipe : possibleRecipes)
                        {
                            if (extras < recipe.extraItems.size() && recipe.extraItems.get(extras).matches(providedStack))
                            {
                                this.recipe = recipe;
                                break;
                            }
                        }
                    }
                    requestedItem = recipe.extraItems.get(extras);
                    matches = requestedItem.matches(providedStack);
                    if (matches) {
                        level.playSound(null, pos, SoundRegistry.ALTAR_CONSUME, SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                        Vec3 providedItemPos = blockEntity.providedItemPos();
                        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), SpiritAltarConsumeParticlePacket.fromSpirits(providedStack, recipe.getSpirits(), providedItemPos.x, providedItemPos.y, providedItemPos.z, itemPos.x, itemPos.y, itemPos.z));
                        extrasInventory.insertItem(level, providedStack.split(requestedItem.count));
                        BlockHelper.updateAndNotifyState(level, pos);
                        break;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        Vec3 itemPos = itemPos(this);
        ItemStack outputStack = recipe.output.stack();
        if (inventory.getStackInSlot(0).hasTag()) {
            outputStack.setTag(stack.getTag());
        }
        stack.shrink(recipe.input.count);
        for (ItemWithCount spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack)) {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }

        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), SpiritAltarCraftParticlePacket.fromSpirits(recipe.getSpirits(), itemPos.x, itemPos.y, itemPos.z));
        progress = 0;
        extrasInventory.clear();
        updateRecipe = true;
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT, SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        inventory.updateData();
        spiritInventory.updateData();
        extrasInventory.updateData();
        recalibrateAccelerators();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 0f;
        accelerators.clear();
        acceleratorPositions.clear();
        ArrayList<IAltarAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        HashMap<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
        for (IAltarAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canAccelerate()) {
                int max = accelerator.getAcceleratorType().maximumEntries;
                int amount = entries.computeIfAbsent(accelerator.getAcceleratorType(), (a) -> 0);
                if (amount < max) {
                    accelerators.add(accelerator);
                    acceleratorPositions.add(((BlockEntity)accelerator).getBlockPos());
                    speed += accelerator.getAcceleration();
                    entries.replace(accelerator.getAcceleratorType(), amount + 1);
                }
            }
        }
    }
    public void passiveParticles() {
        Vec3 itemPos = itemPos(this);
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            for (IAltarAccelerator accelerator : accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(worldPosition, itemPos);
                }
            }
            if (item.getItem() instanceof MalumSpiritItem spiritSplinterItem) {
                Vec3 offset = spiritOffset(this, i);
                Color color = spiritSplinterItem.type.color;
                Color endColor = spiritSplinterItem.type.endColor;
                double x = getBlockPos().getX() + offset.x();
                double y = getBlockPos().getY() + offset.y();
                double z = getBlockPos().getZ() + offset.z();
                SpiritHelper.spawnSpiritParticles(level, x, y, z, color, endColor);
                if (!possibleRecipes.isEmpty()) {
                    Vec3 velocity = new Vec3(x, y, z).subtract(itemPos).normalize().scale(-0.03f);
                    float alpha = 0.07f / spiritInventory.nonEmptyItemAmount;
                    for (IAltarAccelerator accelerator : accelerators) {
                        if (accelerator != null) {
                            accelerator.addParticles(color, endColor, alpha, worldPosition, itemPos);
                        }
                    }
                    RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.125f, 0f)
                            .setLifetime(45)
                            .setScale(0.2f, 0)
                            .randomOffset(0.02f)
                            .randomVelocity(0.01f, 0.01f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(1.25f)
                            .setSpin(0.1f + level.random.nextFloat()*0.1f)
                            .randomVelocity(0.0025f, 0.0025f)
                            .addVelocity(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 2);

                    RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                            .setAlpha(alpha, 0f)
                            .setLifetime(25)
                            .setScale(0.5f, 0)
                            .randomOffset(0.1, 0.1)
                            .randomVelocity(0.02f, 0.02f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(1.5f)
                            .randomVelocity(0.0025f, 0.0025f)
                            .enableNoClip()
                            .repeat(level, itemPos.x, itemPos.y, itemPos.z, 2);
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