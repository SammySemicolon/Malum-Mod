package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.MalumRites;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import com.sammy.malum.network.packets.particle.totem.TotemParticlePacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemBaseTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemBaseTileEntity()
    {
        super(MalumTileEntities.TOTEM_BASE_TILE_ENTITY.get());
    }

    public MalumRiteType rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    public boolean active;
    public int progress;
    public int height;

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (rite != null)
        {
            compound.putString("rite", rite.identifier);
        }
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++)
        {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        rite = MalumRites.getRite(compound.getString("rite"));
        int size = compound.getInt("spiritCount");
        for (int i = 0; i < size; i++)
        {
            spirits.add(SpiritHelper.spiritType(compound.getString("spirit_"+i)));
        }
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        super.readData(compound);
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            if (rite != null)
            {
                progress++;
                if (progress >= rite.interval())
                {
                    rite.executeRite((ServerWorld) world, pos);
                    progress = 0;
                }
            }
            else if (active)
            {
                progress--;
                if (progress <= 0)
                {
                    height++;
                    progress = 20;
                    BlockPos polePos = pos.up(height);
                    if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
                    {
                        Direction poleFacing = world.getBlockState(polePos).get(HORIZONTAL_FACING);
                        Direction baseFacing = getBlockState().get(HORIZONTAL_FACING);
                        if (height == 1)
                        {
                            world.setBlockState(pos, getBlockState().with(HORIZONTAL_FACING, poleFacing));
                            MalumHelper.updateState(world, pos);
                        }
                        if (poleFacing.equals(baseFacing))
                        {
                            TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
                            if (tileEntity.type != null)
                            {
                                spirits.add(tileEntity.type);
                                tileEntity.riteStarting(height);
                            }
                        }
                    }
                    else
                    {
                        MalumRiteType rite = MalumRites.getRite(spirits);
                        if (rite == null)
                        {
                            riteEnding();
                        }
                        else
                        {
                            riteComplete(rite);
                        }
                    }
                }
            }
        }
    }
    public ArrayList<TotemPoleTileEntity> poles()
    {
        ArrayList<TotemPoleTileEntity> poles = new ArrayList<>();
        for (int i = 1; i <= height; i++)
        {
            if (world.getTileEntity(pos.up(i)) instanceof TotemPoleTileEntity)
            {
                poles.add((TotemPoleTileEntity) world.getTileEntity(pos.up(i)));
            }
        }
        return poles;
    }
    public void reset()
    {
        poles().forEach(TotemPoleTileEntity::riteEnding);
        height = 0;
        rite = null;
        active = false;
        progress = 0;
        spirits.clear();
        MalumHelper.updateAndNotifyState(world,pos);
    }
    public void riteStarting()
    {
        active = true;
        MalumHelper.updateAndNotifyState(world,pos);
    }
    public void riteComplete(MalumRiteType rite)
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGED, SoundCategory.BLOCKS,1,1);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), TotemParticlePacket.fromSpirits(spirits, pos, true));
        poles().forEach(p -> p.riteComplete(height));
        progress = 0;
        if (rite.isInstant)
        {
            rite.executeRite((ServerWorld) world, pos);
            reset();
            return;
        }
        this.rite = rite;
        MalumHelper.updateAndNotifyState(world,pos);
    }
    public void riteEnding()
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS,1,0.5f);
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), TotemParticlePacket.fromSpirits(spirits, pos, false));
        reset();
    }
}
