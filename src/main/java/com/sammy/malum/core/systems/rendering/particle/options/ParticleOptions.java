package com.sammy.malum.core.systems.rendering.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.helper.ColorHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.awt.*;

public class ParticleOptions extends ParticleOptionsBase implements net.minecraft.core.particles.ParticleOptions {

    public static Codec<ParticleOptions> codecFor(ParticleType<?> type) {
        return RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("rgba1").forGetter(d -> ColorHelper.getColor(d.r1, d.g1, d.b1, d.a1)),
                Codec.INT.fieldOf("rgba2").forGetter(d -> ColorHelper.getColor(d.r2, d.g2, d.b2, d.a2)),
                Codec.FLOAT.fieldOf("scale1").forGetter(d -> d.scale1),
                Codec.FLOAT.fieldOf("scale2").forGetter(d -> d.scale2),
                Codec.INT.fieldOf("lifetime").forGetter(d -> d.lifetime),
                Codec.FLOAT.fieldOf("startingSpin").forGetter(d -> d.startingSpin),
                Codec.FLOAT.fieldOf("spin").forGetter(d -> d.spin),
                Codec.FLOAT.fieldOf("activeMotionMultiplier").forGetter(d -> d.activeMotionMultiplier),
                Codec.FLOAT.fieldOf("activeSpinMultiplier").forGetter(d -> d.activeSpinMultiplier),
                Codec.BOOL.fieldOf("gravity").forGetter(d -> d.gravity),
                Codec.BOOL.fieldOf("noClip").forGetter(d -> d.noClip),
                Codec.FLOAT.fieldOf("colorCurveMultiplier").forGetter(d -> d.colorCurveMultiplier),
                Codec.FLOAT.fieldOf("alphaCurveMultiplier").forGetter(d -> d.alphaCurveMultiplier)
        ).apply(instance, (rgba1, rgba2, scale1, scale2,
                           lifetime, spin, activeMotionMultiplier, activeSpinMultiplier, startingSpin, gravity, noClip, colorCurveMultiplier, alphaCurveMultiplier) -> {
            ParticleOptions data = new ParticleOptions(type);
            Color color1 = ColorHelper.getColor(rgba1);
            data.r1 = color1.getRed() / 255f;
            data.g1 = color1.getGreen() / 255f;
            data.b1 = color1.getBlue() / 255f;
            data.a1 = color1.getAlpha() / 255f;
            Color color2 = ColorHelper.getColor(rgba1);
            data.r2 = color2.getRed() / 255f;
            data.g2 = color2.getGreen() / 255f;
            data.b2 = color2.getBlue() / 255f;
            data.a2 = color2.getAlpha() / 255f;
            data.scale1 = scale1;
            data.scale2 = scale2;
            data.lifetime = lifetime;
            data.startingSpin = startingSpin;
            data.activeMotionMultiplier = activeMotionMultiplier;
            data.activeSpinMultiplier = activeSpinMultiplier;
            data.spin = spin;
            data.gravity = gravity;
            data.noClip = noClip;
            data.colorCurveMultiplier = colorCurveMultiplier;
            data.alphaCurveMultiplier = alphaCurveMultiplier;
            return data;
        }));
    }

    ParticleType<?> type;

    public ParticleOptions(ParticleType<?> type) {
        this.type = type;
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(r1).writeFloat(g1).writeFloat(b1).writeFloat(a1);
        buffer.writeFloat(r2).writeFloat(g2).writeFloat(b2).writeFloat(a2);
        buffer.writeFloat(scale1).writeFloat(scale2);
        buffer.writeInt(lifetime);
        buffer.writeFloat(startingSpin);
        buffer.writeFloat(spin);
        buffer.writeFloat(activeMotionMultiplier);
        buffer.writeFloat(activeSpinMultiplier);
        buffer.writeBoolean(gravity);
        buffer.writeBoolean(noClip);
        buffer.writeFloat(colorCurveMultiplier);
        buffer.writeFloat(alphaCurveMultiplier);
    }

    @Override
    public String writeToString() {
        return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + r1 + " " + g1 + " " + b1 + " " + a1 + " " + r2 + " " + g2 + " " + b2 + " " + a2 + " " + scale1 + " " + scale2 + " " + lifetime + " " + startingSpin + " " + spin + " " + activeMotionMultiplier + " " + activeSpinMultiplier + " " + gravity + " " + noClip + " " + colorCurveMultiplier;
    }

    public static final Deserializer<ParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        public ParticleOptions fromCommand(ParticleType<ParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r1 = reader.readFloat();
            reader.expect(' ');
            float g1 = reader.readFloat();
            reader.expect(' ');
            float b1 = reader.readFloat();
            reader.expect(' ');
            float a1 = reader.readFloat();
            reader.expect(' ');
            float r2 = reader.readFloat();
            reader.expect(' ');
            float g2 = reader.readFloat();
            reader.expect(' ');
            float b2 = reader.readFloat();
            reader.expect(' ');
            float a2 = reader.readFloat();
            reader.expect(' ');
            float scale1 = reader.readFloat();
            reader.expect(' ');
            float scale2 = reader.readFloat();
            reader.expect(' ');
            int lifetime = reader.readInt();
            reader.expect(' ');
            float startingSpin = reader.readFloat();
            reader.expect(' ');
            float spin = reader.readFloat();
            reader.expect(' ');
            float activeMotionMultiplier = reader.readFloat();
            reader.expect(' ');
            float activeSpinMultiplier = reader.readFloat();
            reader.expect(' ');
            boolean gravity = reader.readBoolean();
            reader.expect(' ');
            boolean noClip = reader.readBoolean();
            reader.expect(' ');
            float colorCurveMultiplier = reader.readFloat();
            reader.expect(' ');
            float alphaCurveMultiplier = reader.readFloat();
            ParticleOptions data = new ParticleOptions(type);
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.a1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.a2 = a2;
            data.scale1 = scale1;
            data.scale2 = scale2;
            data.lifetime = lifetime;
            data.startingSpin = startingSpin;
            data.spin = spin;
            data.activeMotionMultiplier = activeMotionMultiplier;
            data.activeSpinMultiplier = activeSpinMultiplier;
            data.gravity = gravity;
            data.noClip = noClip;
            data.colorCurveMultiplier = colorCurveMultiplier;
            data.alphaCurveMultiplier = alphaCurveMultiplier;
            return data;
        }

        @Override
        public ParticleOptions fromNetwork(ParticleType<ParticleOptions> type, FriendlyByteBuf buf) {
            float r1 = buf.readFloat();
            float g1 = buf.readFloat();
            float b1 = buf.readFloat();
            float a1 = buf.readFloat();
            float r2 = buf.readFloat();
            float g2 = buf.readFloat();
            float b2 = buf.readFloat();
            float a2 = buf.readFloat();
            float scale1 = buf.readFloat();
            float scale2 = buf.readFloat();
            int lifetime = buf.readInt();
            float startingSpin = buf.readFloat();
            float spin = buf.readFloat();
            float activeMotionMultiplier = buf.readFloat();
            float activeSpinMultiplier = buf.readFloat();
            boolean gravity = buf.readBoolean();
            boolean noClip = buf.readBoolean();
            float colorCurveMultiplier = buf.readFloat();
            float alphaCurveMultiplier = buf.readFloat();
            ParticleOptions data = new ParticleOptions(type);
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.a1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.a2 = a2;
            data.scale1 = scale1;
            data.scale2 = scale2;
            data.lifetime = lifetime;
            data.startingSpin = startingSpin;
            data.spin = spin;
            data.activeMotionMultiplier = activeMotionMultiplier;
            data.activeSpinMultiplier = activeSpinMultiplier;
            data.gravity = gravity;
            data.noClip = noClip;
            data.colorCurveMultiplier = colorCurveMultiplier;
            data.alphaCurveMultiplier = alphaCurveMultiplier;
            return data;
        }
    };
}