package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.SoulStorage;
import com.kittykitcatcat.malum.SpiritDataHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.SpiritDataHelper.countNBT;
import static com.kittykitcatcat.malum.SpiritDataHelper.typeNBT;

public class SpiritwoodStave extends Item implements SoulStorage
{
    public SpiritwoodStave(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (stack.getTag() == null)
        {
            stack.setTag(new CompoundNBT());
        }
        SpiritDataHelper.harvestSpirit((PlayerEntity) attacker, target.getEntity().getType().getRegistryName().toString(),1);
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if (stack.hasTag())
        {
            ArrayList<ITextComponent> components = new ArrayList<>();
            components.add(new StringTextComponent(stack.getTag().getString(typeNBT)));
            components.add(new StringTextComponent(stack.getTag().getInt(countNBT) + "/" + capacity()));
            MalumHelper.makeTooltip(stack, worldIn, tooltip, flagIn, components);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public int capacity()
    {
        return 5;
    }
}