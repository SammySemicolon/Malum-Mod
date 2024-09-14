package com.sammy.malum.registry;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class JukeboxSongs {

    public static final ResourceKey<JukeboxSong> ARCANE_ELEGY = registerKey("arcane_elegy");
    public static final ResourceKey<JukeboxSong> AESTHETICA = registerKey("aesthetica");

    private static ResourceKey<JukeboxSong> registerKey(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, MalumMod.malumPath(name));
    }

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, ARCANE_ELEGY, SoundRegistry.ARCANE_ELEGY, 5320, 15);//super(62, SoundRegistry.ARCANE_ELEGY, builder, 5320);
        register(context, AESTHETICA, SoundRegistry.AESTHETICA, 7920, 15);//super(7, SoundRegistry.AESTHETICA, builder, 7920);
    }

    private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> sound, float length, int output) {
        context.register(key, new JukeboxSong(sound, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), length, output));
    }
}

