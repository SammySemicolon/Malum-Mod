package com.sammy.malum.registry;

import com.mojang.brigadier.*;
import com.mojang.brigadier.tree.*;
import com.sammy.malum.*;
import net.minecraft.commands.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MalumCommandRegistry {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("m")
                        .then(ActivateEndPortalFrameCommand.register())
                        .then(ActivateNearestEndPortalFrameCommand.register())
                //.then(ScreenshakeCommand.register())
        );
        dispatcher.register(Commands.literal(MalumMod.MALUM)
                .redirect(cmd));
    }

    /*TODO
    public static <T extends ArgumentType<?>> void registerArgumentType(ResourceLocation key, Class<T> argumentClass, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClass, serializer);
    }

     */
}