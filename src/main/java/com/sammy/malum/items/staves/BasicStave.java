package com.sammy.malum.items.staves;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.SpiritConsumer;
import com.sammy.malum.SpiritDescription;
import com.sammy.malum.capabilities.MalumDataProvider;
import com.sammy.malum.items.curios.CurioNetherborneCapacitor;
import com.sammy.malum.items.curios.CurioVampireNecklace;
import com.sammy.malum.items.staves.effects.ModEffect;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

import static com.sammy.malum.ClientHandler.makeTranslationComponent;
import static com.sammy.malum.MalumHelper.getClosestEntity;
import static com.sammy.malum.capabilities.MalumDataProvider.*;

public abstract class BasicStave extends Item implements SpiritConsumer, SpiritDescription
{
    
    public enum staveOptionEnum
    {
        spiritHarvest(0, "malum.tooltip.stave.option.a"), augmentFunction(1, "malum.tooltip.stave.option.b"), blockBinding(2, "malum.tooltip.stave.option.c"), none(3, "malum.tooltip.stave.option.d");
    
        public final int type;
        public final String string;
    
        staveOptionEnum(int type, String string)
        {
            this.type = type;
            this.string = string;
        }
    }
    public static staveOptionEnum getEnum(int i)
    {
        int length = staveOptionEnum.values().length;
        int index = (length + i % length) % length;
        return staveOptionEnum.values()[index];
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

    ModEffect effect;
    public BasicStave(Properties builder, ModEffect effect)
    {
        super(builder);
        this.effect = effect;
    }

    //region HARVESTING

    public boolean isEntityValid(PlayerEntity playerEntity, LivingEntity livingEntity)
    {
        if (livingEntity.getHealth() > playerEntity.getHealth() && !playerEntity.isCreative())
        {
            if (!CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof CurioNetherborneCapacitor, playerEntity).isPresent())
            {
                return false;
            }
        }
        return !getHusk(livingEntity);
    }
    public static LivingEntity findEntity(PlayerEntity player)
    {
        World world = player.world;
        Vector3d pos = player.getPositionVec();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, new AxisAlignedBB(pos.x - 10, pos.y - 10, pos.z - 10, pos.x + 10, pos.y + 10, pos.z + 10));
        List<Entity> finalList = new ArrayList<>();
        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (entity instanceof LivingEntity)
                {
                    BasicStave stave = null;
                    if (player.getHeldItemMainhand().getItem() instanceof BasicStave)
                    {
                        stave = (BasicStave) player.getHeldItemMainhand().getItem();
                    }
                    if (player.getHeldItemOffhand().getItem() instanceof BasicStave)
                    {
                        stave = (BasicStave) player.getHeldItemOffhand().getItem();
                    }
                    if (stave == null)
                    {
                        return null;
                    }
                    if (stave.isEntityValid(player, (LivingEntity) entity))
                    {
                        Vector3d vecA = player.getLookVec().normalize();
                        Vector3d vecB = (entity.getPositionVec().subtract(player.getPositionVec())).normalize();
                        double angle = 2.0d * Math.atan((vecA.subtract(vecB)).length() / (vecA.add(vecB)).length());
                        if (angle <= 0.6f && angle >= -0.6f)
                        {
                            finalList.add(entity);
                        }
                    }
                }
            }
        }
        if (!finalList.isEmpty())
        {
            return (LivingEntity) getClosestEntity(finalList, player.getPositionVec());
        }
        return null;
    }
    //endregion
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
        if (playerEntity.isSneaking() && effect != null)
        {
            if (effect.type() == ModEffect.effectTypeEnum.blockInteraction)
            {
                BlockState state = context.getWorld().getBlockState(context.getPos());
                ItemStack stack = context.getItem();
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
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.isSneaking())
        {
            if (worldIn.isRemote())
            {
                ClientHandler.spiritHarvestStart(playerIn);
            }
            playerIn.setActiveHand(handIn);
        }
        else if (effect != null)
        {
            if (effect.type() == ModEffect.effectTypeEnum.rightClick)
            {
                playerIn.swingArm(handIn);
                effect.effect(playerIn, itemstack);
                playerIn.addStat(Stats.ITEM_USED.get(playerIn.getHeldItemMainhand().getItem()));
                playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItemMainhand().getItem(), effect.cooldown());
                return ActionResult.resultSuccess(itemstack);
            }
        }
        return ActionResult.resultSuccess(itemstack);
    }
}