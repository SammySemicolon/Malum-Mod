package com.sammy.malum.registry;

import com.mojang.brigadier.tree.*;
import com.sammy.malum.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.*;


public class MalumCommandRegistry {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(Commands.literal("malum")
                            .then(ActivateEndPortalFrameCommand.register())
                            .then(ActivateNearestEndPortalFrameCommand.register())
                    //.then(ScreenshakeCommand.register())
            );
            dispatcher.register(Commands.literal(MalumMod.MALUM)
                    .redirect(cmd));
        });
    }

    /*TODO
    public static <T extends ArgumentType<?>> void registerArgumentType(ResourceLocation key, Class<T> argumentClass, ArgumentSerializer<T> serializer) {
        ArgumentTypes.register(key.toString(), argumentClass, serializer);
    }

     */
}