#version 150

uniform float GameTime;
uniform float Speed;
uniform float Zoom;
uniform float Distortion;
uniform float Intensity;
uniform float Wibble;

uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;

out vec4 fragColor;

float applyDistortion(vec2 uv,float zoom,float distortion, float gooeyness,float wibble)
{
    float gameTime = GameTime * Speed;
    float s = sin(gameTime*0.1);
    float s2 = 0.5+sin(gameTime*1.8);
    vec2 d = uv*(distortion+s*.3);
    d.x += gameTime*0.25+sin(d.x+d.y + gameTime*0.3)*wibble;
    d.y += gameTime*0.25+sin(d.x + gameTime*0.3)*wibble;
    float v1=length(0.5-fract(d.xy))+gooeyness;
    d = (1.0-zoom)*0.5+(uv*zoom);
    float v2=length(0.5-fract(d.xy));
    v1 *= 1.0-v2*v1;
    v1 = v1*v1*v1;
    v1 *= 1.9+s2*0.4;
    return v1;
}

void main()
{
    float goo = applyDistortion(texCoord0, Zoom, Distortion, Intensity, Wibble);
    vec4 col = vertexColor * ColorModulator;
    col.a *= clamp(1.-goo, 0.,1.);
    fragColor = col;
}
