package com.sammy.malum.registry;

import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MalumCommandRegistry {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
    }
}