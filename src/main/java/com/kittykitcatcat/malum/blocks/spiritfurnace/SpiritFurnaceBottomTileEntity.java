package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModItems;
import com.kittykitcatcat.malum.init.ModRecipes;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStartPacket;
import com.kittykitcatcat.malum.network.packets.FurnaceSoundStopPacket;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticleData;
import com.kittykitcatcat.malum.recipes.SpiritFurnaceRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

@Mod.EventBusSubscriber
public class SpiritFurnaceBottomTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceBottomTileEntity()
    {
        super(ModTileEntities.spirit_furnace_bottom_tile_entity);
    }
    public boolean isSmelting;
    public int burnTime;
    public int burnProgress;
    public ItemStackHandler inventory = new ItemStackHandler(3)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack)
        {
            if(stack.getItem().equals(ModItems.spirit_charcoal))
            {
                if (slot == 0)
                {
                    return true;
                }
            }
            return false;
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
            SpiritFurnaceBottomTileEntity.this.markDirty();
            if (!world.isRemote)
            {
                updateContainingBlockInfo();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    };
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
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.put("inventory", inventory.serializeNBT());
        compound.putInt("burnTime", burnTime);
        compound.putInt("burnProgress", burnProgress);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        inventory.deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
        burnTime = compound.getInt("burnTime");
        burnProgress = compound.getInt("burnProgress");
    }

    public enum spiritFuranceSlotEnum
    {
        fuel (0),
        input (1);

        private final int slot;
        private spiritFuranceSlotEnum(int slot) { this.slot = slot;}
    }

    public ItemStack getItemStack(spiritFuranceSlotEnum spiritFuranceSlot)
    {
        return inventory.getStackInSlot(spiritFuranceSlot.slot);
    }
    public void updateIsSmelting(boolean newIsSmelting)
    {
        if (world.getBlockState(pos).getBlock() instanceof SpiritFurnaceBottomBlock && world.getBlockState(pos.up()).getBlock() instanceof SpiritFurnaceTopBlock)
        {
            this.isSmelting = newIsSmelting;
            BlockState bottomState = world.getBlockState(pos).with(BlockStateProperties.LIT, newIsSmelting);
            BlockState topState = world.getBlockState(pos.up()).with(BlockStateProperties.LIT, newIsSmelting);

            world.notifyBlockUpdate(pos, world.getBlockState(pos), bottomState, 3);
            world.setBlockState(pos, bottomState, 3);
            world.notifyBlockUpdate(pos.up(), world.getBlockState(pos.up()), topState, 3);
            world.setBlockState(pos.up(), topState, 3);
            if (!world.isRemote)
            {
                if (newIsSmelting)
                {
                    INSTANCE.send(
                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                            new FurnaceSoundStartPacket(pos.getX(), pos.getY(), pos.getZ()));
                }
                else
                {
                    INSTANCE.send(
                            PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                            new FurnaceSoundStopPacket(pos.getX(), pos.getY(), pos.getZ()));
                }
            }
        }
    }
    public void output(World world, BlockPos pos, ItemStack stack)
    {
        Vec3i direction = world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING).getDirectionVec();
        Vec3d entityPos = new Vec3d(pos).add(0.5,1.5,0.5).subtract(direction.getX() * 0.8f, 0, direction.getZ() * 0.8f);
        ItemEntity entity = new ItemEntity(world,entityPos.x,entityPos.y,entityPos.z, stack);
        entity.setMotion(-direction.getX() * 0.1f, 0.05f, -direction.getZ() * 0.1f);
        world.addEntity(entity);
    }
    @Override
    public void tick()
    {
        //INPUT FROM TOP BLOCK
        if (world.getTileEntity(pos.up()) instanceof SpiritFurnaceTopTileEntity)
        {
            SpiritFurnaceTopTileEntity topTileEntity = (SpiritFurnaceTopTileEntity) world.getTileEntity(pos.up());
            ItemStack stackToAdd = topTileEntity.inventory.getStackInSlot(0);
            ItemStack inputStack = getItemStack(spiritFuranceSlotEnum.input);
            if (stackToAdd.getItem().equals(inputStack.getItem()) || inputStack.isEmpty())
            {
                if (stackToAdd.getCount() > 0 && inputStack.getCount() < 64)
                {
                    if (inputStack.equals(ItemStack.EMPTY))
                    {
                        MalumHelper.setStackInTEInventory(inventory, new ItemStack(stackToAdd.getItem()), 1);
                    }
                    else
                    {
                        MalumHelper.addStackToTEInventory(inventory, stackToAdd, 1);
                    }
                    MalumHelper.setStackInTEInventory(topTileEntity.inventory, ItemStack.EMPTY, 0);
                }
            }
        }
        //SMELTING
        ItemStack inputStack = getItemStack(spiritFuranceSlotEnum.input);
        if (ModRecipes.getSpiritFurnaceRecipe(inputStack) != null)
        {
            if (burnTime <= 0 && getItemStack(spiritFuranceSlotEnum.fuel).getItem().equals(ModItems.spirit_charcoal))
            {
                MalumHelper.decreaseStackSizeInTEInventory(inventory, 1, spiritFuranceSlotEnum.fuel.slot);
                burnTime = 800;
            }
            if (burnTime > 0)
            {
                SpiritFurnaceRecipe recipe = ModRecipes.getSpiritFurnaceRecipe(inputStack);
                if (!isSmelting)
                {
                    isSmelting = true;
                    updateIsSmelting(isSmelting);
                }
                burnProgress++;
                if (burnProgress >= recipe.getBurnTime())
                {
                    MalumHelper.decreaseStackSizeInTEInventory(inventory, 1, spiritFuranceSlotEnum.input.slot);
                    output(world, pos, recipe.getOutputItem());
                    if (recipe.getSideItem() != null)
                    {
                        if (MathHelper.nextInt(world.rand, 0, 4) == 0)
                        {
                            output(world, pos, recipe.getSideItem());
                        }
                    }
                    burnProgress = 0;
                }
            }
            else if (isSmelting)
            {
                isSmelting = false;
                updateIsSmelting(isSmelting);
            }
        }
        else if (isSmelting)
        {
            burnProgress = 0;
            isSmelting = false;
            updateIsSmelting(isSmelting);
        }
        if (burnTime > 0)
        {
            burnTime--;
        }
        //PARTICLES
        if (burnTime > 0)
        {
            if (world.getGameTime() % 5 == 0)
            {
                spawnSoulFlame(world, pos, 0.25f,0.08f);
            }
        }
        if (isSmelting && burnProgress > 0)
        {
            if (world.getGameTime() % 8 == 0)
            {
                spawnSoulFlame(world, pos.up(), 0.06f, 0.05f);
            }
            if (world.getGameTime() % 4 == 0)
            {
                spawnSmoke(world,pos);
            }
        }
    }
    public static void spawnSoulFlame(World world, BlockPos pos, float offset, float speed)
    {
        Vec3i direction = world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING).getDirectionVec();
        Vec3d velocity = MalumHelper.randVelocity(world, -speed, speed);
        Vec3d particlePos = MalumHelper.randPos(pos, world, -offset, offset);
        world.addParticle(new SoulFlameParticleData(), particlePos.getX() + 0.5 + direction.getX() * 0.2f, particlePos.getY() + 0.5, particlePos.getZ() + 0.5 + direction.getZ() * 0.2f, velocity.getX(), velocity.getY(), velocity.getZ());
    }
    public static void spawnSmoke(World world, BlockPos pos)
    {
        Vec3i direction = world.getBlockState(pos).get(BlockStateProperties.HORIZONTAL_FACING).getDirectionVec();
        Vec3d particlePos = MalumHelper.randPos(pos, world, -0.3d, 0.3d);
        if (direction.getZ() != 0)
        {
            world.addParticle(ParticleTypes.SMOKE, particlePos.getX() + 0.5d, particlePos.getY() + 2, particlePos.getZ() + 0.5 - direction.getZ() * 0.4f, 0, 0.04, 0);
        }
        if (direction.getX() != 0)
        {
            world.addParticle(ParticleTypes.SMOKE, particlePos.getX() + 0.5d - direction.getX() * 0.4d, particlePos.getY() + 2, particlePos.getZ() + 0.5, 0, 0.04, 0);
        }
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