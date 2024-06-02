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

public class ModelRegistry {


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

    }
}
