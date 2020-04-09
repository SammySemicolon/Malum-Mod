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
            int radius = 4;
            if (ModRecipes.getBlockCorruptionRecipe(state) != null)
            {
                for (int x = -radius; x <= radius; x++)
                {
                    for (int z = -radius; z <= radius; z++)
                    {
                        for (int y = -radius; y <= radius; y++)
                        {
                            if (!((x == -radius || x == radius) && (z == -radius || z == radius)))
                            {
                                BlockPos additionalPos = pos.add(x,y,z);
                                BlockState additionalState = world.getBlockState(additionalPos);
                                if (ModRecipes.getBlockCorruptionRecipe(additionalState) != null)
                                {
                                    BlockCorruptionRecipe.attemptCorruption(additionalState,world,additionalPos);
                                }
                            }
                        }
                    }
                }
                playerEntity.addStat(Stats.ITEM_USED.get(this));
                playerEntity.swingArm(context.getHand());
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}