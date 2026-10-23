public class Localization {
    public enum Lang {
        ENGLISH, CZECH, SLOVAK, GERMAN, SPANISH, FRENCH, ITALIAN, PORTUGUESE,
        POLISH, RUSSIAN, DUTCH, SWEDISH, JAPANESE, CHINESE, KOREAN, HINDI, ARABIC, TURKISH
    }
    public static Lang current = Lang.ENGLISH;

    public static String get(String key) {
        switch (current) {
            case CZECH: return map(key, "Soubor", "Otevřít...", "Uložit jako .txt...", "Ukončit", "Nastavení", "Zobrazení", "Celá obrazovka", "Soustředění", "Motiv", "Písmo", "Velikost", "Okraje", "Zvuk", "Jazyk", "Úder klávesy", "Zvonek vozíku", "Návrat vozíku", "Úzké", "Standardní", "Široké", "Vintage", "Modrotisk", "Tmavý", "Světlý", "Pergamen", "Režim:", "TAB = Změna", "Slov: ", "Hotovo!", "Chyba:", "ZÁHLAVÍ SCÉNY", "AKCE", "POSTAVA", "POZNÁMKA", "DIALOG", "PŘECHOD", "STRANA/PANEL", "TITULEK", "KAPITOLA", "PRÓZA", "Scénář", "Komiksový scénář", "Román", "Zvolte typ dokumentu", "Změnit typ dokumentu...", "Divadelní hra", "Podcast / Audio drama", "DĚJSTVÍ/SCÉNA", "SCÉNICKÁ POZNÁMKA", "ZVUK/HUDBA", "MODERÁTOR/HOST", "Dracula", "Západ slunce", "Oceán", "Tabule");
            case SLOVAK: return map(key, "Súbor", "Otvoriť...", "Uložiť (.txt)...", "Ukončiť", "Nastavenia", "Zobrazenie", "Celá obrazovka", "Sústredenie", "Motív", "Písmo", "Veľkosť", "Okraje", "Zvuk", "Jazyk", "Úder klávesy", "Zvonček", "Návrat vozíka", "Úzke", "Štandardné", "Široké", "Vintage", "Modrotlač", "Tmavý", "Svetlý", "Pergamen", "Režim:", "TAB = Zmena", "Slov: ", "Hotovo!", "Chyba:", "OBRAZ", "AKCIA", "POSTAVA", "POZNÁMKA", "DIALÓG", "PRECHOD", "STRANA/PANEL", "TITULOK", "KAPITOLA", "PRÓZA", "Scenár", "Komiksový scenár", "Román", "Zvoľte typ dokumentu", "Zmeniť typ dokumentu...", "Divadelná hra", "Podcast", "DEJSTVO/SCÉNA", "SCÉNICKÁ POZNÁMKA", "ZVUK/HUDBA", "MODERÁTOR/HOSŤ", "Dracula", "Západ slnka", "Oceán", "Tabuľa");
            case GERMAN: return map(key, "Datei", "Öffnen...", "Speichern...", "Beenden", "Einstellungen", "Ansicht", "Vollbild", "Fokus", "Design", "Schriftart", "Größe", "Ränder", "Audio", "Sprache", "Tasten-Klick", "Glocke", "Wagenrücklauf", "Schmal", "Standard", "Breit", "Vintage", "Blaupause", "Dunkel", "Hell", "Pergament", "Modus:", "TAB = Wechseln", "Wörter: ", "Erfolgreich!", "Fehler:", "SZENE", "AKTION", "CHARAKTER", "KLAMMER", "DIALOG", "ÜBERGANG", "SEITE/PANEL", "TITEL", "KAPITEL", "PROSA", "Drehbuch", "Comic-Skript", "Roman", "Dokumenttyp wählen", "Typ ändern...", "Theaterstück", "Podcast / Hörspiel", "AKT/SZENE", "REGIEANWEISUNG", "SFX/MUSIK", "GASTGEBER/GAST", "Dracula", "Sonnenuntergang", "Ozean", "Tafel");
            case FRENCH: return map(key, "Fichier", "Ouvrir...", "Enregistrer...", "Quitter", "Paramètres", "Affichage", "Plein écran", "Focus", "Thème", "Police", "Taille", "Marges", "Audio", "Langue", "Clic", "Cloche", "Retour chariot", "Étroit", "Standard", "Large", "Vintage", "Plan", "Sombre", "Clair", "Parchemin", "Mode:", "TAB = Changer", "Mots: ", "Succès!", "Erreur:", "SCÈNE", "ACTION", "PERSONNAGE", "DIDASCALIE", "DIALOGUE", "TRANSITION", "PAGE/CASE", "LÉGENDE", "CHAPITRE", "PROSE", "Scénario", "Script de Comic", "Roman", "Choisir le type", "Changer de type...", "Pièce de théâtre", "Podcast", "ACTE/SCÈNE", "DIDASCALIE", "SFX/MUSIQUE", "HÔTE/INVITÉ", "Dracula", "Coucher de soleil", "Océan", "Tableau");
            case SPANISH: return map(key, "Archivo", "Abrir...", "Guardar...", "Salir", "Ajustes", "Ver", "Pantalla Completa", "Enfoque", "Tema", "Fuente", "Tamaño", "Márgenes", "Audio", "Idioma", "Clic", "Campana", "Retorno", "Estrecho", "Estándar", "Ancho", "Vintage", "Plano", "Oscuro", "Claro", "Pergamino", "Modo:", "TAB = Cambiar", "Palabras: ", "¡Éxito!", "Error:", "ESCENA", "ACCIÓN", "PERSONAJE", "ACOTACIÓN", "DIÁLOGO", "TRANSICIÓN", "PÁGINA/VIÑETA", "LEYENDA", "CAPÍTULO", "PROSA", "Guion", "Guion de Cómic", "Novela", "Seleccionar tipo", "Cambiar tipo...", "Obra de Teatro", "Podcast", "ACTO/ESCENA", "DIRECCIÓN DE ESCENA", "SFX/MÚSICA", "ANFITRIÓN/INVITADO", "Dracula", "Atardecer", "Océano", "Pizarra");
        }

        // ENGLISH FALLBACK
        return map(key, "File", "Open...", "Save to .txt...", "Exit", "Settings", "View", "Fullscreen", "Focus Mode", "Theme", "Font", "Font Size", "Margins", "Audio", "Language", "Keystroke Clack", "Margin Bell", "Carriage Return", "Narrow", "Standard", "Wide", "Vintage", "Blueprint", "Dark", "Light", "Parchment", "Mode:", "--> Press TAB to switch elements", "Words: ", "Success!", "Error:", "SCENE HEADING", "ACTION", "CHARACTER", "PARENTHETICAL", "DIALOGUE", "TRANSITION", "PAGE/PANEL", "CAPTION", "CHAPTER", "PROSE", "Screenplay", "Comic Book Script", "Novel", "Select Document Type", "Change Document Type...", "Stage Play", "Podcast / Audio Drama", "ACT/SCENE", "STAGE DIRECTION", "SFX / MUSIC", "HOST / GUEST", "Dracula", "Sunset", "Ocean", "Chalkboard");
    }

    private static String map(String key, String... v) {
        switch (key) {
            case "File": return v[0]; case "Open": return v[1]; case "Save": return v[2]; case "Exit": return v[3];
            case "Settings": return v[4]; case "View": return v[5]; case "Fullscreen": return v[6]; case "Focus": return v[7];
            case "Theme": return v[8]; case "Font": return v[9]; case "FontSize": return v[10]; case "Margins": return v[11];
            case "Audio": return v[12]; case "Language": return v[13]; case "SoundClack": return v[14]; case "SoundBell": return v[15];
            case "SoundReturn": return v[16]; case "Narrow": return v[17]; case "Standard": return v[18]; case "Wide": return v[19];
            case "Vintage": return v[20]; case "Blueprint": return v[21]; case "Dark": return v[22]; case "Light": return v[23];
            case "Parchment": return v[24]; case "Mode": return v[25]; case "TabHint": return v[26]; case "Words": return v[27];
            case "Success": return v[28]; case "Error": return v[29]; case "SCENE": return v[30]; case "ACTION": return v[31];
            case "CHARACTER": return v[32]; case "PARENTHETICAL": return v[33]; case "DIALOGUE": return v[34]; case "TRANSITION": return v[35];
            case "PAGE_PANEL": return v[36]; case "CAPTION": return v[37]; case "CHAPTER": return v[38]; case "PROSE": return v[39];
            case "Screenplay": return v[40]; case "Comic Book Script": return v[41]; case "Novel": return v[42]; case "HubTitle": return v[43];
            case "ChangeType": return v[44]; case "Stage Play": return v[45]; case "Podcast / Audio Drama": return v[46];
            case "ACT_SCENE": return v[47]; case "STAGE_DIRECTION": return v[48]; case "SFX_MUSIC": return v[49]; case "HOST_GUEST": return v[50];
            case "Dracula": return v[51]; case "Sunset": return v[52]; case "Ocean": return v[53]; case "Chalkboard": return v[54];
        } return key;
    }
}