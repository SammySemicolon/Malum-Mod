package com.sammy.malum.common.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSpiritTypes;
import com.sammy.malum.core.mod_systems.rites.MalumRiteType;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

public abstract class ActivatorRite extends MalumRiteType
{
    public ActivatorRite(String identifier, MalumSpiritType... spirits)
    {
        super(identifier, true, spirits);
    }

    @Override
    public void executeRite(ServerWorld world, BlockPos pos)
    {
        ArrayList<BlockPos> nearbyBlocks = new ArrayList<>(MalumHelper.getBlocks(pos, range(), range(), range(), b -> world.getTileEntity(b) instanceof IAssembled));
        for (BlockPos nearbyPos : nearbyBlocks)
        {
            IAssembled assembled = (IAssembled) world.getTileEntity(nearbyPos);
            assembled.assemble(pos, MalumHelper.toArrayList(MalumSpiritTypes.AQUATIC_SPIRIT, MalumSpiritTypes.ARCANE_SPIRIT));
            MalumHelper.updateAndNotifyState(world, pos);
        }
        super.executeRite(world, pos);
    }
    public interface IAssembled
    {
        void assemble(BlockPos pos, ArrayList<MalumSpiritType> spirits);
    }
}
