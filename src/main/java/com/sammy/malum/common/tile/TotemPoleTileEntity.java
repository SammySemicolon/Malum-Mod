package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.packets.particle.BlockParticlePacket;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;

import static com.sammy.malum.core.registry.misc.PacketRegistry.INSTANCE;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(TileEntityRegistry.TOTEM_POLE_TILE_ENTITY.get());
    }

    public MalumSpiritType type;
    public int desiredColor;
    public int currentColor;
    public int baseLevel;
    @Override
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
        if (MalumHelper.areWeOnClient(level))
        {
            if (type != null && desiredColor != 0)
            {
                passiveParticles();
            }
        }
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (type != null)
        {
            compound.putString("type", type.identifier);
        }
        compound.putInt("desiredColor", desiredColor);
        compound.putInt("currentColor", currentColor);
        compound.putInt("baseLevel", baseLevel);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("type"))
        {
            type = SpiritHelper.getSpiritType(compound.getString("type"));
        }
        desiredColor = compound.getInt("desiredColor");
        currentColor = compound.getInt("currentColor");
        baseLevel = compound.getInt("baseLevel");
        super.readData(compound);
    }
    public void create(MalumSpiritType type)
    {
        level.playSound(null, LevelPosition, SoundRegistry.TOTEM_ENGRAVE, SoundCategory.BLOCKS,1,1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->level.getChunkAt(LevelPosition)), new BlockParticlePacket(type.color, LevelPosition.getX(),LevelPosition.getY(),LevelPosition.getZ()));
        this.type = type;
        this.currentColor = 10;
        MalumHelper.updateAndNotifyState(level,LevelPosition);
    }
    public void riteStarting(int height)
    {
        level.playSound(null, LevelPosition, SoundRegistry.TOTEM_CHARGE, SoundCategory.BLOCKS,1,1 + 0.2f * height);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->level.getChunkAt(LevelPosition)), new BlockParticlePacket(type.color, LevelPosition.getX(),LevelPosition.getY(),LevelPosition.getZ()));
        this.desiredColor = 10;
        this.baseLevel = LevelPosition.getY() - height;
        MalumHelper.updateAndNotifyState(level,LevelPosition);
    }
    public void riteComplete(int height)
    {
        this.desiredColor = 20;
        MalumHelper.updateAndNotifyState(level,LevelPosition);
    }
    public void riteEnding()
    {
        this.desiredColor = 0;
        MalumHelper.updateAndNotifyState(level,LevelPosition);
    }

    @Override
    public void setRemoved()
    {
        if (MalumHelper.areWeOnClient(level))
        {
            return;
        }
        BlockPos basePos = new BlockPos(LevelPosition.getX(), baseLevel, LevelPosition.getZ());
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
            ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(20)
                    .setSpin(0.2f)
                    .setScale(0.25f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, LevelPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

            ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(40)
                    .setSpin(0.1f)
                    .setScale(0.35f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(level, LevelPosition, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
        }
    }
}
