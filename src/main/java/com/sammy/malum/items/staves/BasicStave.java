package com.sammy.malum.items.staves;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDescription;
import com.sammy.malum.items.staves.effects.ModEffect;
import com.sammy.malum.items.utility.ConfigurableItem;
import com.sammy.malum.items.utility.Option;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BasicStave extends ConfigurableItem implements SpiritConsumer, SpiritDescription
{
    public ArrayList<Option> options;
    ModEffect effect;
    
    public BasicStave(Properties builder, ModEffect effect)
    {
        super(builder);
        this.effect = effect;
        options = new ArrayList<>();
        addOption(0, "malum.tooltip.stave.option.a"); //spirit harvest
        addOption(1, "malum.tooltip.stave.option.b"); //augment function
        addOption(2, "malum.tooltip.stave.option.c"); //block configuration
        addOption(3, "malum.tooltip.stave.option.d"); //none
    }
    
    @Override
    public int durability()
    {
        if (effect != null)
        {
            return effect.durability();
        }
        return 0;
    }
    
    @Override
    public String spirit()
    {
        if (effect != null)
        {
            return effect.spirit();
        }
        return null;
    }
    
    @Override
    public ArrayList<ITextComponent> components()
    {
        if (effect != null)
        {
            return effect.components();
        }
        return null;
    }
    
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IWorldReader world, BlockPos pos, PlayerEntity player)
    {
        return true;
    }
    
    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.BOW;
    }
    
    public int getUseDuration(ItemStack stack)
    {
        return 72000;
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack stack = context.getItem();
        if (getOption(stack).option == 1 && effect != null)
        {
            if (effect.type() == ModEffect.effectTypeEnum.blockInteraction)
            {
                BlockState state = context.getWorld().getBlockState(context.getPos());
                playerEntity.swingArm(context.getHand());
                effect.effect(playerEntity, context.getItem(), state);
                playerEntity.addStat(Stats.ITEM_USED.get(stack.getItem()));
                playerEntity.getCooldownTracker().setCooldown(stack.getItem(), effect.cooldown());
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (getOption(stack).option == 0)
        {
            if (worldIn.isRemote())
            {
                ClientHandler.spiritHarvestStart(playerIn);
            }
            playerIn.setActiveHand(handIn);
        }
        if (getOption(stack).option == 1 && effect != null)
        {
            if (effect.type() == ModEffect.effectTypeEnum.rightClick)
            {
                playerIn.swingArm(handIn);
                effect.effect(playerIn, stack);
                playerIn.addStat(Stats.ITEM_USED.get(playerIn.getHeldItemMainhand().getItem()));
                playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItemMainhand().getItem(), effect.cooldown());
                return ActionResult.resultSuccess(stack);
            }
        }
        return ActionResult.resultSuccess(stack);
    }
}