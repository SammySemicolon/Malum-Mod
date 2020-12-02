package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

public class MalumSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final SoundEvent TAINTED_ROCK_BREAK = new SoundEvent(MalumHelper.prefix("tainted_rock_break"));
    public static final SoundEvent TAINTED_ROCK_PLACE = new SoundEvent(MalumHelper.prefix("tainted_rock_break"));
    public static final SoundEvent TAINTED_ROCK_STEP = new SoundEvent(MalumHelper.prefix("tainted_rock_step"));
    
    public static final SoundEvent CRIMSON_ROCK_BREAK = new SoundEvent(MalumHelper.prefix("crimson_rock_break"));
    public static final SoundEvent CRIMSON_ROCK_PLACE = new SoundEvent(MalumHelper.prefix("crimson_rock_break"));
    public static final SoundEvent CRIMSON_ROCK_STEP = new SoundEvent(MalumHelper.prefix("crimson_rock_step"));
    
    public static final SoundEvent SOLAR_ORE_BREAK = new SoundEvent(MalumHelper.prefix("solar_ore_break"));
    public static final SoundEvent SOLAR_ORE_HIT = new SoundEvent(MalumHelper.prefix("solar_ore_hit"));
    public static final SoundEvent SOLAR_ORE_PLACE = new SoundEvent(MalumHelper.prefix("solar_ore_place"));
    
    public static final SoundEvent TRANSMISSIVE_ALLOY_BREAK = new SoundEvent(MalumHelper.prefix("transmissive_alloy_break"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_HIT = new SoundEvent(MalumHelper.prefix("transmissive_alloy_hit"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_PLACE = new SoundEvent(MalumHelper.prefix("transmissive_alloy_place"));
    public static final SoundEvent TRANSMISSIVE_ALLOY_STEP = new SoundEvent(MalumHelper.prefix("transmissive_alloy_step"));
    
    public static final SoundEvent ZOOM_BLOCK_STEP = new SoundEvent(MalumHelper.prefix("zoom_rock_step"));
    
    public static final SoundEvent ARCANE_CRAFT_START = new SoundEvent(MalumHelper.prefix("arcane_craft_start"));
    public static final SoundEvent ARCANE_CRAFT_FINISH = new SoundEvent(MalumHelper.prefix("arcane_craft_finish"));
    
    public static final SoundEvent ABSTRUSE_BLOCK_RETURN = new SoundEvent(MalumHelper.prefix("abstruse_block_return"));
    
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType CRIMSON_ROCK = new SoundType(1.0F, 1.0F, CRIMSON_ROCK_BREAK, CRIMSON_ROCK_STEP, CRIMSON_ROCK_PLACE, SoundEvents.BLOCK_NETHER_BRICKS_HIT, SoundEvents.BLOCK_NETHER_BRICKS_FALL);
    public static final SoundType SOLAR_ORE = new SoundType(1.0F, 1.25F, SOLAR_ORE_BREAK, SoundEvents.BLOCK_STONE_STEP, SOLAR_ORE_PLACE, SOLAR_ORE_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType ZOOM_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, ZOOM_BLOCK_STEP, TAINTED_ROCK_PLACE, SoundEvents.BLOCK_NETHER_BRICKS_HIT, SoundEvents.BLOCK_NETHER_BRICKS_FALL);
    public static final SoundType TRANSMISSIVE_ALLOY = new SoundType(1.0F, 1.0F, TRANSMISSIVE_ALLOY_BREAK, TRANSMISSIVE_ALLOY_STEP, TRANSMISSIVE_ALLOY_PLACE, TRANSMISSIVE_ALLOY_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType CRIMSON_ZOOM_ROCK = new SoundType(1.0F, 1.0F, CRIMSON_ROCK_BREAK, CRIMSON_ROCK_STEP, CRIMSON_ROCK_PLACE, SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static void init()
    {
        SOUNDS.register("tainted_rock_break", ()-> TAINTED_ROCK_BREAK);
        SOUNDS.register("tainted_rock_place", ()-> TAINTED_ROCK_PLACE);
        SOUNDS.register("tainted_rock_step", ()-> TAINTED_ROCK_STEP);
        
        SOUNDS.register("crimson_rock_break", ()-> CRIMSON_ROCK_BREAK);
        SOUNDS.register("crimson_rock_place", ()-> CRIMSON_ROCK_PLACE);
        SOUNDS.register("crimson_rock_step", ()-> CRIMSON_ROCK_STEP);
    
        SOUNDS.register("solar_ore_break", ()-> SOLAR_ORE_BREAK);
        SOUNDS.register("solar_ore_place", ()-> SOLAR_ORE_PLACE);
        SOUNDS.register("solar_ore_hit", ()-> SOLAR_ORE_HIT);
    
        SOUNDS.register("transmissive_alloy_break", ()-> TRANSMISSIVE_ALLOY_BREAK);
        SOUNDS.register("transmissive_alloy_hit", ()-> TRANSMISSIVE_ALLOY_HIT);
        SOUNDS.register("transmissive_alloy_place", ()-> TRANSMISSIVE_ALLOY_PLACE);
        SOUNDS.register("transmissive_alloy_step", ()-> TRANSMISSIVE_ALLOY_STEP);
        
        SOUNDS.register("zoom_rock_step", ()-> ZOOM_BLOCK_STEP);
        SOUNDS.register("arcane_craft_start", ()-> ARCANE_CRAFT_START);
        SOUNDS.register("arcane_craft_finish", ()-> ARCANE_CRAFT_FINISH);
        
        SOUNDS.register("abstruse_block_return", ()-> ABSTRUSE_BLOCK_RETURN);
    }
}