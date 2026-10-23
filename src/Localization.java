public class Localization {
    public enum Lang {
        ENGLISH, CZECH, SLOVAK, GERMAN, SPANISH, FRENCH, ITALIAN, PORTUGUESE,
        POLISH, RUSSIAN, DUTCH, SWEDISH, JAPANESE, CHINESE, KOREAN, HINDI, ARABIC, TURKISH
    }
    public static Lang current = Lang.ENGLISH;

    public static String get(String key) {
        if (current == Lang.CZECH) {
            switch (key) {
                case "File": return "Soubor"; case "New": return "Nový scénář"; case "Open": return "Otevřít...";
                case "Save": return "Uložit jako .txt..."; case "Export": return "Exportovat do PDF...";
                case "Print": return "Tisk..."; case "Exit": return "Ukončit";
                case "Settings": return "Nastavení"; case "View": return "Zobrazení";
                case "Fullscreen": return "Režim celé obrazovky"; case "Focus": return "Režim soustředění";
                case "Theme": return "Motiv"; case "Font": return "Písmo"; case "FontSize": return "Velikost písma";
                case "Margins": return "Okraje"; case "Audio": return "Zvuky stroje"; case "Language": return "Jazyk";
                case "SoundClack": return "Úder klávesy"; case "SoundBell": return "Zvonek na konci řádku";
                case "SoundReturn": return "Návrat vozíku (Enter)";
                case "Narrow": return "Úzké"; case "Standard": return "Standardní"; case "Wide": return "Široké";
                case "Vintage": return "Vintage Stroj"; case "Blueprint": return "Modrotisk"; case "Chalkboard": return "Tabule";
                case "Mode": return "Režim:"; case "TabHint": return "--> TAB = Změna režimu"; case "Words": return "Slov: ";
                case "SCENE": return "ZÁHLAVÍ SCÉNY"; case "ACTION": return "AKCE"; case "CHARACTER": return "POSTAVA";
                case "PARENTHETICAL": return "POZNÁMKA"; case "DIALOGUE": return "DIALOG"; case "TRANSITION": return "PŘECHOD";
            }
        }

        // Default ENGLISH & Fallback
        switch (key) {
            case "File": return "File"; case "New": return "New Script"; case "Open": return "Open...";
            case "Save": return "Save to .txt..."; case "Export": return "Export to PDF...";
            case "Print": return "Print..."; case "Exit": return "Exit Application";
            case "Settings": return "Settings"; case "View": return "View Options";
            case "Fullscreen": return "Toggle Fullscreen"; case "Focus": return "Focus Mode (Hide UI)";
            case "Theme": return "Theme"; case "Font": return "Font"; case "FontSize": return "Font Size";
            case "Margins": return "Margins"; case "Audio": return "Typewriter Audio"; case "Language": return "Language";
            case "SoundClack": return "Keystroke Clack"; case "SoundBell": return "End-of-line Bell";
            case "SoundReturn": return "Carriage Return Slide";
            case "Narrow": return "Narrow"; case "Standard": return "Standard"; case "Wide": return "Wide";
            case "Vintage": return "Vintage Typewriter"; case "Blueprint": return "Blueprint"; case "Chalkboard": return "Chalkboard";
            case "Mode": return "Mode:"; case "TabHint": return "--> Press TAB to switch elements"; case "Words": return "Words: ";
            case "SCENE": return "SCENE HEADING"; case "ACTION": return "ACTION"; case "CHARACTER": return "CHARACTER";
            case "PARENTHETICAL": return "PARENTHETICAL"; case "DIALOGUE": return "DIALOGUE"; case "TRANSITION": return "TRANSITION";
        }
        return key;
    }
}