package com.sammy.malum.items.staves;

import com.sammy.malum.SpiritDataHelper;
import com.sammy.malum.SpiritStorage;
import com.sammy.malum.capabilities.MalumDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import static com.sammy.malum.SpiritDataHelper.*;
import static com.sammy.malum.SpiritDataHelper.countNBT;
import static com.sammy.malum.SpiritDataHelper.typeNBT;
import static com.sammy.malum.capabilities.MalumDataProvider.*;

public class CreativeStave extends Item implements SpiritStorage
{
    public CreativeStave(Properties builder)
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
        setHusk(target, true);
        if (attacker.isSneaking())
        {
            stack.getTag().putString(typeNBT, getSpirit(target));
            stack.getTag().putInt(countNBT, 2147483647);
        }
        else
        {
            harvestSpirit((PlayerEntity) attacker, target, getSpirit(target), 1);
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public int capacity()
    {
        return 2147483647;
    }
}