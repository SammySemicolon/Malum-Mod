package com.sammy.malum.common.items;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.common.blocks.totems.TotemPoleTileEntity;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpiritSplinterItem extends Item
{
    public MalumSpiritType type;
    public SpiritSplinterItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof RotatedPillarBlock)
        {
            if (!world.getBlockState(pos).get(RotatedPillarBlock.AXIS).isVertical())
            {
                return ActionResultType.FAIL;
            }
        }
        PlayerEntity playerEntity = context.getPlayer();
        Block outputBlock = null;
        if (block.equals(MalumBlocks.SUN_KISSED_LOG.get()))
        {
            outputBlock = MalumBlocks.TOTEM_POLE.get();
        }
        if (outputBlock != null)
        {
            world.setBlockState(pos, outputBlock.getDefaultState());
            MalumHelper.updateState(world, pos);
            playerEntity.swingArm(Hand.MAIN_HAND);
            world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1, 1.1f);
            if (world.getTileEntity(pos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(pos);
                totemPoleTileEntity.type = type;
                totemPoleTileEntity.activeTime = 15;
                totemPoleTileEntity.direction = context.getFace();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
