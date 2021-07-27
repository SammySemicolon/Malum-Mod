package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.common.rites.ActivatorRite;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.mod_content.MalumArcaneAssemblyRecipes;
import com.sammy.malum.core.mod_systems.inventory.SimpleInventory;
import com.sammy.malum.core.mod_systems.particle.ParticleManager;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import com.sammy.malum.core.mod_systems.tile.SimpleInventoryTileEntity;
import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.util.math.MathHelper.nextFloat;

public class ArcaneAssemblerTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity, ActivatorRite.IAssembled
{
    public boolean active;
    public int progress;
    public int finish;

    public MalumArcaneAssemblyRecipes.MalumArcaneAssemblerRecipe recipe;
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
                recipe = MalumArcaneAssemblyRecipes.getRecipe(getStackInSlot(0));
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        finish = compound.getInt("finish");
        int size = compound.getInt("spiritCount");
        spirits = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            spirits.add(SpiritHelper.figureOutType(compound.getString("spirit_" + i)));
        }
        super.readData(compound);
        recipe = MalumArcaneAssemblyRecipes.getRecipe(inventory.getStackInSlot(0));
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("finish", finish);
        compound.putInt("spiritCount", spirits.size());
        for (int i = 0; i < spirits.size(); i++)
        {
            MalumSpiritType type = spirits.get(i);
            compound.putString("spirit_" + i, type.identifier);
        }
        return super.writeData(compound);
    }

    @Override
    public void assemble(BlockPos pos, ArrayList<MalumSpiritType> spirits)
    {
        if (recipe != null)
        {
            active = true;
            this.spirits = spirits;
            MalumHelper.updateAndNotifyState(world, this.pos);
        }
    }
    @Override
    public void tick()
    {
        if (active)
        {
            progress++;
            if (progress > 200)
            {
                Vector3d pos = itemPos(this, inventory.getStackInSlot(0).getItem());
                ItemStack stack = inventory.getStackInSlot(0);
                int count = stack.getCount();
                int remainder = count % recipe.inputIngredient.count;
                int timesCrafted = count / recipe.inputIngredient.count * recipe.outputIngredient.count;
                do
                {
                    int stackCount = Math.min(timesCrafted, 64);
                    timesCrafted -= stackCount;
                    world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), MalumHelper.copyWithNewCount(recipe.outputIngredient.getItem(), stackCount)));
                }
                while(timesCrafted != 0);
                progress = 0;
                active = false;
                if (remainder == 0)
                {
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    return;
                }
                inventory.setStackInSlot(0, MalumHelper.copyWithNewCount(inventory.getStackInSlot(0), remainder));
            }
            if (MalumHelper.areWeOnClient(world))
            {
                Vector3d pos = itemPos(this, inventory.getStackInSlot(0).getItem());
                for (int i = 0; i < spirits.size(); i++)
                {
                    MalumSpiritType type = spirits.get(i);
                    float alpha = (1f - i / (float) spirits.size());
                    float scale = (1f + i * 0.25f);
                    particles(type.color, alpha, scale, pos.x, pos.y, pos.z);
                }
                if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
                {
                    SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
                    Color color = item.type.color;
                    SpiritHelper.spiritParticles(world, pos.x, pos.y, pos.z, color);
                }
            }
        }
    }

    public void particles(Color color, float alpha, float scale, double x, double y, double z)
    {
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
        Vector3d centerPos = new Vector3d(x,y,z);
        Vector3d circlePos = MalumHelper.circlePosition(centerPos, 0.5f+rand.nextFloat()*0.25f, rand.nextInt(200),200).add(MathHelper.nextFloat(rand, -0.25f, 0.25f), MathHelper.nextFloat(rand, -0.25f, 0.25f), MathHelper.nextFloat(rand, -0.25f, 0.25f));
        Vector3d velocity = centerPos.subtract(circlePos).normalize().mul(0.02f,0.02f,0.02f);

        ParticleManager.create(MalumParticles.TWINKLE_PARTICLE)
                .setAlpha(0f, 0.6f*alpha)
                .setLifetime(20 + rand.nextInt(5))
                .setScale(twinkleScale*0.2f, 0)
                .setColor(color, color.darker())
                .setSpin(nextFloat(rand, 0.05f, 0.1f))
                .setVelocity(velocity.x,velocity.y,velocity.z)
                .enableNoClip()
                .repeat(world, circlePos.x,circlePos.y,circlePos.z, 1);
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
}