package com.sammy.malum.registry.common;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.common.spiritrite.greater.*;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;

import java.util.ArrayList;
import java.util.List;

public class SpiritRiteRegistry {
    public static List<MalumRiteType> RITES = new ArrayList<>();

    public static MalumRiteType SACRED_RITE = create(new SacredRiteType());
    public static MalumRiteType ELDRITCH_SACRED_RITE = create(new EldritchSacredRiteType());
    public static MalumRiteType WICKED_RITE = create(new WickedRiteType());
    public static MalumRiteType ELDRITCH_WICKED_RITE = create(new EldritchWickedRiteType());

    public static MalumRiteType EARTHEN_RITE = create(new EarthenRiteType());
    public static MalumRiteType ELDRITCH_EARTHEN_RITE = create(new EldritchEarthenRiteType());
    public static MalumRiteType INFERNAL_RITE = create(new InfernalRiteType());
    public static MalumRiteType ELDRITCH_INFERNAL_RITE = create(new EldritchInfernalRiteType());
    public static MalumRiteType AERIAL_RITE = create(new AerialRiteType());
    public static MalumRiteType ELDRITCH_AERIAL_RITE = create(new EldritchAerialRiteType());
    public static MalumRiteType AQUEOUS_RITE = create(new AqueousRiteType());
    public static MalumRiteType ELDRITCH_AQUEOUS_RITE = create(new EldritchAqueousRiteType());

    public static MalumRiteType ARCANE_RITE = create(new ArcaneRiteType());


    public static MalumRiteType create(MalumRiteType type) {
        RITES.add(type);
        return type;
    }

    public static MalumRiteType getRite(String identifier) {
        for (MalumRiteType rite : RITES) {
            if (rite.identifier.equals(identifier)) {
                return rite;
            }
        }
        return null;
    }

    public static MalumRiteType getRite(List<MalumSpiritType> spirits) {
        for (MalumRiteType rite : RITES) {
            if (rite.spirits.equals(spirits)) {
                return rite;
            }
        }
        return null;
    }
}
