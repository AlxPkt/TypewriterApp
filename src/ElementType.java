import java.util.ArrayList;
import java.util.List;

public enum ElementType {
    // SCREENPLAY FORMAT
    SCENE_HEADING(DocumentType.SCREENPLAY, 0, 0, 0, true, "SCENE", 60),
    ACTION(DocumentType.SCREENPLAY, 0, 0, 0, false, "ACTION", 60),
    CHARACTER(DocumentType.SCREENPLAY, 150, 0, 22, true, "CHARACTER", 35),
    PARENTHETICAL(DocumentType.SCREENPLAY, 100, 0, 16, false, "PARENTHETICAL", 20),
    DIALOGUE(DocumentType.SCREENPLAY, 50, 0, 10, false, "DIALOGUE", 35),
    TRANSITION(DocumentType.SCREENPLAY, 200, 0, 45, true, "TRANSITION", 20),

    // COMIC BOOK FORMAT
    PAGE_PANEL(DocumentType.COMIC_BOOK, 0, 0, 0, true, "PAGE_PANEL", 60),
    COMIC_ACTION(DocumentType.COMIC_BOOK, 0, 0, 0, false, "ACTION", 60),
    COMIC_CHARACTER(DocumentType.COMIC_BOOK, 100, 0, 15, true, "CHARACTER", 35),
    COMIC_DIALOGUE(DocumentType.COMIC_BOOK, 50, 0, 8, false, "DIALOGUE", 40),
    CAPTION(DocumentType.COMIC_BOOK, 150, 0, 22, false, "CAPTION", 35),

    // NOVEL FORMAT
    CHAPTER_HEADING(DocumentType.NOVEL, 0, 0, 0, false, "CHAPTER", 50),
    PROSE(DocumentType.NOVEL, 0, 30, 0, false, "PROSE", 85),

    // STAGE PLAY FORMAT
    ACT_SCENE(DocumentType.STAGE_PLAY, 0, 0, 0, true, "ACT_SCENE", 60),
    STAGE_ACTION(DocumentType.STAGE_PLAY, 0, 0, 0, false, "ACTION", 60),
    STAGE_CHARACTER(DocumentType.STAGE_PLAY, 150, 0, 22, true, "CHARACTER", 35),
    STAGE_DIALOGUE(DocumentType.STAGE_PLAY, 50, 0, 10, false, "DIALOGUE", 45),
    STAGE_DIRECTION(DocumentType.STAGE_PLAY, 100, 0, 16, false, "STAGE_DIRECTION", 30),

    // PODCAST / AUDIO DRAMA FORMAT
    AUDIO_SCENE(DocumentType.PODCAST, 0, 0, 0, true, "SCENE", 60),
    SFX_MUSIC(DocumentType.PODCAST, 0, 0, 0, true, "SFX_MUSIC", 60),
    HOST_GUEST(DocumentType.PODCAST, 150, 0, 22, true, "HOST_GUEST", 35),
    POD_DIALOGUE(DocumentType.PODCAST, 50, 0, 10, false, "DIALOGUE", 45);

    private final DocumentType docType;
    private final int swingIndent, firstLineIndent, txtSpaces, maxChars;
    private final boolean autoCapitalize;
    private final String langKey;

    ElementType(DocumentType docType, int swingIndent, int firstLineIndent, int txtSpaces, boolean autoCapitalize, String langKey, int maxChars) {
        this.docType = docType; this.swingIndent = swingIndent; this.firstLineIndent = firstLineIndent;
        this.txtSpaces = txtSpaces; this.autoCapitalize = autoCapitalize; this.langKey = langKey; this.maxChars = maxChars;
    }

    public DocumentType getDocType() { return docType; }
    public int getSwingIndent() { return swingIndent; }
    public int getFirstLineIndent() { return firstLineIndent; }
    public int getTxtSpaces() { return txtSpaces; }
    public boolean isAutoCapitalize() { return autoCapitalize; }
    public int getMaxChars() { return maxChars; }
    public String getLabel() { return Localization.get(langKey); }

    public static ElementType[] getValuesFor(DocumentType type) {
        List<ElementType> list = new ArrayList<>();
        for (ElementType e : values()) { if (e.docType == type) list.add(e); }
        return list.toArray(new ElementType[0]);
    }

    public ElementType next() {
        ElementType[] valid = getValuesFor(this.docType);
        for (int i = 0; i < valid.length; i++) {
            if (valid[i] == this) return valid[(i + 1) % valid.length];
        }
        return valid[0];
    }

    public ElementType getSmartNext() {
        if (this == CHARACTER) return DIALOGUE;
        if (this == SCENE_HEADING) return ACTION;
        if (this == COMIC_CHARACTER) return COMIC_DIALOGUE;
        if (this == PAGE_PANEL) return COMIC_ACTION;
        if (this == CHAPTER_HEADING) return PROSE;
        if (this == STAGE_CHARACTER) return STAGE_DIALOGUE;
        if (this == HOST_GUEST) return POD_DIALOGUE;
        return this;
    }
}