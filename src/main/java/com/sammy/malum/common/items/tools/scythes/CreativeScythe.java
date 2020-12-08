package com.sammy.malum.common.items.tools.scythes;

import com.sammy.malum.common.blocks.taint.ITaintSpreader;
import com.sammy.malum.core.systems.essences.EssenceHelper;
import com.sammy.malum.core.systems.recipes.TaintConversion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class CreativeScythe extends ScytheItem
{
    public CreativeScythe()
    {
        super(ItemTier.WOOD, 9996, 999.2f, new Item.Properties().defaultMaxDamage(-1).isImmuneToFire());
    }
}