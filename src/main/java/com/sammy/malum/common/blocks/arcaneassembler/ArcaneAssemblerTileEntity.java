package com.sammy.malum.common.blocks.arcaneassembler;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.ActivatorRite;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumArcaneAssemblyRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class ArcaneAssemblerTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity, ActivatorRite.IAssembled
{
    public MalumArcaneAssemblyRecipes.MalumArcaneAssemblerRecipe arcaneAssemblerRecipe;
    public int activity;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();

    public ArcaneAssemblerTileEntity()
    {
        super(MalumTileEntities.ARCANE_ASSEMBLER_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ArcaneAssemblerTileEntity.this.markDirty();
                updateContainingBlockInfo();
                arcaneAssemblerRecipe = MalumArcaneAssemblyRecipes.getRecipe(getStackInSlot(0));
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        activity = compound.getInt("activity");
        int size = compound.getInt("spiritCount");
        spirits = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            spirits.add(SpiritHelper.figureOutType(compound.getString("spirit_" + i)));
        }
        super.readData(compound);
        arcaneAssemblerRecipe = MalumArcaneAssemblyRecipes.getRecipe(inventory.getStackInSlot(0));
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("activity", activity);
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++)
        {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        return super.writeData(compound);
    }

    @Override
    public void tick()
    {
        if (activity > 0)
        {
            activity--;
            if (MalumHelper.areWeOnServer(world))
            {

            }
            else
            {
                Vector3d pos = itemPos(this, inventory.getStackInSlot(0).getItem());
                for (int i = 0; i < spirits.size(); i++)
                {
                    MalumSpiritType type = spirits.get(i);
                    particles(type.color,1-i*0.5f,1f+i*0.25f,pos.x, pos.y, pos.z);
                }
            }
        }
        if (MalumHelper.areWeOnClient(world))
        {
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
            {
                SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
                Color color = item.type.color;
                Vector3d pos = itemPos(this, item);
                SpiritHelper.spiritParticles(world, pos.x, pos.y, pos.z, color);
            }
        }
    }

    public static Vector3d itemPos(SimpleTileEntity tileEntity, Item item)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(itemOffset(tileEntity, item));
    }

    public static Vector3d itemOffset(SimpleTileEntity tileEntity, Item item)
    {
        if (item instanceof SpiritItem)
        {
            return new Vector3d(0.5f, 1.25f + Math.sin((tileEntity.getWorld().getGameTime() % 360) / 20f) * 0.1f, 0.5f);
        }
        return new Vector3d(0.5f, 1.25f, 0.5f);
    }

    @Override
    public void assemble(BlockPos pos, ArrayList<MalumSpiritType> spirits)
    {
        //if (arcaneAssemblerRecipe != null)
        {
            activity = 6000;
            this.spirits = spirits;
            MalumHelper.updateAndNotifyState(world, this.pos);
        }
    }
    public void particles(Color color, float alpha, float scale, double x, double y, double z)
    {
       // scale*=3f;
        Random rand = world.rand;

        float wispScale = (0.4f + rand.nextFloat() * 0.2f)*scale;
        ParticleManager.create(MalumParticles.WISP_PARTICLE)
                .setAlpha(0.125f*alpha, 0.1f)
                .setLifetime(10 + rand.nextInt(4))
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setScale(wispScale, wispScale*0.2f)
                .setColor(color, color.darker())
                .randomOffset(0.1f)
                .enableNoClip()
                .randomVelocity(0.01f, 0.01f)
                .repeat(world, x, y, z, 1);

        float twinkleScale = (0.5f + rand.nextFloat() * 0.3f)*scale;
        ParticleManager.create(MalumParticles.TWINKLE_PARTICLE)
                .setAlpha(0.1f*alpha, 0f)
                .setLifetime(15 + rand.nextInt(4))
                .setScale(twinkleScale*0.4f, twinkleScale)
                .setColor(color, color.darker())
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .randomOffset(0.15f*scale)
                .enableNoClip()
                .randomVelocity(0.025f, 0.025f)
                .repeat(world, x, y, z, 1);
    }
}