import java.awt.Color;

public enum Theme {
    DARK(new Color(28, 28, 30), new Color(220, 220, 225), new Color(40, 40, 42), new Color(90, 160, 220), new Color(100,100,100, 80), 0, "Dark"),
    // Dimmed Light Theme (Soft off-white/gray instead of glaring white)
    LIGHT(new Color(236, 236, 240), new Color(45, 45, 48), new Color(220, 220, 225), new Color(0, 100, 200), new Color(160,160,160, 100), 0, "Light"),
    // Dimmed Parchment
    PARCHMENT(new Color(228, 218, 200), new Color(60, 50, 40), new Color(210, 200, 185), new Color(139, 69, 19), new Color(150, 130, 110, 90), 15, "Parchment"),
    // Dimmed Vintage
    VINTAGE(new Color(210, 200, 185), new Color(45, 40, 40), new Color(195, 185, 170), new Color(150, 60, 60), new Color(150, 130, 110, 90), 20, "Vintage"),
    BLUEPRINT(new Color(20, 45, 80), new Color(230, 245, 255), new Color(15, 35, 65), new Color(120, 200, 255), new Color(120, 180, 255, 90), 8, "Blueprint"),
    DRACULA(new Color(40, 42, 54), new Color(248, 248, 242), new Color(33, 34, 44), new Color(255, 121, 198), new Color(98, 114, 164, 100), 0, "Dracula"),
    SUNSET(new Color(45, 25, 25), new Color(255, 210, 170), new Color(35, 15, 15), new Color(255, 160, 90), new Color(150, 80, 80, 100), 0, "Sunset"),
    OCEAN(new Color(15, 35, 45), new Color(210, 240, 255), new Color(10, 25, 35), new Color(50, 190, 210), new Color(60, 120, 150, 100), 0, "Ocean");

    public final Color bg, fg, statusBg, accent, marginColor;
    public final int noiseAlpha;
    public final String langKey;

    Theme(Color bg, Color fg, Color statusBg, Color accent, Color marginColor, int noiseAlpha, String langKey) {
        this.bg = bg; this.fg = fg; this.statusBg = statusBg;
        this.accent = accent; this.marginColor = marginColor;
        this.noiseAlpha = noiseAlpha; this.langKey = langKey;
    }
}