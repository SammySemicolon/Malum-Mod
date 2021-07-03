package com.sammy.malum.common.blocks.runetable;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.runetable.bounding.RuneTableBoundingBlockTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.ActivatorRite;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumRuneTableRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.network.packets.particle.BurstParticlePacket;
import com.sammy.malum.network.packets.particle.SmallBurstParticlePacket;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class RuneTableTileEntity extends MultiblockTileEntity implements ITickableTileEntity, ActivatorRite.IAssembled
{
    public SimpleInventory inventory;

    public RuneTableTileEntity()
    {
        super(MalumTileEntities.RUNE_TABLE_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                RuneTableTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            return;
        }
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
        {
            SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
            Color color = item.type.color;
            Vector3d pos = itemPos(this);
            double x = pos.x;
            double y = pos.y + Math.sin((world.getGameTime() % 360) / 20f) * 0.05f;
            double z = pos.z;
            SpiritHelper.spiritParticles(world, x, y, z, color);
        }
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        super.readData(compound);
    }

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        return super.writeData(compound);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    public static Vector3d itemPos(SimpleTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(itemOffset());
    }

    public static Vector3d itemOffset()
    {
        return new Vector3d(0.5f, 1.25f, 0.5f);
    }

    @Override
    public void assemble(BlockPos pos, ArrayList<MalumSpiritType> spirits)
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (BlockPos part : parts)
        {
            if (world.getTileEntity(part) instanceof RuneTableBoundingBlockTileEntity)
            {
                RuneTableBoundingBlockTileEntity tileEntity = (RuneTableBoundingBlockTileEntity) world.getTileEntity(part);
                ItemStack stack = tileEntity.inventory.getStackInSlot(0);
                if (!stack.isEmpty())
                {
                    stacks.add(stack);
                }
            }
        }
        ItemStack centerStack = inventory.getStackInSlot(0);
        if (!centerStack.isEmpty())
        {
            stacks.add(1, centerStack);
        }
        MalumRuneTableRecipes.MalumRuneTableRecipe recipe = MalumRuneTableRecipes.getRecipe(stacks);
        if (recipe != null)
        {
            int outputCount = 0;
            do
            {
                for (int i = 0; i < stacks.size(); i++)
                {
                    ItemStack stack = stacks.get(i);
                    ItemIngredient ingredient = recipe.itemIngredients.get(i);
                    stack.shrink(ingredient.count);
                }
                outputCount += recipe.outputIngredient.count;
            }
            while (recipe.matches(stacks));
            do
            {
                int stackCount = Math.min(outputCount, 64);
                outputCount -= stackCount;
                ItemStack stack = recipe.outputIngredient.getItem();
                stack.setCount(stackCount);
                ItemEntity itemEntity = new ItemEntity(world, getPos().getX() + 0.5f, getPos().getY() + 1.25f, getPos().getZ() + 0.5f, stack);
                world.addEntity(itemEntity);
            }
            while(outputCount != 0);
            world.playSound(null, getPos(), MalumSounds.RUNE_TABLE_CRAFT, SoundCategory.BLOCKS, 1, 1);
            for (BlockPos part : parts)
            {
                MalumHelper.updateAndNotifyState(world, part);
                if (world.getTileEntity(part) instanceof RuneTableBoundingBlockTileEntity)
                {
                    RuneTableBoundingBlockTileEntity tileEntity = (RuneTableBoundingBlockTileEntity) world.getTileEntity(part);
                    ItemStack stack = tileEntity.inventory.getStackInSlot(0);
                    if (!stack.isEmpty())
                    {
                        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), SmallBurstParticlePacket.fromSpirits(part.getX() + 0.5f, part.getY() + 1.15f, part.getZ() + 0.5f, spirits));
                    }
                }
            }
            MalumHelper.updateAndNotifyState(world, getPos());
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), BurstParticlePacket.fromSpirits(getPos().getX() + 0.5f, getPos().getY() + 1.25f, getPos().getZ() + 0.5f, spirits));
        }
    }
}
