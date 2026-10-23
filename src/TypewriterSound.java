import javax.sound.sampled.*;

public class TypewriterSound {
    public static boolean isClackEnabled = true;
    public static boolean isBellEnabled = true;
    public static boolean isReturnEnabled = true;

    private static Clip clickClip, bellClip, returnClip;

    static {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);

            // 1. Keystroke Clack
            byte[] clickData = new byte[1500];
            for (int i = 0; i < clickData.length; i++) {
                clickData[i] = (byte) ((Math.random() * 255 - 128) * Math.exp(-i / 200.0));
            }
            clickClip = AudioSystem.getClip();
            clickClip.open(format, clickData, 0, clickData.length);

            // 2. High-pitched Mechanical Bell
            byte[] bellData = new byte[22000];
            for (int i = 0; i < bellData.length; i++) {
                double wave = Math.sin(i * 0.4) * 0.6 + Math.sin(i * 0.6) * 0.4;
                bellData[i] = (byte) (wave * 127 * Math.exp(-i / 4000.0));
            }
            bellClip = AudioSystem.getClip();
            bellClip.open(format, bellData, 0, bellData.length);

            // 3. Carriage Return (Sliding zip + heavy thump)
            byte[] returnData = new byte[22000]; // ~0.5 seconds long
            for (int i = 0; i < returnData.length; i++) {
                if (i < 18000) { // The sliding metallic zip
                    returnData[i] = (byte) ((Math.random() * 60 - 30) * Math.exp(-i / 10000.0));
                } else { // The heavy lock thump at the end
                    int thumpIdx = i - 18000;
                    returnData[i] = (byte) (Math.sin(thumpIdx / 10.0) * 127 * Math.exp(-thumpIdx / 800.0));
                }
            }
            returnClip = AudioSystem.getClip();
            returnClip.open(format, returnData, 0, returnData.length);

        } catch (Exception e) {
            System.err.println("Audio synthesis not supported.");
        }
    }

    public static void playClick() {
        if (isClackEnabled && clickClip != null) {
            clickClip.setFramePosition(0); clickClip.start();
        }
    }

    public static void playBell() {
        if (isBellEnabled && bellClip != null) {
            bellClip.setFramePosition(0); bellClip.start();
        }
    }

    public static void playReturn() {
        if (isReturnEnabled && returnClip != null) {
            returnClip.setFramePosition(0); returnClip.start();
        }
    }
}