package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.totems.rites.IPoppetBlessing;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;
import java.util.ArrayList;

import static net.minecraft.particles.ParticleTypes.SMOKE;

public class TotemCoreTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemCoreTileEntity()
    {
        super(MalumTileEntities.TOTEM_CORE_TILE_ENTITY.get());
    }
    
    public int state;
    public int cooldown;
    public int height;
    public MalumRite rite;
    public ArrayList<MalumSpiritType> spirits = new ArrayList<>();
    
    @Override
    public void remove()
    {
        reset();
        super.remove();
    }
    
    @Override
    public void tick()
    {
        if (state == 1)
        {
            if (cooldown == 0)
            {
                BlockPos previousPolePos = pos.up(height);
                height++;
                BlockPos polePos = pos.up(height);
            
                if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
                {
                    TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
                    
                    if (world.getTileEntity(previousPolePos) instanceof TotemPoleTileEntity)
                    {
                        TotemPoleTileEntity previousTotemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(previousPolePos);
                        if (!previousTotemPoleTileEntity.direction.equals(totemPoleTileEntity.direction))
                        {
                            primeForReset();
                            return;
                        }
                    }
                    
                    if (totemPoleTileEntity.type != null)
                    {
                        totemPoleTileEntity.scan(pos);
                        MalumHelper.updateState(world, polePos);
                        addRune(totemPoleTileEntity, totemPoleTileEntity.type);
                    }
                }
                else
                {
                    tryRite();
                }
            }
            else
            {
                cooldown--;
            }
        }
        if (state == 2)
        {
            if (rite != null)
            {
                if (rite.cooldown() == 0 || world.getGameTime() % rite.cooldown() == 0)
                {
                    rite.effect(pos, world);
                }
            }
        }
        if (state == 3)
        {
            if (cooldown < 20)
            {
                cooldown++;
                if (cooldown == 20)
                {
                    reset();
                }
            }
        }
    }
    public void addRune(TotemPoleTileEntity totemPoleTileEntity, MalumSpiritType rune)
    {
        world.playSound(null, totemPoleTileEntity.getPos(), MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.75f + height * 0.25f);
        spirits.add(rune);
        cooldown = 20;
        Color color = totemPoleTileEntity.type.color;
        ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.75f, 0f).setLifetime(30).setScale(0.125f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.02f).enableNoClip().repeatEdges(world, totemPoleTileEntity.getPos(), 40);
    
    }
    
    public void tryRite()
    {
        MalumRite rite = MalumRites.getRite(spirits);
        if (rite != null)
        {
            world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 1.5f + world.rand.nextFloat() * 0.5f);
            for (int i = 0; i < height; i++)
            {
                BlockPos currentPolePos = getPos().up(i);
                if (world.getTileEntity(currentPolePos) instanceof TotemPoleTileEntity)
                {
                    TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(currentPolePos);
                    totemPoleTileEntity.activate();
                    Color color = totemPoleTileEntity.type.color;
                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.75f, 0f).setLifetime(40).setScale(0.125f, 0).setColor(color, color.darker()).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, currentPolePos, 20);
    
                }
            }
            if (rite instanceof IPoppetBlessing)
            {
                ((IPoppetBlessing) rite).blessPoppet(pos, world, rite);
            }
            if (rite.isInstant)
            {
                rite.effect(pos, world);
                primeForReset();
                return;
            }
            setState(2);
            this.rite = rite;
        }
        else
        {
            primeForReset();
        }
        MalumHelper.updateState(world, pos);
    }
    
    public void primeForReset()
    {
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.75f + height * 0.25f);
        for (int i = 1; i < height; i++)
        {
            BlockPos polePos = pos.up(i);
            if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(polePos);
                totemPoleTileEntity.deactivate();
            }
        }
        setState(3);
    }
    public void reset()
    {
        for (int i = 1; i <= height; i++)
        {
            BlockPos polePos = pos.up(i);
            if (world.getTileEntity(polePos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) world.getTileEntity(pos.up(i));
                tileEntity.reset();
                
                Color color = tileEntity.type.color;
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.75f, 0f).setLifetime(40).setScale(0.125f, 0).setColor(color.darker(), color.darker().darker()).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, polePos, 20);
                for (int j = 0; j < 5; j++)
                {
                    ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, polePos);
                    particlePositions.forEach(p -> world.addParticle(SMOKE, p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
                }
            }
        }
        if (height > 0)
        {
            world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.5f);
        }
        setState(0);
    }
    public void setState(int state)
    {
        this.state = state;
        if (state == 0)
        {
            height = 0;
            rite = null;
            spirits = new ArrayList<>();
        }
        else if (state != 3)
        {
            cooldown = 20;
        }
        cooldown = 0;
    }
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (cooldown != 0)
        {
            compound.putInt("cooldown", cooldown);
        }
        if (height != 0)
        {
            compound.putInt("height", height);
        }
        if (!spirits.isEmpty())
        {
            int i = 0;
            for (MalumSpiritType spiritType : spirits)
            {
                compound.putString("spiritType" + i, spiritType.identifier);
                i++;
            }
            compound.putInt("spiritCount", i);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        cooldown = compound.getInt("cooldown");
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