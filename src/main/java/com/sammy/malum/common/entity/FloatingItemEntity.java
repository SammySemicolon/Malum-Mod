package com.sammy.malum.common.entity;

import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FloatingItemEntity extends FloatingEntity{
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ThrowableItemProjectile.class, EntityDataSerializers.ITEM_STACK);
    public FloatingItemEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
    }
    public void setItem(ItemStack pStack) {
        if (pStack.getItem() instanceof ISpiritEntityGlow glow) {
            color = glow.getColor();
            getEntityData().set(DATA_COLOR, color.getRGB());
            endColor = glow.getEndColor();
            getEntityData().set(DATA_END_COLOR, endColor.getRGB());
        }
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, Util.make(pStack.copy(), (p_37451_) -> {
                p_37451_.setCount(1);
            }));
        }
    }
    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }
    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }
    protected Item getDefaultItem()
    {
        return ItemRegistry.SACRED_SPIRIT.get();
    }
    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
    }
}
