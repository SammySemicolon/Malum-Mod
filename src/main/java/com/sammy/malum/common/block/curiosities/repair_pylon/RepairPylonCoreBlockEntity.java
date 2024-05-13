package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
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
        ACTIVE("active"),
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
    public float timer;

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
            compound.putFloat("timer", timer);
        }
        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        state = compound.contains("state") ? CODEC.byName(compound.getString("state")) : RepairPylonState.IDLE;
        spiritAmount = compound.getFloat("spiritAmount");
        repairablePosition = BlockHelper.loadBlockPos(compound.getCompound("targetedBlock"));
        timer = compound.getFloat("timer");
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
        boolean wasNull = recipe == null;
        recipe = SpiritRepairRecipe.getRecipe(level, c -> c.doesRepairMatch(inventory.getStackInSlot(0)) && c.doSpiritsMatch(spiritInventory.nonEmptyItemStacks));
        if (wasNull) {
            setState(RepairPylonState.SEARCHING);
        }
    }

    @Override
    public void tick() {
        super.tick();

        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (level.isClientSide) {
            spiritSpin++;
        }
        else {
            switch (state) {
                case SEARCHING -> {
                    timer++;
                    if (timer >= 100) {
                        if (recipe == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        boolean success = tryRepair();
                        if (success) {
                            setState(RepairPylonState.ACTIVE);
                        }
                        else {
                            timer = 0;
                        }
                    }
                }
                case ACTIVE -> {
                    timer++;
                    if (timer >= 60) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        repairItem(provider);
                    }
                }
                case COOLDOWN -> {
                    timer++;
                    if (timer >= 600) {
                        setState(RepairPylonState.IDLE);
                    }
                }
            }
        }
    }

    public boolean tryRepair() {
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        for (IMalumSpecialItemAccessPoint provider : altarProviders) {
            LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
            ItemStack providedStack = inventoryForAltar.getStackInSlot(0);
            if (recipe.doesInputMatch(providedStack) && recipe.doesRepairMatch(inventory.getStackInSlot(0)) && recipe.doSpiritsMatch(spiritInventory.nonEmptyItemStacks)) {
                repairablePosition = provider.getAccessPointBlockPos();
                return true;
            }
        }
        return false;
    }

    public void repairItem(IMalumSpecialItemAccessPoint provider) {
        final LodestoneBlockEntityInventory suppliedInventory = provider.getSuppliedInventory();
        ItemStack damagedItem = suppliedInventory.getStackInSlot(0);
        ItemStack repairMaterial = inventory.getStackInSlot(0);
        ItemStack result = SpiritRepairRecipe.getRepairRecipeOutput(damagedItem);
        result.setDamageValue(Math.max(0, result.getDamageValue() - (int) (result.getMaxDamage() * recipe.durabilityPercentage)));
        suppliedInventory.setStackInSlot(0, result);
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
        setState(RepairPylonState.IDLE);
    }

    public void setState(RepairPylonState state) {
        this.state = state;
        this.timer = 0;
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
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
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