package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.common.data.attachment.abilities.AvariceData;
import com.sammy.malum.common.data.attachment.abilities.GluttonyData;
import com.sammy.malum.common.data.attachment.abilities.SoulWardData;
import com.sammy.malum.common.data.attachment.gear.*;
import com.sammy.malum.common.data.attachment.soul_data.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class MalumAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MalumMod.MALUM);

    //Soul
    public static final Supplier<AttachmentType<LivingSoulData>> LIVING_SOUL_INFO = register("living_soul_info", LivingSoulData::new, b -> b.serialize(LivingSoulData.CODEC).copyOnDeath());
    public static final Supplier<AttachmentType<ProjectileSoulData>> PROJECTILE_SOUL_INFO = register("projectile_soul_info", ProjectileSoulData::new, b -> b.serialize(ProjectileSoulData.CODEC));
    public static final Supplier<AttachmentType<GeasSoulData>> GEAS_SOUL_INFO = register("geas_soul_info", GeasSoulData::new, b -> b.serialize(GeasSoulData.CODEC).sync(GeasSoulData.STREAM_CODEC).copyOnDeath());

    //Gear

    //Abilities
    public static final Supplier<AttachmentType<SoulWardData>> SOUL_WARD = register("soul_ward", SoulWardData::new, b -> b.serialize(SoulWardData.CODEC).sync(SoulWardData.STREAM_CODEC));

    public static final Supplier<AttachmentType<GluttonyData>> GLUTTONY = register("gluttony", GluttonyData::new, b -> b.serialize(GluttonyData.CODEC).sync(GluttonyData.STREAM_CODEC));
    public static final Supplier<AttachmentType<AvariceData>> AVARICE = register("avarice", AvariceData::new, b -> b.serialize(AvariceData.CODEC).sync(AvariceData.STREAM_CODEC));

    public static final Supplier<AttachmentType<WindTunnelData>> WIND_TUNNEL_INFO = register("wind_tunnel_info", WindTunnelData::new, b -> b.serialize(WindTunnelData.CODEC).sync(WindTunnelData.STREAM_CODEC));


    public static final Supplier<AttachmentType<CachedSpiritDropsData>> CACHED_SPIRIT_DROPS = register("cached_spirit_drops", CachedSpiritDropsData::new, b -> b.serialize(CachedSpiritDropsData.CODEC));

    public static final Supplier<AttachmentType<ProgressionData>> PROGRESSION_DATA = register("progression_data", ProgressionData::new, b -> b.serialize(ProgressionData.CODEC).copyOnDeath());

    public static final Supplier<AttachmentType<CurioData>> CURIO_DATA = register("curio_data", CurioData::new, b -> b.serialize(CurioData.CODEC).sync(CurioData.STREAM_CODEC));


    public static final Supplier<AttachmentType<StaffAbilityData>> STAFF_ABILITIES = register("staff_abilities", StaffAbilityData::new, b -> b.serialize(StaffAbilityData.CODEC).sync(StaffAbilityData.STREAM_CODEC));

    public static final Supplier<AttachmentType<VindictiveBrandDashData>> VINDICTIVE_BRAND_DASH_DATA = register("vindictive_brand_dash", VindictiveBrandDashData::new, b -> b.serialize(VindictiveBrandDashData.CODEC));

    public static final Supplier<AttachmentType<WeepingWellData>> WEEPING_WELL_INFO = register("weeping_well_info", WeepingWellData::new, b -> b.serialize(WeepingWellData.CODEC));

    public static final Supplier<AttachmentType<TouchOfDarknessData>> TOUCH_OF_DARKNESS = register("touch_of_darkness", TouchOfDarknessData::new, b -> b.serialize(TouchOfDarknessData.CODEC).build());

    public static final Supplier<AttachmentType<MalignantInfluenceData>> MALIGNANT_INFLUENCE = register("malignant_influence", MalignantInfluenceData::new, b -> b.serialize(MalignantInfluenceData.CODEC).sync(MalignantInfluenceData.STREAM_CODEC));

    public static final Supplier<AttachmentType<MyriadGatewayData>> MYRIAD_GATEWAY = register("myriad_gateway", MyriadGatewayData::new, b -> b.serialize(MyriadGatewayData.CODEC).sync(MyriadGatewayData.STREAM_CODEC));
    
    public static <T> Supplier<AttachmentType<T>> register(String id, Supplier<T> defaultInstance, Consumer<AttachmentType.Builder<T>> modifier) {
        return ATTACHMENT_TYPES.register(id, () -> {
            AttachmentType.Builder<T> builder = AttachmentType.builder(defaultInstance);
            modifier.accept(builder);
            return builder.build();
        });
    }

}
