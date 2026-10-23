public enum ElementType {
    // swingIndent, txtSpaces, autoCapitalize, langKey, maxLineChars
    SCENE_HEADING(0, 0, true, "SCENE", 65),
    ACTION(0, 0, false, "ACTION", 65),
    CHARACTER(150, 22, true, "CHARACTER", 35),
    PARENTHETICAL(100, 16, false, "PARENTHETICAL", 25),
    DIALOGUE(50, 10, false, "DIALOGUE", 40),
    TRANSITION(200, 45, true, "TRANSITION", 25);

    private final int swingIndent, txtSpaces, maxChars;
    private final boolean autoCapitalize;
    private final String langKey;

    ElementType(int swingIndent, int txtSpaces, boolean autoCapitalize, String langKey, int maxChars) {
        this.swingIndent = swingIndent; this.txtSpaces = txtSpaces;
        this.autoCapitalize = autoCapitalize; this.langKey = langKey;
        this.maxChars = maxChars;
    }

    public int getSwingIndent() { return swingIndent; }
    public int getTxtSpaces() { return txtSpaces; }
    public boolean isAutoCapitalize() { return autoCapitalize; }
    public int getMaxChars() { return maxChars; }
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