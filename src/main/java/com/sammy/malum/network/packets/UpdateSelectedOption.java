package com.sammy.malum.network.packets;

import com.sammy.malum.blocks.utility.BasicTileEntity;
import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import com.sammy.malum.blocks.utility.IFancyRenderer;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSelectedOption
{
    public BlockPos pos;
    public int value;
    public UpdateSelectedOption(BlockPos pos, int value)
    {
        this.pos = pos;
        this.value = value;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(value);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity playerEntity = context.get().getSender();
            if (playerEntity.world.getTileEntity(pos) instanceof ConfigurableTileEntity)
            {
                ConfigurableTileEntity configurableTileEntity = (ConfigurableTileEntity) playerEntity.world.getTileEntity(pos);
                configurableTileEntity.option += value;
                configurableTileEntity.markDirty();
                playerEntity.world.notifyBlockUpdate(pos, playerEntity.world.getBlockState(pos),playerEntity.world.getBlockState(pos), 3);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static UpdateSelectedOption decode(PacketBuffer buf)
    {
        BlockPos pos = buf.readBlockPos();
        int value = buf.readInt();
        return new UpdateSelectedOption(pos, value);
    }
}