package com.sammy.malum.common.blocks.totem.pole;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totem.TotemBaseTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.network.packets.particle.totem.SpiritEngraveParticlePacket;
import com.sammy.malum.network.packets.particle.totem.TotemPoleParticlePacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get());
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
        if (MalumHelper.areWeOnClient(world))
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
            type = SpiritHelper.figureOutType(compound.getString("type"));
        }
        desiredColor = compound.getInt("desiredColor");
        currentColor = compound.getInt("currentColor");
        baseLevel = compound.getInt("baseLevel");
        super.readData(compound);
    }
    public void create(MalumSpiritType type)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_ENGRAVE, SoundCategory.BLOCKS,1,1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), new SpiritEngraveParticlePacket(type.identifier, pos.getX(),pos.getY(),pos.getZ()));
        this.type = type;
        this.currentColor = 10;
    }
    public void riteStarting(int height)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS,1,1 + 0.2f * height);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), new TotemPoleParticlePacket(type.identifier, pos.getX(),pos.getY(),pos.getZ(), false));
        this.desiredColor = 10;
        this.baseLevel = pos.getY() - height;
        MalumHelper.updateAndNotifyState(world,pos);
    }
    public void riteComplete(int height)
    {
        this.desiredColor = 20;
        MalumHelper.updateAndNotifyState(world,pos);
    }
    public void riteEnding()
    {
        this.desiredColor = 0;
        MalumHelper.updateAndNotifyState(world,pos);
    }

    @Override
    public void remove()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            return;
        }
        BlockPos basePos = new BlockPos(pos.getX(), baseLevel, pos.getZ());
        if (world.getTileEntity(basePos) instanceof TotemBaseTileEntity)
        {
            TotemBaseTileEntity totemBaseTileEntity = (TotemBaseTileEntity) world.getTileEntity(basePos);
            if (totemBaseTileEntity.active)
            {
                totemBaseTileEntity.riteEnding();
            }
        }
        super.remove();
    }

    public void passiveParticles()
    {
        Color color = type.color;
        for (int i = -1; i <= 1; i++)
        {
            float extraVelocity = 0.03f * i;
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(20)
                    .setSpin(0.2f)
                    .setScale(0.25f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .enableNoClip()
                    .randomOffset(0.1f, 0.1f)
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(world, pos, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);

            ParticleManager.create(MalumParticles.SMOKE_PARTICLE)
                    .setAlpha(0.025f, 0f)
                    .setLifetime(40)
                    .setSpin(0.1f)
                    .setScale(0.35f, 0)
                    .setColor(color, color)
                    .addVelocity(0, extraVelocity,0)
                    .randomOffset(0.2f)
                    .enableNoClip()
                    .randomVelocity(0.001f, 0.001f)
                    .evenlyRepeatEdges(world, pos, 1, Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH);
        }
    }
}
