package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticleData;
import com.kittykitcatcat.malum.particles.soulharvestparticle.SoulHarvestParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

import static com.kittykitcatcat.malum.SpiritData.*;
import static net.minecraft.util.Hand.MAIN_HAND;

@Mod.EventBusSubscriber
public class SpiritwoodStaveItem extends Item
{
    public SpiritwoodStaveItem(Properties properties)
    {
        super(properties);
        this.addPropertyOverride(new ResourceLocation("spirit"), spiritProperty);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        makeDefaultTooltip(stack, worldIn, tooltip, flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public boolean isDefaultFunction(ItemStack stack)
    {
        return true;
    }
    public boolean canUseAltFunction(ItemStack stack)
    {
        return true;
    }
    public boolean canSuck(ItemStack stack, LivingEntity entity)
    {
        return !stack.getOrCreateTag().contains("entityRegistryName");
    }
    //VISUAL AND CLIENT STUFF
    @SubscribeEvent
    public static void zoomIn(FOVUpdateEvent event)
    {
        if (event.getEntity() != null)
        {
            PlayerEntity player = event.getEntity();
            if (player.getHeldItemMainhand().getItem() instanceof SpiritwoodStaveItem)
            {
                SpiritwoodStaveItem item = (SpiritwoodStaveItem) player.getHeldItemMainhand().getItem();
                if (item.isDefaultFunction(player.getHeldItemMainhand()))
                {
                    float drainProgress = CapabilityValueGetter.getDrainProgress(player);
                    event.setNewfov(event.getFov() - drainProgress / 300);
                }
            }
        }
    }

    @SubscribeEvent
    public static void previewTarget(PlayerEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof SpiritwoodStaveItem)
            {
                SpiritwoodStaveItem item = (SpiritwoodStaveItem) player.getHeldItemMainhand().getItem();
                if (item.isDefaultFunction(player.getHeldItemMainhand()))
                {
                    if (findEntity(player) != null)
                    {
                        LivingEntity target = findEntity(player);
                        if (item.canSuck(player.getHeldItemMainhand(), target))
                        {
                            target.addPotionEffect(new EffectInstance(Effects.GLOWING, 5, 1, true, false));
                        }
                    }
                }
            }
        }
    }

    //LOGIC HANDLING
    @SubscribeEvent
    public static void cancelDrain(LivingEntityUseItemEvent.Start event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof SpiritwoodStaveItem)
            {
                SpiritwoodStaveItem item = (SpiritwoodStaveItem) player.getHeldItemMainhand().getItem();
                if (item.isDefaultFunction(player.getHeldItemMainhand()))
                {
                    if (findEntity(player) == null)
                    {
                        event.setCanceled(true);
                    }
                    else
                    {
                        if (!item.canSuck(player.getHeldItemMainhand(), findEntity(player)))
                        {
                            event.setCanceled(true);
                        }
                    }
                    if (CapabilityValueGetter.getDrainProgress(player) > 0)
                    {
                        CapabilityValueGetter.setDrainProgress(player, 0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void stopDrain(LivingEntityUseItemEvent.Stop event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof SpiritwoodStaveItem)
            {
                if (CapabilityValueGetter.getDrainProgress(player) > 0)
                {
                    CapabilityValueGetter.setDrainProgress(player, 0);
                    stopDrain(player);
                }
            }
        }
    }

    @SubscribeEvent
    public static void drainSoul(LivingEntityUseItemEvent.Tick event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.getHeldItemMainhand().getItem() instanceof SpiritwoodStaveItem)
            {
                SpiritwoodStaveItem item = (SpiritwoodStaveItem) player.getHeldItemMainhand().getItem();
                if (item.isDefaultFunction(player.getHeldItemMainhand()))
                {

                    if (findEntity(player) != null)
                    {
                        if (!item.canSuck(player.getHeldItemMainhand(), findEntity(player)))
                        {
                            event.setCanceled(true);
                        }
                        LivingEntity target = findEntity(player);
                        attemptDrain(player, target);
                    }
                    else
                    {
                        CapabilityValueGetter.setDrainProgress(player, 0);
                        stopDrain(player);
                        event.setCanceled(true);
                    }
                }
            }
            else if (CapabilityValueGetter.getDrainProgress(player) > 0) //FAIL
            {
                CapabilityValueGetter.setDrainProgress(player, 0);
                stopDrain(player);
            }
        }
    }

    public static boolean isEntityValid(LivingEntity target)
    {
        return (target.getHealth() <= target.getMaxHealth() / 10 || target.getHealth() <= 2) && !CapabilityValueGetter.getIsHusk(target);
    }

    public static LivingEntity findEntity(PlayerEntity player)
    {
        World world = player.world;

        Vec3d pos = player.getPositionVector();
        float strength = 5f;
        Vec3d looKVec = pos.add(player.getLookVec().mul(strength / 2, strength / 2, strength / 2));
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - strength, pos.y - strength, pos.z - strength, pos.x + strength, pos.y + strength, pos.z + strength));
        if (!list.isEmpty())
        {
            Entity entity = MalumHelper.getClosestEntity(list, looKVec.x, looKVec.y, looKVec.z);
            if (entity instanceof LivingEntity)
            {
                if (isEntityValid((LivingEntity) entity))
                {
                    return (LivingEntity) entity;
                }
            }
        }
        return null;
    }

    public static void attemptDrain(PlayerEntity player, LivingEntity target)
    {
        float drainProgress = CapabilityValueGetter.getDrainProgress(player);
        CapabilityValueGetter.setDrainProgress(player, drainProgress + 1);
        target.addPotionEffect(new EffectInstance(Effects.NAUSEA, 25, 1, true, false));
        if (drainProgress == 1)
        {
            startDrain(player);
        }
        if (drainProgress >= 85)
        {
            CapabilityValueGetter.setDrainProgress(player, 0);
            drainSoul(player, target);
        }
    }

    public static void drainSoul(PlayerEntity player, LivingEntity target)
    {
        float purity = 1f;
        SpiritData data = new SpiritData(target, purity);
        data.writeSpiritDataIntoNBT(player.getHeldItemMainhand().getOrCreateTag());
        CapabilityValueGetter.setIsHusk(target, true);
        target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 1200, 1));
        target.addPotionEffect(new EffectInstance(Effects.NAUSEA, 1200, 1, true, false));

        player.addStat(Stats.ITEM_USED.get(player.getHeldItemMainhand().getItem()));
        player.swingArm(MAIN_HAND);
        player.world.playSound(player, player.getPosition(), ModSounds.soul_harvest_success, SoundCategory.PLAYERS, 1F, 1F);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "soul_harvest_loop"), SoundCategory.PLAYERS));
        Vec3d particlePos = target.getPositionVec().add(0, target.getEyeHeight(), 0).subtract(player.getLookVec());
        player.world.addParticle(new SoulHarvestParticleData(), particlePos.getX(), particlePos.getY(), particlePos.getZ(), 0, 0, 0);
    }

    public static void startDrain(PlayerEntity player)
    {
        player.world.playSound(player, player.getPosition(), ModSounds.soul_harvest_loop, SoundCategory.PLAYERS, 1F, 1F);
    }

    public static void stopDrain(PlayerEntity player)
    {
        player.world.playSound(player, player.getPosition(), ModSounds.soul_harvest_fail, SoundCategory.PLAYERS, 1F, 1F);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().getSoundHandler().stop(new ResourceLocation(MalumMod.MODID, "soul_harvest_loop"), SoundCategory.PLAYERS));
        player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), 20);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (handIn.equals(MAIN_HAND))
        {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if (itemstack.getItem() instanceof SpiritwoodStaveItem)
            {
                if (((SpiritwoodStaveItem) itemstack.getItem()).isDefaultFunction(playerIn.getHeldItemMainhand()))
                {
                    if (!itemstack.getOrCreateTag().contains("entityRegistryName"))
                    {
                        if (findEntity(playerIn) != null)
                        {
                            playerIn.setActiveHand(handIn);
                        }
                    }
                }
                else if (((SpiritwoodStaveItem) itemstack.getItem()).canUseAltFunction(playerIn.getHeldItemMainhand()))
                {
                    playerIn.setActiveHand(handIn);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    //ACTUAL ITEM
    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }
}