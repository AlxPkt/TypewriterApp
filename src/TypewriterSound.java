import javax.sound.sampled.*;

public class TypewriterSound {
    public static boolean isClackEnabled = true, isBellEnabled = true, isReturnEnabled = true;
    private static Clip clickClip, bellClip, returnClip;

    static {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);
            byte[] clickData = new byte[1500];
            for (int i = 0; i < clickData.length; i++) clickData[i] = (byte) ((Math.random() * 255 - 128) * Math.exp(-i / 200.0));
            clickClip = AudioSystem.getClip(); clickClip.open(format, clickData, 0, clickData.length);

            byte[] bellData = new byte[22000];
            for (int i = 0; i < bellData.length; i++) bellData[i] = (byte) ((Math.sin(i * 0.4) * 0.6 + Math.sin(i * 0.6) * 0.4) * 127 * Math.exp(-i / 4000.0));
            bellClip = AudioSystem.getClip(); bellClip.open(format, bellData, 0, bellData.length);

            byte[] returnData = new byte[22000];
            for (int i = 0; i < returnData.length; i++) {
                if (i < 18000) returnData[i] = (byte) ((Math.random() * 60 - 30) * Math.exp(-i / 10000.0));
                else { int t = i - 18000; returnData[i] = (byte) (Math.sin(t / 10.0) * 127 * Math.exp(-t / 800.0)); }
            }
            returnClip = AudioSystem.getClip(); returnClip.open(format, returnData, 0, returnData.length);
        } catch (Exception e) {}
    }

    public static void playClick() { if (isClackEnabled && clickClip != null) { clickClip.setFramePosition(0); clickClip.start(); } }
    public static void playBell() { if (isBellEnabled && bellClip != null) { bellClip.setFramePosition(0); bellClip.start(); } }
    public static void playReturn() { if (isReturnEnabled && returnClip != null) { returnClip.setFramePosition(0); returnClip.start(); } }
}