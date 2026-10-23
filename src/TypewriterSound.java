import javax.sound.sampled.*;

public class TypewriterSound {
    public static boolean isClackEnabled = true, isBellEnabled = true, isReturnEnabled = true;
    private static Clip clickClip, bellClip, returnClip;

    static {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);

            // 1. Premium "Thock" (Deep, resonant mechanical switch)
            byte[] clickData = new byte[2500];
            for (int i = 0; i < clickData.length; i++) {
                double t = i / 44100.0;
                double noise = (Math.random() * 2.0 - 1.0);
                double clickTransient = noise * Math.exp(-t * 1200.0);
                double woodResonance = Math.sin(t * 300 * Math.PI * 2) * Math.exp(-t * 200.0);
                clickData[i] = (byte) ((clickTransient * 0.4 + woodResonance * 0.6) * 45);
            }
            clickClip = AudioSystem.getClip(); clickClip.open(format, clickData, 0, clickData.length);

            // 2. Crystal Clear Bell
            byte[] bellData = new byte[44100];
            for (int i = 0; i < bellData.length; i++) {
                double t = i / 44100.0;
                double env = Math.exp(-t * 3.5);
                double wave = Math.sin(t * 1600 * Math.PI * 2) * 1.0
                        + Math.sin(t * 2400 * Math.PI * 2) * 0.4;
                bellData[i] = (byte) (wave * 20 * env);
            }
            bellClip = AudioSystem.getClip(); bellClip.open(format, bellData, 0, bellData.length);

            // 3. Smooth, Lubricated Carriage Return (Low-pass filtered)
            byte[] returnData = new byte[28000];
            double lastNoise = 0; // Used for the low-pass filter to remove sharp frequencies

            for (int i = 0; i < returnData.length; i++) {
                double t = i / 44100.0;
                if (i < 22000) {
                    double rawNoise = (Math.random() * 2.0 - 1.0);
                    // Heavy low-pass filter: smooths the noise into a soft rushing sound
                    lastNoise = (lastNoise * 0.85) + (rawNoise * 0.15);

                    double ratchet = Math.abs(Math.sin(t * 100 * Math.PI * 2));
                    // Lowered the volume multiplier to 15 to make it much softer
                    returnData[i] = (byte) (lastNoise * ratchet * 15 * Math.exp(-t * 1.8));
                } else {
                    double lockTime = (i - 22000) / 44100.0;
                    double thump = Math.sin(lockTime * 50 * Math.PI * 2) * Math.exp(-lockTime * 45.0);
                    returnData[i] = (byte) (thump * 45);
                }
            }
            returnClip = AudioSystem.getClip(); returnClip.open(format, returnData, 0, returnData.length);
        } catch (Exception e) {}
    }

    public static void playClick() { if (isClackEnabled && clickClip != null) { clickClip.setFramePosition(0); clickClip.start(); } }
    public static void playBell() { if (isBellEnabled && bellClip != null) { bellClip.setFramePosition(0); bellClip.start(); } }
    public static void playReturn() { if (isReturnEnabled && returnClip != null) { returnClip.setFramePosition(0); returnClip.start(); } }
}