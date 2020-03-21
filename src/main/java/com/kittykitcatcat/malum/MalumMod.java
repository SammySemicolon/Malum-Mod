package com.kittykitcatcat.malum;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod("malum")
public class MalumMod
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public MalumMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
