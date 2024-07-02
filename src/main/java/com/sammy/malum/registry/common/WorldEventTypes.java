package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldevent.ActiveBlightEvent;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import team.lodestar.lodestone.systems.worldevent.WorldEventType;

import static team.lodestar.lodestone.registry.common.LodestoneWorldEventTypeRegistry.registerEventType;


public class WorldEventTypes {
    public static WorldEventType ACTIVE_BLIGHT = registerEventType(new WorldEventType(MalumMod.malumPath("active_blight"), ActiveBlightEvent::new));
    public static WorldEventType TOTEM_CREATED_BLIGHT = registerEventType(new WorldEventType(MalumMod.malumPath("totem_blight"), TotemCreatedBlightEvent::new));

}
