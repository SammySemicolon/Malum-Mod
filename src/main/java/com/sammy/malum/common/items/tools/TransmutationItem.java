package com.sammy.malum.common.items.tools;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.function.Predicate;

public class TransmutationItem extends Item
{
    public TransmutationItem(Properties properties)
    {
        super(properties);
    }


    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft)
    {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
        ArrayList<ItemEntity> items = nearbyItems(worldIn, entityLiving);
        items.forEach(i ->
        {
            i.remove();
            ItemEntity newEntity = new ItemEntity(worldIn, i.getPosX(), i.getPosY(), i.getPosZ(), new ItemStack(Items.GOLD_INGOT, i.getItem().getCount()));
            if (MalumHelper.areWeOnClient(worldIn))
            {
                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setAlpha(0.5f, 0)
                        .setScale(0.25f, 0)
                        .setLifetime(20)
                        .randomOffset(0.125, 0.125)
                        .randomVelocity(0.015f, 0.015f)
                        .setColor(MalumSpiritTypes.MAGIC_SPIRIT_COLOR, MalumSpiritTypes.DEATH_SPIRIT_COLOR)
                        .repeat(worldIn, i.getPosX(), i.getPosY() + i.getHeight() + 0.2f, i.getPosZ(), 16);

                ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.25f, 0)
                        .setScale(0.5f, 0)
                        .setLifetime(20)
                        .setColor(MalumHelper.brighter(MalumSpiritTypes.LIFE_SPIRIT_COLOR, 3), MalumSpiritTypes.DEATH_SPIRIT_COLOR)
                        .repeat(worldIn, i.getPosX(), i.getPosY() + i.getHeight() + 0.2f, i.getPosZ(), 4 + random.nextInt(4));

            }
            worldIn.addEntity(newEntity);
        });
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count)
    {
        ArrayList<ItemEntity> items = nearbyItems(player.world, player);
        items.forEach(i ->
        {
            if (MalumHelper.areWeOnClient(player.world))
            {
                if (player.world.getGameTime() % 4L == 0)
                {
                    ParticleManager.create(MalumParticles.CIRCLE_PARTICLE)
                            .setAlpha(0.2f, 0f)
                            .setLifetime(10)
                            .setSpin(MathHelper.nextFloat(player.world.rand, -0.1f, 0.1f))
                            .setScale(0.4f, 0)
                            .setColor(MalumSpiritTypes.MAGIC_SPIRIT_COLOR, MalumSpiritTypes.DEATH_SPIRIT_COLOR)
                            .enableNoClip()
                            .repeat(player.world, i.getPosX(), i.getPosY() + i.getHeight() / 2, i.getPosZ(), 4);
                }
            }
        });
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(itemstack);
    }
    public ArrayList<ItemEntity> nearbyItems(World world, LivingEntity playerEntity)
    {
        return (ArrayList<ItemEntity>) world.getEntitiesWithinAABB(ItemEntity.class, playerEntity.getBoundingBox().grow(4,4,4));
    }
    public ArrayList<ItemEntity> nearbyItems(World world, LivingEntity playerEntity, Predicate<ItemEntity> predicate)
    {
        return (ArrayList<ItemEntity>) world.getEntitiesWithinAABB(ItemEntity.class, playerEntity.getBoundingBox().grow(4,4,4), predicate);
    }
}