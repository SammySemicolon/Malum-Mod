package com.sammy.malum.core.registry.misc;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

public class SoundRegistry
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final SoundEvent SOULSTONE_BREAK = create(new SoundEvent(DataHelper.prefix("soulstone_break")));
    public static final SoundEvent SOULSTONE_PLACE = create(new SoundEvent(DataHelper.prefix("soulstone_place")));
    public static final SoundEvent SOULSTONE_STEP = create(new SoundEvent(DataHelper.prefix("soulstone_step")));
    public static final SoundEvent SOULSTONE_HIT = create(new SoundEvent(DataHelper.prefix("soulstone_hit")));

    public static final SoundEvent BLAZING_QUARTZ_ORE_BREAK = create(new SoundEvent(DataHelper.prefix("blazing_quartz_ore_break")));
    public static final SoundEvent BLAZING_QUARTZ_ORE_PLACE = create(new SoundEvent(DataHelper.prefix("blazing_quartz_ore_place")));

    public static final SoundEvent BLAZING_QUARTZ_BLOCK_BREAK = create(new SoundEvent(DataHelper.prefix("blazing_quartz_block_break")));
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_PLACE = create(new SoundEvent(DataHelper.prefix("blazing_quartz_block_place")));
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_STEP = create(new SoundEvent(DataHelper.prefix("blazing_quartz_block_step")));
    public static final SoundEvent BLAZING_QUARTZ_BLOCK_HIT = create(new SoundEvent(DataHelper.prefix("blazing_quartz_block_hit")));

    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_BREAK = create(new SoundEvent(DataHelper.prefix("arcane_charcoal_block_break")));
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_PLACE = create(new SoundEvent(DataHelper.prefix("arcane_charcoal_block_place")));
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_STEP = create(new SoundEvent(DataHelper.prefix("arcane_charcoal_block_step")));
    public static final SoundEvent ARCANE_CHARCOAL_BLOCK_HIT = create(new SoundEvent(DataHelper.prefix("arcane_charcoal_block_hit")));

    public static final SoundEvent TAINTED_ROCK_BREAK = create(new SoundEvent(DataHelper.prefix("tainted_rock_break")));
    public static final SoundEvent TAINTED_ROCK_PLACE = create(new SoundEvent(DataHelper.prefix("tainted_rock_place")));
    public static final SoundEvent TAINTED_ROCK_STEP = create(new SoundEvent(DataHelper.prefix("tainted_rock_step")));
    public static final SoundEvent TAINTED_ROCK_HIT = create(new SoundEvent(DataHelper.prefix("tainted_rock_hit")));

    public static final SoundEvent HALLOWED_GOLD_BREAK = create(new SoundEvent(DataHelper.prefix("hallowed_gold_break")));
    public static final SoundEvent HALLOWED_GOLD_HIT = create(new SoundEvent(DataHelper.prefix("hallowed_gold_hit")));
    public static final SoundEvent HALLOWED_GOLD_PLACE = create(new SoundEvent(DataHelper.prefix("hallowed_gold_place")));
    public static final SoundEvent HALLOWED_GOLD_STEP = create(new SoundEvent(DataHelper.prefix("hallowed_gold_step")));

    public static final SoundEvent SOUL_STAINED_STEEL_BREAK = create(new SoundEvent(DataHelper.prefix("soul_stained_steel_break")));
    public static final SoundEvent SOUL_STAINED_STEEL_HIT = create(new SoundEvent(DataHelper.prefix("soul_stained_steel_hit")));
    public static final SoundEvent SOUL_STAINED_STEEL_PLACE = create(new SoundEvent(DataHelper.prefix("soul_stained_steel_place")));
    public static final SoundEvent SOUL_STAINED_STEEL_STEP = create(new SoundEvent(DataHelper.prefix("soul_stained_steel_step")));

    public static final SoundEvent ETHER_PLACE = create(new SoundEvent(DataHelper.prefix("ether_place")));
    public static final SoundEvent ETHER_BREAK = create(new SoundEvent(DataHelper.prefix("ether_break")));

    public static final SoundEvent SCYTHE_CUT = create(new SoundEvent(DataHelper.prefix("scythe_cut")));
    public static final SoundEvent SPIRIT_HARVEST = create(new SoundEvent(DataHelper.prefix("spirit_harvest")));

    public static final SoundEvent TOTEM_CHARGE = create(new SoundEvent(DataHelper.prefix("totem_charge")));
    public static final SoundEvent TOTEM_CHARGED = create(new SoundEvent(DataHelper.prefix("totem_charged")));
    public static final SoundEvent TOTEM_ENGRAVE = create(new SoundEvent(DataHelper.prefix("totem_engrave")));

    public static final SoundEvent ALTAR_CRAFT = create(new SoundEvent(DataHelper.prefix("altar_craft")));
    public static final SoundEvent ALTAR_LOOP = create(new SoundEvent(DataHelper.prefix("altar_loop")));
    public static final SoundEvent ALTAR_CONSUME = create(new SoundEvent(DataHelper.prefix("altar_consume")));
    public static final SoundEvent ALTAR_SPEED_UP = create(new SoundEvent(DataHelper.prefix("altar_speed_up")));

    public static final SoundEvent SINISTER_EQUIP = create(new SoundEvent(DataHelper.prefix("sinister_equip")));
    public static final SoundEvent HOLY_EQUIP = create(new SoundEvent(DataHelper.prefix("holy_equip")));

    public static final SoundEvent TYRVING_CRUSH = create(new SoundEvent(DataHelper.prefix("tyrving_crush")));

    public static final SoundEvent SUSPICIOUS_SOUND = create(new SoundEvent(DataHelper.prefix("suspicious_sound")));

    public static final SoundType SOULSTONE = new SoundType(1.0F, 1.0F, SOULSTONE_BREAK, SOULSTONE_STEP, SOULSTONE_PLACE, SOULSTONE_HIT, SoundEvents.STONE_FALL);
    public static final SoundType BLAZING_QUARTZ_ORE = new SoundType(1.0F, 1.0F, BLAZING_QUARTZ_ORE_BREAK, SoundEvents.NETHER_ORE_STEP, BLAZING_QUARTZ_ORE_PLACE, SoundEvents.NETHER_ORE_HIT, SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType BLAZING_QUARTZ_BLOCK = new SoundType(1.0F, 1.25f, BLAZING_QUARTZ_BLOCK_BREAK, BLAZING_QUARTZ_BLOCK_STEP, BLAZING_QUARTZ_BLOCK_PLACE, BLAZING_QUARTZ_BLOCK_HIT, SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType ARCANE_CHARCOAL_BLOCK = new SoundType(1.0F, 0.9f, ARCANE_CHARCOAL_BLOCK_BREAK, ARCANE_CHARCOAL_BLOCK_STEP, ARCANE_CHARCOAL_BLOCK_PLACE, ARCANE_CHARCOAL_BLOCK_HIT, SoundEvents.NETHER_GOLD_ORE_FALL);
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BASALT_FALL);
    public static final SoundType TWISTED_ROCK = new SoundType(1.0F, 0.75F, TAINTED_ROCK_BREAK, TAINTED_ROCK_STEP, TAINTED_ROCK_PLACE, TAINTED_ROCK_HIT, SoundEvents.BASALT_FALL);
    public static final SoundType HALLOWED_GOLD = new SoundType(1.0F, 1.0F, HALLOWED_GOLD_BREAK, HALLOWED_GOLD_STEP, HALLOWED_GOLD_PLACE, HALLOWED_GOLD_HIT, SoundEvents.METAL_FALL);
    public static final SoundType SOUL_STAINED_STEEL = new SoundType(1.0F, 1.0F, SOUL_STAINED_STEEL_BREAK, SOUL_STAINED_STEEL_STEP, SOUL_STAINED_STEEL_PLACE, SOUL_STAINED_STEEL_HIT, SoundEvents.METAL_FALL);
    public static final SoundType ETHER = new SoundType(1.0F, 1.0F, ETHER_BREAK, SoundEvents.WOOL_STEP, ETHER_PLACE, SoundEvents.ANCIENT_DEBRIS_HIT, SoundEvents.WOOL_FALL);

    public static SoundEvent create(SoundEvent soundEvent)
    {
        SOUNDS.register(soundEvent.getLocation().getPath(), ()->soundEvent);
        return soundEvent;
    }
}