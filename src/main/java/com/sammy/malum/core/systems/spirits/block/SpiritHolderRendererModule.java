package com.sammy.malum.core.systems.spirits.block;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.TextModule;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class SpiritHolderRendererModule extends TextModule
{
    public SpiritHolderRendererModule()
    {
        super(true, 8);
    }
    
    @Override
    public ArrayList<ITextComponent> components(SimpleTileEntity tileEntityIn)
    {
        ArrayList<ITextComponent> components = new ArrayList<>();
        if (tileEntityIn instanceof ISpiritHolderTileEntity)
        {
            ISpiritHolderTileEntity tileEntity = (ISpiritHolderTileEntity) tileEntityIn;
            if (tileEntity.getSpiritType() != null && tileEntity.currentSpirits() > 0)
            {
                components.add(ClientHelper.combinedComponent(ClientHelper.importantComponent(tileEntity.currentSpirits() + "/" + tileEntity.maxSpirits(), true), ClientHelper.importantComponent(tileEntity.getSpiritType(), true)));
            }
            else
            {
            
            }
        }
        return components;
    }
    
    @Override
    public Vector3f offset(Direction direction)
    {
        return direction.toVector3f();
    }
}
