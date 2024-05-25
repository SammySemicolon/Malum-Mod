package com.sammy.malum.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;

public class MalumASM implements Runnable{

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
    }

    /**
     * Remap a class name from intermediary to the runtime name
     * @param intermediaryName the intermediary name for the class alone, such as 'class_123'
     * @return the fully qualified remapped name, such as 'net.minecraft.thing.Thing',
     *         or the input with 'net.minecraft.' prepended if not found.
     */
    public static String mapC(String intermediaryName) {
        return FabricLoader.getInstance().getMappingResolver()
                .mapClassName("intermediary", "net.minecraft." + intermediaryName);
    }
}
