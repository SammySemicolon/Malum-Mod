package com.sammy.malum.common.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;

@SuppressWarnings("all")
public class MalumBoatEntity extends BoatEntity
{
    private final RegistryObject<Item> boatItem;
    private final RegistryObject<Item> plankItem;
    public MalumBoatEntity(EntityType<? extends MalumBoatEntity> type, World world, RegistryObject<Item> boatItem, RegistryObject<Item> plankItem)
    {
        super(type, world);
        this.boatItem = boatItem;
        this.plankItem = plankItem;
    }

    @Override
    public void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {
        this.lastYd = this.getMotion().y;
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

                    this.onLivingFall(this.fallDistance, 1.0F);
                    if (!this.world.isRemote && !this.removed)
                    {
                        this.remove();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
                        {
                            for (int i = 0; i < 3; ++i)
                            {
                                this.entityDropItem(this.getBoatType().asPlank());
                            }

                            for (int j = 0; j < 2; ++j)
                            {
                                this.entityDropItem(Items.STICK);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
            else if (!this.world.getFluidState(this.getPosition().down()).isTagged(FluidTags.WATER) && y < 0.0D)
            {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }

        }
    }

    @Override
    public Item getItemBoat()
    {
        return boatItem.get();
    }
    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
