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
    public static final SoundEvent ritual_start = makeSoundEvent("ritual_start");
    public static final SoundEvent ritual_end = makeSoundEvent("ritual_end");
    public static final SoundEvent ritual_loop = makeSoundEvent("ritual_loop");
    public static final SoundEvent drill_loop = makeSoundEvent("drill_loop");
    public static final SoundEvent furnace_start = makeSoundEvent("furnace_start");
    public static final SoundEvent furnace_loop = makeSoundEvent("furnace_loop");
    public static final SoundEvent furnace_stop = makeSoundEvent("furnace_stop");
    public static final SoundEvent soul_harvest_success = makeSoundEvent("soul_harvest_success");
    public static final SoundEvent soul_harvest_loop = makeSoundEvent("soul_harvest_loop");
    public static final SoundEvent soul_harvest_fail = makeSoundEvent("soul_harvest_fail");

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
        r.register(ritual_start);
        r.register(ritual_end);
        r.register(ritual_loop);
        r.register(drill_loop);
        r.register(furnace_start);
        r.register(furnace_loop);
        r.register(furnace_stop);
        r.register(soul_harvest_success);
        r.register(soul_harvest_loop);
        r.register(soul_harvest_fail);

    }

}
