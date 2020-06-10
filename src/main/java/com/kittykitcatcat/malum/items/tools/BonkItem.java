package com.kittykitcatcat.malum.items.tools;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModSounds;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

import static com.kittykitcatcat.malum.MalumHelper.entityFacingPlayer;

public class BonkItem extends SwordItem
{
    public BonkItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties)
    {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        ModifierEventHandler.makeDefaultTooltip(stack, worldIn, tooltip, flagIn);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
    {
        if (attacker instanceof PlayerEntity)
        {
            target.addVelocity(attacker.getLookVec().x * 2, 0.2, attacker.getLookVec().z * 2);
            attacker.world.playSound(null, attacker.getPosition(), ModSounds.bonk, SoundCategory.PLAYERS, 1F, MathHelper.nextFloat(attacker.world.rand, 1.5f, 2.0f));
            if (attacker.getEntityWorld() instanceof ServerWorld)
            {
                ((ServerWorld) attacker.getEntityWorld()).spawnParticle(new BonkParticleData(), entityFacingPlayer(target, (PlayerEntity) attacker).x + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), entityFacingPlayer(target, (PlayerEntity) attacker).y + 1 + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), entityFacingPlayer(target, (PlayerEntity) attacker).z + MathHelper.nextFloat(MalumMod.random, -0.25f, 0.25f), 1, 0, 0, 0, 0);
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}
