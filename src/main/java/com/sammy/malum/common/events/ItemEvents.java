package com.sammy.malum.common.events;

import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
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
    public static void scytheSweep(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getTrueSource();
            if (playerEntity.swingingHand != null)
            {
                ItemStack stack = playerEntity.getHeldItem(playerEntity.swingingHand);
                if (stack.getItem() instanceof ScytheItem)
                {
                    float multiplier = 0.4f;
                    float damage = 1.0F + (event.getAmount() * multiplier) + (event.getAmount() * EnchantmentHelper.getSweepingDamageRatio(playerEntity));
                    for (LivingEntity livingentity : playerEntity.world.getEntitiesWithinAABB(LivingEntity.class, event.getEntityLiving().getBoundingBox().grow(2.0D, 0.25D, 2.0D)))
                    {
                        if (livingentity != playerEntity && livingentity != event.getEntityLiving() && !playerEntity.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && playerEntity.getDistanceSq(livingentity) < 9.0D)
                        {
                            livingentity.applyKnockback(0.4F, MathHelper.sin(playerEntity.rotationYaw * ((float) Math.PI / 180F)), (-MathHelper.cos(playerEntity.rotationYaw * ((float) Math.PI / 180F))));
                            livingentity.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), damage);
                        }
                    }
                    
                    playerEntity.world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), MalumSounds.SCYTHE_STRIKE, playerEntity.getSoundCategory(), 1.0F, 0.9f + playerEntity.world.rand.nextFloat() * 0.2f);
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
