package com.sammy.malum.common.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
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
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), UpwardsBlockParticlesPacket.fromSpirits(nearbyPos.getX(),nearbyPos.getY(), nearbyPos.getZ(), spirits));
        }
        BlockPos nearbyPos = nearbyBlocks.get(world.rand.nextInt(nearbyBlocks.size()));
        BlockState state = world.getBlockState(nearbyPos);
        IGrowable iGrowable = (IGrowable) state.getBlock();
        iGrowable.grow(world, world.rand, nearbyPos, state);
        BlockPos packetPos = state.isSolid() ? nearbyPos : nearbyPos.down();
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(pos)), UpwardsBlockParticlesPacket.fromSpirits(packetPos.getX(),packetPos.getY(), packetPos.getZ(), spirits));
        super.executeRite(world, pos);
    }
    public interface IAssembled
    {
        public void assemble(MalumSpiritType assemblyType);
    }
}
