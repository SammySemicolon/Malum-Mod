package com.sammy.malum.common.blocks.wellofsuffering;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.IAltarProvider;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class WellOfSufferingTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity
{
    public float progress;
    public boolean active;

    public float water;
    public int evaporation;

    public int spinUp;
    public float spin;

    public WellOfSufferingTileEntity()
    {
        super(MalumTileEntities.WELL_OF_SUFFERING_TILE_ENTITY.get());
        inventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                WellOfSufferingTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    public static Vector3d itemPos(SimpleTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(0.5f, 1.15f, 0.5f);
    }

    public static Vector3d itemOffset(WellOfSufferingTileEntity tileEntity, int slot)
    {
        float distance = 0.5f - Math.min(0.25f, tileEntity.spinUp / 40f) + (float) Math.sin(tileEntity.spin / 20f) * 0.025f;
        float height = 1.25f - Math.min(0.5f, tileEntity.spinUp / 20f);
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f, height, 0.5f), distance, slot, tileEntity.inventory.nonEmptyItems(), (long) tileEntity.spin, 360);
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }

    public void passiveParticles()
    {
        spin += 1 + spinUp / 5f;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritItem)
            {
                Vector3d offset = itemOffset(this, i);
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritItem spiritSplinterItem = (SpiritItem) item.getItem();
                Color color = spiritSplinterItem.type.color;


                ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(10)
                        .setScale(0.3f, 0)
                        .setColor(color.brighter(), color.darker())
                        .enableNoClip()
                        .repeat(world, x, y, z, 2);

                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(80)
                        .setSpin(0.1f)
                        .setScale(0.2f, 0)
                        .setColor(color, color.darker())
                        .enableNoClip()
                        .repeat(world, x, y, z, 1);
            }
        }
    }
}
