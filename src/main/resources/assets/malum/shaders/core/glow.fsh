#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform mat4 ProjInverseMat;
uniform mat4 ViewInverseMat;

uniform vec2 BlurDir;

uniform vec4 SightedType;


in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

// https://danielilett.com/2019-05-08-tut1-3-smo-blur/
// Thank you!

vec3 playerSpace(vec2 uv, float depth)
{
    vec3 ndc = vec3(uv, depth) * 2.0 - 1.0;
    vec4 posCS = vec4(ndc, 1.0);
    vec4 posVS = ProjInverseMat * posCS;
    posVS /= posVS.w;
    vec4 posPS = ViewInverseMat * posVS;
    return posPS.xyz;
}

float remap(float v, float minOld, float maxOld, float minNew, float maxNew)
{
    return minNew + (v - minOld) * (maxNew - minNew) / (maxOld - minOld);
}

float gaussian(int x)
{
    float sigma = 20.0;// standard deviation. No idea what this actually does
    float sigmaSq = sigma * sigma;

    // 1 / sqrt(2 * PI) = 0.398942

    return 0.398942 / sigma * exp(float(-x * x) / (2.0 * sigmaSq));
}

void main()
{
    // Convert depth to distance

    float depth = texture(DiffuseDepthSampler, texCoord).r;
    vec3 posPS = playerSpace(texCoord, depth);
    float dstToSurface = length(posPS);


    // Init variables

    vec3 color = vec3(0.0);

    // Set kernel radius (blur strength) to be dependent on depth
    // So at 0 distance kernel radius = 0 (no blur) and at 8 (or more) distance it is 16

    int kernelRadius = int(remap(clamp(dstToSurface, SightedType.x, SightedType.y), SightedType.x, SightedType.y, SightedType.z, SightedType.w));

    if (kernelRadius == 0)
    {
        color = texture(DiffuseSampler, texCoord).rgb;
    }
    else
    {
        float kernelSum = 0.0;

        for (int x = -kernelRadius; x <= kernelRadius; ++x)
        {
            float gauss = gaussian(x);
            kernelSum += gauss;
            color += gauss * texture(DiffuseSampler, texCoord + oneTexel * BlurDir * x, 0.0).rgb;
        }

        color /= kernelSum;
    }

    fragColor = vec4(color, 1.0) + texture(DiffuseSampler, texCoord);

    gl_FragDepth = depth;
}