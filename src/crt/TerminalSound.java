package crt;

import javax.sound.sampled.*;

public class TerminalSound {
    private static Clip clickClip, bellClip, returnClip, scrollClip;

    static {
        try {
            AudioFormat format = new AudioFormat(44100, 8, 1, true, false);

            // 1. Digital Keystroke (Sharp electronic click)
            byte[] clickData = new byte[1500];
            for (int i = 0; i < clickData.length; i++) {
                double t = i / 44100.0;
                double env = Math.exp(-t * 2500.0);
                double wave = (Math.random() * 2.0 - 1.0);
                clickData[i] = (byte) (wave * 35 * env);
            }
            clickClip = AudioSystem.getClip(); clickClip.open(format, clickData, 0, clickData.length);

            // 2. Terminal Warning Beep (800Hz Sine wave)
            byte[] bellData = new byte[15000];
            for (int i = 0; i < bellData.length; i++) {
                double t = i / 44100.0;
                double env = Math.exp(-t * 8.0);
                double wave = Math.sin(t * 800 * Math.PI * 2);
                bellData[i] = (byte) (wave * 30 * env);
            }
            bellClip = AudioSystem.getClip(); bellClip.open(format, bellData, 0, bellData.length);

            // 3. Retro Command Return (Pitch drop)
            byte[] returnData = new byte[12000];
            for (int i = 0; i < returnData.length; i++) {
                double t = i / 44100.0;
                double freq = Math.max(100, 1500 - (t * 6000));
                double env = Math.exp(-t * 15.0);
                double wave = Math.sin(t * freq * Math.PI * 2);
                returnData[i] = (byte) (wave * 25 * env);
            }
            returnClip = AudioSystem.getClip(); returnClip.open(format, returnData, 0, returnData.length);

            // 4. Digital Scroll Blip
            byte[] scrollData = new byte[800];
            for (int i = 0; i < scrollData.length; i++) {
                double t = i / 44100.0;
                double env = Math.exp(-t * 3000.0);
                double wave = Math.sin(t * 2000 * Math.PI * 2);
                scrollData[i] = (byte) (wave * 15 * env);
            }
            scrollClip = AudioSystem.getClip(); scrollClip.open(format, scrollData, 0, scrollData.length);

        } catch (Exception e) {}
    }

    public static void playClick() { if (clickClip != null) { clickClip.setFramePosition(0); clickClip.start(); } }
    public static void playBell() { if (bellClip != null) { bellClip.setFramePosition(0); bellClip.start(); } }
    public static void playReturn() { if (returnClip != null) { returnClip.setFramePosition(0); returnClip.start(); } }
    public static void playScroll() { if (scrollClip != null) { scrollClip.setFramePosition(0); scrollClip.start(); } }
}
