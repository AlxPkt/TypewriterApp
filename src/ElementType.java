public enum ElementType {
    SCENE_HEADING(0, 0, true, "SCENE"),
    ACTION(0, 0, false, "ACTION"),
    CHARACTER(150, 22, true, "CHARACTER"),
    PARENTHETICAL(100, 16, false, "PARENTHETICAL"),
    DIALOGUE(50, 10, false, "DIALOGUE"),
    TRANSITION(200, 45, true, "TRANSITION");

    private final int swingIndent, txtSpaces;
    private final boolean autoCapitalize;
    private final String langKey;

    ElementType(int swingIndent, int txtSpaces, boolean autoCapitalize, String langKey) {
        this.swingIndent = swingIndent; this.txtSpaces = txtSpaces;
        this.autoCapitalize = autoCapitalize; this.langKey = langKey;
    }

    public int getSwingIndent() { return swingIndent; }
    public int getTxtSpaces() { return txtSpaces; }
    public boolean isAutoCapitalize() { return autoCapitalize; }
    public String getLabel() { return Localization.get(langKey); }

    public ElementType next() {
        ElementType[] vals = values();
        return vals[(this.ordinal() + 1) % vals.length];
    }
    public ElementType getSmartNext() {
        if (this == CHARACTER) return DIALOGUE;
        if (this == SCENE_HEADING) return ACTION;
        return this;
    }
}