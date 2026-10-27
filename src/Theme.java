import java.awt.Color;

public enum Theme {
    // DARK MODE
    DARK("Dark", "CatDark", new Color(30, 30, 30), new Color(220, 220, 220), new Color(100, 150, 255), new Color(20, 20, 20), new Color(50, 50, 50), 0),
    DRACULA("Dracula", "CatDark", new Color(40, 42, 54), new Color(248, 248, 242), new Color(255, 121, 198), new Color(30, 31, 41), new Color(68, 71, 90), 0),
    NORD("Nord", "CatDark", new Color(46, 52, 64), new Color(216, 222, 233), new Color(136, 192, 208), new Color(36, 41, 51), new Color(76, 86, 106), 0),
    SOLARIZED_DARK("SolarizedDark", "CatDark", new Color(0, 43, 54), new Color(131, 148, 150), new Color(42, 161, 152), new Color(0, 33, 43), new Color(7, 54, 66), 0),
    MONOKAI("Monokai", "CatDark", new Color(39, 40, 34), new Color(248, 248, 242), new Color(166, 226, 46), new Color(30, 31, 28), new Color(73, 72, 62), 0),
    ROSE_PINE("RosePine", "CatDark", new Color(25, 23, 36), new Color(224, 222, 244), new Color(235, 111, 146), new Color(19, 17, 28), new Color(38, 35, 58), 0),
    AMETHYST("Amethyst", "CatDark", new Color(30, 20, 40), new Color(220, 200, 240), new Color(180, 100, 255), new Color(20, 12, 28), new Color(60, 40, 80), 5),
    MIDNIGHT("Midnight", "CatDark", new Color(5, 10, 20), new Color(180, 190, 220), new Color(100, 140, 255), new Color(2, 5, 12), new Color(30, 45, 80), 5),
    VAMPIRE("Vampire", "CatDark", new Color(15, 5, 5), new Color(200, 180, 180), new Color(220, 20, 40), new Color(8, 2, 2), new Color(100, 20, 20), 10),

    // LIGHT MODE
    LIGHT("Light", "CatLight", new Color(245, 245, 245), new Color(30, 30, 30), new Color(0, 100, 200), new Color(230, 230, 230), new Color(200, 200, 200), 0),
    SOLARIZED_LIGHT("SolarizedLight", "CatLight", new Color(253, 246, 227), new Color(101, 123, 131), new Color(38, 139, 210), new Color(238, 232, 213), new Color(220, 210, 190), 0),
    LAVENDER("Lavender", "CatLight", new Color(245, 240, 255), new Color(70, 50, 100), new Color(130, 90, 200), new Color(230, 220, 245), new Color(200, 180, 230), 5),
    ARCTIC("Arctic", "CatLight", new Color(235, 245, 250), new Color(40, 60, 80), new Color(0, 150, 200), new Color(220, 235, 245), new Color(180, 210, 230), 0),

    // NATURE & WARM
    SUNSET("Sunset", "CatNature", new Color(45, 25, 30), new Color(255, 220, 180), new Color(255, 100, 80), new Color(30, 15, 20), new Color(80, 40, 50), 10),
    OCEAN("Ocean", "CatNature", new Color(15, 35, 50), new Color(200, 230, 255), new Color(0, 200, 180), new Color(10, 25, 35), new Color(30, 70, 100), 15),
    COFFEE("Coffee", "CatNature", new Color(50, 35, 25), new Color(220, 200, 180), new Color(180, 120, 80), new Color(35, 22, 15), new Color(80, 60, 45), 25),
    FOREST("Forest", "CatNature", new Color(20, 40, 25), new Color(180, 220, 190), new Color(80, 200, 120), new Color(12, 25, 15), new Color(40, 80, 50), 10),
    CHERRY_BLOSSOM("CherryBlossom", "CatNature", new Color(255, 245, 248), new Color(100, 60, 80), new Color(255, 105, 180), new Color(250, 230, 238), new Color(255, 180, 200), 5),
    AUTUMN("Autumn", "CatNature", new Color(45, 25, 15), new Color(230, 200, 170), new Color(255, 120, 50), new Color(30, 15, 8), new Color(150, 80, 40), 15),

    // RETRO & VINTAGE
    PARCHMENT("Parchment", "CatRetro", new Color(240, 230, 210), new Color(60, 50, 40), new Color(180, 60, 60), new Color(220, 210, 190), new Color(200, 190, 170), 40),
    VINTAGE("Vintage", "CatRetro", new Color(50, 40, 35), new Color(220, 200, 180), new Color(200, 100, 80), new Color(35, 25, 20), new Color(70, 60, 50), 30),
    BLUEPRINT("Blueprint", "CatRetro", new Color(20, 40, 80), new Color(230, 240, 255), new Color(100, 180, 255), new Color(10, 25, 55), new Color(40, 80, 150), 20),
    SEPIA("Sepia", "CatRetro", new Color(112, 66, 20), new Color(245, 230, 200), new Color(255, 180, 100), new Color(80, 45, 10), new Color(150, 90, 30), 30),
    TYPEWRITER_CLASSIC("TypewriterClassic", "CatRetro", new Color(240, 235, 225), new Color(20, 20, 20), new Color(180, 50, 50), new Color(225, 220, 210), new Color(180, 175, 165), 35),

    // SCI-FI & CYBER
    HACKER("Hacker", "CatSciFi", new Color(5, 15, 5), new Color(0, 255, 0), new Color(200, 255, 200), new Color(0, 10, 0), new Color(0, 80, 0), 0),
    CYBERPUNK("Cyberpunk", "CatSciFi", new Color(13, 2, 33), new Color(0, 255, 204), new Color(255, 0, 85), new Color(8, 1, 20), new Color(255, 0, 85), 15),
    MATRIX("Matrix", "CatSciFi", new Color(0, 0, 0), new Color(0, 210, 0), new Color(150, 255, 150), new Color(0, 15, 0), new Color(0, 100, 0), 25),
    SYNTHWAVE("Synthwave", "CatSciFi", new Color(43, 33, 90), new Color(255, 139, 255), new Color(0, 240, 255), new Color(30, 22, 65), new Color(255, 139, 255), 20);

    public final String langKey;
    public final String categoryKey;
    public final Color bg, fg, accent, statusBg, marginColor;
    public final int noiseAlpha;

    Theme(String langKey, String categoryKey, Color bg, Color fg, Color accent, Color statusBg, Color marginColor, int noiseAlpha) {
        this.langKey = langKey;
        this.categoryKey = categoryKey;
        this.bg = bg;
        this.fg = fg;
        this.accent = accent;
        this.statusBg = statusBg;
        this.marginColor = marginColor;
        this.noiseAlpha = noiseAlpha;
    }
}