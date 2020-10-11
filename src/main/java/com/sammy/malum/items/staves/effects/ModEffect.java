package com.sammy.malum.items.staves.effects;

import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDescription;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Nullable
public abstract class ModEffect implements SpiritConsumer, SpiritDescription
{
    public enum effectTypeEnum
    {
        rightClick(0),
        blockInteraction(1);

        public final int type;

        effectTypeEnum(int type) { this.type = type;}
    }

    @Override
    public String spirit()
    {
        return null;
    }
    @Override
    public int durability()
    {
        return 0;
    }
    @Override
    public ArrayList<ITextComponent> components()
    {
        return null;
    }

    public void effect(PlayerEntity playerEntity, ItemStack stack)
    {

    }
    public void effect(PlayerEntity playerEntity, ItemStack stack, BlockState state)
    {

    }
    public effectTypeEnum type()
    {
        return effectTypeEnum.rightClick;
    }
    public int cooldown()
    {
        return 10;
    }
}