#version 150

#moj_import <fog.glsl>


uniform sampler2D Sampler0;

uniform float GameTime;
uniform float RingCount;
uniform float RingSpeed;
uniform float CycleDuration;
uniform float TunnelElongation;
uniform float Width;
uniform float Height;
uniform float RotationSpeed;
uniform float Alpha;

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
    vec3 color = paintCircle(uv, center, radius, 0.095, index);
    color *= vec3(0.9,0.9,0.9);
    color += paintCircle(uv, center, radius, 0.025, index); //White
    return color;
}

mat2 rotate2d(float angle){
    return mat2(cos(angle),-sin(angle),
    sin(angle),cos(angle));
}

void main() {
    vec2 uv = texCoord0;

    float rotationAngle = GameTime * RotationSpeed; // Adjust speed of rotation as needed
    uv -= 0.5; // Translate to origin
    uv = rotate2d(rotationAngle) * uv;
    uv += 0.5; // Translate back

    vec2 center = vec2(0.5);
    float spacing = 1.0 / RingCount;

    float radius = mod(1000 * GameTime/RingSpeed, CycleDuration);
    vec3 color;

    float border = 0.25;
    vec2 bl = smoothstep(0.0, border, uv);
    vec2 tr = smoothstep(0.0, border, 1.0 - uv);

    uv.x = floor(uv.x* Width)/ Width;
    uv.y = floor(uv.y* Height)/ Height;

    for(float i = 0.0; i < RingCount; i++){
        color += paintRing(uv, center, TunnelElongation*log(mod(radius + i * spacing, CycleDuration)), i );
        color += paintRing(uv, center, log(mod(radius + i * spacing, CycleDuration)), i);
    }

    vec2 v = rotate2d(GameTime * 1000) * uv;
    color *= vec3(v.x, v.y, 0.7-v.y*v.x);
    color = mix(color, vec3(0.0), distance(uv, center) * 1.95);

    float distToCenter = distance(uv, center);
    float fadeFactor = 1.0 - smoothstep(0.45, 0.5, distToCenter); // Adjusted fading range
    color *= fadeFactor;

    color *= Alpha;
    fragColor = vec4(color, 0.5) * (.75 - distToCenter);
}