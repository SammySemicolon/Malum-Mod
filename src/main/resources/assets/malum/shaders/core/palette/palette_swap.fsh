#version 150

#moj_import <lodestone:common_math.glsl>

uniform sampler2D Sampler0;

uniform float InputPalette[48];
uniform float OutputPalette[48];

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;

    vec4 textureColor = texture(Sampler0, uv);
    if (textureColor.a == 0) {
        discard;
    }

    for(int i = 0; i <= 16; i++) {
        int index = i * 3;
        int r = index;
        int g = index + 1;
        int b = index + 2;

        vec3 inputColor = vec3(InputPalette[r], InputPalette[g], InputPalette[b]);
        vec3 outputColor = vec3(OutputPalette[r], OutputPalette[g], OutputPalette[b]);
        if (textureColor.rgb == inputColor) {
            textureColor.rgb = outputColor;
        }
    }

    fragColor = textureColor;
}
