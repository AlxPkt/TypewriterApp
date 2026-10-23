public class Localization {
    public enum Lang { ENGLISH, CZECH, GERMAN, SPANISH, FRENCH }
    public static Lang current = Lang.ENGLISH;

    public static String get(String key) {
        if (current == Lang.CZECH) {
            switch (key) {
                case "File": return "Soubor"; case "New": return "Nový dokument..."; case "Open": return "Otevřít dokument..."; case "Save": return "Uložit jako .txt..."; case "Print": return "Vytisknout..."; case "Exit": return "Ukončit program";
                case "View": return "Zobrazení"; case "Fullscreen": return "Celá obrazovka"; case "Focus": return "Režim soustředění";
                case "Alignment": return "Zarovnání textu"; case "AlignLeft": return "Zarovnat doleva"; case "AlignCenter": return "Zarovnat na střed"; case "AlignRight": return "Zarovnat doprava"; case "FixedCarriage": return "Pevný válec (Středování stránky)";
                case "Theme": return "Motiv"; case "Font": return "Písmo"; case "FontSize": return "Velikost písma"; case "Language": return "Jazyk rozhraní";
                case "Audio": return "Zvuky stroje"; case "SoundClack": return "Klapání kláves"; case "SoundBell": return "Cinknutí na konci řádku"; case "SoundReturn": return "Odřádkování (Posun válce)"; case "SoundScroll": return "Otáčení válce (Skrollování)";
                case "Mode": return "Režim:"; case "TabHint": return "--> Přepnout pomocí TAB"; case "Words": return "Počet slov: ";
                case "Success": return "Úspěšně uloženo!"; case "Error": return "Chyba:";
                case "HubTitle": return "Vyberte formát dokumentu"; case "ChangeType": return "Změnit formát dokumentu...";
                case "Warning": return "Neuložené změny"; case "Unsaved": return "Máte neuložené změny. Chcete je před odchodem uložit?";
                case "Screenplay": return "Filmový scénář"; case "Comic Book Script": return "Komiksový scénář"; case "Novel": return "Kniha / Román"; case "Stage Play": return "Divadelní hra"; case "Podcast / Audio Drama": return "Podcast / Audio hra";
                case "Poetry": return "Poezie"; case "Article / Blog": return "Článek / Blog"; case "Short Story": return "Povídka"; case "Journal / Diary": return "Deník";
                case "SCENE": return "OBRAZ (MÍSTO DĚJE)"; case "ACTION": return "POPIS DĚJE"; case "CHARACTER": return "POSTAVA"; case "PARENTHETICAL": return "ZÁVORKA (POZNÁMKA)"; case "DIALOGUE": return "PROMLUVA (DIALOG)"; case "TRANSITION": return "STŘIH / PŘECHOD"; case "PAGE_PANEL": return "STRANA / PANEL"; case "CAPTION": return "POPISEK"; case "CHAPTER": return "KAPITOLA"; case "PROSE": return "TEXT"; case "ACT_SCENE": return "DĚJSTVÍ / VÝSTUP"; case "STAGE_DIRECTION": return "SCÉNICKÁ POZNÁMKA"; case "SFX_MUSIC": return "ZVUKOVÝ EFEKT / HUDBA"; case "HOST_GUEST": return "MODERÁTOR / HOST";
                case "Dark": return "Tmavý"; case "Light": return "Světlý"; case "Parchment": return "Pergamen"; case "Vintage": return "Retro"; case "Blueprint": return "Technický výkres"; case "Dracula": return "Dracula"; case "Sunset": return "Západ slunce"; case "Ocean": return "Oceán"; case "Nord": return "Nord"; case "SolarizedDark": return "Solarized (Tmavý)"; case "SolarizedLight": return "Solarized (Světlý)"; case "Monokai": return "Monokai"; case "RosePine": return "Rosé Pine"; case "Coffee": return "Káva"; case "Sepia": return "Sépie"; case "Forest": return "Les"; case "Amethyst": return "Ametyst"; case "Hacker": return "Hacker";
            }
        } else if (current == Lang.GERMAN) {
            switch (key) {
                case "File": return "Datei"; case "New": return "Neu..."; case "Open": return "Öffnen..."; case "Save": return "Speichern als .txt..."; case "Print": return "Drucken..."; case "Exit": return "Beenden";
                case "View": return "Ansicht"; case "Fullscreen": return "Vollbild"; case "Focus": return "Fokus-Modus";
                case "Alignment": return "Textausrichtung"; case "AlignLeft": return "Links"; case "AlignCenter": return "Zentriert"; case "AlignRight": return "Rechts"; case "FixedCarriage": return "Fester Wagen (Zentrieren)";
                case "Theme": return "Design"; case "Font": return "Schriftart"; case "FontSize": return "Schriftgröße"; case "Language": return "Sprache";
                case "Audio": return "Audio"; case "SoundClack": return "Tastenklick"; case "SoundBell": return "Zeilenglocke"; case "SoundReturn": return "Wagenrücklauf"; case "SoundScroll": return "Walzendrehung (Mausrad)";
                case "Mode": return "Modus:"; case "TabHint": return "--> TAB zum Wechseln"; case "Words": return "Wörter: ";
                case "Success": return "Erfolgreich!"; case "Error": return "Fehler:";
                case "HubTitle": return "Dokumentformat wählen"; case "ChangeType": return "Format ändern...";
                case "Warning": return "Ungespeicherte Änderungen"; case "Unsaved": return "Möchten Sie Ihre Änderungen vor dem Fortfahren speichern?";
                case "Screenplay": return "Drehbuch"; case "Comic Book Script": return "Comic-Skript"; case "Novel": return "Roman"; case "Stage Play": return "Theaterstück"; case "Podcast / Audio Drama": return "Podcast / Hörspiel";
                case "Poetry": return "Gedicht"; case "Article / Blog": return "Artikel / Blog"; case "Short Story": return "Kurzgeschichte"; case "Journal / Diary": return "Tagebuch";
                case "SCENE": return "SZENE"; case "ACTION": return "HANDLUNG"; case "CHARACTER": return "CHARAKTER"; case "PARENTHETICAL": return "KLAMMERBEMERKUNG"; case "DIALOGUE": return "DIALOG"; case "TRANSITION": return "ÜBERGANG"; case "PAGE_PANEL": return "SEITE / PANEL"; case "CAPTION": return "BILDUNTERSCHRIFT"; case "CHAPTER": return "KAPITEL"; case "PROSE": return "TEXT"; case "ACT_SCENE": return "AKT / SZENE"; case "STAGE_DIRECTION": return "REGIEANWEISUNG"; case "SFX_MUSIC": return "SFX / MUSIK"; case "HOST_GUEST": return "MODERATOR / GAST";
            }
        } else if (current == Lang.SPANISH) {
            switch (key) {
                case "File": return "Archivo"; case "New": return "Nuevo..."; case "Open": return "Abrir..."; case "Save": return "Guardar como .txt..."; case "Print": return "Imprimir..."; case "Exit": return "Salir";
                case "View": return "Ver"; case "Fullscreen": return "Pantalla completa"; case "Focus": return "Modo enfoque";
                case "Alignment": return "Alineación de texto"; case "AlignLeft": return "Izquierda"; case "AlignCenter": return "Centro"; case "AlignRight": return "Derecha"; case "FixedCarriage": return "Carro Fijo (Centrado)";
                case "Theme": return "Tema"; case "Font": return "Fuente"; case "FontSize": return "Tamaño de fuente"; case "Language": return "Idioma";
                case "Audio": return "Audio"; case "SoundClack": return "Clic de tecla"; case "SoundBell": return "Campana de margen"; case "SoundReturn": return "Retorno de carro"; case "SoundScroll": return "Rodillo (Rueda del ratón)";
                case "Mode": return "Modo:"; case "TabHint": return "--> TAB para cambiar"; case "Words": return "Palabras: ";
                case "Success": return "¡Éxito!"; case "Error": return "Error:";
                case "HubTitle": return "Seleccionar formato"; case "ChangeType": return "Cambiar formato...";
                case "Warning": return "Cambios no guardados"; case "Unsaved": return "¿Desea guardar los cambios antes de continuar?";
                case "Screenplay": return "Guion cinematográfico"; case "Comic Book Script": return "Guion de cómic"; case "Novel": return "Novela"; case "Stage Play": return "Obra de teatro"; case "Podcast / Audio Drama": return "Podcast / Drama en audio";
                case "Poetry": return "Poesía"; case "Article / Blog": return "Artículo / Blog"; case "Short Story": return "Historia corta"; case "Journal / Diary": return "Diario";
                case "SCENE": return "ENCABEZADO DE ESCENA"; case "ACTION": return "ACCIÓN"; case "CHARACTER": return "PERSONAJE"; case "PARENTHETICAL": return "ACOTACIÓN"; case "DIALOGUE": return "DIÁLOGO"; case "TRANSITION": return "TRANSICIÓN"; case "PAGE_PANEL": return "PÁGINA / PANEL"; case "CAPTION": return "LEYENDA"; case "CHAPTER": return "CAPÍTULO"; case "PROSE": return "PROSA"; case "ACT_SCENE": return "ACTO / ESCENA"; case "STAGE_DIRECTION": return "ACOTACIÓN ESCÉNICA"; case "SFX_MUSIC": return "EFECTOS / MÚSICA"; case "HOST_GUEST": return "PRESENTADOR / INVITADO";
            }
        } else if (current == Lang.FRENCH) {
            switch (key) {
                case "File": return "Fichier"; case "New": return "Nouveau..."; case "Open": return "Ouvrir..."; case "Save": return "Enregistrer sous .txt..."; case "Print": return "Imprimer..."; case "Exit": return "Quitter";
                case "View": return "Affichage"; case "Fullscreen": return "Plein écran"; case "Focus": return "Mode concentration";
                case "Alignment": return "Alignement du texte"; case "AlignLeft": return "Gauche"; case "AlignCenter": return "Centre"; case "AlignRight": return "Droite"; case "FixedCarriage": return "Chariot Fixe (Centrage)";
                case "Theme": return "Thème"; case "Font": return "Police"; case "FontSize": return "Taille de police"; case "Language": return "Langue";
                case "Audio": return "Audio"; case "SoundClack": return "Clic de touche"; case "SoundBell": return "Cloche de marge"; case "SoundReturn": return "Retour chariot"; case "SoundScroll": return "Rouleau (Molette de souris)";
                case "Mode": return "Mode:"; case "TabHint": return "--> TAB pour changer"; case "Words": return "Mots: ";
                case "Success": return "Succès !"; case "Error": return "Erreur :";
                case "HubTitle": return "Choisir le format"; case "ChangeType": return "Changer de format...";
                case "Warning": return "Modifications non enregistrées"; case "Unsaved": return "Voulez-vous enregistrer vos modifications avant de continuer ?";
                case "Screenplay": return "Scénario"; case "Comic Book Script": return "Scénario de bande dessinée"; case "Novel": return "Roman"; case "Stage Play": return "Pièce de théâtre"; case "Podcast / Audio Drama": return "Podcast / Fiction audio";
                case "Poetry": return "Poésie"; case "Article / Blog": return "Article / Blog"; case "Short Story": return "Nouvelle"; case "Journal / Diary": return "Journal intime";
                case "SCENE": return "EN-TÊTE DE SCÈNE"; case "ACTION": return "ACTION"; case "CHARACTER": return "PERSONNAGE"; case "PARENTHETICAL": return "DIDASCALIE"; case "DIALOGUE": return "DIALOGUE"; case "TRANSITION": return "TRANSITION"; case "PAGE_PANEL": return "PAGE / CASE"; case "CAPTION": return "LÉGENDE"; case "CHAPTER": return "CHAPITRE"; case "PROSE": return "PROSE"; case "ACT_SCENE": return "ACTE / SCÈNE"; case "STAGE_DIRECTION": return "INDICATION SCÉNIQUE"; case "SFX_MUSIC": return "BRUITAGE / MUSIQUE"; case "HOST_GUEST": return "ANIMATEUR / INVITÉ";
            }
        }

        // DEFAULT ENGLISH
        switch (key) {
            case "File": return "File"; case "New": return "New..."; case "Open": return "Open..."; case "Save": return "Save to .txt..."; case "Print": return "Print..."; case "Exit": return "Exit";
            case "View": return "View"; case "Fullscreen": return "Fullscreen"; case "Focus": return "Focus Mode";
            case "Alignment": return "Text Alignment"; case "AlignLeft": return "Left"; case "AlignCenter": return "Center"; case "AlignRight": return "Right"; case "FixedCarriage": return "Fixed Carriage (Centering)";
            case "Theme": return "Theme"; case "Font": return "Font"; case "FontSize": return "Font Size"; case "Language": return "Language";
            case "Audio": return "Audio"; case "SoundClack": return "Keystroke Clack"; case "SoundBell": return "Margin Bell"; case "SoundReturn": return "Carriage Return"; case "SoundScroll": return "Platen Scroll (Mouse Wheel)";
            case "Mode": return "Mode:"; case "TabHint": return "--> Press TAB to switch"; case "Words": return "Words: ";
            case "Success": return "Success!"; case "Error": return "Error:";
            case "HubTitle": return "Select Document Format"; case "ChangeType": return "Change Format...";
            case "Warning": return "Unsaved Changes"; case "Unsaved": return "Do you want to save your changes before continuing?";
            case "Screenplay": return "Screenplay"; case "Comic Book Script": return "Comic Book Script"; case "Novel": return "Novel"; case "Stage Play": return "Stage Play"; case "Podcast / Audio Drama": return "Podcast";
            case "Poetry": return "Poetry"; case "Article / Blog": return "Article / Blog"; case "Short Story": return "Short Story"; case "Journal / Diary": return "Journal / Diary";
            case "SCENE": return "SCENE HEADING"; case "ACTION": return "ACTION"; case "CHARACTER": return "CHARACTER"; case "PARENTHETICAL": return "PARENTHETICAL"; case "DIALOGUE": return "DIALOGUE"; case "TRANSITION": return "TRANSITION"; case "PAGE_PANEL": return "PAGE/PANEL"; case "CAPTION": return "CAPTION"; case "CHAPTER": return "CHAPTER"; case "PROSE": return "PROSE"; case "ACT_SCENE": return "ACT/SCENE"; case "STAGE_DIRECTION": return "STAGE DIRECTION"; case "SFX_MUSIC": return "SFX / MUSIC"; case "HOST_GUEST": return "HOST / GUEST";
            case "Dark": return "Dark"; case "Light": return "Light"; case "Parchment": return "Parchment"; case "Vintage": return "Vintage"; case "Blueprint": return "Blueprint"; case "Dracula": return "Dracula"; case "Sunset": return "Sunset"; case "Ocean": return "Ocean"; case "Nord": return "Nord"; case "SolarizedDark": return "Solarized Dark"; case "SolarizedLight": return "Solarized Light"; case "Monokai": return "Monokai"; case "RosePine": return "Rosé Pine"; case "Coffee": return "Coffee"; case "Sepia": return "Sepia"; case "Forest": return "Forest"; case "Amethyst": return "Amethyst"; case "Hacker": return "Hacker";
        }
        return key;
    }
}