package com.sammy.malum.common.blockentity.totem;

import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.registry.ParticleRegistry;
import com.sammy.malum.core.registry.SoundRegistry;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.helper.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.network.PacketDistributor;

import java.awt.*;

import static com.sammy.malum.core.registry.PacketRegistry.INSTANCE;

public class TotemPoleTileEntity extends SimpleBlockEntity {

    public MalumSpiritType type;
    public int desiredColor;
    public int currentColor;
    public int baseLevel;
    public boolean corrupted;
    public Block logBlock;
    public Direction direction;

    public TotemPoleTileEntity(BlockEntityType<TotemPoleTileEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.corrupted = ((TotemPoleBlock) state.getBlock()).corrupted;
        this.logBlock = ((TotemPoleBlock) state.getBlock()).logBlock.get();
        this.direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
    }
    public TotemPoleTileEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_POLE.get(), pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (!player.level.isClientSide) {
            if (player.getItemInHand(hand).getItem() instanceof AxeItem) {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) level.getBlockEntity(worldPosition);
                if (totemPoleTileEntity.type != null) {
                    level.setBlockAndUpdate(worldPosition, logBlock.defaultBlockState());
                    INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(totemPoleTileEntity.type.color, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
                    level.playSound(null, worldPosition, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1, 1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("type", type.identifier);
        }
        compound.putInt("desiredColor", desiredColor);
        compound.putInt("currentColor", currentColor);
        compound.putInt("baseLevel", baseLevel);
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
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE, SoundSource.BLOCKS, 1, 1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.color, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
        this.type = type;
        this.currentColor = 10;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteStarting(int height) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE, SoundSource.BLOCKS, 1, 1 + 0.2f * height);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new BlockParticlePacket(type.color, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()));
        this.desiredColor = 10;
        this.baseLevel = worldPosition.getY() - height;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteComplete() {
        this.desiredColor = 20;
        BlockHelper.updateState(level, worldPosition);
    }

    public void riteEnding() {
        this.desiredColor = 0;
        BlockHelper.updateState(level, worldPosition);
    }

    @Override
    public void onBreak() {
        if (level.isClientSide) {
            return;
        }
        BlockPos basePos = new BlockPos(worldPosition.getX(), baseLevel, worldPosition.getZ());
        if (level.getBlockEntity(basePos) instanceof TotemBaseTileEntity base) {
            if (base.active) {
                base.endRite();
            }
        }
    }

    public void passiveParticles() {
        Color color = type.color;
        Color endColor = type.endColor;
        RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(5)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, endColor)
                .setColorCurveMultiplier(0.5f)
                .addVelocity(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.01f, 0.01f)
                .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

        RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.04f, 0f)
                .setLifetime(10)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, endColor)
                .setColorCurveMultiplier(0.5f)
                .addVelocity(0, Mth.nextFloat(level.random, -0.03f, 0.03f), 0)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.01f, 0.01f)
                .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
    }
}