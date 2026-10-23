public class Localization {
    public enum Lang {
        ENGLISH, CZECH, SLOVAK, GERMAN, SPANISH, FRENCH, ITALIAN, PORTUGUESE,
        POLISH, RUSSIAN, DUTCH, SWEDISH, JAPANESE, CHINESE, KOREAN, HINDI, ARABIC, TURKISH
    }
    public static Lang current = Lang.ENGLISH;

    public static String get(String key) {
        switch (current) {
            case CZECH: return map(key, "Soubor", "Nový scénář", "Otevřít...", "Uložit jako .txt...", "Export do PDF", "Tisk", "Ukončit", "Nastavení", "Zobrazení", "Celá obrazovka", "Soustředění", "Motiv", "Písmo", "Velikost", "Okraje", "Zvuk", "Jazyk", "Úder klávesy", "Zvonek vozíku", "Návrat vozíku", "Úzké", "Standardní", "Široké", "Vintage", "Modrotisk", "Tabule", "Režim:", "TAB = Změna", "Slov: ", "Hotovo!", "Chyba:");
            case SLOVAK: return map(key, "Súbor", "Nový scenár", "Otvoriť...", "Uložiť (.txt)...", "Export do PDF", "Tlač", "Ukončiť", "Nastavenia", "Zobrazenie", "Celá obrazovka", "Sústredenie", "Motív", "Písmo", "Veľkosť", "Okraje", "Zvuk", "Jazyk", "Úder klávesy", "Zvonček", "Návrat vozíka", "Úzke", "Štandardné", "Široké", "Vintage", "Modrotlač", "Tabuľa", "Režim:", "TAB = Zmena", "Slov: ", "Hotovo!", "Chyba:");
            case GERMAN: return map(key, "Datei", "Neu", "Öffnen...", "Speichern...", "Exportieren", "Drucken", "Beenden", "Einstellungen", "Ansicht", "Vollbild", "Fokus", "Design", "Schriftart", "Größe", "Ränder", "Audio", "Sprache", "Tasten-Klick", "Glocke", "Wagenrücklauf", "Schmal", "Standard", "Breit", "Vintage", "Blaupause", "Tafel", "Modus:", "TAB = Wechseln", "Wörter: ", "Erfolgreich!", "Fehler:");
            case FRENCH: return map(key, "Fichier", "Nouveau", "Ouvrir...", "Enregistrer...", "Exporter", "Imprimer", "Quitter", "Paramètres", "Affichage", "Plein écran", "Focus", "Thème", "Police", "Taille", "Marges", "Audio", "Langue", "Clic", "Cloche", "Retour chariot", "Étroit", "Standard", "Large", "Vintage", "Plan", "Tableau", "Mode:", "TAB = Changer", "Mots: ", "Succès!", "Erreur:");
            case SPANISH: return map(key, "Archivo", "Nuevo", "Abrir...", "Guardar...", "Exportar", "Imprimir", "Salir", "Ajustes", "Ver", "Pantalla Completa", "Enfoque", "Tema", "Fuente", "Tamaño", "Márgenes", "Audio", "Idioma", "Clic", "Campana", "Retorno de carro", "Estrecho", "Estándar", "Ancho", "Vintage", "Plano", "Pizarra", "Modo:", "TAB = Cambiar", "Palabras: ", "¡Éxito!", "Error:");
            case ITALIAN: return map(key, "File", "Nuovo", "Apri...", "Salva...", "Esporta", "Stampa", "Esci", "Impostazioni", "Visualizza", "Schermo Intero", "Focus", "Tema", "Carattere", "Dimensione", "Margini", "Audio", "Lingua", "Clic", "Campanello", "A capo", "Stretto", "Standard", "Largo", "Vintage", "Progetto", "Lavagna", "Modo:", "TAB = Cambia", "Parole: ", "Fatto!", "Errore:");
            case PORTUGUESE: return map(key, "Arquivo", "Novo", "Abrir...", "Salvar...", "Exportar", "Imprimir", "Sair", "Configurações", "Visualizar", "Tela Cheia", "Foco", "Tema", "Fonte", "Tamanho", "Margens", "Áudio", "Idioma", "Clique", "Sino", "Retorno de Carro", "Estreito", "Padrão", "Largo", "Vintage", "Projeto", "Lousa", "Modo:", "TAB = Mudar", "Palavras: ", "Sucesso!", "Erro:");
            case POLISH: return map(key, "Plik", "Nowy", "Otwórz...", "Zapisz...", "Eksportuj", "Drukuj", "Zakończ", "Ustawienia", "Widok", "Pełny ekran", "Skupienie", "Motyw", "Czcionka", "Rozmiar", "Marginesy", "Dźwięk", "Język", "Klik", "Dzwonek", "Powrót Karetki", "Wąskie", "Standard", "Szerokie", "Vintage", "Projekt", "Tablica", "Tryb:", "TAB = Zmiana", "Słów: ", "Sukces!", "Błąd:");
            case RUSSIAN: return map(key, "Файл", "Новый", "Открыть...", "Сохранить...", "Экспорт", "Печать", "Выход", "Настройки", "Вид", "Во весь экран", "Фокус", "Тема", "Шрифт", "Размер", "Поля", "Звук", "Язык", "Клик", "Звонок", "Возврат каретки", "Узко", "Стандарт", "Широко", "Винтаж", "Чертеж", "Доска", "Режим:", "TAB = Сменить", "Слова: ", "Успех!", "Ошибка:");
            case DUTCH: return map(key, "Bestand", "Nieuw", "Openen...", "Opslaan...", "Exporteren", "Afdrukken", "Afsluiten", "Instellingen", "Beeld", "Volledig Scherm", "Focus", "Thema", "Lettertype", "Grootte", "Marges", "Audio", "Taal", "Klik", "Bel", "Wagenretour", "Smal", "Standaard", "Breed", "Vintage", "Blauwdruk", "Krijtbord", "Modus:", "TAB = Wisselen", "Woorden: ", "Gelukt!", "Fout:");
            case SWEDISH: return map(key, "Arkiv", "Nytt", "Öppna...", "Spara...", "Exportera", "Skriv ut", "Avsluta", "Inställningar", "Visa", "Helskärm", "Fokus", "Tema", "Typsnitt", "Storlek", "Marginaler", "Ljud", "Språk", "Klick", "Klocka", "Vagnretur", "Smal", "Standard", "Bred", "Vintage", "Ritning", "Svarta tavlan", "Läge:", "TAB = Byt", "Ord: ", "Klart!", "Fel:");
            case JAPANESE: return map(key, "ファイル", "新規", "開く...", "保存...", "エクスポート", "印刷", "終了", "設定", "表示", "全画面表示", "集中モード", "テーマ", "フォント", "サイズ", "余白", "音声", "言語", "クリック", "ベル", "キャリッジリターン", "狭い", "標準", "広い", "ビンテージ", "青写真", "黒板", "モード:", "TAB = 変更", "文字数: ", "成功!", "エラー:");
            case CHINESE: return map(key, "文件", "新建", "打开...", "保存...", "导出", "打印", "退出", "设置", "视图", "全屏", "专注模式", "主题", "字体", "大小", "边距", "音频", "语言", "按键声", "铃声", "回车", "窄", "标准", "宽", "复古", "蓝图", "黑板", "模式:", "TAB = 切换", "字数: ", "成功!", "错误:");
            case KOREAN: return map(key, "파일", "새로 만들기", "열기...", "저장...", "내보내기", "인쇄", "종료", "설정", "보기", "전체 화면", "집중 모드", "테마", "글꼴", "크기", "여백", "오디오", "언어", "키보드 소리", "종소리", "캐리지 리턴", "좁게", "표준", "넓게", "빈티지", "청사진", "칠판", "모드:", "TAB = 변경", "단어: ", "성공!", "오류:");
            case HINDI: return map(key, "फ़ाइल", "नया", "खोलें...", "सहेजें...", "निर्यात", "प्रिंट", "बाहर", "सेटिंग्स", "देखें", "पूर्ण स्क्रीन", "फोकस", "थीम", "फ़ॉन्ट", "आकार", "हाशिया", "ऑडियो", "भाषा", "क्लिक", "घंटी", "कैरिज रिटर्न", "संकीर्ण", "मानक", "चौड़ा", "विंटेज", "ब्लूप्रिंट", "चॉकबोर्ड", "मो드:", "TAB = बदलें", "शब्द: ", "सफल!", "त्रुटि:");
            case ARABIC: return map(key, "ملف", "جديد", "فتح...", "حفظ...", "تصدير", "طباعة", "خروج", "إعدادات", "عرض", "ملء الشاشة", "تركيز", "سمة", "خط", "حجم", "هوامش", "صوت", "لغة", "نقر", "جرس", "إرجاع", "ضيق", "قياسي", "عريض", "كلاسيكي", "مخطط", "سبورة", "الوضع:", "TAB = تغيير", "الكلمات: ", "نجاح!", "خطأ:");
            case TURKISH: return map(key, "Dosya", "Yeni", "Aç...", "Kaydet...", "Dışa Aktar", "Yazdır", "Çıkış", "Ayarlar", "Görünüm", "Tam Ekran", "Odak", "Tema", "Yazı Tipi", "Boyut", "Kenar Boşluğu", "Ses", "Dil", "Tıklama", "Zil", "Satır Başı", "Dar", "Standart", "Geniş", "Vintage", "Taslak", "Kara Tahta", "Mod:", "TAB = Değiştir", "Kelime: ", "Başarılı!", "Hata:");
        }

        // ENGLISH FALLBACK
        return map(key, "File", "New Script", "Open...", "Save to .txt...", "Export...", "Print...", "Exit Application", "Settings", "View Options", "Toggle Fullscreen", "Focus Mode", "Theme", "Font", "Font Size", "Margins", "Audio", "Language", "Keystroke Clack", "End-of-line Bell", "Carriage Return Slide", "Narrow", "Standard", "Wide", "Vintage", "Blueprint", "Chalkboard", "Mode:", "--> Press TAB to switch elements", "Words: ", "Success!", "Error:");
    }

    private static String map(String key, String... vals) {
        switch (key) {
            case "File": return vals[0]; case "New": return vals[1]; case "Open": return vals[2];
            case "Save": return vals[3]; case "Export": return vals[4]; case "Print": return vals[5];
            case "Exit": return vals[6]; case "Settings": return vals[7]; case "View": return vals[8];
            case "Fullscreen": return vals[9]; case "Focus": return vals[10]; case "Theme": return vals[11];
            case "Font": return vals[12]; case "FontSize": return vals[13]; case "Margins": return vals[14];
            case "Audio": return vals[15]; case "Language": return vals[16]; case "SoundClack": return vals[17];
            case "SoundBell": return vals[18]; case "SoundReturn": return vals[19]; case "Narrow": return vals[20];
            case "Standard": return vals[21]; case "Wide": return vals[22]; case "Vintage": return vals[23];
            case "Blueprint": return vals[24]; case "Chalkboard": return vals[25]; case "Mode": return vals[26];
            case "TabHint": return vals[27]; case "Words": return vals[28]; case "Success": return vals[29];
            case "Error": return vals[30];
            case "SCENE": return "SCENE HEADING"; case "ACTION": return "ACTION"; case "CHARACTER": return "CHARACTER";
            case "PARENTHETICAL": return "PARENTHETICAL"; case "DIALOGUE": return "DIALOGUE"; case "TRANSITION": return "TRANSITION";
        } return key;
    }
}