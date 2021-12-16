package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.awt.*;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class TotemPoleTileEntity extends SimpleBlockEntity
{

    public MalumSpiritType type;
    public int desiredColor;
    public int currentColor;
    public int baseLevel;
    public boolean corrupted;
    public TotemPoleTileEntity(BlockPos pos, BlockState state)
    {
        super(TileEntityRegistry.TOTEM_POLE_TILE_ENTITY.get(), pos, state);
        this.corrupted = ((TotemPoleBlock)state.getBlock()).corrupted;
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        if (type != null)
        {
            compound.putString("type", type.identifier);
        }
        compound.putInt("desiredColor", desiredColor);
        compound.putInt("currentColor", currentColor);
        compound.putInt("baseLevel", baseLevel);
        compound.putBoolean("corrupted", corrupted);
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound)
    {
        if (compound.contains("type"))
        {
            type = SpiritHelper.getSpiritType(compound.getString("type"));
        }
        desiredColor = compound.getInt("desiredColor");
        currentColor = compound.getInt("currentColor");
        baseLevel = compound.getInt("baseLevel");
        corrupted = compound.getBoolean("corrupted");
        super.load(compound);
    }

    public void tick()
    {
        if (currentColor > desiredColor)
        {
            currentColor--;
        }
        if (currentColor < desiredColor)
        {
            currentColor++;
        }
        if (level.isClientSide)
        {
            if (type != null && desiredColor != 0)
            {
                passiveParticles();
            }
        }
    }
    public void create(MalumSpiritType type)
    {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE, SoundSource.BLOCKS,1,1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->level.getChunkAt(worldPosition)), new BlockParticlePacket(type.color, worldPosition.getX(),worldPosition.getY(),worldPosition.getZ()));
        this.type = type;
        this.currentColor = 10;
        MalumHelper.updateAndNotifyState(level,worldPosition);
    }
    public void riteStarting(int height)
    {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_CHARGE, SoundSource.BLOCKS,1,1 + 0.2f * height);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->level.getChunkAt(worldPosition)), new BlockParticlePacket(type.color, worldPosition.getX(),worldPosition.getY(),worldPosition.getZ()));
        this.desiredColor = 10;
        this.baseLevel = worldPosition.getY() - height;
        MalumHelper.updateAndNotifyState(level,worldPosition);
    }
    public void riteComplete(int height)
    {
        this.desiredColor = 20;
        MalumHelper.updateAndNotifyState(level,worldPosition);
    }
    public void riteEnding()
    {
        this.desiredColor = 0;
        MalumHelper.updateAndNotifyState(level,worldPosition);
    }

    @Override
    public void setRemoved()
    {
        if (level.isClientSide)
        {
            return;
        }
        BlockPos basePos = new BlockPos(worldPosition.getX(), baseLevel, worldPosition.getZ());
        if (level.getBlockEntity(basePos) instanceof TotemBaseTileEntity)
        {
            TotemBaseTileEntity totemBaseTileEntity = (TotemBaseTileEntity) level.getBlockEntity(basePos);
            if (totemBaseTileEntity.active)
            {
                totemBaseTileEntity.endRite();
            }
        }
        super.setRemoved();
    }

    public void passiveParticles()
    {
        Color color = type.color;
        for (int i = -1; i <= 1; i++)
        {
            float extraVelocity = 0.03f * i;
            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(20)
                    .setSpin(0.2f)
                    .setScale(0.25f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

            RenderUtilities.create(ParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(40)
                    .setSpin(0.1f)
                    .setScale(0.35f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, worldPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
        }
    }
}
