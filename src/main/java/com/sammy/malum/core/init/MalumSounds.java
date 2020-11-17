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
    public static final RegistryObject<SoundEvent> TAINTED_ROCK_BREAK = register("tainted_rock_break");
    public static final RegistryObject<SoundEvent> TAINTED_ROCK_STEP = register("tainted_rock_step");
    public static final RegistryObject<SoundEvent> TAINTED_ROCK_PLACE = register("tainted_rock_place");
    public static final RegistryObject<SoundEvent> ZOOM_ROCK_STEP = register("zoom_rock_step");
    public static final RegistryObject<SoundEvent> ARCANE_CRAFT = register("arcane_craft");
    
    public static final SoundType TAINTED_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK.get(), TAINTED_ROCK_STEP.get(), TAINTED_ROCK_PLACE.get(), SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    public static final SoundType ZOOM_ROCK = new SoundType(1.0F, 1.0F, TAINTED_ROCK_BREAK.get(), ZOOM_ROCK_STEP.get(), TAINTED_ROCK_PLACE.get(), SoundEvents.BLOCK_BASALT_HIT, SoundEvents.BLOCK_BASALT_FALL);
    
    private static RegistryObject<SoundEvent> register(String key)
    {
        return SOUNDS.register(key, ()-> new SoundEvent(MalumHelper.prefix(key)));
    }
}