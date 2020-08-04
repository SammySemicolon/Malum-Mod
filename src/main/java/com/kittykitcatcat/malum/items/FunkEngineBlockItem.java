package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SpiritDescription;
import com.kittykitcatcat.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;

public class FunkEngineBlockItem extends BlockItem implements SpiritDescription
{
    public FunkEngineBlockItem(Properties builder)
    {
        super(ModBlocks.funk_engine, builder);
    }

    @Override
    public ArrayList<ITextComponent> components()
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        components.add(new TranslationTextComponent("malum.tooltip.funkengine.desc"));
        return components;
    }
}