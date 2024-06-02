package com.sammy.malum.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;

public class MalumASM implements Runnable {

    @Override
    public void run() {
        extendEnums();
    }

    private static void extendEnums() {
        ClassTinkerers.enumBuilder(mapC("class_1886")) // EnchantmentCategory
                .addEnumSubclass("MALUM_SOUL_HUNTER_WEAPON", "com.sammy.malum.asm.EnchantmentCategorySoulHunter")
                .addEnumSubclass("MALUM_SCYTHE", "com.sammy.malum.asm.EnchantmentCategoryScythe")
                .addEnumSubclass("MALUM_REBOUND_SCYTHE", "com.sammy.malum.asm.EnchantmentCategoryReboundScythe")
                .addEnumSubclass("MALUM_STAFF", "com.sammy.malum.asm.EnchantmentCategoryStaff")
                .addEnumSubclass("MALUM_SCYTHE_OR_STAFF", "com.sammy.malum.asm.EnchantmentCategoryScytheOrStaff")
                .build();

        /*
         TYPE: sacred : -52320
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: wicked : -7457793
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: arcane : -371969
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: eldritch : -1175809
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: aerial : -10944513
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: aqueous : -14518785
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: earthen : -11206883
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: infernal : -19164
[12:39:15] [Render thread/INFO] (Minecraft) [STDOUT]: TYPE: umbral : 1298230381
         */
        //ClassTinkerers.enumBuilder(mapC())
        //ClassTinkerers.enumBuilder(mapC("class_1814"), "L" + mapC("class_124") + ";")  // Rarity // ChatFormatting
        //        .addEnum("WICKED", () -> new Object[] { ChatFormatting.RED })
        //        .build();
    }

    /**
     * Remap a class name from intermediary to the runtime name
     *
     * @param intermediaryName the intermediary name for the class alone, such as 'class_123'
     * @return the fully qualified remapped name, such as 'net.minecraft.thing.Thing',
     * or the input with 'net.minecraft.' prepended if not found.
     */
    public static String mapC(String intermediaryName) {
        return FabricLoader.getInstance().getMappingResolver()
                .mapClassName("intermediary", "net.minecraft." + intermediaryName);
    }
}
