package com.sammy.malum.core.systems.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class SimpleBlockProperties extends BlockBehaviour.Properties {
    public boolean needsPickaxe;
    public boolean needsAxe;
    public boolean needsShovel;
    public boolean needsHoe;

    public boolean needsStone;
    public boolean needsIron;
    public boolean needsDiamond;

    public SimpleBlockProperties(Material p_60905_, MaterialColor p_60906_) {
        super(p_60905_, (p_60952_) -> p_60906_);
    }

    public SimpleBlockProperties needsPickaxe()
    {
        needsPickaxe = true;
        return this;
    }
    public SimpleBlockProperties needsAxe()
    {
        needsAxe = true;
        return this;
    }
    public SimpleBlockProperties needsShovel()
    {
        needsShovel = true;
        return this;
    }
    public SimpleBlockProperties needsHoe()
    {
        needsHoe = true;
        return this;
    }

    public SimpleBlockProperties needsStone()
    {
        needsStone = true;
        return this;
    }
    public SimpleBlockProperties needsIron()
    {
        needsIron = true;
        return this;
    }
    public SimpleBlockProperties needsDiamond()
    {
        needsDiamond = true;
        return this;
    }
}
