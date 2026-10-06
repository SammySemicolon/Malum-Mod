#version 150

#moj_import <lodestone:common_math.glsl>
#moj_import <malum:common_math.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Skybox;
uniform float LumiTransparency;

uniform vec2 ScreenSize;
uniform vec4 ColorModulator;
uniform float GameTime;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

uniform float Speed;
uniform float Distortion;
uniform float Width;
uniform float Height;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    vec2 skyUv = gl_FragCoord.xy/ScreenSize;
    float skyWidth = Width;
    float skyHeight = Height;
    skyUv.x = floor(skyUv.x* skyWidth)/ skyWidth;
    skyUv.y = floor(skyUv.y* skyHeight)/ skyHeight;
    vec4 skyColor = texture(Skybox, skyUv);
    uv.x += skyColor.r * 0.1;
    uv.y -= skyColor.b * 0.1;
    vec4 noise = fancySample(Sampler0, uv, Speed, Distortion, GameTime);
    float luminesence = (0.21 * noise.r + 0.71 * noise.g + 0.07 * noise.b);
    vec4 color = transformColor(noise, LumiTransparency, vertexColor, ColorModulator);
    color = vec4(color.rgb + (skyColor.rgb * noise.rgb * luminesence * 1.5), color.a);
    color.r = min(color.r, 1.0);
    color.g = min(color.g, 1.0);
    color.b = min(color.b, 1.0);
    vec4 fog = applyFog(color, FogStart, FogEnd, FogColor, vertexDistance);
    if (fog.a == 0.0) {
        discard;
    }
    fragColor = fog;
}