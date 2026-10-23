import java.awt.Color;

public enum Theme {
    DARK(new Color(25, 25, 25), new Color(220, 220, 220), new Color(40, 40, 40), new Color(86, 156, 214), new Color(50,50,50), 8, "Dark"),
    LIGHT(new Color(252, 252, 252), new Color(30, 30, 30), new Color(240, 240, 240), new Color(0, 100, 200), new Color(220,220,220), 10, "Light"),
    PARCHMENT(new Color(244, 238, 224), new Color(50, 40, 30), new Color(224, 214, 190), new Color(139, 69, 19), new Color(210, 190, 170), 15, "Parchment"),
    VINTAGE(new Color(230, 220, 200), new Color(35, 30, 30), new Color(200, 190, 170), new Color(160, 50, 50), new Color(200, 50, 50, 100), 25, "Vintage"),
    BLUEPRINT(new Color(15, 45, 85), new Color(230, 245, 255), new Color(10, 30, 60), new Color(100, 200, 255), new Color(100, 150, 255, 100), 12, "Blueprint"),
    CHALKBOARD(new Color(35, 55, 45), new Color(245, 245, 235), new Color(25, 40, 30), new Color(255, 200, 100), new Color(100, 150, 120), 20, "Chalkboard");

    public final Color bg, fg, statusBg, accent, marginColor;
    public final int noiseAlpha; // How visible the paper grain texture is
    public final String langKey;

    Theme(Color bg, Color fg, Color statusBg, Color accent, Color marginColor, int noiseAlpha, String langKey) {
        this.bg = bg; this.fg = fg; this.statusBg = statusBg;
        this.accent = accent; this.marginColor = marginColor;
        this.noiseAlpha = noiseAlpha; this.langKey = langKey;
    }
}