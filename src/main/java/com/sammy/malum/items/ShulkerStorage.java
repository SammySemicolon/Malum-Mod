package com.sammy.malum.items;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.particles.particletypes.lensmagic.LensMagicParticleData;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ShulkerStorage extends Item
{
    public ShulkerStorage(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getItem();
        CompoundNBT nbt = stack.getOrCreateTag();
        TileEntity tile = world.getTileEntity(pos);
        if (nbt.contains("storing"))
        {
            BlockPos newPos = pos.add(context.getFace().getDirectionVec());
            BlockState oldState = world.getBlockState(newPos);
            if (!oldState.isAir(world, newPos))
            {
                return ActionResultType.FAIL;
            }
            nbt.remove("storing");
            BlockState newState = NBTUtil.readBlockState(nbt.getCompound("blockStateData"));
            world.setBlockState(newPos, newState);
            TileEntity newTile = world.getTileEntity(newPos);
            if (newTile != null)
            {
                CompoundNBT tileData = nbt.getCompound("tileData");
                tileData.putInt("x", newPos.getX());
                tileData.putInt("y", newPos.getY());
                tileData.putInt("z", newPos.getZ());
                newTile.read(newState, tileData);
                newTile.markDirty();
                world.markChunkDirty(newPos, newTile);
                nbt.remove("tileData");
            }
            world.notifyBlockUpdate(pos, oldState, newState, 3);
            nbt.remove("blockStateData");
            world.playSound(context.getPlayer(), pos, SoundEvents.ENTITY_SHULKER_OPEN, SoundCategory.PLAYERS, 1, 0.8f);
            world.playSound(context.getPlayer(), pos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.PLAYERS, 1, 1.5f);
            context.getPlayer().swingArm(context.getHand());
            for (int i = 0; i < 20; i++)
            {
                Vector3d startingPos = MalumHelper.vectorFromBlockPos(newPos).add(0.5, 0.5, 0.5);
                Vector3d particlePos = startingPos.add(MalumHelper.randomVector(MalumMod.random, -1, 1));
                Vector3d particleVelocity = particlePos.subtract(startingPos).normalize().mul(-0.1f, -0.1f, -0.1f);
                world.addParticle(new LensMagicParticleData(0.15f + random.nextFloat() * 0.2f), particlePos.x, particlePos.y, particlePos.z, particleVelocity.x, particleVelocity.y, particleVelocity.z);
            }
        }
        else
        {
            nbt.putBoolean("storing", true);
            if (tile != null)
            {
                CompoundNBT tileData = new CompoundNBT();
                tile.write(tileData);
                nbt.put("tileData", tileData);
            }
            nbt.put("blockStateData", NBTUtil.writeBlockState(state));
            world.removeTileEntity(pos);
            world.destroyBlock(pos, false);
            world.notifyBlockUpdate(pos, state, world.getBlockState(pos), 3);
            world.playSound(context.getPlayer(), pos, SoundEvents.ENTITY_SHULKER_CLOSE, SoundCategory.PLAYERS, 1, 0.8f);
            world.playSound(context.getPlayer(), pos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.PLAYERS, 1, 1.5f);
            context.getPlayer().swingArm(context.getHand());
            for (int i = 0; i < 20; i++)
            {
                Vector3d startingPos = MalumHelper.vectorFromBlockPos(pos).add(0.5, 0.5, 0.5);
                Vector3d particlePos = startingPos.add(MalumHelper.randomVector(MalumMod.random, -0.5, 0.5));
                Vector3d particleVelocity = particlePos.subtract(startingPos).normalize().mul(0.1f, 0.1f, 0.1f);
                world.addParticle(new LensMagicParticleData(0.15f + random.nextFloat() * 0.2f), particlePos.x, particlePos.y, particlePos.z, particleVelocity.x, particleVelocity.y, particleVelocity.z);
            }
        }
        return super.onItemUse(context);
    }
}