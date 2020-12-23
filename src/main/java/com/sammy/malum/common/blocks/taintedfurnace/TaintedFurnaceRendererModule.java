package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.spirits.block.ISpiritHolderTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.TextModule;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class TaintedFurnaceRendererModule extends TextModule
{
    public TaintedFurnaceRendererModule()
    {
        super(true, 8);
    }
    
    @Override
    public ArrayList<ITextComponent> components(SimpleTileEntity tileEntityIn)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        if (tileEntityIn instanceof TaintedFurnaceCoreTileEntity)
        {
            TaintedFurnaceCoreTileEntity tileEntity = (TaintedFurnaceCoreTileEntity) tileEntityIn;
            components.add(ClientHelper.combinedComponent(ClientHelper.simpleTranslatableComponent("malum.tooltip.fuel"), ClientHelper.importantComponent("" + tileEntity.fuel, true)));
            components.add(ClientHelper.combinedComponent(ClientHelper.simpleTranslatableComponent("malum.tooltip.progress"), ClientHelper.importantComponent(tileEntity.progress + "/200", true)));
        }
        return components;
    }
    
    @Override
    public Vector3f offset(Direction direction)
    {
        return direction.toVector3f();
    }
}
