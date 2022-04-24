package com.sammy.malum.common.blockentity.crucible;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.setup.OrtusParticles;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import com.sammy.ortus.systems.multiblock.HorizontalDirectionStructure;
import com.sammy.ortus.systems.multiblock.MultiBlockCoreEntity;

import com.sammy.ortus.systems.multiblock.MultiBlockStructure;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.function.Supplier;

public class SpiritCatalyzerCoreBlockEntity extends MultiBlockCoreEntity implements ICrucibleAccelerator {
    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get().defaultBlockState())));
    public static final ICrucibleAccelerator.CrucibleAcceleratorType CATALYZER = new ICrucibleAccelerator.ArrayCrucibleAcceleratorType("catalyzer",
            new float[]{0.2f, 0.25f, 0.3f, 0.4f, 0.45f, 0.5f, 0.6f, 0.8f},
            new int[]{1, 1, 1, 2, 2, 3, 3, 5},
            new float[]{0.25f, 0.5f, 0.75f, 1f, 1.5f, 2f, 3f, 8f});

    public OrtusBlockEntityInventory inventory;
    public int burnTicks;
    IAccelerationTarget target;

    public SpiritCatalyzerCoreBlockEntity(BlockEntityType<? extends SpiritCatalyzerCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new OrtusBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof MalumSpiritItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public SpiritCatalyzerCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CATALYZER.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
        if (burnTicks != 0) {
            compound.putInt("burnTicks", burnTicks);
        }
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        burnTicks = compound.getInt("burnTicks");
        super.load(compound);
    }

    @Override
    public CrucibleAcceleratorType getAcceleratorType() {
        return CATALYZER;
    }

    @Override
    public IAccelerationTarget getTarget() {
        return target;
    }

    @Override
    public void setTarget(IAccelerationTarget target) {
        this.target = target;
    }

    @Override
    public boolean canStartAccelerating() {
        boolean ticks = burnTicks > 0;
        boolean stack = ForgeHooks.getBurnTime(inventory.getStackInSlot(0), RecipeType.SMELTING) > 0;
        return ticks || stack;
    }

    @Override
    public boolean canAccelerate() {
        updateBurnTicks();
        return burnTicks > 0;
    }

    @Override
    public void tick() {
        if (target != null && !target.canBeAccelerated()) {
            setTarget(null);
        } else if (target != null) {
            if (burnTicks > 0) {
                burnTicks--;
            }
        }
    }

    public void updateBurnTicks() {
        if (burnTicks == 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                burnTicks = ForgeHooks.getBurnTime(inventory.getStackInSlot(0), RecipeType.SMELTING) / 2;
                stack.shrink(1);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }
    }

    @Override
    public void addParticles(Color color, Color endColor, float alpha, BlockPos targetPos, Vec3 targetItemPos) {
        if (burnTicks > 0) {
            Vec3 startPos = getItemPos(this);
            float random = level.random.nextFloat() * 0.04f;
            Vec3 velocity = startPos.subtract(targetItemPos.add(random, random, random)).normalize().scale(-0.08f);

            ParticleBuilders.create(OrtusParticles.WISP_PARTICLE)
                    .setAlpha(alpha * 5f, 0f)
                    .setLifetime((int) (10 + level.random.nextInt(8) + Math.sin((0.2 * level.getGameTime()) % 6.28f)))
                    .setScale(0.15f + level.random.nextFloat() * 0.15f, 0)
                    .randomOffset(0.05)
                    .setSpinOffset((0.075f * level.getGameTime() % 6.28f))
                    .setSpin(0.1f + level.random.nextFloat() * 0.05f)
                    .setColor(color.brighter(), endColor)
                    .setAlphaCurveMultiplier(0.5f)
                    .setColorCurveMultiplier(0.75f)
                    .setMotion(velocity.x, velocity.y, velocity.z)
                    .enableNoClip()
                    .repeat(level, startPos.x, startPos.y, startPos.z, 1);

            ParticleBuilders.create(OrtusParticles.WISP_PARTICLE)
                    .setAlpha(alpha * 3, 0f)
                    .setLifetime(15)
                    .setScale(0.2f + level.random.nextFloat() * 0.15f, 0)
                    .randomOffset(0.05)
                    .setSpinOffset((0.15f * level.getGameTime()) % 6.28f)
                    .setColor(color, endColor)
                    .enableNoClip()
                    .repeat(level, startPos.x, startPos.y, startPos.z, 1);

            ParticleBuilders.create(OrtusParticles.STAR_PARTICLE)
                    .setAlpha(alpha * 3, 0f)
                    .setLifetime(15)
                    .setScale(0.45f + level.random.nextFloat() * 0.15f, 0)
                    .randomOffset(0.05)
                    .setSpinOffset((0.075f * level.getGameTime()) % 6.28f)
                    .setColor(color, endColor)
                    .enableNoClip()
                    .repeat(level, startPos.x, startPos.y, startPos.z, 1);
        }
    }


    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        inventory.interact(player.level, player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBreak() {
        inventory.dumpItems(level, DataHelper.fromBlockPos(worldPosition).add(0.5f, 0.5f, 0.5f));
        super.onBreak();
    }

    public static Vec3 getItemPos(SpiritCatalyzerCoreBlockEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.95f, 0.5f);
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