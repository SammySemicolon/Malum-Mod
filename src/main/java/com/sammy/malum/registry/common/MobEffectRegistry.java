package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEffectRegistry {
    public static final Map<ResourceLocation, Float> ALCHEMICAL_PROFICIENCY_MAP = new HashMap<>();
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MalumMod.MALUM);

    public static final RegistryObject<MobEffect> GAIAN_BULWARK = EFFECTS.register("gaian_bulwark", EarthenAura::new);
    public static final RegistryObject<MobEffect> EARTHEN_MIGHT = EFFECTS.register("earthen_might", CorruptedEarthenAura::new);

    public static final RegistryObject<MobEffect> MINERS_RAGE = EFFECTS.register("miners_rage", InfernalAura::new);
    public static final RegistryObject<MobEffect> IFRITS_EMBRACE = EFFECTS.register("ifrits_embrace", CorruptedInfernalAura::new);

    public static final RegistryObject<MobEffect> ZEPHYRS_COURAGE = EFFECTS.register("zephyrs_courage", AerialAura::new);
    public static final RegistryObject<MobEffect> AETHERS_CHARM = EFFECTS.register("aethers_charm", CorruptedAerialAura::new);

    public static final RegistryObject<MobEffect> POSEIDONS_GRASP = EFFECTS.register("poseidons_grasp", AqueousAura::new);
    public static final RegistryObject<MobEffect> ANGLERS_LURE = EFFECTS.register("anglers_lure", CorruptedAqueousAura::new);

    public static final RegistryObject<MobEffect> GLUTTONY = attachAlchemicalProficiency(EFFECTS.register("gluttony", GluttonyEffect::new), 0.5f);
    public static final RegistryObject<MobEffect> CANCEROUS_GROWTH = EFFECTS.register("cancerous_growth", GrowingFleshEffect::new);
    public static final RegistryObject<MobEffect> WICKED_INTENT = attachAlchemicalProficiency(EFFECTS.register("wicked_intent", WickedIntentEffect::new), 0.2f);
    public static final RegistryObject<MobEffect> REJECTED = EFFECTS.register("rejected", RejectedEffect::new);

    public static RegistryObject<MobEffect> attachAlchemicalProficiency(RegistryObject<MobEffect> effect, float proficiency) {
        ALCHEMICAL_PROFICIENCY_MAP.put(effect.getId(), proficiency);
        return effect;
    }

    @SubscribeEvent
    public static void addPotionRecipes(FMLCommonSetupEvent event) {
        PotionBrewing.addMix(Potions.WATER, ItemRegistry.ROTTING_ESSENCE.get(), Potions.MUNDANE);
        PotionBrewing.addMix(Potions.AWKWARD, ItemRegistry.ROTTING_ESSENCE.get(), Potions.POISON);
        PotionBrewing.addMix(Potions.WATER, ItemRegistry.ASTRAL_WEAVE.get(), Potions.MUNDANE);
        PotionBrewing.addMix(Potions.AWKWARD, ItemRegistry.ASTRAL_WEAVE.get(), Potions.INVISIBILITY);
    }
}
