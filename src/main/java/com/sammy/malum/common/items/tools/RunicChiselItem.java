package com.sammy.malum.common.items.tools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumChiseling;
import com.sammy.malum.core.modcontent.MalumChiseling.MalumChiselRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RunicChiselItem extends Item
{
    public RunicChiselItem(Properties properties)
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
        ItemStack stack = playerEntity.getHeldItem(context.getHand().equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND);
        ItemStack chisel = playerEntity.getHeldItem(context.getHand());
        Block outputBlock = MalumChiseling.getChiseledBlock(block, stack);
        if (outputBlock != null)
        {
            if (outputBlock instanceof TotemPoleBlock)
            {
                TotemPoleBlock totemPoleBlock = (TotemPoleBlock) outputBlock;
                if (block instanceof RotatedPillarBlock)
                {
                    if (!world.getBlockState(pos).get(RotatedPillarBlock.AXIS).isVertical())
                    {
                        return ActionResultType.FAIL;
                    }
                }
                world.setBlockState(context.getPos(), totemPoleBlock.state(context));
                MalumHelper.updateState(world, pos);
                playerEntity.swingArm(Hand.MAIN_HAND);
                stack.shrink(1);
                chisel.damageItem(1, playerEntity,(entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS,1,1.1f);
            }
            else
            {
                MalumHelper.setBlockStateWithExistingProperties(world, pos,outputBlock.getDefaultState(),3,true);
                world.playSound(null, pos, outputBlock.getDefaultState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS,1,1.1f);
                playerEntity.swingArm(Hand.MAIN_HAND);
            }
            for (int i = 0; i < 5;i++)
            {
                MalumHelper.spawnParticles(world, pos, new BlockParticleData(ParticleTypes.BLOCK, outputBlock.getDefaultState()), 0);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}