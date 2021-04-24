package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entities.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.entities.spirit.SpiritSplinterItemEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MalumEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MalumMod.MODID);
    
    public static final RegistryObject<EntityType<SpiritSplinterItemEntity>> SPIRIT_ESSENCE = ENTITY_TYPES.register("spirit_essence",
            () -> EntityType.Builder.create(SpiritSplinterItemEntity::new, EntityClassification.MISC).size(0.5F, 0.75F)
                    .trackingRange(9).build(MalumHelper.prefix("spirit_essence").toString()));
    
    public static final RegistryObject<EntityType<ScytheBoomerangEntity>> SCYTHE_BOOMERANG = ENTITY_TYPES.register("scythe_boomerang",
            () -> EntityType.Builder.create(ScytheBoomerangEntity::new, EntityClassification.MISC).size(2.5F, 0.75F)
                    .trackingRange(9).build(MalumHelper.prefix("scythe_boomerang").toString()));

}
