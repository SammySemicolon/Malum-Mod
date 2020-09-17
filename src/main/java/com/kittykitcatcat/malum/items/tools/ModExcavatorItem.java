package com.kittykitcatcat.malum.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraftforge.common.ToolType;

import java.util.Set;

public class ModExcavatorItem extends ToolItem
{
    public ModExcavatorItem(float damage, float speed, IItemTier tier, Set<Block> effectiveBlocksIn, Properties properties)
    {
        super(damage + 1, speed - 3f, tier, effectiveBlocksIn, properties.maxDamage(tier.getMaxUses()).addToolType(ToolType.PICKAXE, tier.getHarvestLevel()).addToolType(ToolType.AXE, tier.getHarvestLevel()).addToolType(ToolType.SHOVEL, tier.getHarvestLevel()));
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state)
    {
        float efficiency = super.getDestroySpeed(stack, state);
        if (state.getBlock().equals(Blocks.OBSIDIAN))
        {
            efficiency *= 100f;
        }
        return efficiency;
    }
}