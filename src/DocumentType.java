public enum DocumentType {
    SCREENPLAY("Screenplay"),
    COMIC_BOOK("Comic Book Script"),
    NOVEL("Novel"),
    STAGE_PLAY("Stage Play"),
    PODCAST("Podcast / Audio Drama"),
    POETRY("Poetry"),
    ARTICLE("Article / Blog"),
    SHORT_STORY("Short Story"),
    JOURNAL("Journal / Diary");

    public final String langKey;

    DocumentType(String langKey) {
        this.langKey = langKey;
    }
}