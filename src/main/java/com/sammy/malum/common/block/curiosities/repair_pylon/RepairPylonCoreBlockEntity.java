package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.pylon.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class RepairPylonCoreBlockEntity extends MultiBlockCoreEntity {

    private static final Vec3 PYLON_ITEM_OFFSET = new Vec3(0.5f, 2.5f, 0.5f);
    private static final int HORIZONTAL_RANGE = 6;
    private static final int VERTICAL_RANGE = 4;

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(
            new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState()),
            new MultiBlockStructure.StructurePiece(0, 2, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState().setValue(RepairPylonComponentBlock.TOP, true))));

    public static final StringRepresentable.EnumCodec<RepairPylonState> CODEC = StringRepresentable.fromEnum(RepairPylonState::values);
    public enum RepairPylonState implements StringRepresentable{
        IDLE("idle"),
        SEARCHING("searching"),
        CHARGING("active"),
        REPAIRING("repairing"),
        COOLDOWN("cooldown");
        final String name;

        RepairPylonState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public SpiritRepairRecipe recipe;

    public RepairPylonState state = RepairPylonState.IDLE;
    public BlockPos repairablePosition;
    public int timer;

    public float spiritAmount;
    public float spiritSpin;

    private final LazyOptional<IItemHandler> combinedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, spiritInventory));

    public RepairPylonCoreBlockEntity(BlockEntityType<? extends RepairPylonCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new MalumBlockEntityInventory(4, 64) {
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

    public RepairPylonCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.REPAIR_PYLON.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        compound.putString("state", state.name);
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (repairablePosition != null) {
            compound.put("targetedBlock", BlockHelper.saveBlockPos(new CompoundTag(), repairablePosition));
        }
        if (timer != 0) {
            compound.putInt("timer", timer);
        }
        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        state = compound.contains("state") ? CODEC.byName(compound.getString("state")) : RepairPylonState.IDLE;
        spiritAmount = compound.getFloat("spiritAmount");
        repairablePosition = BlockHelper.loadBlockPos(compound.getCompound("targetedBlock"));
        timer = compound.getInt("timer");
        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");

        super.load(compound);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            final boolean isEmpty = heldStack.isEmpty();
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
            if (isEmpty) {
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
    public void init() {
        if (state.equals(RepairPylonState.COOLDOWN)) {
            return;
        }
        recipe = SpiritRepairRecipe.getRecipe(level, c -> c.doesRepairMatch(inventory.getStackInSlot(0)) && c.doSpiritsMatch(spiritInventory.nonEmptyItemStacks));
        if (recipe != null) {
            if (state.equals(RepairPylonState.IDLE)) {
                setState(RepairPylonState.SEARCHING);
            }
            if (level.isClientSide) {
                RepairPylonSoundInstance.playSound(this);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (level.isClientSide) {
            spiritSpin++;
            if (state.equals(RepairPylonState.COOLDOWN) && timer < 1200) {
                timer++;
            }
            RepairPylonParticleEffects.passiveRepairPylonParticles(this);
        }
        else {
            if (!state.equals(RepairPylonState.IDLE) && !state.equals(RepairPylonState.COOLDOWN)) {
                if (recipe == null) {
                    setState(RepairPylonState.IDLE);
                    return;
                }
            }
            switch (state) {
                case IDLE -> {
                    if (recipe != null) {
                        setState(RepairPylonState.SEARCHING);
                    }
                }
                case SEARCHING -> {
                    timer++;
                    if (timer >= 40) {
                        boolean success = tryRepair();
                        if (success) {
                            setState(RepairPylonState.CHARGING);
                        }
                        else {
                            timer = 0;
                        }
                    }
                }
                case CHARGING -> {
                    timer++;
                    if (timer >= 600) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider) || !tryRepair(provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        prepareRepair(provider);
                    }
                }
                case REPAIRING -> {
                    timer++;
                    if (timer >= 40) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider) || !tryRepair(provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        repairItem(provider);
                    }
                }
                case COOLDOWN -> {
                    timer++;
                    if (timer >= 1200) {
                        setState(RepairPylonState.IDLE);
                    }
                }
            }
        }
    }

    public boolean tryRepair() {
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        for (IMalumSpecialItemAccessPoint provider : altarProviders) {
            boolean success = tryRepair(provider);
            if (success) {
                repairablePosition = provider.getAccessPointBlockPos();
                return true;
            }
        }
        return false;
    }

    public boolean tryRepair(IMalumSpecialItemAccessPoint provider) {
        LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
        SpiritRepairRecipe newRecipe = SpiritRepairRecipe.getRecipe(level, inventoryForAltar.getStackInSlot(0), inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks);
        return newRecipe != null;
    }

    public void prepareRepair(IMalumSpecialItemAccessPoint provider) {
        ParticleEffectTypeRegistry.REPAIR_PYLON_PREPARES.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        level.playSound(null, worldPosition, SoundRegistry.REPAIR_PYLON_REPAIR_START.get(), SoundSource.BLOCKS, 1.0f, 0.8f);
        setState(RepairPylonState.REPAIRING);
    }

    public void repairItem(IMalumSpecialItemAccessPoint provider) {
        final LodestoneBlockEntityInventory suppliedInventory = provider.getSuppliedInventory();
        ItemStack damagedItem = suppliedInventory.getStackInSlot(0);
        ItemStack repairMaterial = inventory.getStackInSlot(0);
        repairMaterial.shrink(recipe.repairMaterial.getCount());
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
        ItemStack result = SpiritRepairRecipe.getRepairRecipeOutput(damagedItem);
        result.setDamageValue(Math.max(0, result.getDamageValue() - (int) (result.getMaxDamage() * recipe.durabilityPercentage)));
        suppliedInventory.setStackInSlot(0, result);
        ParticleEffectTypeRegistry.REPAIR_PYLON_REPAIRS.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        level.playSound(null, worldPosition, SoundRegistry.REPAIR_PYLON_REPAIR_FINISH.get(), SoundSource.BLOCKS, 1.0f, 0.8f);
        setState(RepairPylonState.COOLDOWN);
    }

    public void setState(RepairPylonState state) {
        this.state = state;
        this.timer = state.equals(RepairPylonState.SEARCHING) ? 100 : 0;
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return PYLON_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 2.75f;
        if (state.equals(RepairPylonState.COOLDOWN)) {
            int relativeCooldown = timer < 1110 ? Math.min(timer, 90) : 1200-timer;
            distance += getCooldownOffset(relativeCooldown, Easing.SINE_OUT) * 0.25f;
            height -= getCooldownOffset(relativeCooldown, Easing.QUARTIC_OUT) * getCooldownOffset(relativeCooldown, Easing.BACK_OUT) * 0.5f;
        }
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public float getCooldownOffset(int relativeCooldown, Easing easing) {
        return easing.ease(relativeCooldown / 90f, 0, 1, 1);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return combinedInventory.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
    }
}