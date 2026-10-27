public enum ElementType {
    // Format: langKey, maxChars, firstLineIndent, swingIndent, txtSpaces, autoCapitalize
    SCENE_HEADING("SCENE", 65, 0, 0, 0, true),
    ACTION("ACTION", 65, 0, 0, 0, false),
    CHARACTER("CHARACTER", 35, 150, 150, 22, true),
    PARENTHETICAL("PARENTHETICAL", 25, 110, 110, 16, false),
    DIALOGUE("DIALOGUE", 35, 80, 80, 10, false),
    TRANSITION("TRANSITION", 20, 400, 400, 45, true),

    // COMIC BOOK ELEMENTS
    COMIC_PAGE("PAGE", 65, 0, 0, 0, true),
    COMIC_PANEL("PANEL", 65, 0, 0, 0, true),
    CAPTION("CAPTION", 35, 80, 80, 10, false),

    // NOVEL & GENERAL PROSE
    CHAPTER_HEADING("CHAPTER", 65, 0, 0, 0, true),
    PROSE("PROSE", 65, 40, 0, 0, false), // Has a 40px first-line indent for books

    // STAGE PLAY
    ACT_SCENE("ACT_SCENE", 65, 200, 200, 25, true),
    STAGE_DIRECTION("STAGE_DIRECTION", 45, 100, 100, 15, false),

    // PODCAST
    AUDIO_SCENE("SFX_MUSIC", 65, 0, 0, 0, true),
    HOST_GUEST("HOST_GUEST", 35, 150, 150, 22, true),

    // NEW FORMAT-SPECIFIC ELEMENTS
    HEADING("TITLE", 65, 0, 0, 0, true),
    SUBHEADING("SUBHEADING", 65, 0, 0, 0, true),
    BODY_TEXT("BODY", 65, 0, 0, 0, false), // No first-line indent (for articles)
    VERSE("VERSE", 65, 0, 0, 0, false), // Left aligned, no caps (for poetry)
    DATE_ENTRY("DATE", 65, 0, 0, 0, true); // For journals

    private final String langKey;
    private final int maxChars, firstLineIndent, swingIndent, txtSpaces;
    private final boolean autoCapitalize;

    ElementType(String langKey, int maxChars, int firstLineIndent, int swingIndent, int txtSpaces, boolean autoCapitalize) {
        this.langKey = langKey;
        this.maxChars = maxChars;
        this.firstLineIndent = firstLineIndent;
        this.swingIndent = swingIndent;
        this.txtSpaces = txtSpaces;
        this.autoCapitalize = autoCapitalize;
    }

    public String getLabel() { return Localization.get(langKey); }
    public int getMaxChars() { return maxChars; }
    public int getFirstLineIndent() { return firstLineIndent; }
    public int getSwingIndent() { return swingIndent; }
    public int getTxtSpaces() { return txtSpaces; }
    public boolean isAutoCapitalize() { return autoCapitalize; }

    public static ElementType[] getValuesFor(DocumentType type) {
        switch (type) {
            case SCREENPLAY: return new ElementType[]{SCENE_HEADING, ACTION, CHARACTER, PARENTHETICAL, DIALOGUE, TRANSITION};
            case COMIC_BOOK: return new ElementType[]{COMIC_PAGE, COMIC_PANEL, ACTION, CHARACTER, PARENTHETICAL, DIALOGUE, CAPTION};
            case STAGE_PLAY: return new ElementType[]{ACT_SCENE, STAGE_DIRECTION, CHARACTER, DIALOGUE};
            case PODCAST: return new ElementType[]{AUDIO_SCENE, ACTION, HOST_GUEST, DIALOGUE};
            case NOVEL: return new ElementType[]{CHAPTER_HEADING, PROSE};
            case POETRY: return new ElementType[]{HEADING, VERSE};
            case ARTICLE: return new ElementType[]{HEADING, SUBHEADING, BODY_TEXT};
            case SHORT_STORY: return new ElementType[]{HEADING, PROSE};
            case JOURNAL: return new ElementType[]{DATE_ENTRY, PROSE};
            default: return new ElementType[]{CHAPTER_HEADING, PROSE};
        }
    }

    public ElementType next(DocumentType type) {
        ElementType[] allowed = getValuesFor(type);
        for (int i = 0; i < allowed.length; i++) {
            if (allowed[i] == this) return allowed[(i + 1) % allowed.length];
        }
        return allowed[0];
    }

    public ElementType getSmartNext(DocumentType type) {
        switch (type) {
            case STAGE_PLAY:
                switch(this) {
                    case ACT_SCENE: return STAGE_DIRECTION;
                    case STAGE_DIRECTION: return CHARACTER;
                    case CHARACTER: return DIALOGUE;
                    case DIALOGUE: return STAGE_DIRECTION;
                    default: return STAGE_DIRECTION;
                }
            case COMIC_BOOK:
                switch(this) {
                    case COMIC_PAGE: return COMIC_PANEL;
                    case COMIC_PANEL: return ACTION;
                    case CAPTION: return ACTION;
                    case CHARACTER: return DIALOGUE;
                    case PARENTHETICAL: return DIALOGUE;
                    case DIALOGUE: return ACTION;
                    default: return ACTION;
                }
            case PODCAST:
                switch(this) {
                    case AUDIO_SCENE: return ACTION;
                    case HOST_GUEST: return DIALOGUE;
                    case DIALOGUE: return HOST_GUEST;
                    default: return ACTION;
                }
            case POETRY:
                switch(this) {
                    case HEADING: return VERSE;
                    case VERSE: return VERSE;
                    default: return VERSE;
                }
            case ARTICLE:
                switch(this) {
                    case HEADING: return SUBHEADING;
                    case SUBHEADING: return BODY_TEXT;
                    case BODY_TEXT: return BODY_TEXT;
                    default: return BODY_TEXT;
                }
            case SHORT_STORY:
                switch(this) {
                    case HEADING: return PROSE;
                    case PROSE: return PROSE;
                    default: return PROSE;
                }
            case JOURNAL:
                switch(this) {
                    case DATE_ENTRY: return PROSE;
                    case PROSE: return PROSE;
                    default: return PROSE;
                }
            case NOVEL:
                switch(this) {
                    case CHAPTER_HEADING: return PROSE;
                    case PROSE: return PROSE;
                    default: return PROSE;
                }
            case SCREENPLAY:
            default:
                switch(this) {
                    case SCENE_HEADING: return ACTION;
                    case CHARACTER: return DIALOGUE;
                    case PARENTHETICAL: return DIALOGUE;
                    case DIALOGUE: return ACTION;
                    case TRANSITION: return SCENE_HEADING;
                    default: return ACTION;
                }
        }
    }
}