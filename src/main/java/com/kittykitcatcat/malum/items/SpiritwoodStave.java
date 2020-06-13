package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SoulStorage;
import com.kittykitcatcat.malum.SpiritDataHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import static net.minecraft.block.EnderChestBlock.CONTAINER_NAME;

public class SpiritwoodStave extends Item implements SoulStorage
{
    public SpiritwoodStave(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        SpiritDataHelper.harvestSpirit((PlayerEntity) attacker, target.getEntity().getType().getRegistryName().toString(),1);
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public int capacity()
    {
        return 5;
    }
}