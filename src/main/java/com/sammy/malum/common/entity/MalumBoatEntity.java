package com.sammy.malum.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.Level.GameRules;
import net.minecraft.Level.Level;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.entity.item.BoatEntity.Status;

@SuppressWarnings("all")
public class MalumBoatEntity extends BoatEntity
{
    private final RegistryObject<Item> boatItem;
    private final RegistryObject<Item> plankItem;
    public MalumBoatEntity(EntityType<? extends MalumBoatEntity> type, Level Level, RegistryObject<Item> boatItem, RegistryObject<Item> plankItem)
    {
        super(type, Level);
        this.boatItem = boatItem;
        this.plankItem = plankItem;
    }

    @Override
    public void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger())
        {
            if (onGroundIn)
            {
                if (this.fallDistance > 3.0F)
                {
                    if (this.status != Status.ON_LAND)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F);
                    if (!this.level.isClientSide && !this.removed)
                    {
                        this.remove();
                        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
                        {
                            for (int i = 0; i < 3; ++i)
                            {
                                this.spawnAtLocation(this.getBoatType().getPlanks());
                            }

                            for (int j = 0; j < 2; ++j)
                            {
                                this.spawnAtLocation(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
            else if (!this.level.getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < 0.0D)
            {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }

        }
    }

    @Override
    public Item getDropItem()
    {
        return boatItem.get();
    }
    @Override
    public IPacket<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
