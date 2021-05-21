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

    public static final SoundEvent SOULSTONE_BREAK = register(new SoundEvent(MalumHelper.prefix("soulstone_break")));
    public static final SoundEvent SOULSTONE_PLACE = register(new SoundEvent(MalumHelper.prefix("soulstone_place")));
    public static final SoundEvent SOULSTONE_STEP = register(new SoundEvent(MalumHelper.prefix("soulstone_step")));
    public static final SoundEvent SOULSTONE_HIT = register(new SoundEvent(MalumHelper.prefix("soulstone_hit")));

    public static final SoundEvent BLAZING_QUARTZ_BREAK = register(new SoundEvent(MalumHelper.prefix("blazing_quartz_break")));
    public static final SoundEvent BLAZING_QUARTZ_PLACE = register(new SoundEvent(MalumHelper.prefix("blazing_quartz_place")));

    public static final SoundEvent TAINTED_ROCK_BREAK = register(new SoundEvent(MalumHelper.prefix("tainted_rock_break")));
    public static final SoundEvent TAINTED_ROCK_PLACE = register(new SoundEvent(MalumHelper.prefix("tainted_rock_place")));
    public static final SoundEvent TAINTED_ROCK_STEP = register(new SoundEvent(MalumHelper.prefix("tainted_rock_step")));
    public static final SoundEvent TAINTED_ROCK_HIT = register(new SoundEvent(MalumHelper.prefix("tainted_rock_hit")));

    public static final SoundEvent HALLOWED_GOLD_BREAK = register(new SoundEvent(MalumHelper.prefix("hallowed_gold_break")));
    public static final SoundEvent HALLOWED_GOLD_HIT = register(new SoundEvent(MalumHelper.prefix("hallowed_gold_hit")));
    public static final SoundEvent HALLOWED_GOLD_PLACE = register(new SoundEvent(MalumHelper.prefix("hallowed_gold_place")));
    public static final SoundEvent HALLOWED_GOLD_STEP = register(new SoundEvent(MalumHelper.prefix("hallowed_gold_step")));

    public static final SoundEvent SOUL_STAINED_STEEL_BREAK = register(new SoundEvent(MalumHelper.prefix("soul_stained_steel_break")));
    public static final SoundEvent SOUL_STAINED_STEEL_HIT = register(new SoundEvent(MalumHelper.prefix("soul_stained_steel_hit")));
    public static final SoundEvent SOUL_STAINED_STEEL_PLACE = register(new SoundEvent(MalumHelper.prefix("soul_stained_steel_place")));
    public static final SoundEvent SOUL_STAINED_STEEL_STEP = register(new SoundEvent(MalumHelper.prefix("soul_stained_steel_step")));

    public static final SoundEvent ETHER_PLACE = register(new SoundEvent(MalumHelper.prefix("ether_place")));
    public static final SoundEvent ETHER_BREAK = register(new SoundEvent(MalumHelper.prefix("ether_break")));

    public static final SoundEvent ABSTRUSE_BLOCK_RETURNS = register(new SoundEvent(MalumHelper.prefix("abstruse_block_returns")));
    public static final SoundEvent SCYTHE_STRIKE = register(new SoundEvent(MalumHelper.prefix("scythe_strike")));
    public static final SoundEvent SPIRIT_HARVEST = register(new SoundEvent(MalumHelper.prefix("spirit_harvest")));

    public static final SoundEvent TOTEM_CHARGE = register(new SoundEvent(MalumHelper.prefix("totem_charge")));
    public static final SoundEvent TOTEM_CHARGED = register(new SoundEvent(MalumHelper.prefix("totem_charged")));
    public static final SoundEvent TOTEM_ENGRAVE = register(new SoundEvent(MalumHelper.prefix("totem_engrave")));

    public static final SoundEvent ALTAR_CRAFT = register(new SoundEvent(MalumHelper.prefix("altar_craft")));
    public static final SoundEvent ALTAR_LOOP = register(new SoundEvent(MalumHelper.prefix("altar_loop")));
    public static final SoundEvent ALTAR_CONSUME = register(new SoundEvent(MalumHelper.prefix("altar_consume")));
    public static final SoundEvent ALTAR_SPEED_UP = register(new SoundEvent(MalumHelper.prefix("altar_speed_up")));

    public static final SoundEvent BOOK_TRAVEL = register(new SoundEvent(MalumHelper.prefix("book_travel")));

    public static final SoundEvent SINISTER_EQUIP = register(new SoundEvent(MalumHelper.prefix("sinister_equip")));
    public static final SoundEvent HOLY_EQUIP = register(new SoundEvent(MalumHelper.prefix("holy_equip")));
    public static final SoundEvent TYRVING_CRUSH = register(new SoundEvent(MalumHelper.prefix("tyrving_crush")));

    public static final SoundType SOULSTONE = new SoundType(1.0F, 1.0F, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, SOULSTONE_HIT, SoundEvents.BLOCK_STONE_FALL);
    public static final SoundType BLAZING_QUARTZ = new SoundType(1.0F, 1.0F, BLAZING_QUARTZ_BREAK, SoundEvents.BLOCK_NETHER_ORE_STEP, BLAZING_QUARTZ_PLACE, SoundEvents.BLOCK_NETHER_ORE_PLACE, SoundEvents.BLOCK_NETHER_GOLD_ORE_FALL);
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType TWISTED_ROCK = new SoundType(1.0F, 0.75F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType HALLOWED_GOLD = new SoundType(1.0F, 1.0F, HALLOWED_GOLD_BREAK, HALLOWED_GOLD_STEP, HALLOWED_GOLD_PLACE, HALLOWED_GOLD_HIT, SoundEvents.BLOCK_METAL_FALL);
    public static final SoundType SOUL_STAINED_STEEL = new SoundType(1.0F, 1.0F, SOUL_STAINED_STEEL_BREAK, SOUL_STAINED_STEEL_STEP, SOUL_STAINED_STEEL_PLACE, SOUL_STAINED_STEEL_HIT, SoundEvents.BLOCK_METAL_FALL);
    public static final SoundType ETHER = new SoundType(1.0F, 1.0F, ETHER_BREAK, SoundEvents.BLOCK_WOOL_STEP, ETHER_PLACE, SoundEvents.BLOCK_ANCIENT_DEBRIS_HIT, SoundEvents.BLOCK_WOOL_FALL);

    public static SoundEvent register(SoundEvent soundEvent)
    {
        SOUNDS.register(soundEvent.name.getPath(), ()->soundEvent);
        return soundEvent;
    }
}