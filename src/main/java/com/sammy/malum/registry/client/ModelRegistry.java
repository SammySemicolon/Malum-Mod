package com.sammy.malum.registry.client;

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
import net.minecraft.client.Minecraft;

public class ModelRegistry {

    public static AncientSoulHunterArmorModel ANCIENT_SOUL_HUNTER_ARMOR;

    public static AncientSoulStainedSteelArmorModel ANCIENT_SOUL_STAINED_STEEL_ARMOR;

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

    public static void registerLayers() {
        GENERIC_SLIM_ARMOR = new GenericSlimArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(GenericSlimArmorModel.LAYER));
        GENERIC_ARMOR = new GenericArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(GenericArmorModel.LAYER));

        COMMANDO = new CommandoArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(CommandoArmorModel.LAYER));
        EXECUTIONER = new ExecutionerArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(ExecutionerArmorModel.LAYER));

        ULTRAKILL_MACHINE = new UltrakillMachineArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(UltrakillMachineArmorModel.LAYER));

        ANCIENT_SOUL_HUNTER_ARMOR = new AncientSoulHunterArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(AncientSoulHunterArmorModel.LAYER));
        ANCIENT_SOUL_STAINED_STEEL_ARMOR = new AncientSoulStainedSteelArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(AncientSoulStainedSteelArmorModel.LAYER));

        PRIDEWEAR = new PridewearArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(PridewearArmorModel.LAYER));
        SLIM_PRIDEWEAR = new SlimPridewearArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(SlimPridewearArmorModel.LAYER));

        TOP_HAT = new TopHatModel(Minecraft.getInstance().getEntityModels().bakeLayer(TopHatModel.LAYER));
        TAIL_MODEL = new TailModel(Minecraft.getInstance().getEntityModels().bakeLayer(TailModel.LAYER));

        HEAD_OVERLAY_MODEL = new HeadOverlayModel(Minecraft.getInstance().getEntityModels().bakeLayer(HeadOverlayModel.LAYER));
        SCARF = new ScarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ScarfModel.LAYER));
    }
}
