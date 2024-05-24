package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.*;
import com.sammy.malum.client.model.cosmetic.GenericArmorModel;
import com.sammy.malum.client.model.cosmetic.GenericSlimArmorModel;
import com.sammy.malum.client.model.cosmetic.ScarfModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulHunterArmorModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulStainedSteelArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.PridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.SlimPridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.CommandoArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.ExecutionerArmorModel;
import com.sammy.malum.client.model.cosmetic.ultrakill.UltrakillMachineArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class ModelRegistry {

    public static SoulHunterArmorModel SOUL_HUNTER_ARMOR;
    public static AncientSoulHunterArmorModel ANCIENT_SOUL_HUNTER_ARMOR;

    public static SoulStainedSteelArmorModel SOUL_STAINED_ARMOR;
    public static AncientSoulStainedSteelArmorModel ANCIENT_SOUL_STAINED_STEEL_ARMOR;

    public static MalignantStrongholdArmorModel MALIGNANT_LEAD_ARMOR;

    public static GenericSlimArmorModel GENERIC_SLIM_ARMOR;
    public static GenericArmorModel GENERIC_ARMOR;

    public static CommandoArmorModel COMMANDO;
    public static ExecutionerArmorModel EXECUTIONER;

    public static UltrakillMachineArmorModel ULTRAKILL_MACHINE;

    public static PridewearArmorModel PRIDEWEAR;
    public static SlimPridewearArmorModel SLIM_PRIDEWEAR;

    public static TopHatModel TOP_HAT;
    public static TailModel TAIL_MODEL;

    public static HeadOverlayModel HEAD_OVERLAY_MODEL;
    public static ScarfModel SCARF;

    public static void registerLayerDefinitions() {

        EntityModelLayerRegistry.registerModelLayer(SoulHunterArmorModel.LAYER, SoulHunterArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MalignantStrongholdArmorModel.LAYER, MalignantStrongholdArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GenericSlimArmorModel.LAYER, GenericSlimArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GenericArmorModel.LAYER, GenericArmorModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(CommandoArmorModel.LAYER, CommandoArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ExecutionerArmorModel.LAYER, ExecutionerArmorModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(UltrakillMachineArmorModel.LAYER, UltrakillMachineArmorModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(AncientSoulStainedSteelArmorModel.LAYER, AncientSoulStainedSteelArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AncientSoulHunterArmorModel.LAYER, AncientSoulHunterArmorModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(PridewearArmorModel.LAYER, PridewearArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SlimPridewearArmorModel.LAYER, SlimPridewearArmorModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(TopHatModel.LAYER, TopHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(TailModel.LAYER, TailModel::createBodyLayer);

        EntityModelLayerRegistry.registerModelLayer(HeadOverlayModel.LAYER, HeadOverlayModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScarfModel.LAYER, ScarfModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        SOUL_HUNTER_ARMOR = new SoulHunterArmorModel(event.getEntityModels().bakeLayer(SoulHunterArmorModel.LAYER));
        SOUL_STAINED_ARMOR = new SoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(SoulStainedSteelArmorModel.LAYER));
        MALIGNANT_LEAD_ARMOR = new MalignantStrongholdArmorModel(event.getEntityModels().bakeLayer(MalignantStrongholdArmorModel.LAYER));
        GENERIC_SLIM_ARMOR = new GenericSlimArmorModel(event.getEntityModels().bakeLayer(GenericSlimArmorModel.LAYER));
        GENERIC_ARMOR = new GenericArmorModel(event.getEntityModels().bakeLayer(GenericArmorModel.LAYER));

        COMMANDO = new CommandoArmorModel(event.getEntityModels().bakeLayer(CommandoArmorModel.LAYER));
        EXECUTIONER = new ExecutionerArmorModel(event.getEntityModels().bakeLayer(ExecutionerArmorModel.LAYER));

        ULTRAKILL_MACHINE = new UltrakillMachineArmorModel(event.getEntityModels().bakeLayer(UltrakillMachineArmorModel.LAYER));

        ANCIENT_SOUL_HUNTER_ARMOR = new AncientSoulHunterArmorModel(event.getEntityModels().bakeLayer(AncientSoulHunterArmorModel.LAYER));
        ANCIENT_SOUL_STAINED_STEEL_ARMOR = new AncientSoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(AncientSoulStainedSteelArmorModel.LAYER));

        PRIDEWEAR = new PridewearArmorModel(event.getEntityModels().bakeLayer(PridewearArmorModel.LAYER));
        SLIM_PRIDEWEAR = new SlimPridewearArmorModel(event.getEntityModels().bakeLayer(SlimPridewearArmorModel.LAYER));

        TOP_HAT = new TopHatModel(event.getEntityModels().bakeLayer(TopHatModel.LAYER));
        TAIL_MODEL = new TailModel(event.getEntityModels().bakeLayer(TailModel.LAYER));

        HEAD_OVERLAY_MODEL = new HeadOverlayModel(event.getEntityModels().bakeLayer(HeadOverlayModel.LAYER));
        SCARF = new ScarfModel(event.getEntityModels().bakeLayer(ScarfModel.LAYER));
    }
}
