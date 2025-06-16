public class Noise {
    private final int seed = (int)(Math.random() * Integer.MAX_VALUE);

    private float noise(int x, int y){
        int n = x * 374761393 + y * 668265263 + seed;
        n = (n ^ (n >> 13)) * 1274126177;
        return 1.0f - ((n & 0x7fffffff) / 1073741824.0f);
    }

    private float interpolate(float a, float b, float t){
        float ft = t * (float)Math.PI;
        float f = (1 - (float)Math.cos(ft)) * 0.5f;
        return a * (1 - f) + b * f;
    }

    private float smoothNoise(float x, float y){
        int x0 = (int)x;
        int y0 = (int)y;
        float xf = x - x0;
        float yf = y - y0;

        float top = interpolate(noise(x0, y0), noise(x0 + 1, y0), xf);
        float bottom = interpolate(noise(x0, y0 + 1), noise(x0 + 1, y0 + 1), xf);

        return interpolate(top, bottom, yf);
    }

    public float perlin(float x, float y, int octaves, float persistance){
        float total = 0; 
        float freq = 1;
        float amp = 1;
        float max = 0;

        for(int i = 0; i < octaves; i++){
            total += smoothNoise(x*freq, y*freq) * amp;
            max += amp;
            amp *= persistance;
            freq *= 2;
        }

        return total/max;
    }
}
