package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import java.awt.*;

import static com.sammy.malum.MalumHelper.*;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritAltarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritAltarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get());
    
        spiritInventory = new SimpleInventory(5, 64, t-> t.getItem() instanceof SpiritSplinterItem)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public SimpleInventory spiritInventory;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        spiritInventory.writeData(compound, "spiritInventory");
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        spiritInventory.readData(compound, "spiritInventory");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            for (int i = 0; i < spiritInventory.slotCount; i++)
            {
                ItemStack item = spiritInventory.getStackInSlot(i);
                if (item.getItem() instanceof SpiritSplinterItem)
                {
                    Vector3d offset = itemOffset(this, i);
                    double x = getPos().getX() + offset.getX();
                    double y = getPos().getY() + offset.getY();
                    double z = getPos().getZ() + offset.getZ();
                    
                    SpiritSplinterItem spiritSplinterItem = (SpiritSplinterItem) item.getItem();
                    Color color = spiritSplinterItem.type.color;
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.4f, 0f).setLifetime(20).setScale(0.075f, 0).randomOffset(0.25, 0.25).randomVelocity(0.02f, 0.08f).setColor(brighter(color, 2), darker(color, 3)).randomVelocity(0f, 0.01f).addVelocity(0, 0.01f, 0).enableNoClip().repeat(world, x,y,z, 1);
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.03f, 0f).setLifetime(60).setScale(0.3f, 0).randomOffset(0.2, 0.1).randomVelocity(0.02f, 0.02f).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).addVelocity(0, -0.005f, 0).enableNoClip().repeat(world, x,y,z,2);
                }
            }
        }
    }
    
    public static Vector3d itemOffset(SpiritAltarTileEntity tileEntity, int slot)
    {
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f,0.75f,0.5f), 1,slot, tileEntity.spiritInventory.nonEmptyItems(),tileEntity.world.getGameTime(),360);
    }
}