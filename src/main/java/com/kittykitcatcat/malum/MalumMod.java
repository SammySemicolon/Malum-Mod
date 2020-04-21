package com.kittykitcatcat.malum;

import net.minecraft.block.CoralBlock;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@SuppressWarnings("unused")
@Mod("malum")
public class MalumMod
{

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random random = new Random();
    public MalumMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
