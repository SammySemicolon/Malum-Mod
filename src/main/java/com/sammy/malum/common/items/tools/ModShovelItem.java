package com.sammy.malum.common.items.tools;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ModShovelItem extends ShovelItem
{
    public ModShovelItem(IItemTier material, int damage, float speed, Properties properties)
    {
        super(material, damage + 1.5f, speed - 3f, properties.maxDamage(material.getMaxUses()).addToolType(ToolType.SHOVEL, material.getHarvestLevel()));
    }
}