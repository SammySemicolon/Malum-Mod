package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blockentity.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import com.sammy.malum.common.item.equipment.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.packets.particle.block.blight.BlightTransformItemParticlePacket;
import com.sammy.malum.core.systems.item.ItemSkin;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.*;
import java.util.function.Function;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemSkinRegistry {

    public static final Map<String, ItemSkin> SKINS = new LinkedHashMap<>();
    public static Map<ItemSkin, ItemSkin.ItemSkinDatagenData> SKIN_DATAGEN_DATA = new HashMap<>();

    @SubscribeEvent
    public static void wipeCache(InterModEnqueueEvent event) {
        SKIN_DATAGEN_DATA = null;
    }

    @SubscribeEvent
    public static void registerItemSkins(FMLCommonSetupEvent event) {
        registerPridewear("ace", ItemRegistry.ACE_PRIDEWEAVE.get());
        registerPridewear("agender", ItemRegistry.AGENDER_PRIDEWEAVE.get());
        registerPridewear("aro", ItemRegistry.ARO_PRIDEWEAVE.get());
        registerPridewear("aroace", ItemRegistry.AROACE_PRIDEWEAVE.get());
        registerPridewear("bi", ItemRegistry.BI_PRIDEWEAVE.get());
        registerPridewear("demiboy", ItemRegistry.DEMIBOY_PRIDEWEAVE.get());
        registerPridewear("demigirl", ItemRegistry.DEMIGIRL_PRIDEWEAVE.get());
        registerPridewear("enby", ItemRegistry.ENBY_PRIDEWEAVE.get());
        registerPridewear("gay", ItemRegistry.GAY_PRIDEWEAVE.get());
        registerPridewear("genderfluid", ItemRegistry.GENDERFLUID_PRIDEWEAVE.get());
        registerPridewear("genderqueer", ItemRegistry.GENDERQUEER_PRIDEWEAVE.get());
        registerPridewear("intersex", ItemRegistry.INTERSEX_PRIDEWEAVE.get());
        registerPridewear("lesbian", ItemRegistry.LESBIAN_PRIDEWEAVE.get());
        registerPridewear("pan", ItemRegistry.PAN_PRIDEWEAVE.get());
        registerPridewear("plural", ItemRegistry.PLURAL_PRIDEWEAVE.get());
        registerPridewear("poly", ItemRegistry.POLY_PRIDEWEAVE.get());
        registerPridewear("pride", ItemRegistry.PRIDE_PRIDEWEAVE.get());
        registerPridewear("trans", ItemRegistry.TRANS_PRIDEWEAVE.get());

        registerItemSkin("executioner_drip",
                new ItemSkin(
                        LodestoneArmorItem.class, ItemRegistry.DREADED_WEAVE.get(),
                        p -> malumPath("textures/armor/cosmetic/starstorm_executioner.png"),
                        p -> ModelRegistry.EXECUTIONER),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/executioner_",
                        "malum:models/item/executioner_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("commando_drip",
                new ItemSkin(
                        LodestoneArmorItem.class, ItemRegistry.CORNERED_WEAVE.get(),
                        p -> malumPath("textures/armor/cosmetic/commando.png"),
                        p -> ModelRegistry.COMMANDO),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/commando_",
                        "malum:models/item/commando_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ultrakill_v1",
                new ItemSkin(
                        LodestoneArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V1.get(),
                        p -> malumPath("textures/armor/cosmetic/v1.png"),
                        p -> ModelRegistry.ULTRAKILL_MACHINE),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v1_",
                        "malum:models/item/v1_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ultrakill_v2",
                new ItemSkin(
                        LodestoneArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V2.get(),
                        p -> malumPath("textures/armor/cosmetic/v2.png"),
                        p -> ModelRegistry.ULTRAKILL_MACHINE),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v2_",
                        "malum:models/item/v2_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin("ancient_cloth",
                new ItemSkin(
                        SoulHunterArmorItem.class, ItemRegistry.ANCIENT_WEAVE.get(),
                        p -> malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"),
                        p -> ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_hunter_",
                        "malum:models/item/ancient_soul_hunter_",
                        "cloak", "robe", "leggings", "boots"
                ));

        registerItemSkin("ancient_metal",
                new ItemSkin(
                        SoulStainedSteelArmorItem.class, ItemRegistry.ANCIENT_WEAVE.get(),
                        p -> malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"),
                        p -> ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_stained_steel_",
                        "malum:models/item/ancient_soul_stained_steel_",
                        "helmet", "chestplate", "leggings", "boots"
                ));
    }

    public static InteractionResult performAugmentation(IAltarProvider altarProvider, Player player, InteractionHand hand) {
        LodestoneBlockEntityInventory inventory = altarProvider.getInventoryForAltar();
        Level level = player.level;
        ItemStack target = inventory.getStackInSlot(0);
        if (!target.isEmpty()) {
            ItemStack weave = player.getItemInHand(hand);
            String skinTag = ItemSkinRegistry.getApplicableItemSkinTag(target, weave);
            if (skinTag != null) {
                if (!level.isClientSide) {
                    Random random = level.random;
                    CompoundTag tag = target.getOrCreateTag();
                    CompoundTag modifiedTag = tag.copy();
                    modifiedTag.putString(ItemSkin.MALUM_SKIN_TAG, skinTag);
                    if (tag.equals(modifiedTag)) {
                        return InteractionResult.FAIL;
                    }
                    target.setTag(modifiedTag);
                    Vec3 pos = altarProvider.getItemPosForAltar();
                    ItemEntity pEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), target);
                    pEntity.setDeltaMovement(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0.25f, 0.5f), Mth.nextFloat(random, -0.1F, 0.1F));
                    MALUM_CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(altarProvider.getBlockPosForAltar())), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.identifier), pos));
                    level.playSound(null, altarProvider.getBlockPosForAltar(), SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);

                    level.addFreshEntity(pEntity);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                    if (!player.isCreative()) {
                        weave.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static String getApplicableItemSkinTag(ItemStack target, ItemStack weave) {
        for (Map.Entry<String, ItemSkin> entry : SKINS.entrySet()) {
            if (entry.getValue().validArmorClass.isInstance(target.getItem()) && entry.getValue().weaveItem.equals(weave.getItem())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static ItemSkin getAppliedItemSkin(ItemStack stack) {
        return stack.hasTag() ? SKINS.get(stack.getTag().getString(ItemSkin.MALUM_SKIN_TAG)) : null;
    }

    public static ItemSkin registerItemSkin(String tag, ItemSkin skin) {
        SKINS.put(tag, skin);
        return skin;
    }

    public static ItemSkin registerItemSkin(String tag, ItemSkin skin, ItemSkin.ItemSkinDatagenData datagenData) {
        SKINS.put(tag, skin);
        if (DatagenModLoader.isRunningDataGen()) {
            SKIN_DATAGEN_DATA.put(skin, datagenData);
        }
        return skin;
    }

    public static void registerPridewear(String tag, Item prideweave) {
        String drip = tag + "_drip";

        Function<LivingEntity, ResourceLocation> armorTextureFunction = p -> {
            if (p instanceof AbstractClientPlayer clientPlayer && clientPlayer.getModelName().equals("slim")) {
                return malumPath("textures/armor/cosmetic/pride/" + drip + "_slim.png");
            }
            return malumPath("textures/armor/cosmetic/pride/" + drip + ".png");
        };
        Function<LivingEntity, LodestoneArmorModel> armorModelFunction = p -> {
            if (p instanceof AbstractClientPlayer clientPlayer && clientPlayer.getModelName().equals("slim")) {
                return ModelRegistry.SLIM_PRIDEWEAR;
            }
            return ModelRegistry.PRIDEWEAR;
        };
        registerItemSkin(drip,
                new ItemSkin(LodestoneArmorItem.class, prideweave, armorTextureFunction, armorModelFunction),
                new ItemSkin.ItemSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/pride/"+tag+"_",
                        "malum:models/item/pridewear"+tag+"_",
                        "beanie", "hoodie", "shorts", "socks"
                ));
    }
}
