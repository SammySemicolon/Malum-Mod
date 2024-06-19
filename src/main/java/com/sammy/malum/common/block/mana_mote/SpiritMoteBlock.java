package com.sammy.malum.common.block.mana_mote;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import java.util.function.Supplier;

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
        return (SpiritMoteBlock) super.setBlockEntity(type);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
