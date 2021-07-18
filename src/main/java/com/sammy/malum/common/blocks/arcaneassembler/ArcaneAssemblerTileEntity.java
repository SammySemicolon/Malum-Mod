package com.sammy.malum.common.blocks.arcaneassembler;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.ActivatorRite;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumArcaneAssemblyRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.sammy.malum.common.blocks.arcaneassembler.ArcaneAssemblerTileEntity.StateEnum.*;
import static net.minecraft.util.math.MathHelper.nextFloat;

public class ArcaneAssemblerTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity, ActivatorRite.IAssembled
{
    public enum StateEnum
    {
        IDLE(0), REQUESTING(1), CRAFTING(2);
        int value;
        StateEnum(int value)
        {
            this.value = value;
        }
    }
    public StateEnum state = IDLE;

    public SimpleInventory extraInventory;
    public int progress;

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
        extraInventory = new SimpleInventory(8, 64, s-> recipe.isItemAllowed(s), s-> false)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ArcaneAssemblerTileEntity.this.markDirty();
                updateContainingBlockInfo();
                if (recipe.matches(inventory.getStackInSlot(0), stacks()))
                {
                    state = CRAFTING;
                }
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        state = StateEnum.values()[compound.getInt("state")];
        progress = compound.getInt("progress");
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
        compound.putInt("state", state.value);
        compound.putInt("progress", progress);
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
            if (recipe.extraIngredients.isEmpty())
            {
                state = CRAFTING;
                progress = 0;
            }
            else
            {
                state = REQUESTING;
                progress = 300;
            }
            this.spirits = spirits;
            MalumHelper.updateAndNotifyState(world, this.pos);
        }
    }
    public void setState(StateEnum state)
    {
        this.state = state;
        this.progress = state == REQUESTING ? 300 : 0;
    }
    @Override
    public void tick()
    {
        float particleIntensity = 0;
        if (state.equals(REQUESTING))
        {
            particleIntensity = 1;
            progress--;
            if (progress == 0)
            {
                state = IDLE;
            }
        }
        if (state.equals(CRAFTING))
        {
            particleIntensity = 1 + progress / 120f;
            progress++;
            if (progress >= 60)
            {
                if (MalumHelper.areWeOnServer(world))
                {
                    ArrayList<ItemStack> stacks = extraInventory.stacks();
                    for (int i = 0; i < recipe.extraIngredients.size(); i++)
                    {
                        stacks.get(i).shrink(1);
                    }
                    inventory.getStackInSlot(0).shrink(recipe.primeIngredient.count);
                    world.addEntity(new ItemEntity(world, getPos().getX() + 0.5f, getPos().getY() + 1.25f, getPos().getZ() + 0.5f, MalumHelper.copyWithNewCount(recipe.outputIngredient.getItem(), recipe.outputIngredient.count)));
                    if (!recipe.matches(inventory.getStackInSlot(0), stacks))
                    {
                        MalumHelper.updateAndNotifyState(world, pos);
                        state = IDLE;
                        progress = 0;
                        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), MalumSounds.RUNE_TABLE_CRAFT, SoundCategory.BLOCKS,1,1);
                    }
                    else
                    {
                        MalumHelper.updateState(world, pos);
                        progress -= 2;
                    }
                }
            }
        }
        if (MalumHelper.areWeOnClient(world))
        {
            if (particleIntensity > 0)
            {
                Vector3d pos = itemPos(this, inventory.getStackInSlot(0).getItem());
                for (int i = 0; i < spirits.size(); i++)
                {
                    MalumSpiritType type = spirits.get(i);
                    float alpha = (1f - i / (float)spirits.size()) * (particleIntensity/2f);
                    float scale = (1f + i * 0.25f) * particleIntensity;
                    particles(type.color, alpha, scale, pos.x, pos.y, pos.z);
                }
            }
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
            {
                SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
                Color color = item.type.color;
                Vector3d pos = itemPos(this, item);
                SpiritHelper.spiritParticles(world, pos.x, pos.y, pos.z, color);
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