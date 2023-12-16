package com.sammy.malum.registry.common.item.tabs;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;

public class CreativeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MalumMod.MALUM);

    public static final RegistryObject<CreativeModeTab> CONTENT = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".content",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_basis_of_magic"))
                    .icon(() -> ItemRegistry.SPIRIT_ALTAR.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> VOID = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".void",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_void_chronicles"))
                    .icon(() -> ItemRegistry.STRANGE_NUCLEUS.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> BUILDING = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".building",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_arcane_construct"))
                    .icon(() -> ItemRegistry.TAINTED_ROCK.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> NATURE = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".nature",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_natural_wonders"))
                    .icon(() -> ItemRegistry.RUNEWOOD_SAPLING.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> METALLURGY = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".metallurgy",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_metallurgic_magics"))
                    .icon(() -> ItemRegistry.ALCHEMICAL_IMPETUS.get().getDefaultInstance()).build()
    );

    public static final RegistryObject<CreativeModeTab> COSMETIC = CREATIVE_MODE_TABS.register(MalumMod.MALUM + ".cosmetic",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + MalumMod.MALUM + "_cosmetics"))
                    .icon(() -> ItemRegistry.WEAVERS_WORKBENCH.get().getDefaultInstance()).build()
    );

    @SuppressWarnings("DataFlowIssue")
    public static void populateItemGroups(BuildCreativeModeTabContentsEvent event) {
        final ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        if (TAB_SORTING.containsKey(tabKey)) {
            TAB_SORTING.get(tabKey).stream().map(ForgeRegistries.ITEMS::getValue).forEach(event::accept);
        }
    }
}
