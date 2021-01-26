package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.extractionfocus.ExtractionFocusBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes.MalumSpiritKilnRecipe;
import com.sammy.malum.core.systems.fuel.IFuelTileEntity;
import com.sammy.malum.core.systems.fuel.SimpleFuelSystem;
import com.sammy.malum.core.systems.multiblock.MultiblockTileEntity;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.network.packets.ParticlePacket;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.fml.network.PacketDistributor;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreBlock.STATE;
import static com.sammy.malum.network.NetworkManager.INSTANCE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

@SuppressWarnings("ConstantConditions")
public class SpiritKilnCoreTileEntity extends MultiblockTileEntity implements ITickableTileEntity, IFuelTileEntity
{
    public SpiritKilnCoreTileEntity()
    {
        super(MalumTileEntities.SPIRIT_KILN_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        sideInventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritKilnCoreTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
        powerStorage = new SimpleFuelSystem(this, 64);
    }
    
    @Override
    public SimpleFuelSystem getSystem()
    {
        return powerStorage;
    }
    
    public boolean isPowered()
    {
        return getBlockState().get(STATE) == 2;
    }
    
    public SimpleInventory inventory;
    public SimpleInventory sideInventory;
    public SimpleFuelSystem powerStorage;
    public BlockPos extractionFocus;
    
    public int state;
    public MalumSpiritKilnRecipe currentRecipe;
    public float progress;
    
    @Override
    public void readData(CompoundNBT compound)
    {
        inventory.readData(compound);
        powerStorage.readData(compound);
        extractionFocus = NBTUtil.readBlockPos(compound);
        progress = compound.getFloat("progress");
        
        super.readData(compound);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        inventory.writeData(compound);
        powerStorage.writeData(compound);
        if (extractionFocus != null)
        {
            NBTUtil.writeBlockPos(extractionFocus);
        }
        compound.putFloat("progress", progress);
        return super.writeData(compound);
    }
    
    @Override
    public void tick()
    {
        ItemStack stack = inventory.getStackInSlot(0);
        state = getBlockState().get(STATE);
        if (MalumHelper.areWeOnServer(world))
        {
            if (state != 1)
            {
                maintainState();
            }
            if (currentRecipe != null)
            {
                if (powerStorage.fuel > 0)
                {
                    passiveSound();
                }
            }
            else
            {
                if (progress > 0)
                {
                    progress = 0;
                }
            }
        }
    }
    
    public void passiveSound()
    {
        if (world.rand.nextFloat() < 0.05f)
        {
            world.playSound(null,pos, SoundEvents.BLOCK_BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }
    //endregion
    
    //region state helpers
    public void maintainState()
    {
        if (powerStorage.fuel > 0 && currentRecipe != null)
        {
            if (!isPowered())
            {
                setAndUpdateState(2);
            }
        }
        else
        {
            if (isPowered())
            {
                setAndUpdateState(0);
            }
        }
    }
    
    public void setAndUpdateState(int value)
    {
        world.setBlockState(pos, world.getBlockState(pos).with(STATE, value));
        world.setBlockState(pos.up(), world.getBlockState(pos.up()).with(STATE, value));
        
        MalumHelper.updateState(world.getBlockState(pos), world, pos);
        MalumHelper.updateState(world.getBlockState(pos.up()), world, pos.up());
    }
    //endregion
    
    //region vector helpers
    public Vector3d smokeParticleOutputPos()
    {
        Direction direction = getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset() * 0.4f, 0, direction.getZOffset() * 0.4f);
        Vector3d particlePos = MalumHelper.randPos(pos, world.rand, -0.3f, 0.3f);
        Vector3d finalPos = new Vector3d(0, 0, 0);
        if (directionVector.getZ() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f, particlePos.getY() + 2, particlePos.getZ() + 0.5f - directionVector.getZ());
        }
        if (directionVector.getX() != 0)
        {
            finalPos = new Vector3d(particlePos.getX() + 0.5f - directionVector.getX(), particlePos.getY() + 2, particlePos.getZ() + 0.5f);
        }
        return finalPos;
    }
    
    public void repairKiln()
    {
        if (MalumHelper.areWeOnServer(world))
        {
            world.playSound(null, this.pos, MalumSounds.SPIRIT_KILN_REPAIR, SoundCategory.BLOCKS, 0.4f, 0.9f + world.rand.nextFloat() * 0.2f);
            setAndUpdateState(0);
        }
        else
        {
            for (int i = 0; i < 3; i++)
            {
                ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
                ArrayList<Vector3d> particlePositionsUp = MalumHelper.blockOutlinePositions(world, pos.up());
                particlePositionsUp.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, MalumBlocks.TAINTED_ROCK.get().getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
            }
        }
    }
    
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(HORIZONTAL_FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 1.25, direction.getZOffset());
        return new Vector3d(0.5 + directionVector.getX() * 0.25, directionVector.getY(), 0.5 + directionVector.getZ() * 0.25);
    }
}