package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritStorage;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.SpiritDataHelper.countNBT;
import static com.kittykitcatcat.malum.SpiritDataHelper.typeNBT;

public class SpiritVault extends Item implements SpiritStorage
{
    public SpiritVault(Properties builder)
    {
        super(builder);
    }

    @Override
    public int capacity()
    {
        return 20;
    }
}