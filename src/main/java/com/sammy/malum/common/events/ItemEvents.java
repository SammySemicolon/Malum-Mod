package com.sammy.malum.common.events;

import com.sammy.malum.common.entities.ScytheBoomerangEntity;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents
{
    @SubscribeEvent
    public static void onEntityHit(LivingHurtEvent event)
    {
        
        ItemStack stack = ItemStack.EMPTY;
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
            if (attacker.swingingHand != null)
            {
                stack = attacker.getHeldItem(attacker.swingingHand);
            }
            if (attacker.isHandActive() && stack.isEmpty())
            {
                stack = attacker.getActiveItemStack();
            }
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                stack = entity.scythe;
            }
        }
        EffectInstance existingBleeding = event.getEntityLiving().getActivePotionEffect(MalumEffects.BLEEDING.get());
        if (existingBleeding != null)
        {
            event.setAmount(event.getAmount() * 1 + (existingBleeding.getAmplifier() + 1) / 4f);
        }
        if (stack.getItem() instanceof ScytheItem)
        {
            int outflow = EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.OUTFLOW.get(), stack);
            if (outflow > 0)
            {
                int amount = 20;
                if (existingBleeding != null)
                {
                    amount += existingBleeding.getDuration();
                }
                event.getEntityLiving().addPotionEffect(new EffectInstance(MalumEffects.BLEEDING.get(),amount,outflow-1));
            }
        }
    }
    @SubscribeEvent
    public static void scytheSweep(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
    
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                playerEntity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), MalumSounds.SCYTHE_STRIKE, entity.getSoundCategory(), 1.0F, 0.9f + playerEntity.world.rand.nextFloat() * 0.2f);
    
            }
            else if (playerEntity.swingingHand != null)
            {
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem() instanceof ScytheItem)
                {
                    ScytheItem item = (ScytheItem) stack.getItem();
                    item.sweepingEdgeTypeBeat(playerEntity, event.getEntityLiving(), (float) playerEntity.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    playerEntity.spawnSweepParticles();
                }
            }
        }
    }
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (state.getBlock() instanceof IAlwaysActivatedBlock)
        {
            if (event.getItemStack().getItem() instanceof BlockItem)
            {
                if (event.getPlayer().isSneaking())
                {
                    return;
                }
            }
            state.getBlock().onBlockActivated(state, event.getWorld(), event.getPos(), event.getPlayer(), event.getHand(), null);
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
        }
    }
}
