package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritAltarRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
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
    
        inventory = new SimpleInventory(1, 64, t-> !(t.getItem() instanceof SpiritSplinterItem))
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
                recipe = MalumSpiritAltarRecipes.getRecipe(inventory.getStackInSlot(slot));
            }
        };
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
    
    public int progress;
    public int spinUp;
    public float spin;
    public SimpleInventory inventory;
    public SimpleInventory spiritInventory;
    public MalumSpiritAltarRecipes.MalumSpiritAltarRecipe recipe;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (progress != 0)
        {
            compound.putInt("progress", progress);
        }
        if (spinUp != 0)
        {
            compound.putInt("spinUp", spinUp);
        }
        
        inventory.writeData(compound);
        spiritInventory.writeData(compound, "spiritInventory");
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        spinUp = compound.getInt("spinUp");
        inventory.readData(compound);
        spiritInventory.readData(compound, "spiritInventory");
        recipe = MalumSpiritAltarRecipes.getRecipe(inventory.getStackInSlot(0));
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (recipe != null)
        {
            if (spinUp < 10)
            {
                spinUp++;
            }
            ItemStack stack = inventory.getStackInSlot(0);
            progress++;
            if (progress >= 60)
            {
                Vector3d itemPos = itemPos(this);
                if (recipe.matches(spiritInventory.nonEmptyStacks()))
                {
                    for (MalumSpiritIngredient ingredient : recipe.spiritIngredients)
                    {
                        if (MalumHelper.areWeOnClient(world))
                        {
                            Color color = ingredient.type.color;
                            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.6f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color, color.darker()).randomOffset(0.1f).addVelocity(0,0.12f,0).randomVelocity(0.03f, 0.08f).enableGravity().repeat(world,itemPos.x,itemPos.y,itemPos.z, 20);
                            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(60).setScale(0.4f, 0).setColor(color, color.darker()).randomOffset(0.25f, 0.1f).randomVelocity(0.004f, 0.004f).enableNoClip().repeat(world, itemPos.x, itemPos.y, itemPos.z, 12);
                            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.05f, 0f).setLifetime(30).setScale(0.2f, 0).setColor(color, color.darker()).randomOffset(0.05f, 0.05f).randomVelocity(0.002f, 0.002f).enableNoClip().repeat(world, itemPos.x, itemPos.y, itemPos.z, 8);
                        }
                        for (int i = 0; i < spiritInventory.slotCount; i++)
                        {
                            ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                            if (ingredient.matches(spiritStack))
                            {
                                spiritStack.shrink(ingredient.count);
                                break;
                            }
                        }
                    }
                    stack.shrink(recipe.inputIngredient.count);
                    ItemEntity entity = new ItemEntity(world, itemPos.x,itemPos.y,itemPos.z, recipe.outputIngredient.outputItem());
                    world.addEntity(entity);
                    progress = 0;
                    recipe = MalumSpiritAltarRecipes.getRecipe(stack);
                    MalumHelper.updateState(world.getBlockState(pos), world, pos);
                    if (recipe != null && !recipe.matches(spiritInventory.nonEmptyStacks()))
                    {
                        inventory.dumpItems(world, itemPos);
                    }
                }
            }
        }
        else
        {
            progress = 0;
            if (spinUp > 0)
            {
                spinUp--;
            }
        }
        if (MalumHelper.areWeOnClient(world))
        {
            spin += 1+ spinUp / 5f;
            Vector3d itemPos = itemPos(this);
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
                    if (recipe != null)
                    {
                        Vector3d velocity = new Vector3d(x, y, z).subtract(itemPos).normalize().scale(-0.03f);
                        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f, 0f).setLifetime(40).setScale(0.1f, 0).randomOffset(0.02f).randomVelocity(0.01f, 0.01f).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).addVelocity(velocity.x, velocity.y, velocity.z).enableNoClip().repeat(world, x, y, z, 2);
                        float alpha = 0.075f - spiritInventory.slotCount * 0.01f;
                        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(alpha, 0f).setLifetime(20).setScale(0.4f, 0).randomOffset(0.1, 0.1).randomVelocity(0.02f, 0.02f).setColor(color, color.darker()).randomVelocity(0.0025f, 0.0025f).enableNoClip().repeat(world, itemPos.x,itemPos.y,itemPos.z,2);
                    }
                }
            }
        }
    }
    public static Vector3d itemPos(SpiritAltarTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(0.5f,1.15f,0.5f);
    }
    public static Vector3d itemOffset(SpiritAltarTileEntity tileEntity, int slot)
    {
        float distance = 1 - tileEntity.spinUp / 40f;
        float height = 0.75f + tileEntity.spinUp / 20f;
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f,height,0.5f), distance,slot, tileEntity.spiritInventory.nonEmptyItems(), (long)tileEntity.spin,360);
    }
}