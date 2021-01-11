package com.sammy.malum.common.items.tools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumChiseling;
import com.sammy.malum.core.modcontent.MalumChiseling.MalumChiselRecipe;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
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
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ChiselItem extends Item
{
    public ChiselItem(Properties properties)
    {
        super(properties);
    }
    
    @SubscribeEvent
    public static void leftCLick(PlayerInteractEvent.LeftClickBlock event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity playerEntity = event.getPlayer();
        Hand hand = null;
        if (playerEntity.getHeldItemMainhand().getItem() instanceof ChiselItem)
        {
            hand = Hand.MAIN_HAND;
        }
        if (playerEntity.getHeldItemOffhand().getItem() instanceof ChiselItem)
        {
            hand = Hand.OFF_HAND;
        }
        if (hand != null)
        {
            ItemStack chisel = playerEntity.getHeldItem(hand);
            ItemStack rune = playerEntity.getHeldItem(hand.equals(Hand.MAIN_HAND) ? Hand.OFF_HAND : Hand.MAIN_HAND);
            Block outputBlock = MalumChiseling.getChiseledBlock(block, rune);
            if (outputBlock != null)
            {
                if (outputBlock instanceof TotemPoleBlock)
                {
                    TotemPoleBlock totemPoleBlock = (TotemPoleBlock) outputBlock;
                    if (block instanceof RotatedPillarBlock)
                    {
                        if (!world.getBlockState(pos).get(RotatedPillarBlock.AXIS).isVertical())
                        {
                            return;
                        }
                    }
                    world.setBlockState(pos, totemPoleBlock.state(event.getFace()));
                    MalumHelper.updateState(world, pos);
                    playerEntity.swingArm(hand);
                    rune.shrink(1);
                    chisel.damageItem(1, playerEntity, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                    world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1, 1.1f);
                    return;
                }
                else
                {
                    MalumHelper.setBlockStateWithExistingProperties(world, pos, outputBlock.getDefaultState(), 3);
                    world.playSound(null, pos, outputBlock.getDefaultState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1, 1.1f);
                    playerEntity.swingArm(Hand.MAIN_HAND);
                }
                for (int i = 0; i < 5; i++)
                {
                    MalumHelper.spawnParticles(world, pos, new BlockParticleData(ParticleTypes.BLOCK, outputBlock.getDefaultState()), 0);
                }
            }
            event.setCanceled(true);
        }
    }
}