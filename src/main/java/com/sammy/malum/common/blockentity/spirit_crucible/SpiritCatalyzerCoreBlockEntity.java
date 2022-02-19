package com.sammy.malum.common.blockentity.spirit_crucible;

import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.ParticleRegistry;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.block.BlockRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.multiblock.HorizontalDirectionStructure;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
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
            new float[]{0.25f, 0.5f, 0.75f, 1f, 1.5f, 2f, 3f, 7.5f});

    public SimpleBlockEntityInventory inventory;
    public SpiritCrucibleCoreBlockEntity crucible;
    public int burnTicks;

    public SpiritCatalyzerCoreBlockEntity(BlockEntityType<? extends SpiritCatalyzerCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new SimpleBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof MalumSpiritItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                updateBurnTicks();
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
    public boolean canAccelerate() {
        updateBurnTicks();
        return burnTicks > 0;
    }

    @Override
    public void tick() {
        if (crucible != null && crucible.recipe != null) {
            if (burnTicks > 0) {
                burnTicks--;
            }
        }
    }

    @Override
    public void setCrucible(SpiritCrucibleCoreBlockEntity crucible) {
        this.crucible = crucible;
    }

    public void updateBurnTicks()
    {
        if (burnTicks == 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                burnTicks = stack.getBurnTime(RecipeType.SMELTING);
                stack.shrink(1);
            }
        }
    }

    @Override
    public void addParticles(Color color, Color endColor, float alpha, BlockPos altarPos, Vec3 crucibleItemPos) {
        if (burnTicks > 0) {
            Vec3 startPos = itemPos(this);

            RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(alpha*1.5f, 0f)
                    .setLifetime(25)
                    .setScale(0.05f + level.random.nextFloat() * 0.1f, 0)
                    .randomOffset(0.1)
                    .setStartingSpin((0.2f * level.getGameTime() % 6.28f))
                    .setColor(color, endColor)
                    .randomVelocity(0.005f, 0.005f)
                    .enableNoClip()
                    .repeat(level, startPos.x, startPos.y, startPos.z, 1);

            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(alpha*1.5f, 0f)
                    .setLifetime(25)
                    .setScale(0.2f + level.random.nextFloat() * 0.1f, 0)
                    .randomOffset(0.05)
                    .setStartingSpin((0.075f * level.getGameTime() % 6.28f))
                    .setColor(color, endColor)
                    .enableNoClip()
                    .repeat(level, startPos.x, startPos.y, startPos.z, 1);

            RenderUtilities.create(ParticleRegistry.STAR_PARTICLE)
                    .setAlpha(alpha*2, 0f)
                    .setLifetime(25)
                    .setScale(0.45f + level.random.nextFloat() * 0.1f, 0)
                    .randomOffset(0.05)
                    .setStartingSpin((0.075f * level.getGameTime() % 6.28f))
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

    public static Vec3 itemPos(SpiritCatalyzerCoreBlockEntity blockEntity) {
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