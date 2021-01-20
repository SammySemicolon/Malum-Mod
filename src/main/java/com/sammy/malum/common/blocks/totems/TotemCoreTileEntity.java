package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.totems.rites.IPoppetBlessing;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.POWERED;

public class TotemCoreTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemCoreTileEntity()
    {
        super(MalumTileEntities.TOTEM_CORE_TILE_ENTITY.get());
    }
    
    public boolean active;
    public int progress;
    public int height;
    public MalumRite rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    
    
    @Override
    public void tick()
    {
        if (rite != null)
        {
            if (MalumHelper.areWeOnClient(world))
            {
                prolongedRiteEffects();
            }
            else
            {
                MalumHelper.updateState(getBlockState(),world, pos);
            }
            if (rite.isInstant)
            {
                reset(false); //This shouldn't ever happen, but might aswell check
                return;
            }
            if (rite.cooldown() == 0 || world.getGameTime() % rite.cooldown() == 0)
            {
                rite.effect(pos, world);
            }
            else
            {
                progress--;
            }
            for (int i =1; i < height; i++)
            {
                if (!(world.getBlockState(pos.up(i)).getBlock() instanceof TotemPoleBlock))
                {
                    fail();
                    return;
                }
            }
            return;
        }
        if (active)
        {
            progress++;
            if (progress > 20)
            {
                BlockPos previousPolePos = pos.up(height);
                BlockState previousPoleState = world.getBlockState(previousPolePos);
                height++;
                
                BlockPos polePos = pos.up(height);
                BlockState poleState = world.getBlockState(polePos);
    
                if (poleState.getBlock() instanceof TotemPoleBlock)
                {
                    if (previousPoleState.getBlock() instanceof TotemPoleBlock)
                    {
                        if (!poleState.get(HORIZONTAL_FACING).equals(previousPoleState.get(HORIZONTAL_FACING)))
                        {
                            fail();
                            return;
                        }
                    }
                    if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
                    {
                        TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
                        if (totemPoleTileEntity.type != null)
                        {
                            world.setBlockState(polePos, poleState.with(BlockStateProperties.POWERED, true));
                            addRune(polePos, totemPoleTileEntity.type);
                        }
                        else
                        {
                            fail();
                        }
                    }
                }
                else
                {
                    tryRite();
                }
            }
        }
    }
    
    public void prolongedRiteEffects()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = MalumConstants.faded();
            for (int i = 0; i < height; i++)
            {
                BlockPos currentPolePos = getPos().up(i);
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, currentPolePos, 2);
            }
        }
    }
    public void riteEffects()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = MalumConstants.bright();
    
            for (int i = 0; i < height; i++)
            {
                BlockPos currentPolePos = getPos().up(i);
                if (world.getBlockState(currentPolePos).getBlock() instanceof TotemPoleBlock)
                {
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, currentPolePos, 80);
                }
            }
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 80);
        }
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.5f + world.rand.nextFloat() * 0.25f);
    }
    
    
    public void failureEffects(BlockPos pos)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = MalumConstants.darkest();
            for (int i = 0; i < 12; i++)
            {
                ArrayList<Vector3d> particlePositionsUp = MalumHelper.blockOutlinePositions(world, pos);
                particlePositionsUp.forEach(p -> world.addParticle(ParticleTypes.SMOKE, p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
            }
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 80);
        }
    }
    
    public void addRuneEffects(BlockPos pos)
    {
        if (MalumHelper.areWeOnClient(world))
        {
            Color color = MalumConstants.bright();
            ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setScale(0.05f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 80);
        }
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.75f + height * 0.25f);
    }
    
    public void addRune(BlockPos pos, MalumSpiritType rune)
    {
        addRuneEffects(pos);
    
        spirits.add(rune);
        progress = 0;
    }
    
    public void tryRite()
    {
        MalumRite rite = MalumRites.getRite(spirits);
        if (rite != null)
        {
            progress = 0;
            riteEffects();
            if (rite instanceof IPoppetBlessing)
            {
                ((IPoppetBlessing) rite).blessPoppet(pos,world,rite);
            }
            if (rite.isInstant)
            {
                rite.effect(pos, world);
                reset(false);
                
                return;
            }
            this.rite = rite;
        }
        else
        {
            fail();
        }
    }
    
    public void fail()
    {
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1, 0.5f);
        reset(true);
    }
    
    public void reset(boolean isFailure)
    {
        for (int i = 0; i < height; i++)
        {
            BlockPos currentPolePos = getPos().up(i);
            if (world.getBlockState(currentPolePos).getBlock() instanceof TotemPoleBlock)
            {
                if (isFailure)
                {
                    failureEffects(currentPolePos);
                }
                world.setBlockState(currentPolePos, MalumBlocks.SUN_KISSED_LOG.get().getDefaultState());
            }
        }
        if (isFailure)
        {
            failureEffects(pos);
        }
        world.setBlockState(pos,getBlockState().with(POWERED, false));
        active = false;
        progress = 0;
        height = 0;
        rite = null;
        spirits = new ArrayList<>();
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        
        int i = 0;
        for (MalumSpiritType spiritType : spirits)
        {
            compound.putString("spiritType" + i, spiritType.identifier);
            i++;
        }
        compound.putInt("spiritCount", i);
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        spirits = new ArrayList<>();
        for (int i = 0; i < compound.getInt("spiritCount"); i++)
        {
            MalumSpiritType spiritType = SpiritHelper.figureOutType(compound.getString("spiritType" + i));
            spirits.add(spiritType);
        }
        rite = MalumRites.getRite(spirits);
        super.readData(compound);
    }
}