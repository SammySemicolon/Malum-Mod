package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

public class MalumSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final SoundEvent TAINTED_ROCK_BREAK = new SoundEvent(MalumHelper.prefix("tainted_rock_break"));
    public static final SoundEvent TAINTED_ROCK_PLACE = new SoundEvent(MalumHelper.prefix("tainted_rock_break"));
    public static final SoundEvent TAINTED_ROCK_STEP = new SoundEvent(MalumHelper.prefix("tainted_rock_step"));
    public static final SoundEvent TAINTED_ROCK_HIT = new SoundEvent(MalumHelper.prefix("tainted_rock_hit"));
    
    public static final SoundEvent SOLAR_ORE_BREAK = new SoundEvent(MalumHelper.prefix("solar_ore_break"));
    public static final SoundEvent SOLAR_ORE_HIT = new SoundEvent(MalumHelper.prefix("solar_ore_hit"));
    public static final SoundEvent SOLAR_ORE_PLACE = new SoundEvent(MalumHelper.prefix("solar_ore_place"));
    
    public static final SoundEvent TRANSMISSIVE_ALLOY_BREAK = new SoundEvent(MalumHelper.prefix("transmissive_alloy_break"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_HIT = new SoundEvent(MalumHelper.prefix("transmissive_alloy_hit"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_PLACE = new SoundEvent(MalumHelper.prefix("transmissive_alloy_place"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_STEP = new SoundEvent(MalumHelper.prefix("transmissive_alloy_step"));
    
    public static final SoundEvent RUIN_PLATING_BREAK = new SoundEvent(MalumHelper.prefix("ruin_plating_break"));
    public static final SoundEvent RUIN_PLATING_HIT = new SoundEvent(MalumHelper.prefix("ruin_plating_hit"));
    public static final SoundEvent RUIN_PLATING_PLACE = new SoundEvent(MalumHelper.prefix("ruin_plating_place"));
    public static final SoundEvent RUIN_PLATING_STEP = new SoundEvent(MalumHelper.prefix("ruin_plating_step"));
    
    public static final SoundEvent ARCANE_CRAFT_START = new SoundEvent(MalumHelper.prefix("arcane_craft_start"));
    public static final SoundEvent ARCANE_CRAFT_FINISH = new SoundEvent(MalumHelper.prefix("arcane_craft_finish"));
    
    public static final SoundEvent ABSTRUSE_BLOCK_RETURN = new SoundEvent(MalumHelper.prefix("abstruse_block_return"));
    public static final SoundEvent RUIN_ARMOR_EQUIP = new SoundEvent(MalumHelper.prefix("ruin_armor_equip"));
    public static final SoundEvent TAINT_SPREAD = new SoundEvent(MalumHelper.prefix("taint_spread"));
    
    public static final SoundEvent TAINTED_FURNACE_CONSUME = new SoundEvent(MalumHelper.prefix("tainted_furnace_consume"));
    public static final SoundEvent TAINTED_FURNACE_FAIL = new SoundEvent(MalumHelper.prefix("tainted_furnace_fail"));
    public static final SoundEvent TAINTED_FURNACE_FINISH = new SoundEvent(MalumHelper.prefix("tainted_furnace_finish"));
    
    public static final SoundEvent KARMIC_HOLDER_ACTIVATE = new SoundEvent(MalumHelper.prefix("karmic_holder_activate"));
    
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType DARKENED_ROCK = new SoundType(1.0F, 0.75F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType TRANSMISSIVE_ALLOY = new SoundType(1.0F, 1.0F, TRANSMISSIVE_ALLOY_BREAK, TRANSMISSIVE_ALLOY_STEP, TRANSMISSIVE_ALLOY_PLACE, TRANSMISSIVE_ALLOY_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType RUIN_PLATING = new SoundType(1.0F, 1.0F, RUIN_PLATING_BREAK, RUIN_PLATING_STEP, RUIN_PLATING_PLACE, RUIN_PLATING_HIT, SoundEvents.BLOCK_STONE_FALL);

    public static void init()
    {
        SOUNDS.register("tainted_rock_break", ()-> TAINTED_ROCK_BREAK);
        SOUNDS.register("tainted_rock_place", ()-> TAINTED_ROCK_PLACE);
        SOUNDS.register("tainted_rock_step", ()-> TAINTED_ROCK_STEP);
        SOUNDS.register("tainted_rock_hit", ()-> TAINTED_ROCK_HIT);
        
        SOUNDS.register("solar_ore_break", ()-> SOLAR_ORE_BREAK);
        SOUNDS.register("solar_ore_place", ()-> SOLAR_ORE_PLACE);
        SOUNDS.register("solar_ore_hit", ()-> SOLAR_ORE_HIT);
    
        SOUNDS.register("transmissive_alloy_break", ()-> TRANSMISSIVE_ALLOY_BREAK);
        SOUNDS.register("transmissive_alloy_hit", ()-> TRANSMISSIVE_ALLOY_HIT);
        SOUNDS.register("transmissive_alloy_place", ()-> TRANSMISSIVE_ALLOY_PLACE);
        SOUNDS.register("transmissive_alloy_step", ()-> TRANSMISSIVE_ALLOY_STEP);
    
        SOUNDS.register("ruin_plating_break", ()-> RUIN_PLATING_BREAK);
        SOUNDS.register("ruin_plating_hit", ()-> RUIN_PLATING_HIT);
        SOUNDS.register("ruin_plating_place", ()-> RUIN_PLATING_PLACE);
        SOUNDS.register("ruin_plating_step", ()-> RUIN_PLATING_STEP);
        
        SOUNDS.register("arcane_craft_start", ()-> ARCANE_CRAFT_START);
        SOUNDS.register("arcane_craft_finish", ()-> ARCANE_CRAFT_FINISH);
        
        SOUNDS.register("abstruse_block_return", ()-> ABSTRUSE_BLOCK_RETURN);
        SOUNDS.register("ruin_armor_equip", ()-> RUIN_ARMOR_EQUIP);
        SOUNDS.register("taint_spread", ()-> TAINT_SPREAD);
    
        SOUNDS.register("tainted_furnace_consume", ()-> TAINTED_FURNACE_CONSUME);
        SOUNDS.register("tainted_furnace_fail", ()-> TAINTED_FURNACE_FAIL);
        SOUNDS.register("tainted_furnace_finish", ()-> TAINTED_FURNACE_FINISH);
    
        SOUNDS.register("karmic_holder_activate", ()-> KARMIC_HOLDER_ACTIVATE);
    }
}