package com.sammy.malum.common.entity;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class FloatingItemEntity extends FloatingEntity {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(FloatingItemEntity.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<String> DATA_SPIRIT = SynchedEntityData.defineId(FloatingItemEntity.class, EntityDataSerializers.STRING);

    public ItemStack itemStack = ItemStack.EMPTY;
    protected MalumSpiritType spiritType = SpiritTypeRegistry.ARCANE_SPIRIT;

    public FloatingItemEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }
        pCompound.putString("spiritType", spiritType.identifier);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
        setSpirit(pCompound.getString("spiritType"));
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack);
        }
    }

    public MalumSpiritType getSpiritType() {
        return spiritType;
    }

    public void setSpirit(MalumSpiritType spiritType) {
        setSpirit(spiritType.identifier);
        this.spiritType = spiritType;
    }
    public void setSpirit(String spiritIdentifier) {
        this.getEntityData().set(DATA_SPIRIT, spiritIdentifier);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
        this.getEntityData().define(DATA_SPIRIT, SpiritTypeRegistry.ARCANE_SPIRIT.identifier);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_ITEM_STACK.equals(pKey)) {
            itemStack = getEntityData().get(DATA_ITEM_STACK);
        }
        if (DATA_SPIRIT.equals(pKey)) {
            spiritType = SpiritTypeRegistry.SPIRITS.get(entityData.get(DATA_SPIRIT));
        }
        super.onSyncedDataUpdated(pKey);
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.ARCANE_SPIRIT.get();
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }
}