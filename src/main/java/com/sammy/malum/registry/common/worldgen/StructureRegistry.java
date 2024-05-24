package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.WeepingWellStructure;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureRegistry {
    public static final LazyRegistrar<StructureType<?>> STRUCTURES = LazyRegistrar.create(Registries.STRUCTURE_TYPE, MalumMod.MALUM);

    public static final RegistryObject<StructureType<WeepingWellStructure>> WEEPING_WELL = STRUCTURES.register("weeping_well", () -> () -> (WeepingWellStructure.CODEC));

}
