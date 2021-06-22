package com.sammy.malum.common.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.network.packets.rites.BlastParticlePacket;
import com.sammy.malum.network.packets.rites.UpwardsBlockParticlesPacket;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public abstract class ActivatorRite extends MalumRiteType
{
    public MalumSpiritType assemblyType;
    public ActivatorRite(MalumSpiritType assemblyType, String identifier, MalumSpiritType... spirits)
    {
        super(identifier, true, spirits);
        this.assemblyType = assemblyType;
    }

    @Override
    public void executeRite(ServerWorld world, BlockPos pos)
    {
        ArrayList<BlockPos> nearbyBlocks = new ArrayList<>(MalumHelper.getBlocks(pos, range(), range(), range(), b -> world.getTileEntity(b) instanceof IAssembled));
        for (BlockPos nearbyPos : nearbyBlocks)
        {
            IAssembled assembled = (IAssembled) world.getTileEntity(nearbyPos);
            assembled.assemble(assemblyType);
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), BlastParticlePacket.fromSpirits(nearbyPos.getX()+0.5f, nearbyPos.getY()+1.5f, nearbyPos.getZ()+0.5f, MalumSpiritTypes.ELDRITCH_SPIRIT, MalumSpiritTypes.AQUATIC_SPIRIT));
            MalumHelper.updateAndNotifyState(world, pos);
        }
        super.executeRite(world, pos);
    }
    public interface IAssembled
    {
        public void assemble(MalumSpiritType assemblyType);
    }
}
