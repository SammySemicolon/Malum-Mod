package com.sammy.malum.init;

import com.google.common.base.Preconditions;
import com.sammy.malum.effects.CharmEffect;
import com.sammy.malum.effects.DoomEffect;
import com.sammy.malum.effects.DreadEffect;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects
{
    public static Effect charm;
    public static Effect dread;
    public static Effect doom;
    @SubscribeEvent
    public static void RegisterCurios(RegistryEvent.Register<Effect> event)
    {
    
        final IForgeRegistry<Effect> registry = event.getRegistry();
        registry.registerAll(
                charm = setup(new CharmEffect(), "charm"),
                dread = setup(new DreadEffect(), "dread"),
                doom = setup(new DoomEffect(), "doom")
        );
    }
    
    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name)
    {
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
        return setup(entry, new ResourceLocation(MODID, name));
    }
    
    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
    {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}