public enum DocumentType {
    SCREENPLAY("Screenplay"),
    COMIC_BOOK("Comic Book Script"),
    NOVEL("Novel"),
    STAGE_PLAY("Stage Play"),
    PODCAST("Podcast / Audio Drama");

    public final String langKey;
    DocumentType(String langKey) { this.langKey = langKey; }
}