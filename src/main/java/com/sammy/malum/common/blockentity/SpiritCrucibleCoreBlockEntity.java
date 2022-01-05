package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.item.ImpetusItem;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.packets.particle.altar.SpiritAltarCraftParticlePacket;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.function.Supplier;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity {
    public static final Supplier<MultiBlockStructure> STRUCTURE = ()->(MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get().defaultBlockState())));

    public SimpleBlockEntityInventory inventory;
    public SimpleBlockEntityInventory spiritInventory;
    public SpiritFocusingRecipe recipe;
    public boolean updateRecipe;
    public float spiritAmount;
    public float spiritSpin;
    public int soundCooldown;
    public float progress;

    public SpiritCrucibleCoreBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_CRUCIBLE.get(), pos, state);
        inventory = new SimpleBlockEntityInventory(1, 1, t -> !(t.getItem() instanceof MalumSpiritItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                updateRecipe = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new SimpleBlockEntityInventory(3, 64, t -> t.getItem() instanceof MalumSpiritItem) {
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
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (progress != 0) {
            compound.putFloat("progress", progress);
        }
        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getFloat("spiritAmount");
        progress = compound.getFloat("progress");
        updateRecipe = true;
        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");
        super.load(compound);
    }

    @Override
    public MultiBlockStructure getStructure() {
        return STRUCTURE.get();
    }
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            if (!(heldStack.getItem() instanceof MalumSpiritItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty()) {
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
    public void onBreak() {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        super.onBreak();
    }

    @Override
    public void tick() {
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (updateRecipe)
        {
            recipe = SpiritFocusingRecipe.getRecipe(level, inventory.getStackInSlot(0), spiritInventory.getNonEmptyItemStacks());
            updateRecipe = false;
        }
        if (level.isClientSide) {
            spiritSpin += 1+Math.cos(Math.sin(level.getGameTime()*0.05f));
            passiveParticles();
        }
        else
        {
            if (recipe != null)
            {
                if (soundCooldown > 0) {
                    soundCooldown--;
                } else {
                    level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_LOOP, SoundSource.BLOCKS, 1, 1f);
                    soundCooldown = 220;
                }
                progress++;
                if (progress >= recipe.time)
                {
                    craft();
                }
            }
        }
    }
    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        Vec3 itemPos = itemPos(this);
        ItemStack outputStack = recipe.output.stack();
        if (recipe.durabilityCost != 0 && stack.isDamageableItem())
        {
            boolean success = stack.hurt(recipe.durabilityCost, level.random, null);
            if (success && stack.getItem() instanceof ImpetusItem impetusItem)
            {
                inventory.setStackInSlot(0, impetusItem.cracked.get().getDefaultInstance());
                updateRecipe = true;
            }
        }
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
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT, SoundSource.BLOCKS, 1, 1f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        progress = 0;
        recipe = SpiritFocusingRecipe.getRecipe(level, stack, spiritInventory.getNonEmptyItemStacks());
        inventory.updateData();
        spiritInventory.updateData();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }
    public static Vec3 itemPos(SpiritCrucibleCoreBlockEntity blockEntity) {
        return DataHelper.fromBlockPos(blockEntity.getBlockPos()).add(blockEntity.itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.5f, 0.5f);
    }

    public static Vec3 spiritOffset(SpiritCrucibleCoreBlockEntity blockEntity, int slot) {
        float distance = 0.75f + (float) Math.sin(blockEntity.spiritSpin / 20f) * 0.025f;
        float height = 1.75f;
        return DataHelper.rotatedCirclePosition(new Vec3(0.5f, height, 0.5f), distance, slot, blockEntity.spiritAmount, (long) blockEntity.spiritSpin, 360);
    }

    public void passiveParticles() {
        Vec3 itemPos = itemPos(this);
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof MalumSpiritItem spiritSplinterItem) {
                Vec3 offset = spiritOffset(this, i);
                Color color = spiritSplinterItem.type.color;
                Color endColor = spiritSplinterItem.type.endColor;
                double x = getBlockPos().getX() + offset.x();
                double y = getBlockPos().getY() + offset.y();
                double z = getBlockPos().getZ() + offset.z();
                SpiritHelper.spawnSpiritParticles(level, x, y, z, color, endColor);
                if (recipe != null) {
                    Vec3 velocity = new Vec3(x, y, z).subtract(itemPos).normalize().scale(-0.03f);
                    RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                            .setAlpha(0.15f, 0f)
                            .setLifetime(40)
                            .setScale(0.2f, 0)
                            .randomOffset(0.02f)
                            .randomVelocity(0.01f, 0.01f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(0.75f)
                            .randomVelocity(0.0025f, 0.0025f)
                            .addVelocity(velocity.x, velocity.y, velocity.z)
                            .enableNoClip()
                            .repeat(level, x, y, z, 2);

                    float alpha = 0.08f / spiritInventory.nonEmptyItemAmount;
                    RenderUtilities.create(ParticleRegistry.SPARKLE_PARTICLE)
                            .setAlpha(alpha, 0f)
                            .setLifetime(20)
                            .setScale(0.5f, 0)
                            .randomOffset(0.1, 0.1)
                            .randomVelocity(0.02f, 0.02f)
                            .setColor(color, endColor)
                            .setColorCurveMultiplier(0.75f)
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
