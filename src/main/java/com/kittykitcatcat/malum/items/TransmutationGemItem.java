package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.recipes.BlockTransmutationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
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
            BlockTransmutationRecipe.attemptBlockTransmutation(state, world, pos);
            playerEntity.addStat(Stats.ITEM_USED.get(this));
            playerEntity.swingArm(context.getHand());
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}