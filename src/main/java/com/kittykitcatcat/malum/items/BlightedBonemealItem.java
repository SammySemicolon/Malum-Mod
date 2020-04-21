package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.recipes.BlockCorruptionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class BlightedBonemealItem extends Item
{
    public BlightedBonemealItem(Properties builder)
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
            if (ModRecipes.getBlockCorruptionRecipe(state) != null)
            {
                BlockCorruptionRecipe.attemptCorruption(state,world,pos);
                playerEntity.addStat(Stats.ITEM_USED.get(this));
                playerEntity.swingArm(context.getHand());
                playerEntity.getHeldItem(context.getHand()).shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}