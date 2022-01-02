package com.sammy.malum.core.helper;

import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ItemHelper {
    public static ItemStack copyWithNewCount(ItemStack stack, int newCount) {
        ItemStack newStack = stack.copy();
        newStack.setCount(newCount);
        return newStack;
    }

    public static <T extends LivingEntity> boolean damageItem(ItemStack stack, int amount, T entityIn, Consumer<T> onBroken) {
        if (!entityIn.level.isClientSide && (!(entityIn instanceof Player) || !((Player) entityIn).getAbilities().instabuild)) {
            if (stack.isDamageableItem()) {
                amount = stack.getItem().damageItem(stack, amount, entityIn, onBroken);
                if (stack.hurt(amount, entityIn.getRandom(), entityIn instanceof ServerPlayer ? (ServerPlayer) entityIn : null)) {
                    onBroken.accept(entityIn);
                    Item item = stack.getItem();
                    stack.shrink(1);
                    if (entityIn instanceof Player) {
                        ((Player) entityIn).awardStat(Stats.ITEM_BROKEN.get(item));
                    }

                    stack.setDamageValue(0);
                    return true;
                }

            }
        }
        return false;
    }

    public static <T extends Entity> Entity getClosestEntity(List<T> entities, Vec3 pos) {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;

        for (T entity : entities) {
            double newDistance = entity.distanceToSqr(pos.x, pos.y, pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance) {
                cachedDistance = newDistance;
                resultEntity = entity;
            }

        }
        return resultEntity;
    }

    public static ArrayList<ItemStack> nonEmptyStackList(ArrayList<ItemStack> stacks) {
        ArrayList<ItemStack> nonEmptyStacks = new ArrayList<>();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                nonEmptyStacks.add(stack);
            }
        }
        return nonEmptyStacks;
    }

    public static void giveAmplifyingEffect(MobEffect effect, LivingEntity target, int duration, int amplifier, int cap) {
        MobEffectInstance instance = target.getEffect(effect);
        if (instance != null) {
            amplifier += instance.getAmplifier() + 1;
        }
        target.addEffect(new MobEffectInstance(effect, duration, Math.min(amplifier, cap)));
    }

    public static void giveStackingEffect(MobEffect effect, LivingEntity target, int duration, int amplifier) {
        MobEffectInstance instance = target.getEffect(effect);
        if (instance != null) {
            duration += instance.getDuration();
        }
        target.addEffect(new MobEffectInstance(effect, duration, amplifier));
    }

    public static void quietlyGiveItemToPlayer(Player player, @Nonnull ItemStack stack) {
        if (stack.isEmpty()) return;
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        Level level = player.level;
        ItemStack remainder = stack;
        if (!remainder.isEmpty()) {
            remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
        }
        if (!remainder.isEmpty() && !level.isClientSide) {
            ItemEntity entityitem = new ItemEntity(level, player.getX(), player.getY() + 0.5, player.getZ(), remainder);
            entityitem.setPickUpDelay(40);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));
            level.addFreshEntity(entityitem);
        }
    }

    @Nonnull
    public static Optional<ImmutableTriple<String, Integer, ItemStack>> findCosmeticCurio(Predicate<ItemStack> filter, @Nonnull final LivingEntity livingEntity) {
        ImmutableTriple<String, Integer, ItemStack> result = CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(handler ->
        {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();

            for (String id : curios.keySet()) {
                ICurioStacksHandler stacksHandler = curios.get(id);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStackHelper = stacksHandler.getCosmeticStacks();

                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);

                    if (!stack.isEmpty() && filter.test(stack)) {
                        return new ImmutableTriple<>(id, i, stack);
                    }
                }
                for (int i = 0; i < cosmeticStackHelper.getSlots(); i++) {
                    ItemStack stack = cosmeticStackHelper.getStackInSlot(i);

                    if (!stack.isEmpty() && filter.test(stack)) {
                        return new ImmutableTriple<>(id, i, stack);
                    }
                }
            }
            return new ImmutableTriple<>("", 0, ItemStack.EMPTY);
        }).orElse(new ImmutableTriple<>("", 0, ItemStack.EMPTY));

        return result.getLeft().isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public static boolean hasCurioEquipped(LivingEntity entity, RegistryObject<Item> curio) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(curio.get(), entity).isPresent();
    }

    public static ArrayList<ItemStack> eventResponders(LivingEntity attacker, LivingEntity attacked)
    {
        ArrayList<ItemStack> itemStacks = ItemHelper.equippedCurios(attacker, p -> p.getItem() instanceof IEventResponderItem);
        ItemStack stack = attacker.getMainHandItem();
        if (stack.getItem() instanceof IEventResponderItem) {
            itemStacks.add(stack);
        }
        attacker.getArmorSlots().forEach(s -> {
            if (s.getItem() instanceof IEventResponderItem) {
                itemStacks.add(s);
            }
        });
        return itemStacks;
    }

    public static ArrayList<ItemStack> equippedCurios(LivingEntity entity) {
        Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (optional.isPresent()) {
            IItemHandlerModifiable handler = optional.get();
            for (int i = 0; i < handler.getSlots(); i++) {
                stacks.add(handler.getStackInSlot(i));
            }
        }
        return stacks;
    }

    public static ArrayList<ItemStack> equippedCurios(LivingEntity entity, Predicate<ItemStack> predicate) {
        Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (optional.isPresent()) {
            IItemHandlerModifiable handler = optional.get();
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (predicate.test(stack)) {
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }

    public static void applyEnchantments(LivingEntity user, Entity target, ItemStack stack) {
        EnchantmentHelper.EnchantmentVisitor visitor = (enchantment, level) ->
                enchantment.doPostAttack(user, target, level);
        if (user != null) {
            EnchantmentHelper.runIterationOnInventory(visitor, user.getAllSlots());
        }

        if (user instanceof Player) {
            EnchantmentHelper.runIterationOnItem(visitor, stack);
        }
    }

    public static void giveItemToEntity(ItemStack item, LivingEntity entity) {
        if (entity instanceof Player) {
            ItemHandlerHelper.giveItemToPlayer((Player) entity, item);
        } else {
            ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getY() + 0.5, entity.getZ(), item);
            itemEntity.setPickUpDelay(40);
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(0, 1, 0));
            entity.level.addFreshEntity(itemEntity);
        }
    }
}
