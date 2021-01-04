package com.sammy.malum.core.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.effects.Bleeding;
import com.sammy.malum.common.effects.FoolsLuck;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEffects
{
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MalumMod.MODID);
    public static final RegistryObject<Effect> FOOLS_LUCK = EFFECTS.register("fools_luck", FoolsLuck::new);
    public static final RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", Bleeding::new);
}
