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
    
    public static final SoundEvent HALLOWED_GOLD_BREAK = new SoundEvent(MalumHelper.prefix("hallowed_gold_break"));
    public static final SoundEvent HALLOWED_GOLD_HIT = new SoundEvent(MalumHelper.prefix("hallowed_gold_hit"));
    public static final SoundEvent HALLOWED_GOLD_PLACE = new SoundEvent(MalumHelper.prefix("hallowed_gold_place"));
    public static final SoundEvent HALLOWED_GOLD_STEP = new SoundEvent(MalumHelper.prefix("hallowed_gold_step"));
    
    public static final SoundEvent SPIRITED_METAL_BREAK = new SoundEvent(MalumHelper.prefix("spirited_metal_break"));
    public static final SoundEvent SPIRITED_METAL_HIT = new SoundEvent(MalumHelper.prefix("spirited_metal_hit"));
    public static final SoundEvent SPIRITED_METAL_PLACE = new SoundEvent(MalumHelper.prefix("spirited_metal_place"));
    public static final SoundEvent SPIRITED_METAL_STEP = new SoundEvent(MalumHelper.prefix("spirited_metal_step"));
    
    public static final SoundEvent ABSTRUSE_BLOCK_RETURN = new SoundEvent(MalumHelper.prefix("abstruse_block_return"));
    public static final SoundEvent SCYTHE_STRIKE = new SoundEvent(MalumHelper.prefix("scythe_strike"));
    public static final SoundEvent SPIRIT_HARVEST = new SoundEvent(MalumHelper.prefix("spirit_harvest"));
    
    public static final SoundEvent TOTEM_CHARGE = new SoundEvent(MalumHelper.prefix("totem_charge"));
    public static final SoundEvent TOTEM_CHARGED = new SoundEvent(MalumHelper.prefix("totem_charged"));
    public static final SoundEvent TOTEM_ENGRAVE = new SoundEvent(MalumHelper.prefix("totem_engrave"));
    
    public static final SoundEvent ALTAR_CRAFT = new SoundEvent(MalumHelper.prefix("altar_craft"));
    public static final SoundEvent ALTAR_LOOP = new SoundEvent(MalumHelper.prefix("altar_loop"));
    
    public static final SoundEvent BOOK_TRAVEL = new SoundEvent(MalumHelper.prefix("book_travel"));
    
    public static final SoundEvent SINISTER_EQUIP = new SoundEvent(MalumHelper.prefix("sinister_equip"));
    public static final SoundEvent HOLY_EQUIP = new SoundEvent(MalumHelper.prefix("holy_equip"));
    public static final SoundEvent TYRVING_HIT = new SoundEvent(MalumHelper.prefix("tyrving_hit"));
    
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType TWISTED_ROCK = new SoundType(1.0F, 0.75F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType HALLOWED_GOLD = new SoundType(1.0F, 1.0F, HALLOWED_GOLD_BREAK, HALLOWED_GOLD_STEP, HALLOWED_GOLD_PLACE, HALLOWED_GOLD_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType SOUL_STAINED_STEEL = new SoundType(1.0F, 1.0F, SPIRITED_METAL_BREAK, SPIRITED_METAL_STEP, SPIRITED_METAL_PLACE, SPIRITED_METAL_HIT, SoundEvents.BLOCK_STONE_FALL);
    
    public static void init()
    {
        SOUNDS.register("tainted_rock_break", ()-> TAINTED_ROCK_BREAK);
        SOUNDS.register("tainted_rock_place", ()-> TAINTED_ROCK_PLACE);
        SOUNDS.register("tainted_rock_step", ()-> TAINTED_ROCK_STEP);
        SOUNDS.register("tainted_rock_hit", ()-> TAINTED_ROCK_HIT);
        
        SOUNDS.register("hallowed_gold_break", ()-> HALLOWED_GOLD_BREAK);
        SOUNDS.register("hallowed_gold_hit", ()-> HALLOWED_GOLD_HIT);
        SOUNDS.register("hallowed_gold_place", ()-> HALLOWED_GOLD_PLACE);
        SOUNDS.register("hallowed_gold_step", ()-> HALLOWED_GOLD_STEP);
    
        SOUNDS.register("spirited_metal_break", ()-> SPIRITED_METAL_BREAK);
        SOUNDS.register("spirited_metal_hit", ()-> SPIRITED_METAL_HIT);
        SOUNDS.register("spirited_metal_place", ()-> SPIRITED_METAL_PLACE);
        SOUNDS.register("spirited_metal_step", ()-> SPIRITED_METAL_STEP);
        
        SOUNDS.register("abstruse_block_return", ()-> ABSTRUSE_BLOCK_RETURN);
        SOUNDS.register("scythe_strike", ()-> SCYTHE_STRIKE);
        SOUNDS.register("spirit_harvest", ()-> SPIRIT_HARVEST);
        
        SOUNDS.register("totem_charge", ()-> TOTEM_CHARGE);
        SOUNDS.register("totem_charged", ()-> TOTEM_CHARGED);
        SOUNDS.register("totem_engrave", ()-> TOTEM_ENGRAVE);
        
        SOUNDS.register("altar_craft", ()-> ALTAR_CRAFT);
        SOUNDS.register("altar_loop", ()-> ALTAR_LOOP);
    
        SOUNDS.register("book_travel", ()-> BOOK_TRAVEL);
    
        SOUNDS.register("sinister_equip", ()-> SINISTER_EQUIP);
        SOUNDS.register("holy_equip", ()-> HOLY_EQUIP);
        SOUNDS.register("tyrving_hit", ()-> TYRVING_HIT);
    }
}