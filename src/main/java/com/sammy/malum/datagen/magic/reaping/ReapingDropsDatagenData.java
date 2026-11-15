package com.sammy.malum.datagen.magic.reaping;

import com.sammy.malum.MalumMod;
import com.sammy.malum.datagen.magic.MalumMagicDatagenProvider;
import com.sammy.malum.datagen.magic.banner.BannerPatternJsonBody;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.entity.MalumCultistEntityTypes;
import com.sammy.malum.registry.common.entity.MalumEntityTypes;
import com.sammy.malum.registry.common.util.MetallicsItemRegistryBundle;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;

public class ReapingDropsDatagenData {

    public static void init(MalumMagicDatagenProvider provider) {
        create(provider, "rotting_essence")
                .addMobSource(EntityType.ZOMBIE, EntityType.HUSK, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOGLIN, EntityType.ZOMBIFIED_PIGLIN)
                .addDrop(Ingredient.of(MalumContent.Materials.ROTTING_ESSENCE), UniformInt.of(2, 4), 0.2f);
        create(provider, "grim_talc")
                .addMobSource(EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.STRAY, EntityType.BOGGED, EntityType.SKELETON_HORSE)
                .addDrop(Ingredient.of(MalumContent.Materials.GRIM_TALC), UniformInt.of(1, 3), 0.4f);
        create(provider, "eerie_weave")
                .addMobSource(EntityType.PHANTOM, EntityType.GHAST)
                .addDrop(Ingredient.of(MalumContent.Materials.EERIE_WEAVE), UniformInt.of(1, 2), 0.6f);
        create(provider, "warp_flux")
                .addMobSource(EntityType.ENDERMAN)
                .addDrop(Ingredient.of(MalumContent.Materials.WARP_FLUX), UniformInt.of(2, 4), 0.4f);

        create(provider, "wind_nucleus")
                .addMobSource(EntityType.BREEZE)
                .addDrop(Ingredient.of(MalumContent.Materials.WIND_NUCLEUS), ConstantInt.of(1));
        create(provider, "pyre_nucleus")
                .addMobSource(EntityType.BLAZE)
                .addDrop(Ingredient.of(MalumContent.Materials.PYRE_NUCLEUS), ConstantInt.of(1));

        create(provider, "entropic_anomaly")
                .addMobSource(MalumCultistEntityTypes.CARDINAL)
                .addDrop(Ingredient.of(MalumContent.Materials.ENTROPIC_ANOMALY), ConstantInt.of(1));
        create(provider, "blade_memory")
                .addMobSource(MalumCultistEntityTypes.EVANGELIST)
                .addDrop(Ingredient.of(MalumContent.Materials.BLADE_MEMORY), ConstantInt.of(1));


        createMetalDrops(provider, MalumContent.AlchemyAndMetallics.IRON_METALLICS);
        createMetalDrops(provider, MalumContent.AlchemyAndMetallics.COPPER_METALLICS);
        createMetalDrops(provider, MalumContent.AlchemyAndMetallics.GOLD_METALLICS);
    }

    public static void createMetalDrops(MalumMagicDatagenProvider provider, MetallicsItemRegistryBundle bundle) {
        var name = "derealized_" + bundle.id;
        create(provider, name)
                .addBlockSource(new TagMatchTest(bundle.getOreTag()))
                .addDrop(Ingredient.of(bundle.getDerealizedMetal()), ConstantInt.of(1), 0.05f);
    }

    public static ReapingDropsJsonBody create(MalumMagicDatagenProvider provider, String id) {
        return provider.addData(MalumMod.malumPath(id), ReapingDropsJsonBody::new);
    }
}