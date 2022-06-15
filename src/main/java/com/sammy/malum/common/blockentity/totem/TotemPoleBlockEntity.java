package com.sammy.malum.common.blockentity.totem;

import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.packets.particle.block.BlockParticlePacket;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.SoundRegistry;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.setup.OrtusParticleRegistry;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import com.sammy.ortus.systems.easing.Easing;
import com.sammy.ortus.systems.rendering.particle.ParticleBuilders;
import com.sammy.ortus.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.awt.*;

import static com.sammy.malum.core.setup.server.PacketRegistry.MALUM_CHANNEL;

public class TotemPoleBlockEntity extends OrtusBlockEntity {

    public MalumSpiritType type;
    public boolean haunted;
    public int desiredColor;
    public int currentColor;
    public int baseLevel;
    public boolean corrupted;
    public Block logBlock;
    public Direction direction;

    public TotemPoleBlockEntity(BlockEntityType<? extends TotemPoleBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.corrupted = ((TotemPoleBlock<?>) state.getBlock()).corrupted;
        this.logBlock = ((TotemPoleBlock<?>) state.getBlock()).logBlock.get();
        this.direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
    }

    public TotemPoleBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_POLE.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem().equals(ItemRegistry.HEX_ASH.get()) && !haunted) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            }
            if (!player.isCreative()) {
                held.shrink(1);
            }
            haunted = true;
            desiredColor = 20;
            MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.getColor(), worldPosition));
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
            level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
            if (corrupted) {
                level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            BlockHelper.updateState(level, worldPosition);
            return InteractionResult.SUCCESS;
        }
        if (held.canPerformAction(ToolActions.AXE_STRIP)) {
            if (haunted) {
                if (level.isClientSide) {
                    return InteractionResult.SUCCESS;
                }
                desiredColor = 0;
                haunted = false;
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.getColor(), worldPosition));
                level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
                if (corrupted) {
                    level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
                }
                BlockHelper.updateState(level, worldPosition);
                return InteractionResult.SUCCESS;
            }
            if (type != null) {
                if (level.isClientSide) {
                    return InteractionResult.SUCCESS;
                }
                level.setBlockAndUpdate(worldPosition, logBlock.defaultBlockState());
                MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.getColor(), worldPosition));
                level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
                if (corrupted) {
                    level.playSound(null, worldPosition, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("type", type.identifier);
        }
        if (desiredColor != 0) {
            compound.putInt("desiredColor", desiredColor);
        }
        if (currentColor != 0) {
            compound.putInt("currentColor", currentColor);
        }
        if (baseLevel != 0) {
            compound.putInt("baseLevel", baseLevel);
        }
        compound.putBoolean("corrupted", corrupted);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("type")) {
            type = SpiritHelper.getSpiritType(compound.getString("type"));
        }
        desiredColor = compound.getInt("desiredColor");
        currentColor = compound.getInt("currentColor");
        baseLevel = compound.getInt("baseLevel");
        corrupted = compound.getBoolean("corrupted");
        super.load(compound);
    }

    @Override
    public void tick() {
        if (currentColor > desiredColor) {
            currentColor--;
        }
        if (currentColor < desiredColor) {
            currentColor++;
        }
        if (level.isClientSide) {
            if (type != null && desiredColor != 0) {
                passiveParticles();
            }
        }
    }

    public void create(MalumSpiritType type) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.getColor(), worldPosition));
        this.type = type;
        this.currentColor = 10;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteStarting(int height) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE.get(), SoundSource.BLOCKS, 1, 0.9f + 0.2f * height);
        MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.getColor(), worldPosition));
        this.desiredColor = 10;
        this.baseLevel = worldPosition.getY() - height;
        this.haunted = false;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteComplete() {
        this.desiredColor = 20;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteEnding() {
        this.desiredColor = 0;
        this.haunted = false;
        BlockHelper.updateState(level, worldPosition);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (level.isClientSide) {
            return;
        }
        BlockPos basePos = new BlockPos(worldPosition.getX(), baseLevel, worldPosition.getZ());
        if (level.getBlockEntity(basePos) instanceof TotemBaseBlockEntity base) {
            if (base.active) {
                base.endRite();
            }
        }
    }

    public void passiveParticles() {
        if (level.getGameTime() % 6L == 0) {
            Color color = type.getColor();
            Color endColor = type.getEndColor();
            ParticleBuilders.create(OrtusParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0, 0.06f, 0.12f)
                    .setLifetime(35)
                    .setSpin(0.2f)
                    .setScale(0, 0.4f, 0)
                    .setScaleEasing(Easing.LINEAR, Easing.CIRC_IN_OUT)
                    .setColor(color, endColor)
                    .setColorCoefficient(0.5f)
                    .addMotion(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.2f)
                    .randomMotion(0.01f, 0.02f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

            ParticleBuilders.create(OrtusParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0, 0.06f, 0.03f)
                    .setLifetime(60)
                    .setSpin(0.1f)
                    .setScale(0f, 0.55f, 0.3f)
                    .setColor(color, endColor)
                    .setColorCoefficient(0.5f)
                    .addMotion(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                    .randomOffset(0.1f, 0.2f)
                    .enableNoClip()
                    .randomMotion(0.01f, 0.02f)
                    .overwriteRemovalProtocol(SimpleParticleOptions.SpecialRemovalProtocol.ENDING_CURVE_INVISIBLE)
                    .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
        }
    }
}