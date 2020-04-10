package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.recipes.BlockTransmutationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TransmutationGemItem extends Item
{
    public TransmutationGemItem(Properties builder)
    {
        super(builder);
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity != null)
        {
            World world = context.getWorld();
            BlockPos pos = context.getPos();
            BlockState state = world.getBlockState(pos);
            if (BlockTransmutationRecipe.attemptBlockTransmutation(state, world, pos))
            {
                playerEntity.addStat(Stats.ITEM_USED.get(this));
                playerEntity.swingArm(context.getHand());
                playerEntity.world.playSound(playerEntity, playerEntity.getPosition(), ModSounds.transmutate, SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(world.rand, 0.8f, 1.2f));
                playerEntity.world.playSound(playerEntity, playerEntity.getPosition(), state.getSoundType().getBreakSound(), SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(world.rand, 0.8f, 1.2f));
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}