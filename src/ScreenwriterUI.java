import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class ScreenwriterUI extends JFrame {
    private JTextPane textPane;
    private JLabel statusLabel, wordCountLabel;
    private JPanel statusPanel;
    private JMenuBar menuBar;

    private JMenu fileMenu, settingsMenu, viewMenu, themeMenu, fontMenu, sizeMenu, langMenu, audioMenu;
    private JMenuItem newItem, openItem, saveItem, printItem, changeTypeItem, exitMenuItem;
    private JMenuItem fullscreenItem, focusItem;
    private JCheckBoxMenuItem clackToggle, bellToggle, returnToggle;
    private JButton dedicatedExitButton;

    // Autocomplete Components
    private JPopupMenu suggestionMenu;
    private String currentSuggestion = null;

    private final DocumentType documentType;
    private ElementType currentMode;
    private Theme currentTheme = Theme.DARK;
    private TexturePaint paperTexture;

    private String currentFontName = "Courier New";
    private int currentFontSize = 18;
    private int wordCount = 0;

    private boolean isFullscreen = false;
    private boolean isFocusMode = false;

    public ScreenwriterUI(DocumentType docType) {
        this.documentType = docType;
        this.currentMode = ElementType.getValuesFor(docType)[0];

        setTitle("Screenwriter Pro - " + Localization.get(docType.langKey));
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        suggestionMenu = new JPopupMenu();
        suggestionMenu.setFocusable(false); // Prevents popup from stealing typing focus
        suggestionMenu.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));

        textPane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (paperTexture != null) {
                    g2d.setPaint(paperTexture); g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g2d.setColor(getBackground()); g2d.fillRect(0, 0, getWidth(), getHeight());
                }

                g2d.setColor(currentTheme.marginColor);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(100, 0, 100, getHeight());
                g2d.drawLine(getWidth() - 100, 0, getWidth() - 100, getHeight());
                super.paintComponent(g);
            }
        };
        textPane.setOpaque(false);
        textPane.setBorder(BorderFactory.createEmptyBorder(60, 120, 60, 120));

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        wordCountLabel = new JLabel();
        wordCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(wordCountLabel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);

        setupMenus();
        setupKeyBindings();
        setupDocumentFilter();
        setupListeners();

        applyTheme();
        applyFont();
        applyCurrentFormatting();
    }

    private void generateTexture() {
        if (currentTheme == Theme.DARK || currentTheme == Theme.LIGHT || currentTheme == Theme.DRACULA || currentTheme == Theme.OCEAN) {
            paperTexture = null; return;
        }
        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(currentTheme.bg); g.fillRect(0, 0, 128, 128);
        g.setColor(new Color(0, 0, 0, currentTheme.noiseAlpha));
        for (int i = 0; i < 4000; i++) g.fillRect((int)(Math.random() * 128), (int)(Math.random() * 128), 1, 1);
        g.dispose();
        paperTexture = new TexturePaint(img, new Rectangle(0, 0, 128, 128));
    }

    private void setupMenus() {
        menuBar = new JMenuBar(); menuBar.setBorderPainted(false);
        fileMenu = new JMenu(); settingsMenu = new JMenu();

        newItem = new JMenuItem(); newItem.addActionListener(e -> { textPane.setText(""); wordCount = 0; updateStatus(); });
        openItem = new JMenuItem(); openItem.addActionListener(e -> openFile());
        saveItem = new JMenuItem(); saveItem.addActionListener(e -> saveFile());
        printItem = new JMenuItem(); printItem.addActionListener(e -> printFile());

        changeTypeItem = new JMenuItem();
        changeTypeItem.addActionListener(e -> {
            this.dispose();
            Main.showLauncher();
        });

        exitMenuItem = new JMenuItem(); exitMenuItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem); fileMenu.add(openItem); fileMenu.addSeparator();
        fileMenu.add(saveItem); fileMenu.add(printItem); fileMenu.addSeparator();
        fileMenu.add(changeTypeItem); fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        viewMenu = new JMenu();
        fullscreenItem = new JMenuItem(); fullscreenItem.addActionListener(e -> toggleFullscreen());
        focusItem = new JMenuItem(); focusItem.addActionListener(e -> toggleFocusMode());
        viewMenu.add(fullscreenItem); viewMenu.add(focusItem);

        themeMenu = new JMenu();
        for (Theme t : Theme.values()) {
            JMenuItem item = new JMenuItem();
            item.addActionListener(e -> { currentTheme = t; applyTheme(); });
            themeMenu.add(item);
        }

        fontMenu = new JMenu();
        for (String f : new String[]{"Courier New", "Consolas", "Monospaced", "Georgia", "Times New Roman"}) {
            JMenuItem item = new JMenuItem(f);
            item.addActionListener(e -> { currentFontName = f; applyFont(); });
            fontMenu.add(item);
        }

        sizeMenu = new JMenu();
        for (int s : new int[]{14, 16, 18, 22}) {
            JMenuItem item = new JMenuItem(s + " pt");
            item.addActionListener(e -> { currentFontSize = s; applyFont(); });
            sizeMenu.add(item);
        }

        langMenu = new JMenu();
        for (Localization.Lang l : Localization.Lang.values()) {
            JMenuItem item = new JMenuItem(l.name());
            item.addActionListener(e -> { Localization.current = l; updateLanguage(); });
            langMenu.add(item);
        }

        audioMenu = new JMenu();
        clackToggle = new JCheckBoxMenuItem(); clackToggle.setState(TypewriterSound.isClackEnabled);
        clackToggle.addActionListener(e -> TypewriterSound.isClackEnabled = clackToggle.getState());
        bellToggle = new JCheckBoxMenuItem(); bellToggle.setState(TypewriterSound.isBellEnabled);
        bellToggle.addActionListener(e -> TypewriterSound.isBellEnabled = bellToggle.getState());
        returnToggle = new JCheckBoxMenuItem(); returnToggle.setState(TypewriterSound.isReturnEnabled);
        returnToggle.addActionListener(e -> TypewriterSound.isReturnEnabled = returnToggle.getState());
        audioMenu.add(clackToggle); audioMenu.add(bellToggle); audioMenu.add(returnToggle);

        settingsMenu.add(viewMenu); settingsMenu.addSeparator();
        settingsMenu.add(themeMenu); settingsMenu.add(fontMenu); settingsMenu.add(sizeMenu);
        settingsMenu.addSeparator(); settingsMenu.add(audioMenu); settingsMenu.addSeparator(); settingsMenu.add(langMenu);

        dedicatedExitButton = new JButton();
        dedicatedExitButton.setFocusPainted(false); dedicatedExitButton.setBorderPainted(false);
        dedicatedExitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dedicatedExitButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        dedicatedExitButton.addActionListener(e -> System.exit(0));

        menuBar.add(fileMenu); menuBar.add(settingsMenu);
        menuBar.add(Box.createHorizontalGlue()); menuBar.add(dedicatedExitButton);
        setJMenuBar(menuBar);
        updateLanguage();
    }

    private void toggleFullscreen() {
        dispose();
        setUndecorated(!isFullscreen);
        if (!isFullscreen) setExtendedState(JFrame.MAXIMIZED_BOTH);
        else { setExtendedState(JFrame.NORMAL); setSize(1000, 800); setLocationRelativeTo(null); }
        isFullscreen = !isFullscreen; setVisible(true);
    }

    private void toggleFocusMode() {
        isFocusMode = !isFocusMode;
        menuBar.setVisible(!isFocusMode); statusPanel.setVisible(!isFocusMode);
    }

    private void applyTheme() {
        generateTexture();
        textPane.setBackground(currentTheme.bg);
        textPane.setForeground(currentTheme.fg); textPane.setCaretColor(currentTheme.accent);
        statusPanel.setBackground(currentTheme.statusBg);
        statusLabel.setForeground(currentTheme.accent); wordCountLabel.setForeground(currentTheme.fg);
        menuBar.setBackground(currentTheme.statusBg); fileMenu.setForeground(currentTheme.accent);
        settingsMenu.setForeground(currentTheme.accent); dedicatedExitButton.setBackground(currentTheme.statusBg);
        dedicatedExitButton.setForeground(new Color(220, 60, 60));
        textPane.repaint();
    }

    private void applyFont() { textPane.setFont(new Font(currentFontName, Font.PLAIN, currentFontSize)); }

    private void updateLanguage() {
        setTitle("Screenwriter Pro - " + Localization.get(documentType.langKey));
        fileMenu.setText(Localization.get("File"));
        newItem.setText(Localization.get("New")); openItem.setText(Localization.get("Open"));
        saveItem.setText(Localization.get("Save")); printItem.setText(Localization.get("Print"));
        changeTypeItem.setText(Localization.get("ChangeType"));
        exitMenuItem.setText(Localization.get("Exit"));
        dedicatedExitButton.setText(" " + Localization.get("Exit") + " ");

        settingsMenu.setText(Localization.get("Settings")); viewMenu.setText(Localization.get("View"));
        fullscreenItem.setText(Localization.get("Fullscreen")); focusItem.setText(Localization.get("Focus"));

        themeMenu.setText(Localization.get("Theme")); fontMenu.setText(Localization.get("Font"));
        sizeMenu.setText(Localization.get("FontSize")); langMenu.setText(Localization.get("Language"));
        audioMenu.setText(Localization.get("Audio"));

        clackToggle.setText(Localization.get("SoundClack")); bellToggle.setText(Localization.get("SoundBell"));
        returnToggle.setText(Localization.get("SoundReturn"));

        for (int i = 0; i < Theme.values().length; i++) themeMenu.getItem(i).setText(Localization.get(Theme.values()[i].langKey));
        updateStatus();
    }

    private void updateStatus() {
        statusLabel.setText(Localization.get("Mode") + " [" + currentMode.getLabel() + "]  " + Localization.get("TabHint"));
        wordCountLabel.setText(Localization.get("Words") + wordCount);
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileManager.openFromTxt(textPane.getStyledDocument(), documentType, chooser.getSelectedFile().getAbsolutePath());
                wordCountLabel.setText(Localization.get("Words") + textPane.getText().split("\\s+").length);
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, Localization.get("Error") + ex.getMessage()); }
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileManager.saveToTxt(textPane.getStyledDocument(), chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, Localization.get("Success"));
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, Localization.get("Error") + ex.getMessage()); }
        }
    }

    private void printFile() {
        try {
            if (textPane.print()) JOptionPane.showMessageDialog(this, Localization.get("Success"));
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, Localization.get("Error") + ex.getMessage()); }
    }

    // --- PREDICTIVE TEXT ENGINE ---
    private String[] getSuggestionsForCurrentMode() {
        if (currentMode == ElementType.SCENE_HEADING || currentMode == ElementType.AUDIO_SCENE) {
            return new String[]{"INT. ", "EXT. ", "INT/EXT. "};
        } else if (currentMode == ElementType.TRANSITION) {
            return new String[]{"CUT TO:", "FADE IN:", "FADE OUT:", "DISSOLVE TO:"};
        } else if (currentMode == ElementType.CHAPTER_HEADING) {
            return new String[]{"CHAPTER "};
        } else if (currentMode == ElementType.PAGE_PANEL) {
            return new String[]{"PAGE ", "PANEL "};
        } else if (currentMode == ElementType.ACT_SCENE) {
            return new String[]{"ACT ", "SCENE "};
        }
        return null; // Return null for creative elements (Action, Dialogue, Prose) to prevent intrusion
    }

    private void triggerAutocompleteCheck(int caretOffset) {
        SwingUtilities.invokeLater(() -> {
            suggestionMenu.setVisible(false);
            String[] dict = getSuggestionsForCurrentMode();
            if (dict == null) return;

            try {
                javax.swing.text.Element p = textPane.getStyledDocument().getParagraphElement(caretOffset);
                int start = p.getStartOffset();
                String text = textPane.getText(start, caretOffset - start).toUpperCase();

                if (text.trim().isEmpty()) {
                    currentSuggestion = null;
                    return;
                }

                suggestionMenu.removeAll();
                boolean foundMatch = false;

                for (String word : dict) {
                    if (word.startsWith(text) && !word.equals(text)) {
                        JMenuItem item = new JMenuItem(word + "  (Press Enter)");
                        item.setFocusable(false);
                        item.setBackground(currentTheme.statusBg);
                        item.setForeground(currentTheme.accent);
                        item.setFont(new Font(currentFontName, Font.BOLD, currentFontSize - 4));
                        suggestionMenu.add(item);
                        currentSuggestion = word;
                        foundMatch = true;
                        break; // Display only the closest top match to keep it clean
                    }
                }

                if (foundMatch) {
                    Rectangle rect = textPane.modelToView2D(caretOffset).getBounds();
                    suggestionMenu.show(textPane, rect.x, rect.y + rect.height + 5);
                    textPane.requestFocusInWindow();
                } else {
                    currentSuggestion = null;
                }
            } catch (Exception e) {}
        });
    }

    private void setupListeners() {
        textPane.addCaretListener(e -> {
            StyledDocument doc = textPane.getStyledDocument();
            javax.swing.text.Element p = doc.getCharacterElement(textPane.getCaretPosition()).getParentElement();
            String modeName = (String) p.getAttributes().getAttribute("ElementType");
            if (modeName != null) { try { currentMode = ElementType.valueOf(modeName); updateStatus(); } catch (Exception ignored) {} }
        });

        textPane.getDocument().addDocumentListener(new DocumentListener() {
            private void updateCount() {
                String text = textPane.getText().trim();
                wordCount = text.isEmpty() ? 0 : text.split("\\s+").length; updateStatus();
            }
            public void insertUpdate(DocumentEvent e) { updateCount(); }
            public void removeUpdate(DocumentEvent e) { updateCount(); }
            public void changedUpdate(DocumentEvent e) { updateCount(); }
        });
    }

    private void setupKeyBindings() {
        InputMap im = textPane.getInputMap(); ActionMap am = textPane.getActionMap();
        im.put(KeyStroke.getKeyStroke("TAB"), "tab-action");
        am.put("tab-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                suggestionMenu.setVisible(false);
                currentSuggestion = null;
                currentMode = currentMode.next(); applyCurrentFormatting(); updateStatus(); TypewriterSound.playClick();
            }
        });

        im.put(KeyStroke.getKeyStroke("ENTER"), "enter-action");
        am.put("enter-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                // If the suggestion popup is visible, ENTER auto-fills the word instead of making a new line
                if (suggestionMenu.isVisible() && currentSuggestion != null) {
                    suggestionMenu.setVisible(false);
                    try {
                        javax.swing.text.Element p = textPane.getStyledDocument().getParagraphElement(textPane.getCaretPosition());
                        int start = p.getStartOffset();
                        int end = textPane.getCaretPosition();
                        textPane.getStyledDocument().remove(start, end - start);
                        textPane.getStyledDocument().insertString(start, currentSuggestion, textPane.getCharacterAttributes());
                        TypewriterSound.playClick();
                    } catch (Exception ex) {}
                    currentSuggestion = null;
                    return;
                }

                suggestionMenu.setVisible(false);
                textPane.replaceSelection("\n"); currentMode = currentMode.getSmartNext();
                applyCurrentFormatting(); updateStatus(); TypewriterSound.playReturn();
            }
        });
    }

    private void setupDocumentFilter() {
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(new DocumentFilter() {
            private boolean processHardStop(FilterBypass fb, int offset, String textToInsert) throws BadLocationException {
                if (textToInsert.contains("\n")) return true;
                javax.swing.text.Element p = ((StyledDocument)fb.getDocument()).getParagraphElement(offset);
                int currentLength = p.getEndOffset() - p.getStartOffset() - 1;
                int newLength = currentLength + textToInsert.length();
                if (newLength > currentMode.getMaxChars()) return false;
                if (newLength == currentMode.getMaxChars() - 8) TypewriterSound.playBell();
                return true;
            }

            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (!processHardStop(fb, offset, string)) return;
                if (currentMode.isAutoCapitalize() && string != null) string = string.toUpperCase();
                super.insertString(fb, offset, string, attr); TypewriterSound.playClick();
                triggerAutocompleteCheck(offset + string.length());
            }

            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && !processHardStop(fb, offset, text)) return;
                if (currentMode.isAutoCapitalize() && text != null) text = text.toUpperCase();
                super.replace(fb, offset, length, text, attrs);
                if (text != null && text.length() == 1) TypewriterSound.playClick();
                triggerAutocompleteCheck(offset + (text != null ? text.length() : 0));
            }

            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); TypewriterSound.playClick();
                triggerAutocompleteCheck(offset);
            }
        });
    }

    private void applyCurrentFormatting() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setLeftIndent(attrs, currentMode.getSwingIndent());
        StyleConstants.setFirstLineIndent(attrs, currentMode.getFirstLineIndent());
        attrs.addAttribute("ElementType", currentMode.name());
        doc.setParagraphAttributes(textPane.getCaretPosition(), 0, attrs, false);
    }
}