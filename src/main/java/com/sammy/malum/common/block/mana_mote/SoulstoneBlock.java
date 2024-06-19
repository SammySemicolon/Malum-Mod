package com.sammy.malum.common.block.mana_mote;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import java.util.function.*;

public class SoulstoneBlock extends Block {
    public SoulstoneBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack stack = player.getItemInHand(pHand);
        if (!(stack.getItem() instanceof SpiritShardItem spiritShardItem)) {
            return super.use(pState, pLevel, pPos, player, pHand, pHit);
        }
        MalumSpiritType spiritType = spiritShardItem.type;
        final Supplier<SpiritMoteBlock> spiritMote = spiritType.spiritMote;
        if (spiritMote == null)
        {
            return super.use(pState, pLevel, pPos, player, pHand, pHit);
        }
        BlockState blockstate = spiritMote.get().defaultBlockState();
        SoundType soundtype = blockstate.getSoundType(pLevel, pPos, player);
        pLevel.setBlock(pPos, blockstate, 3);
        pLevel.levelEvent(2001, pPos, Block.getId(pState));
        pLevel.levelEvent(2001, pPos, Block.getId(blockstate));
        pLevel.playSound(player, pPos, SoundRegistry.SPIRIT_MOTE_CREATED.get(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, Mth.nextFloat(pLevel.random, 1.1f, 1.4f));
        if (!player.getAbilities().instabuild) {
            stack.shrink(2);
        }
        if (!pLevel.isClientSide) {
            ParticleEffectTypeRegistry.SPIRIT_MOTE_SPARKLES.createPositionedEffect(pLevel, new PositionEffectData(pPos), new ColorEffectData(spiritType));
        }
        return InteractionResult.SUCCESS;
    }
}
