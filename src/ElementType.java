public enum ElementType {
    // Format: langKey, maxChars, firstLineIndent, swingIndent, txtSpaces, autoCapitalize
    SCENE_HEADING("SCENE", 65, 0, 0, 0, true),
    ACTION("ACTION", 65, 0, 0, 0, false),
    CHARACTER("CHARACTER", 35, 150, 150, 22, true),
    PARENTHETICAL("PARENTHETICAL", 25, 110, 110, 16, false),
    DIALOGUE("DIALOGUE", 35, 80, 80, 10, false),
    TRANSITION("TRANSITION", 20, 400, 400, 45, true),

    PAGE_PANEL("PAGE_PANEL", 65, 0, 0, 0, true),
    CAPTION("CAPTION", 35, 80, 80, 10, false),

    CHAPTER_HEADING("CHAPTER", 65, 0, 0, 0, true),
    PROSE("PROSE", 65, 40, 0, 0, false),

    ACT_SCENE("ACT_SCENE", 65, 200, 200, 25, true),
    STAGE_DIRECTION("STAGE_DIRECTION", 45, 100, 100, 15, false),

    AUDIO_SCENE("SFX_MUSIC", 65, 0, 0, 0, true),
    HOST_GUEST("HOST_GUEST", 35, 150, 150, 22, true);

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

    // Maps the correct formatting elements to the Document Type you chose in the Hub
    public static ElementType[] getValuesFor(DocumentType type) {
        switch (type) {
            case SCREENPLAY: return new ElementType[]{SCENE_HEADING, ACTION, CHARACTER, PARENTHETICAL, DIALOGUE, TRANSITION};
            case COMIC_BOOK: return new ElementType[]{PAGE_PANEL, SCENE_HEADING, ACTION, CHARACTER, PARENTHETICAL, DIALOGUE, CAPTION};
            case STAGE_PLAY: return new ElementType[]{ACT_SCENE, STAGE_DIRECTION, CHARACTER, DIALOGUE};
            case PODCAST: return new ElementType[]{AUDIO_SCENE, ACTION, HOST_GUEST, DIALOGUE};
            case NOVEL:
            case POETRY:
            case ARTICLE:
            case SHORT_STORY:
            case JOURNAL:
            default:
                return new ElementType[]{CHAPTER_HEADING, PROSE};
        }
    }

    // Handles the cycle when you press TAB
    public ElementType next() {
        switch(this) {
            case SCENE_HEADING: return ACTION;
            case ACTION: return CHARACTER;
            case CHARACTER: return PARENTHETICAL;
            case PARENTHETICAL: return DIALOGUE;
            case DIALOGUE: return TRANSITION;
            case TRANSITION: return SCENE_HEADING;

            case PAGE_PANEL: return SCENE_HEADING;
            case CAPTION: return ACTION;

            case CHAPTER_HEADING: return PROSE;
            case PROSE: return CHAPTER_HEADING;

            case ACT_SCENE: return STAGE_DIRECTION;
            case STAGE_DIRECTION: return CHARACTER;

            case AUDIO_SCENE: return ACTION;
            case HOST_GUEST: return DIALOGUE;

            default: return PROSE;
        }
    }

    // Handles the predictive logic when you press ENTER
    public ElementType getSmartNext() {
        switch(this) {
            case SCENE_HEADING: return ACTION;
            case CHARACTER: return DIALOGUE;
            case PARENTHETICAL: return DIALOGUE;
            case DIALOGUE: return ACTION;
            case TRANSITION: return SCENE_HEADING;
            case CHAPTER_HEADING: return PROSE;
            case PROSE: return PROSE;
            case PAGE_PANEL: return ACTION;
            case CAPTION: return ACTION;
            case ACT_SCENE: return STAGE_DIRECTION;
            case STAGE_DIRECTION: return DIALOGUE;
            case AUDIO_SCENE: return ACTION;
            case HOST_GUEST: return DIALOGUE;
            default: return ACTION;
        }
    }
}