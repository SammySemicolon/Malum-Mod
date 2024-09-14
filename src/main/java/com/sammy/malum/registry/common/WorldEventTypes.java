package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.worldevent.ActiveBlightEvent;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

public class WorldEventTypes {

    public static DeferredRegister<WorldEventType> TYPES = DeferredRegister.create(LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY, MalumMod.MALUM);



    public static WorldEventType ACTIVE_BLIGHT = TYPES.register(new WorldEventType(MalumMod.malumPath("active_blight"), ActiveBlightEvent::new, false));
    public static WorldEventType TOTEM_CREATED_BLIGHT = registerEventType(new WorldEventType(MalumMod.malumPath("totem_blight"), TotemCreatedBlightEvent::new, false));

}