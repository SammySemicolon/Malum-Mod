package com.sammy.malum.common.block.mana_mote;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.*;

public class SpiritMoteBlock extends LodestoneEntityBlock<MoteOfManaBlockEntity> {

    public final MalumSpiritType spiritType;
    public SpiritMoteBlock(Properties properties, MalumSpiritType spiritType) {
        super(properties.lightLevel(b -> 6));
        this.spiritType = spiritType;
    }

    @Override
    protected void spawnDestroyParticles(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState) {

    }

    @Override
    public SpiritMoteBlock setBlockEntity(Supplier<BlockEntityType<MoteOfManaBlockEntity>> type) {
        return (SpiritMoteBlock)super.setBlockEntity(type);
    }
}
