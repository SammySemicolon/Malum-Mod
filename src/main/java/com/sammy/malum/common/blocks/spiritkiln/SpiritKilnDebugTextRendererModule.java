package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.recipes.SpiritKilnRecipe;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.TextModule;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class SpiritKilnDebugTextRendererModule extends TextModule
{
    public SpiritKilnDebugTextRendererModule()
    {
        super(true, 8);
    }
    
    @Override
    public ArrayList<ITextComponent> components(SimpleTileEntity tileEntityIn)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        if (tileEntityIn instanceof SpiritKilnCoreTileEntity)
        {
            SpiritKilnCoreTileEntity tileEntity = (SpiritKilnCoreTileEntity) tileEntityIn;
            SpiritKilnRecipe recipe = SpiritKilnRecipe.getRecipe(tileEntity.inventory.getStackInSlot(0));
            if (recipe != null)
            {
                components.add(ClientHelper.combinedComponent(ClientHelper.simpleTranslatableComponent("malum.tooltip.progress"), ClientHelper.importantComponent(tileEntity.progress + "/" + recipe.recipeTime, true)));
            }
            components.add(ClientHelper.combinedComponent(ClientHelper.simpleTranslatableComponent("malum.tooltip.fuel"), ClientHelper.importantComponent("" + tileEntity.fuel, true)));
        }
        return components;
    }
    
    @Override
    public Vector3f offset(Direction direction)
    {
        return direction.toVector3f();
    }
}
