public class Localization {
    public enum Lang { ENGLISH, CZECH }
    public static Lang current = Lang.ENGLISH;

    public static String get(String key) {
        if (current == Lang.CZECH) {
            switch (key) {
                case "File": return "Soubor";
                case "New": return "Nový...";
                case "Open": return "Otevřít...";
                case "Save": return "Uložit jako .txt...";
                case "Print": return "Tisk...";
                case "Exit": return "Ukončit";
                case "Settings": return "Nastavení";
                case "View": return "Zobrazení";
                case "Fullscreen": return "Celá obrazovka";
                case "Focus": return "Režim soustředění";
                case "Theme": return "Motiv";
                case "Font": return "Písmo";
                case "FontSize": return "Velikost písma";
                case "Language": return "Jazyk";
                case "Audio": return "Zvuk stroje";
                case "SoundClack": return "Úder klávesy";
                case "SoundBell": return "Zvonek vozíku";
                case "SoundReturn": return "Návrat vozíku";
                case "Mode": return "Režim:";
                case "TabHint": return "--> TAB = Změna";
                case "Words": return "Slov: ";
                case "Success": return "Hotovo!";
                case "Error": return "Chyba:";
                case "HubTitle": return "Zvolte formát dokumentu";
                case "ChangeType": return "Změnit formát...";

                // Warning Dialogs
                case "Warning": return "Neuložené změny";
                case "Unsaved": return "Chcete uložit své změny před pokračováním?";

                // Formats & Themes
                case "Screenplay": return "Scénář";
                case "Comic Book Script": return "Komiksový scénář";
                case "Novel": return "Román";
                case "Stage Play": return "Divadelní hra";
                case "Podcast / Audio Drama": return "Podcast";
                case "Dark": return "Tmavý";
                case "Light": return "Světlý";
                case "Parchment": return "Pergamen";
                case "Vintage": return "Vintage";
                case "Blueprint": return "Modrotisk";
                case "Dracula": return "Dracula";
                case "Sunset": return "Západ slunce";
                case "Ocean": return "Oceán";

                // Elements
                case "SCENE": return "ZÁHLAVÍ SCÉNY";
                case "ACTION": return "AKCE";
                case "CHARACTER": return "POSTAVA";
                case "PARENTHETICAL": return "POZNÁMKA";
                case "DIALOGUE": return "DIALOG";
                case "TRANSITION": return "PŘECHOD";
                case "PAGE_PANEL": return "STRANA/PANEL";
                case "CAPTION": return "TITULEK";
                case "CHAPTER": return "KAPITOLA";
                case "PROSE": return "PRÓZA";
                case "ACT_SCENE": return "DĚJSTVÍ/SCÉNA";
                case "STAGE_DIRECTION": return "SCÉNICKÁ POZNÁMKA";
                case "SFX_MUSIC": return "ZVUK/HUDBA";
                case "HOST_GUEST": return "MODERÁTOR/HOST";
            }
        }

        // DEFAULT ENGLISH
        switch (key) {
            case "File": return "File";
            case "New": return "New...";
            case "Open": return "Open...";
            case "Save": return "Save to .txt...";
            case "Print": return "Print...";
            case "Exit": return "Exit";
            case "Settings": return "Settings";
            case "View": return "View";
            case "Fullscreen": return "Fullscreen";
            case "Focus": return "Focus Mode";
            case "Theme": return "Theme";
            case "Font": return "Font";
            case "FontSize": return "Font Size";
            case "Language": return "Language";
            case "Audio": return "Audio";
            case "SoundClack": return "Keystroke Clack";
            case "SoundBell": return "Margin Bell";
            case "SoundReturn": return "Carriage Return";
            case "Mode": return "Mode:";
            case "TabHint": return "--> Press TAB to switch";
            case "Words": return "Words: ";
            case "Success": return "Success!";
            case "Error": return "Error:";
            case "HubTitle": return "Select Document Format";
            case "ChangeType": return "Change Format...";

            // Warning Dialogs
            case "Warning": return "Unsaved Changes";
            case "Unsaved": return "Do you want to save your changes before continuing?";

            // Formats & Themes
            case "Screenplay": return "Screenplay";
            case "Comic Book Script": return "Comic Book Script";
            case "Novel": return "Novel";
            case "Stage Play": return "Stage Play";
            case "Podcast / Audio Drama": return "Podcast";
            case "Dark": return "Dark";
            case "Light": return "Light";
            case "Parchment": return "Parchment";
            case "Vintage": return "Vintage";
            case "Blueprint": return "Blueprint";
            case "Dracula": return "Dracula";
            case "Sunset": return "Sunset";
            case "Ocean": return "Ocean";

            // Elements
            case "SCENE": return "SCENE HEADING";
            case "ACTION": return "ACTION";
            case "CHARACTER": return "CHARACTER";
            case "PARENTHETICAL": return "PARENTHETICAL";
            case "DIALOGUE": return "DIALOGUE";
            case "TRANSITION": return "TRANSITION";
            case "PAGE_PANEL": return "PAGE/PANEL";
            case "CAPTION": return "CAPTION";
            case "CHAPTER": return "CHAPTER";
            case "PROSE": return "PROSE";
            case "ACT_SCENE": return "ACT/SCENE";
            case "STAGE_DIRECTION": return "STAGE DIRECTION";
            case "SFX_MUSIC": return "SFX / MUSIC";
            case "HOST_GUEST": return "HOST / GUEST";
        }
        return key;
    }
}