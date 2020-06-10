package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.boom.BoomParticleData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.entityFacingPlayer;
import static com.kittykitcatcat.malum.MalumHelper.makeTooltip;

@Mod.EventBusSubscriber
public class ModifierEventHandler
{

    public enum Modifiers
    {
        fiery ("fiery"), //swing hot magma when hitting enemies, heat up the tool when mining blocks, make go faster
        withering ("withering"), //wither and hunger enemies
        living ("living"), //repair tool when mining blocks
        shattering ("shattering"); //armor penetration
        final String name;
        Modifiers(String name)
        {
            this.name = name;
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static void makeDefaultTooltip(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ArrayList<ITextComponent> arrayList = new ArrayList<>();
        if (stack.getTag() != null)
        {
            for(Modifiers modifier : Modifiers.values())
            {
                if (hasModifier(stack, modifier))
                {
                    arrayList.add(new TranslationTextComponent("malum.tooltip.modifier." + modifier.name).applyTextStyle(TextFormatting.GRAY));
                    makeTooltip(stack, worldIn, tooltip, flagIn, arrayList);
                }
            }
        }
    }
    public static boolean hasModifier(ItemStack stack, Modifiers modifierEnum)
    {
        return stack.getOrCreateTag().contains("modifier") && stack.getOrCreateTag().getString("modifier").equals(modifierEnum.name);
    }
    @SubscribeEvent
    public static void attack(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity entity = (PlayerEntity) event.getSource().getTrueSource();
            if (hasModifier(entity.getHeldItem(entity.getActiveHand()), Modifiers.fiery))
            {
                if (!event.getEntityLiving().isBurning())
                {
                    event.getEntityLiving().setFire(4);
                    event.setAmount(event.getAmount() * 1.25f);
                    entity.world.playSound(null,entity.getPosX(), entity.getPosY(),entity.getPosZ(),SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 1,1.8f);
                    if (entity.world instanceof ServerWorld)
                    {
                        ((ServerWorld) entity.getEntityWorld()).spawnParticle(new BoomParticleData(), entityFacingPlayer(event.getEntityLiving(), entity).x + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), entityFacingPlayer(event.getEntityLiving(), entity).y + 1 + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), entityFacingPlayer(event.getEntityLiving(), entity).z + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), 1, 0, 0, 0, 0);
                    }
                }
            }
            if (hasModifier(entity.getHeldItem(entity.getActiveHand()), Modifiers.shattering))
            {
                event.setAmount(event.getAmount() + event.getEntityLiving().getTotalArmorValue() / 2f);
                entity.world.playSound(null,entity.getPosX(), entity.getPosY(),entity.getPosZ(), ModSounds.shattering_sound, SoundCategory.PLAYERS, 0.5f,2f);
            }
            if (hasModifier(entity.getHeldItem(entity.getActiveHand()), Modifiers.living))
            {
                if (MathHelper.nextInt(MalumMod.random, 0, 100) > 50)
                {
                    entity.heal(1);
                    entity.getHeldItem(entity.getActiveHand()).setDamage(entity.getHeldItem(entity.getActiveHand()).getDamage() - 2);
                }
            }
        }
    }
    @SubscribeEvent
    public static void blockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getPlayer() != null)
        {
            PlayerEntity playerEntity = event.getPlayer();
            if (hasModifier(playerEntity.getHeldItem(playerEntity.getActiveHand()), Modifiers.fiery))
            {
                if (MathHelper.nextInt(MalumMod.random, 0, 100) > 90)
                {
                    playerEntity.addPotionEffect(new EffectInstance(Effects.HASTE, 100, 1));

                    playerEntity.world.playSound(null,playerEntity.getPosX(), playerEntity.getPosY(),playerEntity.getPosZ(),SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 1,1.8f);
                }
            }
            if (hasModifier(playerEntity.getHeldItem(playerEntity.getActiveHand()), Modifiers.living))
            {
                if (MathHelper.nextInt(MalumMod.random, 0, 100) > 50)
                {
                    playerEntity.getHeldItem(playerEntity.getActiveHand()).setDamage(playerEntity.getHeldItem(playerEntity.getActiveHand()).getDamage() - 2);
                }
            }
        }
    }
}
