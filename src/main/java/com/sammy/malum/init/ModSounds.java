package com.sammy.malum.init;

import com.sammy.malum.MalumMod;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
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
    public static final SoundEvent furnace_start = makeSoundEvent("furnace_start");
    public static final SoundEvent furnace_loop = makeSoundEvent("furnace_loop");
    public static final SoundEvent furnace_stop = makeSoundEvent("furnace_stop");

    public static final SoundEvent spirit_harvest_drain = makeSoundEvent("spirit_harvest_drain");
    public static final SoundEvent spirit_harvest_failure = makeSoundEvent("spirit_harvest_failure");
    public static final SoundEvent spirit_harvest_success = makeSoundEvent("spirit_harvest_success");

    public static final SoundEvent machine_toggle_sound = makeSoundEvent("machine_toggle_sound");
    public static final SoundEvent bonk = makeSoundEvent("bonk");

    public static final SoundEvent redstone_pulse = makeSoundEvent("redstone_pulse");
    public static final SoundEvent netherborne = makeSoundEvent("netherborne");
    public static final SoundEvent skeletons_in_the_night = makeSoundEvent("skeletons_in_the_night");
    public static final SoundEvent prismatropolis = makeSoundEvent("prismatropolis");
    public static final SoundEvent aetherborne = makeSoundEvent("aetherborne");

    public static final SoundEvent the_bone_brigade_blues = makeSoundEvent("the_bone_brigade_blues");
    public static final SoundEvent ender_alley = makeSoundEvent("ender_alley");

    private static SoundEvent makeSoundEvent(String name)
    {
        ResourceLocation loc = new ResourceLocation(MalumMod.MODID, name);
        return new SoundEvent(loc).setRegistryName(name);
    }
    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
    {
        IForgeRegistry<SoundEvent> r = evt.getRegistry();
        r.register(furnace_start);
        r.register(furnace_loop);
        r.register(furnace_stop);

        r.register(spirit_harvest_drain);
        r.register(spirit_harvest_success);
        r.register(spirit_harvest_failure);

        r.register(machine_toggle_sound);
        r.register(bonk);

        r.register(redstone_pulse);
        r.register(netherborne);
        r.register(skeletons_in_the_night);
        r.register(prismatropolis);
        r.register(aetherborne);
        r.register(the_bone_brigade_blues);
        r.register(ender_alley);
    }
}
