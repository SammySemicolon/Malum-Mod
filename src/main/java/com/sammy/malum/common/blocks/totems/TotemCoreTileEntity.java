package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

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
    public ArrayList<MalumRune> runes = new ArrayList<>();
    @Override
    public void tick()
    {
        if (rite != null)
        {
            rite.effect(pos, world);
            return;
        }
        if (active)
        {
            progress--;
            if (progress <= 0)
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
                            reset();
                            return;
                        }
                    }
                    TotemPoleBlock poleBlock = (TotemPoleBlock) poleState.getBlock();
                    world.setBlockState(polePos, poleState.with(BlockStateProperties.POWERED, true));
                    addRune(poleBlock.rune);
                }
                else
                {
                    tryRite();
                }
            }
        }
    }
    public void addRune(MalumRune rune)
    {
        runes.add(rune);
        progress = 20;
    }
    public void tryRite()
    {
        MalumRite rite = MalumRites.getRite(runes);
        if (rite != null)
        {
            if (rite.isInstant)
            {
                rite.effect(pos,world);
                reset();
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
        reset();
    }
    public void reset()
    {
        for (int i = 0; i < height; i++)
        {
            BlockPos pos = getPos().up(i);
            if (world.getBlockState(pos).getBlock() instanceof TotemPoleBlock)
            {
                world.setBlockState(pos, MalumBlocks.SUN_KISSED_LOG.get().getDefaultState());
            }
        }
        active = false;
        progress = 0;
        height = 0;
        rite = null;
        runes = new ArrayList<>();
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putInt("progress", progress);
        compound.putInt("height", height);
        
        int i = 0;
        for (MalumRune rune : runes)
        {
            compound.putString("rune" + i, rune.id);
            i++;
        }
        compound.putInt("runeCount", i);
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        progress = compound.getInt("progress");
        height = compound.getInt("height");
        for (int i = 0; i < compound.getInt("runeCount"); i ++)
        {
            MalumRune rune = MalumRunes.getRune(compound.getString("rune" + i));
            runes.add(rune);
        }
        rite = MalumRites.getRite(runes);
        super.readData(compound);
    }
}
