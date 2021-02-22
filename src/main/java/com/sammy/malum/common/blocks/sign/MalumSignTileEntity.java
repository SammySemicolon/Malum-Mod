package com.sammy.malum.common.blocks.sign;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Function;

public class MalumSignTileEntity extends SimpleTileEntity
{
    private final ITextComponent[] signText = new ITextComponent[]{StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY, StringTextComponent.EMPTY};
    private boolean isEditable = true;
    private PlayerEntity player;
    private final IReorderingProcessor[] renderText = new IReorderingProcessor[4];
    private DyeColor textColor = DyeColor.BLACK;
    
    public MalumSignTileEntity()
    {
        super(MalumTileEntities.SIGN_TILE_ENTITY.get());
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        for (int i = 0; i < 4; ++i)
        {
            String s = ITextComponent.Serializer.toJson(this.signText[i]);
            compound.putString("Text" + (i + 1), s);
        }
        compound.putString("Color", this.textColor.getTranslationKey());
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        this.isEditable = false;
        super.readData(compound);
        this.textColor = DyeColor.byTranslationKey(compound.getString("Color"), DyeColor.BLACK);
    
        for (int i = 0; i < 4; ++i)
        {
            String s = compound.getString("Text" + (i + 1));
            ITextComponent itextcomponent = ITextComponent.Serializer.getComponentFromJson(s.isEmpty() ? "\"\"" : s);
            if (this.world instanceof ServerWorld)
            {
                try
                {
                    this.signText[i] = TextComponentUtils.func_240645_a_(this.getCommandSource(null), itextcomponent, null, 0);
                } catch (CommandSyntaxException commandsyntaxexception)
                {
                    this.signText[i] = itextcomponent;
                }
            }
            else
            {
                this.signText[i] = itextcomponent;
            }
    
            this.renderText[i] = null;
        }
    }
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getText(int line)
    {
        return this.signText[line];
    }
    
    public void setText(int line, ITextComponent signText)
    {
        this.signText[line] = signText;
        this.renderText[line] = null;
    }
    
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IReorderingProcessor func_242686_a(int p_242686_1_, Function<ITextComponent, IReorderingProcessor> p_242686_2_)
    {
        if (this.renderText[p_242686_1_] == null && this.signText[p_242686_1_] != null)
        {
            this.renderText[p_242686_1_] = p_242686_2_.apply(this.signText[p_242686_1_]);
        }
        
        return this.renderText[p_242686_1_];
    }
    
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 9, this.getUpdateTag());
    }
    
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    
    public boolean onlyOpsCanSetNbt()
    {
        return true;
    }
    
    public boolean getIsEditable()
    {
        return this.isEditable;
    }
    
    @OnlyIn(Dist.CLIENT)
    public void setEditable(boolean isEditableIn)
    {
        this.isEditable = isEditableIn;
        if (!isEditableIn)
        {
            this.player = null;
        }
        
    }
    
    public void setPlayer(PlayerEntity playerIn)
    {
        this.player = playerIn;
    }
    
    public PlayerEntity getPlayer()
    {
        return this.player;
    }
    
    public boolean executeCommand(PlayerEntity playerIn)
    {
        for (ITextComponent itextcomponent : this.signText)
        {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();
            if (style != null && style.getClickEvent() != null)
            {
                ClickEvent clickevent = style.getClickEvent();
                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                {
                    playerIn.getServer().getCommandManager().handleCommand(this.getCommandSource((ServerPlayerEntity) playerIn), clickevent.getValue());
                }
            }
        }
        
        return true;
    }
    
    public CommandSource getCommandSource(@Nullable ServerPlayerEntity playerIn)
    {
        String s = playerIn == null ? "Sign" : playerIn.getName().getString();
        ITextComponent itextcomponent = (ITextComponent) (playerIn == null ? new StringTextComponent("Sign") : playerIn.getDisplayName());
        return new CommandSource(ICommandSource.DUMMY, Vector3d.copyCentered(this.pos), Vector2f.ZERO, (ServerWorld) this.world, 2, s, itextcomponent, this.world.getServer(), playerIn);
    }
    
    public DyeColor getTextColor()
    {
        return this.textColor;
    }
    
    public boolean setTextColor(DyeColor newColor)
    {
        if (newColor != this.getTextColor())
        {
            this.textColor = newColor;
            this.markDirty();
            this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
            return true;
        }
        else
        {
            return false;
        }
    }
}