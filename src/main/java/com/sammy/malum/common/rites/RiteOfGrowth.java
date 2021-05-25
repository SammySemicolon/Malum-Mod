package com.sammy.malum.common.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.network.packets.rites.CropGrowthPacket;
import com.sammy.malum.network.packets.totem.TotemPoleParticlePacket;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.*;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class RiteOfGrowth extends MalumRiteType
{
    public RiteOfGrowth()
    {
        super("rite_of_growth", false, ARCANE_SPIRIT, HOLY_SPIRIT, HOLY_SPIRIT);
    }

    @Override
    public void executeRite(ServerWorld world, BlockPos pos)
    {
        if (world.rand.nextFloat() < 0.9f)
        {
            return;
        }
        ArrayList<BlockPos> nearbyBlocks = new ArrayList<>(MalumHelper.getBlocks(pos, range(), range(), range(), b -> {
            BlockState state = world.getBlockState(b);
            if (state.getBlock() instanceof IGrowable)
            {
                IGrowable growable = (IGrowable) state.getBlock();
                return growable.canGrow(world, b,state,false);
            }
            else
            {
                return false;
            }
        }));
        if (nearbyBlocks.isEmpty())
        {
            return;
        }
        BlockPos nearbyPos = nearbyBlocks.get(world.rand.nextInt(nearbyBlocks.size()));
        BlockState state = world.getBlockState(nearbyPos);
        IGrowable iGrowable = (IGrowable) state.getBlock();
        iGrowable.grow(world, world.rand, nearbyPos, state);
        BlockPos packetPos = state.isSolid() ? nearbyPos : nearbyPos.down();
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(()->world.getChunkAt(nearbyPos)), new CropGrowthPacket("holy", packetPos.getX(),packetPos.getY(), packetPos.getZ()));
        super.executeRite(world, pos);
    }

    @Override
    public int range()
    {
        return 4;
    }
}
