package com.sammy.malum.core.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entities.SpiritEssenceEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MalumMod.MODID);
    
    public static final RegistryObject<EntityType<SpiritEssenceEntity>> SPIRIT_ESSENCE = ENTITY_TYPES.register("spirit_essence",
            () -> EntityType.Builder.create(SpiritEssenceEntity::new, EntityClassification.MISC).size(0.25F, 0.25F)
                    .trackingRange(9).build(new ResourceLocation(MalumMod.MODID, "spirit_essence").toString()));
}
