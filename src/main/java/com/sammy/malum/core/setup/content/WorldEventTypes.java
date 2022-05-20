package com.sammy.malum.core.setup.content;

import com.sammy.malum.common.worldevent.ActiveBlightEvent;
import com.sammy.malum.common.worldevent.TotemCreatedBlightEvent;
import com.sammy.ortus.systems.worldevent.WorldEventType;

import static com.sammy.ortus.setup.worldevent.OrtusWorldEventTypeRegistry.registerEventType;

public class WorldEventTypes {
    public static WorldEventType ACTIVE_BLIGHT = registerEventType(new WorldEventType("active_blight", ActiveBlightEvent::new));
    public static WorldEventType TOTEM_CREATED_BLIGHT = registerEventType(new WorldEventType("totem_blight", TotemCreatedBlightEvent::new));

}
