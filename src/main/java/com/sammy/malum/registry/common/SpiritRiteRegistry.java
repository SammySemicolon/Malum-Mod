package com.sammy.malum.registry.common;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.common.spiritrite.greater.*;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.MalumRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.MalumMod.MALUM;

public class SpiritRiteRegistry {
    public static final DeferredRegister<MalumRiteType> RITES = DeferredRegister.create(MalumRegistries.RITES,MALUM);

    public static RegistryObject<MalumRiteType> SACRED_RITE = RITES.register("sacred", SacredRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_SACRED_RITE = RITES.register("eldritch_sacred", EldritchSacredRiteType::new);
    public static RegistryObject<MalumRiteType> WICKED_RITE = RITES.register("wicked", WickedRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_WICKED_RITE = RITES.register("eldritch", EldritchWickedRiteType::new);

    public static RegistryObject<MalumRiteType> EARTHEN_RITE = RITES.register("earthen", EarthenRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_EARTHEN_RITE = RITES.register("eldritch_earthen", EldritchEarthenRiteType::new);
    public static RegistryObject<MalumRiteType> INFERNAL_RITE = RITES.register("infernal", InfernalRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_INFERNAL_RITE = RITES.register("eldritch_infernal", EldritchInfernalRiteType::new);
    public static RegistryObject<MalumRiteType> AERIAL_RITE = RITES.register("aerial", AerialRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_AERIAL_RITE = RITES.register("eldritch_aerial", EldritchAerialRiteType::new);
    public static RegistryObject<MalumRiteType> AQUEOUS_RITE = RITES.register("aqueous", AqueousRiteType::new);
    public static RegistryObject<MalumRiteType> ELDRITCH_AQUEOUS_RITE = RITES.register("eldritch_aqueous", EldritchAqueousRiteType::new);

    public static RegistryObject<MalumRiteType> ARCANE_RITE = RITES.register("arcane", ArcaneRiteType::new);



    public static MalumRiteType getRite(String identifier) {
        for (MalumRiteType rite : MalumRegistries.RITES.getValues()) {
            if (rite.identifier.equals(identifier)) {
                return rite;
            }
        }
        return null;
    }

    public static MalumRiteType getRite(List<MalumSpiritType> spirits) {
        for (MalumRiteType rite : MalumRegistries.RITES.getValues()) {
            if (rite.spirits.equals(spirits)) {
                return rite;
            }
        }
        return null;
    }
}
