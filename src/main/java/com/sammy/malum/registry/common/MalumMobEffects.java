package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effect.*;
import com.sammy.malum.common.effect.ascension.*;
import com.sammy.malum.common.effect.rite.aura.*;
import com.sammy.malum.common.effect.rite.aura.soulwood.*;
import com.sammy.malum.common.effect.geas.*;
import com.sammy.malum.common.effect.gluttony.*;
import com.sammy.malum.common.effect.rite.*;
import com.sammy.malum.common.effect.weapon.*;
import com.sammy.malum.common.effect.weapon.vindictive_brand.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber()
public class MalumMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MalumMod.MALUM);

    //Rite
    public static final DeferredHolder<MobEffect, MobEffect> SACRED_NOURISHMENT = MOB_EFFECTS.register("sacred_nourishment", SacredNourishment::new);
    public static final DeferredHolder<MobEffect, MobEffect> WICKED_EMPOWERMENT = MOB_EFFECTS.register("wicked_empowerment", WickedEmpowerment::new);

    public static final DeferredHolder<MobEffect, MobEffect> HOWLING_GALE = MOB_EFFECTS.register("howling_gale", HowlingGaleEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SKY_TETHER = MOB_EFFECTS.register("sky_tether", SkyTetherEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FLOWING_GRASP = MOB_EFFECTS.register("flowing_grasp", FlowingGraspEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> GOOD_TIDES = MOB_EFFECTS.register("good_tides", GoodTidesEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> STONE_WARD = MOB_EFFECTS.register("stone_ward", StoneWardEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> OAKEN_MIGHT = MOB_EFFECTS.register("oaken_might", OakenMightEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> BURNING_FERVOR = MOB_EFFECTS.register("burning_fervor", BurningFervorEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FIERY_EMBRACE = MOB_EFFECTS.register("fiery_embrace", FieryEmbraceEffect::new);

    //Geas
    public static final DeferredHolder<MobEffect, MobEffect> ARCANAPHAGE = MOB_EFFECTS.register("arcanaphage", ArcanaphageEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SHAKEN_FAITH = MOB_EFFECTS.register("shaken_faith", ShakenFaithEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TRUE_SHOT = MOB_EFFECTS.register("true_shot", TrueShotEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> FLAWED_AIM = MOB_EFFECTS.register("flawed_aim", FlawedAimEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> AVARICE = MOB_EFFECTS.register("avarice", AvariceEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> WYRD_EXHAUSTION = MOB_EFFECTS.register("wyrd_exhaustion", WyrdExhaustionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> GLEEFUL_TARGET = MOB_EFFECTS.register("gleeful_target", GleefulTargetEffect::new);

    //Gluttony
    public static final DeferredHolder<MobEffect, MobEffect> GLUTTONY = MOB_EFFECTS.register("gluttony", GluttonyEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> TRIAL_OF_FAITH = MOB_EFFECTS.register("trial_of_faith", TrialOfFaithEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> DESPERATE_NEED = MOB_EFFECTS.register("desperate_need", DesperateNeedEffect::new);

    //Silence
    public static final DeferredHolder<MobEffect, MobEffect> SILENCED = MOB_EFFECTS.register("silenced", SilencedEffect::new);

    //Trinket
    public static final DeferredHolder<MobEffect, MobEffect> CANCEROUS_GROWTH = MOB_EFFECTS.register("cancerous_growth", GrowingFleshEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> ECHOING_ARCANA = MOB_EFFECTS.register("echoing_arcana", EchoingArcanaEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> WICKED_INTENT = MOB_EFFECTS.register("wicked_intent", WickedIntentEffect::new);

    //Weapon
    public static final DeferredHolder<MobEffect, MobEffect> GRIM_CERTAINTY = MOB_EFFECTS.register("grim_certainty", GrimCertaintyEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> IMMINENT_DELIVERANCE = MOB_EFFECTS.register("imminent_deliverance", ImminentDeliveranceEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> BURROWING_RESENTMENT = MOB_EFFECTS.register("burrowing_resentment", BurrowingResentmentEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> UNSEALING_RITUAL = MOB_EFFECTS.register("unsealing_ritual", UnsealingRitualEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> INSATIABLE_VINDICATION = MOB_EFFECTS.register("insatiable_vindication", InsatiableVindicationEffect::new);

    //Misc
    public static final DeferredHolder<MobEffect, MobEffect> ASCENSION = MOB_EFFECTS.register("ascension", AscensionEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> LIFTED = MOB_EFFECTS.register("lifted", LiftedEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> HATRED = MOB_EFFECTS.register("hatred", HatredEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> REJECTED = MOB_EFFECTS.register("rejected", RejectedEffect::new);

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.WATER, MalumContent.Materials.ROTTING_ESSENCE.get(), Potions.MUNDANE);
        builder.addMix(Potions.AWKWARD, MalumContent.Materials.ROTTING_ESSENCE.get(), Potions.POISON);

        builder.addMix(Potions.WATER, MalumContent.Materials.EERIE_WEAVE.get(), Potions.MUNDANE);
        builder.addMix(Potions.AWKWARD, MalumContent.Materials.EERIE_WEAVE.get(), Potions.INVISIBILITY);
    }
}
