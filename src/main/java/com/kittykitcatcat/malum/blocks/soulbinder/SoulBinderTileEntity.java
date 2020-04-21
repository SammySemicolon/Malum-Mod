package com.kittykitcatcat.malum.blocks.soulbinder;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.blocks.ritualanchor.RitualAnchorTileEntity;
import com.kittykitcatcat.malum.blocks.souljar.SoulJarTileEntity;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.SpiritInfusionFinishSoundPacket;
import com.kittykitcatcat.malum.network.packets.SpiritInfusionSoundStartPacket;
import com.kittykitcatcat.malum.network.packets.SpiritInfusionStopLoopSoundPacket;
import com.kittykitcatcat.malum.network.packets.SpiritWhisperPacket;
import com.kittykitcatcat.malum.particles.loosesoulparticle.LooseSoulParticleData;
import com.kittykitcatcat.malum.particles.souleruptionparticle.SoulEruptionParticleData;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticleData;
import com.kittykitcatcat.malum.particles.soulharvestparticle.SoulHarvestParticleData;
import com.kittykitcatcat.malum.recipes.SpiritInfusionRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static com.kittykitcatcat.malum.MalumHelper.randPos;
import static com.kittykitcatcat.malum.MalumMod.*;
import static com.kittykitcatcat.malum.blocks.soulbinder.SoulBinderBlock.findList;
import static com.kittykitcatcat.malum.blocks.soulbinder.SoulBinderBlock.getAnchorAt;
import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class SoulBinderTileEntity extends TileEntity implements ITickableTileEntity
{
    public SoulBinderTileEntity()
    {
        super(ModTileEntities.soul_binder_tile_entity);
    }

    public boolean active;
    public int infusionProgress;
    BlockPos jarPos;

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putBoolean("active", active);
        compound.putInt("infusionTime", infusionProgress);
        if (jarPos != null)
        {
            compound.putInt("blockPosX", jarPos.getX());
            compound.putInt("blockPosY", jarPos.getY());
            compound.putInt("blockPosZ", jarPos.getZ());
        }
        return compound;
    }

    public ItemStack getInputStack(ItemStackHandler inventory)
    {
        return inventory.getStackInSlot(0);
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        active = compound.getBoolean("active");
        infusionProgress = compound.getInt("infusionTime");
        jarPos = new BlockPos(compound.getInt("blockPosX"), compound.getInt("blockPosY"), compound.getInt("blockPosZ"));
    }

    public ItemStackHandler inventory = new ItemStackHandler(1)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 0;
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            SoulBinderTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
        {
            return ItemStack.EMPTY;
        }
    };

    @Override
    public void tick()
    {
        if (!inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack stack = inventory.getStackInSlot(0);
            List<Item> anchorInputs = findList(pos, world);
            for (SpiritInfusionRecipe recipe : ModRecipes.spiritInfusionRecipes)
            {
                if (anchorInputs.equals(recipe.getItems()))
                {
                    if (stack.getItem().equals(recipe.getCatalyst()))
                    {
                        if (SpiritData.findSpiritData(2, recipe, pos, world) != null)
                        {
                            if (infusionProgress == 0)
                            {
                                if (!world.isRemote)
                                {
                                    INSTANCE.send(
                                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                                            new SpiritInfusionSoundStartPacket(pos.getX(), pos.getY(), pos.getZ()));
                                }
                            }
                            if (infusionProgress == recipe.getInfusionTime() - 30)
                            {
                                if (!world.isRemote)
                                {
                                    INSTANCE.send(
                                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                                            new SpiritInfusionFinishSoundPacket(pos.getX(), pos.getY(), pos.getZ()));
                                }
                            }
                            infusionProgress++;
                            if (MathHelper.nextInt(world.rand, 0, 80) == 0)
                            {
                                if (!world.isRemote)
                                {
                                    INSTANCE.send(
                                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                                            new SpiritWhisperPacket(pos.getX(), pos.getY(), pos.getZ()));
                                }
                            }
                            for (SoulBinderBlock.anchorOffset offset : SoulBinderBlock.anchorOffset.values())
                            {
                                BlockPos anchorPos = pos.add(offset.offsetX, 0, offset.offsetY);
                                if (getAnchorAt(anchorPos, world) != null)
                                {
                                    if (world.getTileEntity(anchorPos) instanceof RitualAnchorTileEntity)
                                    {
                                        RitualAnchorTileEntity anchorTileEntity = (RitualAnchorTileEntity) world.getTileEntity(anchorPos);
                                        if (anchorTileEntity.inventory.getStackInSlot(0) != ItemStack.EMPTY)
                                        {
                                            Vec3d targetPos = randPos(new Vec3d(pos.getX() + 0.5, pos.getY() + 1.8, pos.getZ() + 0.5), random, -0.1, 0.1);
                                            Vec3d particlePos = randPos(new Vec3d(anchorPos.getX(), anchorPos.getY(), anchorPos.getZ()).add(0.5, 1.1, 0.5), random, -0.1, 0.1);
                                            double d0 = targetPos.x - particlePos.x;
                                            double d1 = targetPos.y - particlePos.y;
                                            double d2 = targetPos.z - particlePos.z;
                                            Vec3d velocity = new Vec3d(d0, d1, d2).mul(0.1, 0.3, 0.1);
                                            world.addParticle(new ItemParticleData(ParticleTypes.ITEM, anchorTileEntity.inventory.getStackInSlot(0)), particlePos.getX(), particlePos.getY(), particlePos.getZ(), velocity.x, velocity.y, velocity.z);
                                        }
                                    }
                                }
                            }
                            if (infusionProgress % 3 == 0)
                            {
                                Vec3d targetPos = randPos(new Vec3d(pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5), random, -2, 2);
                                Vec3d particlePos = randPos(new Vec3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5, 1.2, 0.5), random, -0.3, 0.3);
                                world.addParticle(new LooseSoulParticleData(), particlePos.getX(), particlePos.getZ(), targetPos.x, targetPos.y, targetPos.z, 0);
                            }

                            if (infusionProgress >= recipe.getInfusionTime())
                            {
                                if (world.getTileEntity(jarPos) instanceof SoulJarTileEntity)
                                {
                                    SoulJarTileEntity tileEntity = (SoulJarTileEntity) world.getTileEntity(jarPos);
                                    tileEntity.purity -= recipe.getData().purity;
                                    infusionProgress = 0;
                                    ItemStack outputStack = recipe.getOutputStack();
                                    if (recipe.getInfusionResult() != null)
                                    {
                                        recipe.getInfusionResult().result(stack, outputStack);
                                    }
                                    for (SoulBinderBlock.anchorOffset offset : SoulBinderBlock.anchorOffset.values())
                                    {
                                        BlockPos anchorPos = pos.add(offset.offsetX, 0, offset.offsetY);
                                        if (getAnchorAt(anchorPos, world) != null)
                                        {
                                            if (world.getTileEntity(anchorPos) instanceof RitualAnchorTileEntity)
                                            {
                                                RitualAnchorTileEntity anchorTileEntity = (RitualAnchorTileEntity) world.getTileEntity(anchorPos);
                                                if (!anchorTileEntity.inventory.getStackInSlot(0).isEmpty())
                                                {
                                                    anchorTileEntity.inventory.setStackInSlot(0, ItemStack.EMPTY);
                                                    world.addParticle(new SoulEruptionParticleData(), anchorPos.getX() + 0.5, anchorPos.getY() + 1.4, anchorPos.getZ() + 0.5, 0, 0, 0);
                                                }
                                            }
                                        }
                                    }
                                    if (!world.isRemote)
                                    {
                                        INSTANCE.send(
                                                PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                                                new SpiritInfusionStopLoopSoundPacket(pos.getX(), pos.getY(), pos.getZ()));
                                    }
                                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                                    world.addParticle(new SoulHarvestParticleData(), pos.getX() + 0.5, pos.getY() + 2.2, pos.getZ() + 0.5, 0, 0, 0);
                                    world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, outputStack));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> inventory);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return lazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        read(tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }
}