import java.awt.Color;

public enum Theme {
    // --- CLASSIC THEMES ---
    DARK(new Color(28, 28, 30), new Color(220, 220, 225), new Color(40, 40, 42), new Color(90, 160, 220), new Color(100,100,100, 80), 0, "Dark"),
    LIGHT(new Color(236, 236, 240), new Color(45, 45, 48), new Color(220, 220, 225), new Color(0, 100, 200), new Color(160,160,160, 100), 0, "Light"),
    PARCHMENT(new Color(228, 218, 200), new Color(60, 50, 40), new Color(210, 200, 185), new Color(139, 69, 19), new Color(150, 130, 110, 90), 15, "Parchment"),
    VINTAGE(new Color(210, 200, 185), new Color(45, 40, 40), new Color(195, 185, 170), new Color(150, 60, 60), new Color(150, 130, 110, 90), 20, "Vintage"),
    BLUEPRINT(new Color(20, 45, 80), new Color(230, 245, 255), new Color(15, 35, 65), new Color(120, 200, 255), new Color(120, 180, 255, 90), 8, "Blueprint"),
    DRACULA(new Color(40, 42, 54), new Color(248, 248, 242), new Color(33, 34, 44), new Color(255, 121, 198), new Color(98, 114, 164, 100), 0, "Dracula"),
    SUNSET(new Color(45, 25, 25), new Color(255, 210, 170), new Color(35, 15, 15), new Color(255, 160, 90), new Color(150, 80, 80, 100), 0, "Sunset"),
    OCEAN(new Color(15, 35, 45), new Color(210, 240, 255), new Color(10, 25, 35), new Color(50, 190, 210), new Color(60, 120, 150, 100), 0, "Ocean"),

    // --- 10 NEW THEMES ---
    NORD(new Color(46, 52, 64), new Color(216, 222, 233), new Color(59, 66, 82), new Color(136, 192, 208), new Color(76, 86, 106, 100), 0, "Nord"),
    SOLARIZED_DARK(new Color(0, 43, 54), new Color(131, 148, 150), new Color(7, 54, 66), new Color(38, 139, 210), new Color(88, 110, 117, 90), 0, "SolarizedDark"),
    SOLARIZED_LIGHT(new Color(253, 246, 227), new Color(101, 123, 131), new Color(238, 232, 213), new Color(42, 161, 152), new Color(147, 161, 161, 90), 0, "SolarizedLight"),
    MONOKAI(new Color(39, 40, 34), new Color(248, 248, 242), new Color(49, 50, 44), new Color(166, 226, 46), new Color(117, 113, 94, 100), 0, "Monokai"),
    ROSE_PINE(new Color(25, 23, 36), new Color(224, 222, 244), new Color(31, 29, 46), new Color(235, 188, 186), new Color(110, 106, 134, 100), 0, "RosePine"),
    COFFEE(new Color(44, 30, 22), new Color(218, 192, 163), new Color(56, 38, 28), new Color(212, 163, 115), new Color(112, 85, 65, 100), 8, "Coffee"),
    SEPIA(new Color(245, 230, 200), new Color(90, 60, 30), new Color(225, 210, 180), new Color(180, 80, 40), new Color(190, 160, 120, 90), 15, "Sepia"),
    FOREST(new Color(20, 35, 25), new Color(200, 220, 205), new Color(25, 45, 30), new Color(100, 180, 120), new Color(60, 90, 70, 100), 0, "Forest"),
    AMETHYST(new Color(30, 15, 35), new Color(230, 210, 240), new Color(45, 25, 50), new Color(170, 100, 220), new Color(100, 60, 120, 100), 0, "Amethyst"),
    HACKER(new Color(10, 10, 10), new Color(20, 220, 20), new Color(20, 20, 20), new Color(50, 255, 50), new Color(0, 100, 0, 100), 0, "Hacker");

    public final Color bg, fg, statusBg, accent, marginColor;
    public final int noiseAlpha;
    public final String langKey;

    Theme(Color bg, Color fg, Color statusBg, Color accent, Color marginColor, int noiseAlpha, String langKey) {
        this.bg = bg; this.fg = fg; this.statusBg = statusBg;
        this.accent = accent; this.marginColor = marginColor;
        this.noiseAlpha = noiseAlpha; this.langKey = langKey;
    }
}