package com.sammy.malum.core.handlers;

import com.sammy.malum.MalumMod;
import com.simibubi.create.Create;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MalumResourcePackHandler {

    @SubscribeEvent
    public static void clientInit(final FMLClientSetupEvent event) {
        extractFiles("Chibi Spirits");
    }

    public static void extractFiles(String... packs) {
        createFolderIfMissing("resourcepacks");

        for (String name : packs) {
            InputStream folderInJar = MalumResourcePackHandler.class.getResourceAsStream("/opt_in/" + name + ".zip");
            try {
                Files.copy(folderInJar, Paths.get("resourcepacks/" + name + ".zip"));
            } catch (FileAlreadyExistsException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                folderInJar.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFolderIfMissing(String name) {
        try {
            Files.createDirectories(Paths.get(name));
        } catch (IOException var2) {
            Create.LOGGER.warn("Could not create Folder: {}", name);
        }

    }

}