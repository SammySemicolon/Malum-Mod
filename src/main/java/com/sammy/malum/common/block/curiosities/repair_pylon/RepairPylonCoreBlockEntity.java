package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.visual_effects.RepairPylonParticleEffects;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.Collection;
import java.util.function.Supplier;

public class RepairPylonCoreBlockEntity extends MultiBlockCoreEntity {

    private static final Vec3 PYLON_ITEM_OFFSET = new Vec3(0.5f, 2.5f, 0.5f);
    private static final int HORIZONTAL_RANGE = 6;
    private static final int VERTICAL_RANGE = 4;

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(
            new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState()),
            new MultiBlockStructure.StructurePiece(0, 2, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState().setValue(RepairPylonComponentBlock.TOP, true))));

    public static final StringRepresentable.EnumCodec<RepairPylonState> CODEC = StringRepresentable.fromEnum(RepairPylonState::values);

    public enum RepairPylonState implements StringRepresentable {
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
    public float timer;

    public float spiritAmount;
    public float spiritSpin;

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
            public boolean isItemValid(int slot, ItemVariant resource, int count) {
                if (!(resource.getItem() instanceof SpiritShardItem spiritItem))
                    return false;

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
        compound.put("Inventory", inventory.serializeNBT());
        compound.put("spiritInventory", spiritInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag compound) {
        state = compound.contains("state") ? CODEC.byName(compound.getString("state")) : RepairPylonState.IDLE;
        spiritAmount = compound.getFloat("spiritAmount");
        repairablePosition = BlockHelper.loadBlockPos(compound.getCompound("targetedBlock"));
        timer = compound.getFloat("timer");
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        spiritInventory.deserializeNBT(compound.getCompound("spiritInventory"));

        super.load(compound);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            if (!spiritInventory.interact(this, level, player, hand, stack -> stack.getItem() instanceof SpiritShardItem || stack.isEmpty())) {
                inventory.interact(this, level, player, hand, stack -> true);
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

            RepairPylonParticleEffects.passiveRepairPylonParticles(this);
        }
        else {
            switch (state) {
                case IDLE -> {
                    if (recipe != null) {
                        setState(RepairPylonState.SEARCHING);
                    }
                }
                case SEARCHING -> {
                    timer++;
                    if (timer >= 100) {
                        if (recipe == null) {
                            timer = 0;
                            return;
                        }
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
                    if (recipe == null) {
                        setState(RepairPylonState.IDLE);
                        return;
                    }
                    if (timer >= 60) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        prepareRepair(provider);
                    }
                }
                case REPAIRING -> {
                    timer++;
                    if (recipe == null) {
                        setState(RepairPylonState.IDLE);
                        return;
                    }
                    if (timer >= 30) {
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
            SpiritRepairRecipe newRecipe = SpiritRepairRecipe.getRecipe(level, inventoryForAltar.getStackInSlot(0), inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks);
            if (newRecipe != null) {
                repairablePosition = provider.getAccessPointBlockPos();
                return true;
            }
        }
        return false;
    }

    public void prepareRepair(IMalumSpecialItemAccessPoint provider) {
        ParticleEffectTypeRegistry.REPAIR_PYLON_PREPARES.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
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
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return PYLON_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 2.75f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }
}