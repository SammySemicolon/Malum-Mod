package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.SpiritData.makeDefaultTooltip;
import static com.kittykitcatcat.malum.SpiritData.spiritProperty;
import static net.minecraft.util.Hand.MAIN_HAND;

@Mod.EventBusSubscriber
public class EnderStaveItem extends SpiritwoodStaveItem
{
    public EnderStaveItem(Properties builder)
    {
        super(builder);
        this.addPropertyOverride(new ResourceLocation("spirit"), spiritProperty);
    }
    @Override
    public boolean isDefaultFunction(ItemStack stack)
    {
        return !stack.getOrCreateTag().contains("entityRegistryName");
    }

    @Override
    public boolean canUseAltFunction(ItemStack stack)
    {
        return stack.getOrCreateTag().getString("entityRegistryName").equals("minecraft:enderman") && stack.getOrCreateTag().getFloat("purity") > 0;
    }

    @Override
    public boolean canDrain(ItemStack stack, LivingEntity entity)
    {
        return entity.getType().getRegistryName().toString().equals("minecraft:enderman") && !stack.getOrCreateTag().contains("entityRegistryName");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ArrayList<ITextComponent> arrayList = new ArrayList<>();
        arrayList.add(new TranslationTextComponent("malum.tooltip.only.desc").applyTextStyle(TextFormatting.GRAY)
                .appendSibling(new StringTextComponent(" " + EntityType.ENDERMAN.getName().getString()).applyTextStyle(TextFormatting.DARK_PURPLE)));
        arrayList.add(new TranslationTextComponent("malum.tooltip.use.desc").applyTextStyle(TextFormatting.GRAY)
                .appendSibling(new StringTextComponent(" " + EntityType.ENDERMAN.getName().getString() + " ").applyTextStyle(TextFormatting.DARK_PURPLE))
                .appendSibling(new TranslationTextComponent("malum.tooltip.teleport.desc").applyTextStyle(TextFormatting.GRAY)));
        makeDefaultTooltip(stack, worldIn, tooltip, flagIn,arrayList);
    }

    @SubscribeEvent
    public static void zoomIn(FOVUpdateEvent event)
    {
        if (event.getEntity() != null)
        {
            PlayerEntity player = event.getEntity();
            if (player.getHeldItemMainhand().getItem() instanceof EnderStaveItem)
            {
                float teleportChargeTime = CapabilityValueGetter.getTeleportChargeTime(player);
                event.setNewfov(event.getFov() - teleportChargeTime / 32);
            }
        }
    }

    @SubscribeEvent
    public static void stopTeleport(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof EnderStaveItem)
            {
                if (CapabilityValueGetter.getTeleportChargeTime(player) > 0)
                {
                    CapabilityValueGetter.setTeleportChargeTime(player, 0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void chargeTeleport(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof EnderStaveItem)
            {
                SpiritwoodStaveItem item = (SpiritwoodStaveItem) player.getHeldItemMainhand().getItem();
                if (!item.isDefaultFunction(player.getHeldItemMainhand()))
                {
                    boolean hasTeleported = attemptTeleport(player);
                    if (hasTeleported)
                    {
                        SpiritData.decreaseSpiritPurity(player,player.getHeldItemMainhand().getOrCreateTag(),0.01f);
                        event.setCanceled(true);
                    }
                }
            }
            else if (CapabilityValueGetter.getTeleportChargeTime(player) > 0) //FAIL
            {
                CapabilityValueGetter.setTeleportChargeTime(player, 0);
            }
        }
    }

    public static boolean attemptTeleport(PlayerEntity player)
    {
        float teleportChargeTime = CapabilityValueGetter.getTeleportChargeTime(player);
        CapabilityValueGetter.setTeleportChargeTime(player, teleportChargeTime + 1);
        player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10, 0, true, false));
        if (teleportChargeTime >= 20)
        {
            teleport(player);
            CapabilityValueGetter.setTeleportChargeTime(player, 0);
            return true;
        }
        return false;
    }

    public static void teleport(PlayerEntity player)
    {
        player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 8, 0, true, false));
        Vec3d newPosition = (player.getPositionVec());
        int distance = 12;

        for (int i = 0; i <= distance * 4; i++)
        {
            newPosition = MalumHelper.tryTeleportPlayer(player, player.getLookVec(), newPosition, i / 4);
        }
        player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 1F);
        player.teleportKeepLoaded(newPosition.x, newPosition.y, newPosition.z);
        player.world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1F, 1F);
        player.addStat(Stats.ITEM_USED.get(player.getHeldItemMainhand().getItem()));
        player.swingArm(MAIN_HAND);
        player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 8);
    }
}