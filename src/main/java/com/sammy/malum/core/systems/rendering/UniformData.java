package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.shaders.AbstractUniform;

public class UniformData {
    public final String uniformName;
    public final int uniformType;

    public UniformData(String uniformName, int uniformType) {
        this.uniformName = uniformName;
        this.uniformType = uniformType;
    }

    public void setUniformValue(AbstractUniform uniform) {

    }

    public static class FloatUniformData extends UniformData {
        public final float[] array;

        public FloatUniformData(String uniformName, int uniformType, float[] array) {
            super(uniformName, uniformType);
            this.array = array;
        }

        @Override
        public void setUniformValue(AbstractUniform uniform) {
            if (uniformType <= 7) {
                uniform.setSafe(array[0], array[1], array[2], array[3]);
            } else {
                uniform.set(array);
            }
        }
    }

    public static class IntegerUniformData extends UniformData {
        public final int[] array;

        public IntegerUniformData(String uniformName, int uniformType, int[] array) {
            super(uniformName, uniformType);
            this.array = array;
        }

        @Override
        public void setUniformValue(AbstractUniform uniform) {
            uniform.setSafe(array[0], array[1], array[2], array[3]);
        }
    }
}
