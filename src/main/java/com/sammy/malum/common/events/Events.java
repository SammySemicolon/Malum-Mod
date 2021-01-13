package com.sammy.malum.common.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.equipment.curios.CurioPoppetBelt;
import com.sammy.malum.common.items.equipment.poppets.BlessedPoppet;
import com.sammy.malum.common.items.equipment.poppets.PoppetItem;
import com.sammy.malum.common.items.equipment.poppets.PoppetOfUndying;
import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.totems.rites.IPoppetBlessing;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.antlr.v4.runtime.misc.Triple;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sammy.malum.common.items.equipment.poppets.PoppetItem.cast;

@Mod.EventBusSubscriber
public class Events
{
    
    @SubscribeEvent
    public static void blessedEffect(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.world.getGameTime() % 175L == 0)
            {
                if (playerEntity.world instanceof ServerWorld)
                {
                    ArrayList<ItemStack> poppets = new ArrayList<>();
                    if (playerEntity.getHeldItemOffhand().getItem() instanceof BlessedPoppet)
                    {
                        poppets.add(playerEntity.getHeldItemOffhand());
                    }
                    if (playerEntity.getHeldItemMainhand().getItem() instanceof BlessedPoppet)
                    {
                        poppets.add(playerEntity.getHeldItemMainhand());
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).isPresent())
                    {
                        ItemStack belt = CuriosApi.getCuriosHelper().findEquippedCurio(MalumItems.POPPET_BELT.get(), playerEntity).get().right;
                        SimpleInventory inventory = CurioPoppetBelt.create(belt);
                        poppets.addAll(MalumHelper.takeAll(inventory.nonEmptyStacks(), i -> i.getItem() instanceof BlessedPoppet));
                    }
                    poppets.forEach(p -> {
                        CompoundNBT nbt = p.getOrCreateTag();
                        if (nbt.contains("blessing"))
                        {
                            String blessingType = nbt.getString("blessing");
                            MalumRites.MalumRite rite = MalumRites.getRite(blessingType);
                            if (rite instanceof IPoppetBlessing)
                            {
                                ((IPoppetBlessing) rite).blessingEffect(playerEntity);
                            }
                        }
                    });
                }
            }
        }
    }
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            if (!event.getSource().canHarmInCreative())
            {
                PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
                ArrayList<Triple<ItemStack, SimpleInventory, Integer>> poppets = PoppetItem.poppets(playerEntity, i -> i.getItem() instanceof PoppetOfUndying);
                Optional<Triple<ItemStack, SimpleInventory, Integer>> poppetOfUndying = poppets.stream().findFirst();
                if (poppetOfUndying.isPresent())
                {
                    if (playerEntity instanceof ServerPlayerEntity)
                    {
                        ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerEntity;
                        serverplayerentity.addStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                        CriteriaTriggers.USED_TOTEM.trigger(serverplayerentity, poppetOfUndying.get().a);
                    }
                    playerEntity.setHealth(1.0F);
                    playerEntity.clearActivePotions();
                    playerEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
                    playerEntity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
                    playerEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
                    playerEntity.world.setEntityState(playerEntity, (byte) 35);
                    if (poppetOfUndying.get().b != null)
                    {
                        SimpleInventory inventory = poppetOfUndying.get().b;
                        inventory.setStackInSlot(poppetOfUndying.get().c, ItemStack.EMPTY);
                        inventory.writeData(inventory.beltItem.getOrCreateTag());
                    }
                    else
                    {
                        poppetOfUndying.get().a.shrink(1);
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntityLiving();
            if (playerEntity.world instanceof ServerWorld)
            {
                if (event.getSource().equals(MalumDamageSources.VOODOO))
                {
                    return;
                }
                ServerWorld world = (ServerWorld) playerEntity.world;
                ArrayList<Triple<ItemStack, SimpleInventory, Integer>> poppets = PoppetItem.poppets(playerEntity, i -> i.getItem() instanceof PoppetItem);
                MalumHelper.takeAll(poppets, p -> cast(p.a).onlyDirect()).forEach(p -> {
                    if (event.getSource().getTrueSource() instanceof LivingEntity)
                    {
                        cast(p.a).effect(p.a, event, world, playerEntity, (LivingEntity) event.getSource().getTrueSource(),p.b,p.c);
                    }
                });
                ArrayList<LivingEntity> targets = (ArrayList<LivingEntity>) world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(playerEntity.getPosition()).grow(12)).stream().filter(e -> !world.getBlockState(e.getPosition().down()).getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get())).collect(Collectors.toList());
                targets.remove(playerEntity);
                poppets.forEach(p -> cast(p.a).effect(p.a, event, world, playerEntity, targets, p.b,p.c));
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
    @SubscribeEvent
    public static void knockBack(LivingKnockBackEvent event)
    {
        if (event.getEntityLiving().getActivePotionEffect(MalumEffects.WARDING.get()) != null)
        {
            event.setStrength(0);
        }
    }
    @SubscribeEvent
    public static void explode(ExplosionEvent.Start event)
    {
        if (event.getExplosion().getExploder() instanceof CreeperEntity)
        {
            CreeperEntity entity = (CreeperEntity) event.getExplosion().getExploder();
            if (entity.getActivePotionEffect(MalumEffects.WARDING.get()) != null)
            {
                event.setCanceled(true);
            }
        }
    }
}
