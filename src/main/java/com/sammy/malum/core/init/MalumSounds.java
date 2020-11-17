package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

public class MalumSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final SoundEvent TAINTED_ROCK_BREAK = new SoundEvent(MalumHelper.prefix("tainted_rock_break"));
    public static final SoundEvent TAINTED_ROCK_STEP = new SoundEvent(MalumHelper.prefix("tainted_rock_step"));
    public static final SoundEvent TAINTED_ROCK_PLACE = new SoundEvent(MalumHelper.prefix("tainted_rock_place"));
    public static final SoundEvent ZOOM_BLOCK_STEP = new SoundEvent(MalumHelper.prefix("zoom_rock_step"));
    public static final SoundEvent ARCANE_CRAFT = new SoundEvent(MalumHelper.prefix("arcane_craft"));
    
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType ZOOM_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, ZOOM_BLOCK_STEP, TAINTED_ROCK_PLACE, SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static void init()
    {
        SOUNDS.register("tainted_rock_break", ()-> TAINTED_ROCK_BREAK);
        SOUNDS.register("tainted_rock_step", ()-> TAINTED_ROCK_STEP);
        SOUNDS.register("tainted_rock_place", ()-> TAINTED_ROCK_PLACE);
        SOUNDS.register("zoom_rock_step", ()-> ZOOM_BLOCK_STEP);
        SOUNDS.register("arcane_craft", ()-> ARCANE_CRAFT);
    }
}