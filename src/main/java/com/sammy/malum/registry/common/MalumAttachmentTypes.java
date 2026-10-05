package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.common.data.attachment.gear.*;
import com.sammy.malum.common.data.attachment.soul_data.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.registries.*;

import java.util.function.*;

public class MalumAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MalumMod.MALUM);

    public static final Supplier<AttachmentType<LivingSoulData>> LIVING_SOUL_INFO = ATTACHMENT_TYPES.register(
            "living_soul_info", () -> AttachmentType.builder(LivingSoulData::new)
                    .serialize(LivingSoulData.CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<ProjectileSoulData>> PROJECTILE_SOUL_INFO = ATTACHMENT_TYPES.register(
            "projectile_soul_info", () -> AttachmentType.builder(ProjectileSoulData::new)
                    .serialize(ProjectileSoulData.CODEC).build());

    public static final Supplier<AttachmentType<GeasSoulData>> GEAS_SOUL_INFO = ATTACHMENT_TYPES.register(
            "geas_soul_info", () -> AttachmentType.builder(GeasSoulData::new)
                    .serialize(GeasSoulData.CODEC).sync(GeasSoulData.STREAM_CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<WindTunnelData>> WIND_TUNNEL_INFO = ATTACHMENT_TYPES.register(
            "wind_tunnel_info", () -> AttachmentType.builder(WindTunnelData::new)
                    .serialize(WindTunnelData.CODEC).sync(WindTunnelData.STREAM_CODEC).build());

    public static final Supplier<AttachmentType<AvariceMarkData>> AVARICE_MARK = ATTACHMENT_TYPES.register(
            "avarice_mark", () -> AttachmentType.builder(AvariceMarkData::new)
                    .serialize(AvariceMarkData.CODEC).sync(AvariceMarkData.STREAM_CODEC).build());

    public static final Supplier<AttachmentType<CachedSpiritDropsData>> CACHED_SPIRIT_DROPS = ATTACHMENT_TYPES.register(
            "cached_spirit_drops", () -> AttachmentType.builder(CachedSpiritDropsData::new)
                    .serialize(CachedSpiritDropsData.CODEC).build());

    public static final Supplier<AttachmentType<ProgressionData>> PROGRESSION_DATA = ATTACHMENT_TYPES.register(
            "progression_data", () -> AttachmentType.builder(ProgressionData::new)
                    .serialize(ProgressionData.CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<CurioData>> CURIO_DATA = ATTACHMENT_TYPES.register(
            "curio_data", () -> AttachmentType.builder(CurioData::new)
                    .serialize(CurioData.CODEC).sync(CurioData.STREAM_CODEC).build());

    public static final Supplier<AttachmentType<SoulWardData>> SOUL_WARD = ATTACHMENT_TYPES.register(
            "soul_ward", () -> AttachmentType.builder(SoulWardData::new)
                    .serialize(SoulWardData.CODEC).sync(SoulWardData.STREAM_CODEC).build());

    public static final Supplier<AttachmentType<StaffAbilityData>> STAFF_ABILITIES = ATTACHMENT_TYPES.register(
            "staff_abilities", () -> AttachmentType.builder(StaffAbilityData::new)
                    .serialize(StaffAbilityData.CODEC).sync(StaffAbilityData.STREAM_CODEC).build());

    public static final Supplier<AttachmentType<VindictiveBrandDashData>> VINDICTIVE_BRAND_DASH_DATA = ATTACHMENT_TYPES.register(
            "vindictive_brand_dash", () -> AttachmentType.builder(VindictiveBrandDashData::new)
                    .serialize(VindictiveBrandDashData.CODEC).build());

    public static final Supplier<AttachmentType<WeepingWellData>> WEEPING_WELL_INFO = ATTACHMENT_TYPES.register(
            "weeping_well_info", () -> AttachmentType.builder(WeepingWellData::new)
                    .serialize(WeepingWellData.CODEC).build());

    public static final Supplier<AttachmentType<TouchOfDarknessData>> TOUCH_OF_DARKNESS = ATTACHMENT_TYPES.register(
            "touch_of_darkness", () -> AttachmentType.builder(TouchOfDarknessData::new)
                    .serialize(TouchOfDarknessData.CODEC).build());

    public static final Supplier<AttachmentType<MalignantInfluenceData>> MALIGNANT_INFLUENCE = ATTACHMENT_TYPES.register(
            "malignant_influence", () -> AttachmentType.builder(MalignantInfluenceData::new)
                    .serialize(MalignantInfluenceData.CODEC).sync(MalignantInfluenceData.STREAM_CODEC).build());

}
