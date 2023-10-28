package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.curiosities.tablet.ITabletTracker;
import com.sammy.malum.common.block.curiosities.tablet.TwistedTabletBlockEntity;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.packets.particle.curiosities.altar.AltarConsumeParticlePacket;
import com.sammy.malum.common.packets.particle.curiosities.altar.AltarCraftParticlePacket;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements IAccelerationTarget, ITabletTracker {
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public SpiritFocusingRecipe focusingRecipe;
    public SpiritRepairRecipe repairRecipe;
    public float spiritAmount;
    public float spiritSpin;
    public float speed;
    public float damageChance;
    public int maxDamage;
    public float progress;
    public boolean isCrafting;

    public int queuedCracks;
    public int crackTimer;

    public List<BlockPos> tabletPositions = new ArrayList<>();
    public List<TwistedTabletBlockEntity> twistedTablets = new ArrayList<>();
    public TwistedTabletBlockEntity validTablet;
    public int tabletFetchCooldown;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<ICrucibleAccelerator> accelerators = new ArrayList<>();

    private final LazyOptional<IItemHandler> combinedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, spiritInventory));

    public SpiritCrucibleCoreBlockEntity(BlockEntityType<? extends SpiritCrucibleCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 1, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new LodestoneBlockEntityInventory(4, 64) {
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

    public SpiritCrucibleCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CRUCIBLE.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (progress != 0) {
            compound.putFloat("progress", progress);
        }
        if (speed != 0) {
            compound.putFloat("speed", speed);
        }
        if (damageChance != 0) {
            compound.putFloat("damageChance", damageChance);
        }
        if (maxDamage != 0) {
            compound.putInt("maxDamage", maxDamage);
        }
        if (queuedCracks != 0) {
            compound.putInt("queuedCracks", queuedCracks);
        }

        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");

        saveTwistedTabletData(compound);
        saveAcceleratorData(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getFloat("spiritAmount");
        progress = compound.getFloat("progress");
        speed = compound.getFloat("speed");
        damageChance = compound.getFloat("damageChance");
        maxDamage = compound.getInt("maxDamage");
        queuedCracks = compound.getInt("queuedCracks");

        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");

        loadTwistedTabletData(level, compound);
        loadAcceleratorData(level, compound);
        super.load(compound);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            fetchTablets(level, worldPosition.above());
            ItemStack heldStack = player.getMainHandItem();
            recalibrateAccelerators(level, worldPosition);
            ItemStack spiritStack = spiritInventory.interact(level, player, hand);
            if (!spiritStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            }
            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty()) {
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    public void fetchTablets(Level level, BlockPos pos) {
        ITabletTracker.super.fetchTablets(level, pos);
        if (focusingRecipe == null && !getTablets().isEmpty()) {
            for (TwistedTabletBlockEntity tablet : getTablets()) {
                repairRecipe = SpiritRepairRecipe.getRecipe(level, inventory.getStackInSlot(0), tablet.inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks);
                if (repairRecipe != null) {
                    validTablet = tablet;
                    break;
                }
            }
        } else {
            repairRecipe = null;
            validTablet = null;
        }
    }

    @Override
    public List<TwistedTabletBlockEntity> getTablets() {
        return twistedTablets;
    }

    @Override
    public List<BlockPos> getTabletPositions() {
        return tabletPositions;
    }

    @Override
    public boolean canBeAccelerated() {
        return focusingRecipe != null && !isRemoved();
    }

    @Override
    public List<ICrucibleAccelerator> getAccelerators() {
        return accelerators;
    }

    @Override
    public List<BlockPos> getAcceleratorPositions() {
        return acceleratorPositions;
    }

    @Override
    public int getLookupRange() {
        return 4;
    }

    @Override
    public void init() {
        if (level.isClientSide && focusingRecipe == null && repairRecipe == null) {
            CrucibleSoundInstance.playSound(this);
        }
        fetchTablets(level, worldPosition.above());
        focusingRecipe = SpiritFocusingRecipe.getRecipe(level, inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks);
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (queuedCracks > 0) {
            crackTimer++;
            if (crackTimer % 7 == 0) {
                float pitch = 0.95f + (crackTimer - 8) * 0.015f + level.random.nextFloat() * 0.05f;
                level.playSound(null, worldPosition, SoundRegistry.IMPETUS_CRACK.get(), SoundSource.BLOCKS, 0.7f, pitch);
                queuedCracks--;
                if (queuedCracks == 0) {
                    crackTimer = 0;
                }
            }
        }
        if (level.isClientSide) {
            spiritSpin += (1 + Math.cos(Math.sin(level.getGameTime() * 0.1f))) * (1 + speed * 0.1f);
            passiveParticles();
        } else {
            if (focusingRecipe != null) {
                if (!accelerators.isEmpty()) {
                    boolean canAccelerate = true;
                    for (ICrucibleAccelerator accelerator : accelerators) {
                        boolean canAcceleratorAccelerate = accelerator.canAccelerate();
                        if (!canAcceleratorAccelerate) {
                            canAccelerate = false;
                        }
                    }
                    if (!canAccelerate) {
                        recalibrateAccelerators(level, worldPosition);
                        BlockHelper.updateAndNotifyState(level, worldPosition);
                    }
                }
            } else if (speed > 0) {
                speed = 0f;
                damageChance = 0f;
                maxDamage = 0;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }

            if (focusingRecipe != null) {
                isCrafting = true;
                progress += 1 + speed;
                if (progress >= focusingRecipe.time) {
                    craft();
                }
                return;
            } else if (repairRecipe != null && !getTablets().isEmpty()) {
                isCrafting = true;
                ItemStack damagedItem = inventory.getStackInSlot(0);

                int time = 400 + damagedItem.getDamageValue() * 5;
                progress++;
                if (!repairRecipe.repairMaterial.matches(validTablet.inventory.getStackInSlot(0))) {
                    fetchTablets(level, worldPosition.above());
                }
                if (progress >= time) {
                    repair();
                }
            }
            if (focusingRecipe == null && repairRecipe == null) {
                progress = 0;
                isCrafting = false;
            }

            if (focusingRecipe == null) {
                tabletFetchCooldown--;
                if (tabletFetchCooldown <= 0) {
                    tabletFetchCooldown = 5;
                    fetchTablets(level, worldPosition.above());
                }
            }
        }
    }

    public void repair() {
        Vec3 itemPos = getItemPos(this);
        Vec3 providedItemPos = validTablet.getItemPos();
        ItemStack damagedItem = inventory.getStackInSlot(0);
        ItemStack repairMaterial = validTablet.inventory.getStackInSlot(0);
        ItemStack result = SpiritRepairRecipe.getRepairRecipeOutput(damagedItem);
        result.setDamageValue(Math.max(0, result.getDamageValue() - (int) (result.getMaxDamage() * repairRecipe.durabilityPercentage)));
        inventory.setStackInSlot(0, result);

        if (repairRecipe.repairMaterial.getItem() instanceof SpiritShardItem spiritShardItem) {
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarConsumeParticlePacket(repairMaterial, List.of(spiritShardItem.type.identifier), providedItemPos.x, providedItemPos.y, providedItemPos.z, itemPos.x, itemPos.y, itemPos.z));
        } else {
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarConsumeParticlePacket(repairMaterial, repairRecipe.spirits.stream().map(s -> s.type.identifier).collect(Collectors.toList()), providedItemPos.x, providedItemPos.y, providedItemPos.z, itemPos.x, itemPos.y, itemPos.z));
        }

        repairMaterial.shrink(repairRecipe.repairMaterial.getCount());
        validTablet.inventory.updateData();

        for (SpiritWithCount spirit : repairRecipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack)) {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }

        spiritInventory.updateData();
        if (!repairRecipe.spirits.isEmpty()) {
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarCraftParticlePacket(repairRecipe.spirits.stream().map(s -> s.type.identifier).collect(Collectors.toList()), itemPos));
        } else if (repairRecipe.repairMaterial.getItem() instanceof SpiritShardItem spiritShardItem) {
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarCraftParticlePacket(List.of(spiritShardItem.type.identifier), itemPos));
        }
        repairRecipe = SpiritRepairRecipe.getRecipe(level, damagedItem, repairMaterial, spiritInventory.nonEmptyItemStacks);
        fetchTablets(level, worldPosition.above());
        BlockHelper.updateAndNotifyState(level, validTablet.getBlockPos());
        finishRecipe();
    }

    public void craft() {
        Vec3 itemPos = getItemPos(this);
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = focusingRecipe.output.copy();
        if (focusingRecipe.durabilityCost != 0 && stack.isDamageableItem()) {
            int durabilityCost = focusingRecipe.durabilityCost;
            float chance = damageChance;
            while (durabilityCost < durabilityCost + maxDamage) {
                if (level.random.nextFloat() < chance) {
                    durabilityCost++;
                    chance *= chance;
                } else {
                    break;
                }
            }
            queuedCracks = durabilityCost;
            boolean success = stack.hurt(durabilityCost, level.random, null);
            if (success && stack.getItem() instanceof ImpetusItem impetusItem) {
                inventory.setStackInSlot(0, impetusItem.getCrackedVariant().getDefaultInstance());
            }
        }
        for (SpiritWithCount spirit : focusingRecipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack)) {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }
        spiritInventory.updateData();
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new AltarCraftParticlePacket(focusingRecipe.spirits.stream().map(s -> s.type.identifier).collect(Collectors.toList()), itemPos));
        focusingRecipe = SpiritFocusingRecipe.getRecipe(level, stack, spiritInventory.nonEmptyItemStacks);
        finishRecipe();
    }

    public void finishRecipe() {
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + level.random.nextFloat() * 0.5f);
        progress = 0;
        recalibrateAccelerators(level, worldPosition);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    @Override
    public Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> recalibrateAccelerators(Level level, BlockPos pos) {
        Map<ICrucibleAccelerator.CrucibleAcceleratorType, Integer> accelerators = IAccelerationTarget.super.recalibrateAccelerators(level, pos);
        speed = 0f;
        damageChance = 0f;
        maxDamage = 0;
        accelerators.forEach((e, c) -> {
            speed += e.getAcceleration(c);
            damageChance = Math.min(damageChance + e.getDamageChance(c), 1);
            maxDamage += e.getMaximumDamage(c);
        });
        return accelerators;
    }

    public static Vec3 getItemPos(SpiritCrucibleCoreBlockEntity blockEntity) {
        return BlockHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.6f, 0.5f);
    }

    public Vec3 spiritOffset(int index, float partialTicks) {
        float distance = 0.75f + (float) Math.sin((spiritSpin + partialTicks) / 20f) * 0.025f;
        float height = 1.75f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, index, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public void passiveParticles() {
        Vec3 itemPos = getItemPos(this);
        //passive spirit particles
        int spiritsRendered = 0;
        if (!spiritInventory.isEmpty()) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack item = spiritInventory.getStackInSlot(i);
                if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                    Vec3 offset = spiritOffset(spiritsRendered++, 0);
                    double x = getBlockPos().getX() + offset.x();
                    double y = getBlockPos().getY() + offset.y();
                    double z = getBlockPos().getZ() + offset.z();
                    SpiritLightSpecs.spiritLightSpecs(level, new Vec3(x, y, z), spiritSplinterItem.type);
                }
            }
        }
        //spirit particles shot out from the twisted tablet
        if (repairRecipe != null) {
            TwistedTabletBlockEntity tabletBlockEntity = validTablet;

            List<Color> colors = new ArrayList<>();
            List<Color> endColors = new ArrayList<>();
            if (repairRecipe.repairMaterial.getItem() instanceof SpiritShardItem spiritItem) {
                colors.add(spiritItem.type.getPrimaryColor());
                endColors.add(spiritItem.type.getSecondaryColor());
            } else if (!spiritInventory.isEmpty()) {
                for (int i = 0; i < spiritInventory.slotCount; i++) {
                    ItemStack item = spiritInventory.getStackInSlot(i);
                    if (item.getItem() instanceof SpiritShardItem spiritItem) {
                        colors.add(spiritItem.type.getPrimaryColor());
                        endColors.add(spiritItem.type.getSecondaryColor());
                    }
                }
            }
            for (int i = 0; i < colors.size(); i++) {
                Color color = colors.get(i);
                Color endColor = endColors.get(i);
                Vec3 tabletItemPos = tabletBlockEntity.getItemPos();
                Vec3 velocity = tabletItemPos.subtract(itemPos).normalize().scale(-0.1f);

                starParticles(itemPos, color, endColor);

                WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.24f / colors.size(), 0f).build())
                        .setScaleData(GenericParticleData.create(0.45f + level.random.nextFloat() * 0.15f, 0).build())
                        .setSpinData(SpinParticleData.create(0).setSpinOffset((0.075f * level.getGameTime()) % 6.28f).build())
                        .setColorData(ColorParticleData.create(color, endColor).build())
                        .setLifetime(15)
                        .setRandomOffset(0.05)
                        .enableNoClip()
                        .repeat(level, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);

                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.4f / colors.size(), 0f).setCoefficient(0.5f).build())
                        .setScaleData(GenericParticleData.create(0.2f + level.random.nextFloat() * 0.15f, 0).build())
                        .setLifetime((int) (10 + level.random.nextInt(8) + Math.sin((0.5 * level.getGameTime()) % 6.28f)))
                        .setSpinData(SpinParticleData.create(0.1f + level.random.nextFloat() * 0.05f).setSpinOffset((0.075f * level.getGameTime() % 6.28f)).build())
                        .setColorData(ColorParticleData.create(color.brighter(), endColor).setCoefficient(0.75f).build())
                        .setRandomOffset(0.05)
                        .setMotion(velocity.x, velocity.y, velocity.z)
                        .enableNoClip()
                        .repeat(level, tabletItemPos.x, tabletItemPos.y, tabletItemPos.z, 1);
            }
        }
        if (focusingRecipe != null || repairRecipe != null && !(validTablet.inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem)) {
            focusingParticles(itemPos);
        }
    }

    public void focusingParticles(Vec3 itemPos) {
        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            for (ICrucibleAccelerator accelerator : accelerators) {
                if (accelerator != null) {
                    accelerator.addParticles(worldPosition, itemPos);
                }
            }
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = spiritOffset(spiritsRendered++, 0);
                Color color = spiritSplinterItem.type.getPrimaryColor();
                Color endColor = spiritSplinterItem.type.getSecondaryColor();
                double x = getBlockPos().getX() + offset.x();
                double y = getBlockPos().getY() + offset.y();
                double z = getBlockPos().getZ() + offset.z();
                Vec3 velocity = new Vec3(x, y, z).subtract(itemPos).normalize().scale(-0.03f);
                if (repairRecipe == null) {
                    for (ICrucibleAccelerator accelerator : accelerators) {
                        if (accelerator != null) {
                            accelerator.addParticles(color, endColor, 0.08f / spiritInventory.nonEmptyItemAmount, worldPosition, itemPos);
                        }
                    }
                }
                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.30f, 0f).build())
                        .setScaleData(GenericParticleData.create(0.2f, 0).build())
                        .setColorData(ColorParticleData.create(color, endColor).setCoefficient(0.75f).build())
                        .setLifetime(40)
                        .setRandomOffset(0.02f)
                        .setRandomMotion(0.0025f, 0.0025f)
                        .addMotion(velocity.x, velocity.y, velocity.z)
                        .enableNoClip()
                        .spawn(level, x, y, z);

                WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.12f / spiritInventory.nonEmptyItemAmount, 0f).build())
                        .setSpinData(SpinParticleData.create(0).setSpinOffset((0.075f * level.getGameTime() % 6.28f)).build())
                        .setScaleData(GenericParticleData.create(0.2f + level.random.nextFloat() * 0.1f, 0).build())
                        .setColorData(ColorParticleData.create(color, endColor).build())
                        .setLifetime(25)
                        .setRandomOffset(0.05)
                        .enableNoClip()
                        .spawn(level, itemPos.x, itemPos.y, itemPos.z);

                starParticles(itemPos, color, endColor);
            }
        }
    }

    public void starParticles(Vec3 itemPos, Color color, Color endColor) {
        WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.07f / spiritInventory.nonEmptyItemAmount, 0.16f / spiritInventory.nonEmptyItemAmount, 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                .setScaleData(GenericParticleData.create(0.2f, 0.45f + level.random.nextFloat() * 0.1f, 0).setEasing(Easing.QUINTIC_IN, Easing.CUBIC_IN_OUT).build())
                .setSpinData(SpinParticleData.create(0, 0.2f, 0).setEasing(Easing.CUBIC_IN, Easing.EXPO_IN).build())
                .setColorData(ColorParticleData.create(color, endColor).setEasing(Easing.BOUNCE_IN_OUT).setCoefficient(0.5f).build())
                .setLifetime(25)
                .setRandomOffset(0.1)
                .setRandomMotion(0.02f)
                .setRandomMotion(0.0025f, 0.0025f)
                .enableNoClip()
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .spawn(level, itemPos.x, itemPos.y, itemPos.z);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return combinedInventory.cast();
        }
        return super.getCapability(cap, side);
    }
}
