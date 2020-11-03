package com.sammy.malum.network.packets;

import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import com.sammy.malum.blocks.utility.IConfigurableTileEntity;
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
    
    public static UpdateSelectedOption decode(PacketBuffer buf)
    {
        BlockPos pos = buf.readBlockPos();
        int value = buf.readInt();
        return new UpdateSelectedOption(pos, value);
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
            if (playerEntity.world.getTileEntity(pos) instanceof IConfigurableTileEntity)
            {
                IConfigurableTileEntity configurableTileEntity = (IConfigurableTileEntity) playerEntity.world.getTileEntity(pos);
                configurableTileEntity.setOption(configurableTileEntity.getOption() + value);;
                playerEntity.world.getTileEntity(pos).markDirty();
                playerEntity.world.notifyBlockUpdate(pos, playerEntity.world.getBlockState(pos), playerEntity.world.getBlockState(pos), 3);
            }
        });
        context.get().setPacketHandled(true);
    }
}