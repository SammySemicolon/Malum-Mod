package com.sammy.malum.common.items;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumChiseling;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MalumRuneItem extends Item
{
    public MalumRune rune;
    public MalumRuneItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity playerEntity = context.getPlayer();
        Block outputBlock = null;
        if (block.equals(MalumBlocks.SUN_KISSED_LOG.get()))
        {
            outputBlock = rune.carvedForm;
        }
        if (outputBlock != null)
        {
            if (block instanceof RotatedPillarBlock)
            {
                if (!world.getBlockState(pos).get(RotatedPillarBlock.AXIS).isVertical())
                {
                    return ActionResultType.FAIL;
                }
            }
            if (outputBlock instanceof TotemPoleBlock)
            {
                TotemPoleBlock totemPoleBlock = (TotemPoleBlock) outputBlock;
                world.setBlockState(context.getPos(), totemPoleBlock.state(context));
                MalumHelper.updateState(world, pos);
                playerEntity.swingArm(Hand.MAIN_HAND);
                world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS,1,1.1f);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
}
