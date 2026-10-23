import javax.sound.sampled.*;

public class TypewriterSound {
    public static boolean isClackEnabled = true, isBellEnabled = true, isReturnEnabled = true, isScrollEnabled = true;
    private static Clip clickClip, bellClip, returnClip, scrollClip;

    static {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);

            // 1. Premium "Thock"
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

            // 3. Smooth Carriage Return
            byte[] returnData = new byte[28000];
            double lastNoise = 0;
            for (int i = 0; i < returnData.length; i++) {
                double t = i / 44100.0;
                if (i < 22000) {
                    double rawNoise = (Math.random() * 2.0 - 1.0);
                    lastNoise = (lastNoise * 0.85) + (rawNoise * 0.15);
                    double ratchet = Math.abs(Math.sin(t * 100 * Math.PI * 2));
                    returnData[i] = (byte) (lastNoise * ratchet * 15 * Math.exp(-t * 1.8));
                } else {
                    double lockTime = (i - 22000) / 44100.0;
                    double thump = Math.sin(lockTime * 50 * Math.PI * 2) * Math.exp(-lockTime * 45.0);
                    returnData[i] = (byte) (thump * 45);
                }
            }
            returnClip = AudioSystem.getClip(); returnClip.open(format, returnData, 0, returnData.length);

            // 4. Platen Scroll Ratchet (Very fast, quiet plastic/metal tick)
            byte[] scrollData = new byte[1200];
            for (int i = 0; i < scrollData.length; i++) {
                double t = i / 44100.0;
                double noise = (Math.random() * 2.0 - 1.0);
                double tick = noise * Math.exp(-t * 4000.0);
                scrollData[i] = (byte) (tick * 15); // Very quiet
            }
            scrollClip = AudioSystem.getClip(); scrollClip.open(format, scrollData, 0, scrollData.length);

        } catch (Exception e) {}
    }

    public static void playClick() { if (isClackEnabled && clickClip != null) { clickClip.setFramePosition(0); clickClip.start(); } }
    public static void playBell() { if (isBellEnabled && bellClip != null) { bellClip.setFramePosition(0); bellClip.start(); } }
    public static void playReturn() { if (isReturnEnabled && returnClip != null) { returnClip.setFramePosition(0); returnClip.start(); } }
    public static void playScroll() { if (isScrollEnabled && scrollClip != null) { scrollClip.setFramePosition(0); scrollClip.start(); } }
}