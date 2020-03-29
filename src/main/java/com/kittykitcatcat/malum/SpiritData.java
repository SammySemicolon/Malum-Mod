package com.kittykitcatcat.malum;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.Optional;

public class SpiritData
{
    public String entityRegistryName;
    public String entityDisplayName;
    public float purity;
    public SpiritData(LivingEntity entity, float purity)
    {
        if (entity.getType().getRegistryName() != null)
        {
            entityRegistryName = entity.getType().getRegistryName().toString();
            entityDisplayName = entity.getType().getName().getString();
            this.purity = purity;
        }
    }
    public SpiritData(String entityRegistryName, String entityDisplayName, float purity)
    {
        this.entityRegistryName = entityRegistryName;
        this.entityDisplayName = entityDisplayName;
        this.purity = purity;
    }
    public Optional<EntityType<?>> getType()
    {
        return EntityType.byKey(entityRegistryName);
    }
    public boolean isPureEnough(float purity)
    {
        return this.purity >= purity;
    }
    public void writeSpiritDataIntoNBT(CompoundNBT nbt)
    {
        nbt.putString("entityRegistryName", entityRegistryName);
        nbt.putString("entityDisplayName", entityDisplayName);
        nbt.putFloat("purity", purity);
    }
}
