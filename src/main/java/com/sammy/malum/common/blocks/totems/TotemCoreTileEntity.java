package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
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
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;

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
                if (MalumHelper.areWeOnClient(world))
                {
                    int size = rite.spirits.size();
                    for (int i = 0; i < size; i++)
                    {
                        MalumSpiritType type = rite.spirits.get(i);
                        Color color = type.color;
                        TotemPoleTileEntity.passiveParticles(pos, world, color);
                    }
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
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.65f + 0.15f * height);
    
        spirits.add(rune);
        cooldown = 20;
        totemPoleTileEntity.scan(pos);
    }
    
    public void tryRite()
    {
        MalumRite rite = MalumRites.getRite(spirits);
        if (rite != null)
        {
            world.playSound(null, pos, MalumSounds.TOTEM_CHARGED, SoundCategory.BLOCKS, 1, 0.75f + 0.15f * (height-1));
            for (int i = 0; i < height; i++)
            {
                BlockPos currentPolePos = getPos().up(i);
                if (world.getTileEntity(currentPolePos) instanceof TotemPoleTileEntity)
                {
                    TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(currentPolePos);
                    totemPoleTileEntity.activate();
                }
            }
            if (rite instanceof IPoppetBlessing)
            {
                world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos).grow(3)).forEach(e -> {
                    if (e.getItem().getItem().equals(MalumItems.POPPET.get()))
                    {
                        ItemStack stack = new ItemStack(MalumItems.BLESSED_POPPET.get());
                        stack.getOrCreateTag().putString("blessing", rite.identifier);
                        if (MalumHelper.areWeOnClient(world))
                        {
                            float alphaMultiplier = 1 - height * 0.1f;
                            for (int i = 0; i < height; i++)
                            {
                                BlockPos currentPolePos = getPos().up(i);
                                if (world.getTileEntity(currentPolePos) instanceof TotemPoleTileEntity)
                                {
                                    TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) world.getTileEntity(currentPolePos);
                                    Color color = totemPoleTileEntity.type.color;
                                    world.playSound(null, e.getPosition(), SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR, SoundCategory.BLOCKS, 1, 1.5f);
                                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.5f*alphaMultiplier, 0f).setLifetime(40).setScale(0.01f, 0).randomOffset(0.2f).setColor(color, color.darker()).randomVelocity(0.02f, 0.04f).enableNoClip().repeat(world, e.getPosX(), e.getPosY()+0.25f, e.getPosZ(), 10);
                                    ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(0.1f*alphaMultiplier, 0f).setLifetime(80).setScale(0.5f, 0).randomOffset(0.1f).setColor(color, color.darker()).randomVelocity(0.01f, 0.01f).enableNoClip().repeat(world, e.getPosX(), e.getPosY()+0.25f, e.getPosZ(), 20);
                                }
                            }
                        }
                        world.addEntity(new ItemEntity(world, e.getPosX(), e.getPosY(), e.getPosZ(), stack));
                        e.remove();
            
                    }
                });
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
        world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.5f + 0.125f * (height-1));
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
            }
        }
        if (height > 0)
        {
            world.playSound(null, pos, MalumSounds.TOTEM_CHARGE, SoundCategory.BLOCKS, 1, 0.6f);
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
    public void circleParticles()
    {
    
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
        if (state != 0)
        {
            compound.putInt("state", state);
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
        state = compound.getInt("state");
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