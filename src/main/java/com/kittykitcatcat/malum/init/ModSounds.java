package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MalumMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModSounds
{
    public static final SoundEvent transmutate = makeSoundEvent("transmutate");
    public static final SoundEvent spirit_infusion_start = makeSoundEvent("spirit_infusion_start");
    public static final SoundEvent spirit_infusion_loop = makeSoundEvent("spirit_infusion_loop");
    public static final SoundEvent spirit_infusion_stop = makeSoundEvent("spirit_infusion_stop");
    public static final SoundEvent spirit_whisper = makeSoundEvent("spirit_whisper");
    public static final SoundEvent soul_jar_fill = makeSoundEvent("soul_jar_fill");
    public static final SoundEvent soul_jar_place = makeSoundEvent("soul_jar_place");
    public static final SoundEvent soul_jar_break = makeSoundEvent("soul_jar_break");
    public static final SoundEvent soul_jar_breaking = makeSoundEvent("soul_jar_breaking");
    public static final SoundEvent furnace_start = makeSoundEvent("furnace_start");
    public static final SoundEvent furnace_loop = makeSoundEvent("furnace_loop");
    public static final SoundEvent furnace_stop = makeSoundEvent("furnace_stop");
    public static final SoundEvent spirit_harvest_drain = makeSoundEvent("spirit_harvest_drain");
    public static final SoundEvent spirit_harvest_failure = makeSoundEvent("spirit_harvest_failure");
    public static final SoundEvent spirit_harvest_success = makeSoundEvent("spirit_harvest_success");
    public static final SoundEvent shattering_sound = makeSoundEvent("shattering_sound");
    public static final SoundEvent bonk = makeSoundEvent("bonk");

    private static SoundEvent makeSoundEvent(String name)
    {
        ResourceLocation loc = new ResourceLocation(MalumMod.MODID, name);
        return new SoundEvent(loc).setRegistryName(name);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
        IForgeRegistry<SoundEvent> r = evt.getRegistry();
        r.register(transmutate);
        r.register(spirit_infusion_start);
        r.register(spirit_infusion_loop);
        r.register(spirit_infusion_stop);
        r.register(spirit_whisper);
        r.register(soul_jar_fill);
        r.register(soul_jar_place);
        r.register(soul_jar_break);
        r.register(soul_jar_breaking);
        r.register(furnace_start);
        r.register(furnace_loop);
        r.register(furnace_stop);
        r.register(spirit_harvest_success);
        r.register(spirit_harvest_drain);
        r.register(spirit_harvest_failure);
        r.register(shattering_sound);
        r.register(bonk);

    }

}
