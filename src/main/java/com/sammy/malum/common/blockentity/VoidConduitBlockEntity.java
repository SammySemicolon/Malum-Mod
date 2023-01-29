package com.sammy.malum.common.blockentity;

import com.sammy.malum.common.packets.particle.block.VoidConduitParticlePacket;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.FavorOfTheVoidRecipe;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleOptions;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class VoidConduitBlockEntity extends LodestoneBlockEntity {

    public final List<ItemStack> eatenItems = new ArrayList<>();
    public int progress;
    public int streak;

    protected static final VoxelShape WELL_SHAPE = Block.box(-16.0D, 11.0D, -16.0D, 32.0D, 13.0D, 32.0D);
    public VoidConduitBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.VOID_CONDUIT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (!eatenItems.isEmpty()) {
            compound.putInt("itemCount", eatenItems.size());
            for (int i = 0; i < eatenItems.size(); i++) {
                CompoundTag itemTag = new CompoundTag();
                ItemStack stack = eatenItems.get(i);
                stack.save(itemTag);
                compound.put("item_"+i, itemTag);
            }
        }
        compound.putInt("progress", progress);
        compound.putInt("streak", streak);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        eatenItems.clear();
        for (int i = 0; i < compound.getInt("itemCount"); i++) {
            CompoundTag itemTag = compound.getCompound("item_"+i);
            eatenItems.add(ItemStack.of(itemTag));
        }
        progress = compound.getInt("progress");
        streak = compound.getInt("streak");
        super.load(compound);
    }

    @Override
    public void tick() {
        super.tick();
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameTime() % 100L == 0) {
                level.playSound(null, worldPosition, SoundRegistry.UNCANNY_VALLEY.get(), SoundSource.HOSTILE, 1f, Mth.nextFloat(level.getRandom(), 0.55f, 1.75f));
            }
            if (serverLevel.getGameTime() % 20L == 0) {
                level.playSound(null, worldPosition, SoundRegistry.VOID_HEARTBEAT.get(), SoundSource.HOSTILE, 1.5f, Mth.nextFloat(level.getRandom(), 0.95f, 1.15f));
            }
            if (serverLevel.getGameTime() % 40L == 0) {
                List<ItemEntity> items = serverLevel.getEntitiesOfClass(
                        ItemEntity.class,
                        new AABB(worldPosition.offset(1, -3, 1), worldPosition.offset(-1, -1, -1)).inflate(1))
                        .stream().sorted(Comparator.comparingInt(itemEntity -> itemEntity.age)).collect(Collectors.toList());

                for (ItemEntity entity : items) {
                    ItemStack item = entity.getItem();
                    if (item.getItem().equals(ItemRegistry.BLIGHTED_GUNK.get())) {
                        progress+=20;
                    }
                    eatenItems.add(item);
                    entity.discard();
                }
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
            if (!eatenItems.isEmpty()) {
                progress++;
                if (progress >= 80) {
                    int resultingProgress = 65;
                    ItemStack stack = eatenItems.get(eatenItems.size()-1);
                    if (stack.getItem().equals(ItemRegistry.BLIGHTED_GUNK.get())) {
                        resultingProgress = 72+streak/4;
                        streak++;
                        level.playSound(null, worldPosition, SoundRegistry.HUNGRY_BELT_FEEDS.get(), SoundSource.PLAYERS, 0.7f, 0.6f + level.random.nextFloat() * 0.3f+streak*0.05f);
                        level.playSound(null, worldPosition, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.7f, 0.6f + level.random.nextFloat() * 0.2f+streak*0.05f);
                    }
                    else {
                        FavorOfTheVoidRecipe recipe = FavorOfTheVoidRecipe.getRecipe(level, stack);
                        float pitch = Mth.nextFloat(level.getRandom(), 0.85f, 1.35f) + streak * 0.1f;
                        if (recipe != null) {
                            streak++;
                            int amount = recipe.output.getCount() * stack.getCount();
                            while (amount > 0) {
                                int count = Math.min(64, amount);
                                ItemStack outputStack = new ItemStack(recipe.output.getItem(), count);
                                outputStack.setTag(recipe.output.getTag());
                                ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f, outputStack);
                                entity.setDeltaMovement(0, 0.65f, 0.15f);
                                level.addFreshEntity(entity);
                                amount -= count;
                            }
                            level.playSound(null, worldPosition, SoundRegistry.VOID_TRANSMUTATION.get(), SoundSource.HOSTILE, 2f, pitch);
                        } else {
                            ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f, stack);
                            entity.setDeltaMovement(0, 0.65f, 0.15f);
                            level.addFreshEntity(entity);
                            level.playSound(null, worldPosition, SoundRegistry.VOID_REJECTION.get(), SoundSource.HOSTILE, 2f, pitch);
                        }
                    }
                    progress = resultingProgress;
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new VoidConduitParticlePacket(worldPosition.getX()+0.5f, worldPosition.getY()+0.75f, worldPosition.getZ()+0.5f));
                    eatenItems.remove(eatenItems.size()-1);
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                if (eatenItems.isEmpty()) {
                    progress = 0;
                }
            }
            else if (streak != 0) {
                streak = 0;
            }
        }
        else {
            if (level.getGameTime() % 6L == 0) {
                ClientOnly.spawnParticles(level, worldPosition);
            }
        }
    }
    public static class ClientOnly {
        public static void spawnParticles(Level level, BlockPos blockPos) {
            float multiplier = Mth.nextFloat(level.random, 0.4f, 1f);
            Color color = new Color((int) (12 * multiplier), (int) (3 * multiplier), (int) (12 * multiplier));
            Color endColor = color.darker();
            ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0, 0.2f, 0f)
                    .setAlphaEasing(Easing.SINE_IN, Easing.SINE_OUT)
                    .setLifetime(60)
                    .setSpin(0.1f, 0.4f, 0)
                    .setSpinEasing(Easing.SINE_IN, Easing.SINE_OUT)
                    .setScale(0f, 0.9f, 0.5f)
                    .setScaleEasing(Easing.SINE_IN, Easing.SINE_OUT)
                    .setColor(color, endColor)
                    .setColorCoefficient(0.5f)
                    .addMotion(0, level.random.nextFloat() * 0.01f, 0)
                    .randomOffset(3f, 0.02f)
                    .enableNoClip()
                    .setRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .surroundVoxelShape(level, blockPos, WELL_SHAPE, 12);
        }
    }
}