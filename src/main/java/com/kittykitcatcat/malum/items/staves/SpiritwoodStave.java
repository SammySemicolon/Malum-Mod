package com.kittykitcatcat.malum.items.staves;

import com.kittykitcatcat.malum.SpiritDataHelper;
import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class SpiritwoodStave extends Item implements SpiritStorage
{
    public SpiritwoodStave(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player)
    {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (stack.getTag() == null)
        {
            stack.setTag(new CompoundNBT());
        }
        if (!CapabilityValueGetter.getHusk(target))
        {
            CapabilityValueGetter.setHusk(target, true);
            SpiritDataHelper.harvestSpirit((PlayerEntity) attacker, SpiritDataHelper.getSpirit(target), 1);
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public int capacity()
    {
        return 5;
    }
}