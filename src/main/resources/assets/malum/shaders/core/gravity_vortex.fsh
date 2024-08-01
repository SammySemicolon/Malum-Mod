#version 150

#moj_import <fog.glsl>


uniform sampler2D Sampler0;

uniform float GameTime;
uniform float RingCount;
uniform float RingSpeed;
uniform float CycleDuration;
uniform float TunnelElongation;

const vec2 center = vec2(0.5);
const float spacing = 1.0 / RingCount;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;

out vec4 fragColor;

//taken from https://www.shadertoy.com/view/ltBXRc
float variation(vec2 v1, vec2 v2, float strength, float speed) {
    return sin(dot(normalize(v1), normalize(v2)) * strength + GameTime * 1000 * speed) / 100.0;
}

//taken from https://www.shadertoy.com/view/ltBXRc
vec3 paintCircle (vec2 uv, vec2 center, float rad, float width, float index) {
    vec2 diff = center-uv;
    float len = length(diff);
    float scale = rad;
    float mult = mod(index, 2.) == 0. ? 1. : -1.;
    len += variation(diff, vec2(rad*mult, 1.0), 7.0*scale, 2.0);
    len -= variation(diff, vec2(1.0, rad*mult), 7.0*scale, 2.0);
    float circle = smoothstep((rad-width)*scale, (rad)*scale, len) - smoothstep((rad)*scale, (rad+width)*scale, len);
    return vec3(circle);
}


vec3 paintRing(vec2 uv, vec2 center, float radius, float index){
    vec3 color = paintCircle(uv, center, radius, 0.075, index);
    color += paintCircle(uv, center, radius, 0.015, index); //White
    return color;
}


void main() {
    vec2 uv = texCoord0;

    float radius = mod(1000 * GameTime/RingSpeed, CycleDuration);
    vec3 color;

    float border = 0.25;
    vec2 bl = smoothstep(0.0, border, uv);
    vec2 tr = smoothstep(0.0, border, 1.0 - uv);

    for(float i = 0.0; i < RingCount; i++){
        color += paintRing(uv, center, TunnelElongation*log(mod(radius + i * spacing, CycleDuration)), i );
        color += paintRing(uv, center, log(mod(radius + i * spacing, CycleDuration)), i);
    }

    color = mix(color, vec3(0.0), distance(uv, center) * 1.95);

    fragColor = vec4(color, 0.5);
}