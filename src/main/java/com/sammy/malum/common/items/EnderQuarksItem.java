package com.sammy.malum.common.items;

import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EnderQuarksItem extends Item
{
    public EnderQuarksItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if (entityIn instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if (playerEntity.getActivePotionEffect(MalumEffects.ENDER_LINK.get()) == null || (stack.getOrCreateTag().contains("expiryDate") && worldIn.getGameTime() >= stack.getTag().getLong("expiryDate")))
            {
                if (stack.getOrCreateTag().contains("x"))
                {
                    stack.getTag().remove("x");
                    stack.getTag().remove("y");
                    stack.getTag().remove("z");
                }
                if (stack.getTag().contains("expiryDate"))
                {
                    stack.getTag().remove("expiryDate");
                }
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains("x") && playerIn.getActivePotionEffect(MalumEffects.ENDER_LINK.get()) != null)
        {
            double x = nbt.getDouble("x");
            double y = nbt.getDouble("y");
            double z = nbt.getDouble("z");
    
            for (int i = 0; i < 32; ++i)
            {
                playerIn.world.addParticle(ParticleTypes.PORTAL, x, y + playerIn.world.rand.nextDouble() * 2.0D, z, playerIn.world.rand.nextGaussian(), 0.0D, playerIn.world.rand.nextGaussian());
                playerIn.world.addParticle(ParticleTypes.PORTAL, playerIn.getPosX(), playerIn.getPosY() + playerIn.world.rand.nextDouble() * 2.0D, playerIn.getPosZ(), playerIn.world.rand.nextGaussian(), 0.0D, playerIn.world.rand.nextGaussian());
            }
            playerIn.removePotionEffect(MalumEffects.ENDER_LINK.get());
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(playerIn, x, y, z, 5.0F);
            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
            {
                playerIn.teleportKeepLoaded(x, y, z);
                playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1.75f);
                playerIn.attackEntityFrom(DamageSource.FALL, event.getAttackDamage());
            }
            nbt.remove("x");
            nbt.remove("y");
            nbt.remove("z");
            playerIn.swing(handIn, true);
            stack.shrink(1);
        }
        else
        {
            for (int i = 0; i < 32; ++i)
            {
                playerIn.world.addParticle(ParticleTypes.PORTAL, playerIn.getPosX(), playerIn.getPosY() + playerIn.world.rand.nextDouble() * 2.0D, playerIn.getPosZ(), playerIn.world.rand.nextGaussian(), 0.0D, playerIn.world.rand.nextGaussian());
            }
            playerIn.addPotionEffect(new EffectInstance(MalumEffects.ENDER_LINK.get(), 600, 0));
            playerIn.swing(handIn, true);
            playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1, 1f);
            nbt.putDouble("x", playerIn.getPositionVec().x);
            nbt.putDouble("y", playerIn.getPositionVec().y);
            nbt.putDouble("z", playerIn.getPositionVec().z);
            nbt.putLong("expiryDate", playerIn.world.getGameTime() + 600);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}