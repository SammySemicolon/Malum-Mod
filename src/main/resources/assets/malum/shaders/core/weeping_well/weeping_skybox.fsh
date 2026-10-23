#version 150

#moj_import <lodestone:common_math.glsl>
#moj_import <malum:common_math.glsl>

uniform sampler2D Sampler0;
uniform float LumiTransparency;

uniform vec4 ColorModulator;
uniform float GameTime;
uniform float Speed;
uniform float Distortion;
uniform float Width;
uniform float Height;
uniform vec4 UVCoordinates;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0;
    vec2 uCap = vec2(UVCoordinates.x, UVCoordinates.y);
    vec2 vCap = vec2(UVCoordinates.z, UVCoordinates.w);
    uv.x = floor(uv.x * Width)/ Width;
    uv.y = floor(uv.y * Height)/ Height;
    uv.x = clamp(uv.x, uCap.x, uCap.y);
    uv.y = clamp(uv.y, vCap.x, vCap.y);
    vec4 noise = fancySample(Sampler0, uv, Speed, Distortion, GameTime);
    vec4 color = transformColor(noise, LumiTransparency, vertexColor, ColorModulator);
    fragColor = color;
}