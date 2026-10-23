import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;

public class ScreenwriterUI extends JFrame {
    private JTextPane textPane;
    private JScrollPane scrollPane; // Pulled out so the engine can manipulate the scrolling viewport
    private JLabel statusLabel, wordCountLabel;
    private JLabel focusToggleBtn, fullscreenToggleBtn;
    private JPanel statusPanel, rightStatusPanel;

    private JPanel topMenuContainer;
    private JMenuBar menuBar;
    private Timer menuAnimator;
    private boolean isMenuShowing = true;

    private JMenu fileMenu, viewMenu, themeMenu, fontMenu, sizeMenu, langMenu, audioMenu;
    private JMenuItem newItem, openItem, saveItem, printItem, changeTypeItem, exitMenuItem;
    private JMenuItem fullscreenItem, focusItem;

    // New Typing Mechanics Menus
    private JMenu alignMenu;
    private JMenuItem leftAlign, centerAlign, rightAlign;
    private JCheckBoxMenuItem fixedCarriageToggle;

    private JCheckBoxMenuItem clackToggle, bellToggle, returnToggle;

    private JPopupMenu suggestionMenu;
    private String currentSuggestion = null;

    private final DocumentType documentType;
    private ElementType currentMode;
    private TexturePaint paperTexture;

    private int wordCount = 0;
    private boolean isFullscreen = false;
    private boolean isFocusMode = false;

    public ScreenwriterUI(DocumentType docType) {
        this.documentType = docType;
        this.currentMode = ElementType.getValuesFor(docType)[0];

        setTitle("TYPEWRITER - " + Localization.get(docType.langKey));
        setSize(1100, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        suggestionMenu = new JPopupMenu();
        suggestionMenu.setFocusable(false);

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

                g2d.setColor(Main.globalTheme.marginColor);
                Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
                g2d.setStroke(dashed);
                g2d.drawLine(100, 0, 100, getHeight());
                g2d.drawLine(getWidth() - 100, 0, getWidth() - 100, getHeight());
                super.paintComponent(g);
            }
        };
        textPane.setOpaque(false);
        textPane.setBorder(BorderFactory.createEmptyBorder(60, 120, 60, 120));

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));
        statusPanel.add(statusLabel, BorderLayout.WEST);

        rightStatusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightStatusPanel.setOpaque(false);

        wordCountLabel = new JLabel();
        wordCountLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));

        focusToggleBtn = new JLabel(Localization.get("Focus"));
        focusToggleBtn.setFont(new Font("Century Gothic", Font.BOLD, 13));
        focusToggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        focusToggleBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { focusToggleBtn.setForeground(Main.globalTheme.accent); }
            public void mouseExited(MouseEvent evt) { focusToggleBtn.setForeground(isFocusMode ? Main.globalTheme.accent : Main.globalTheme.fg); }
            public void mouseClicked(MouseEvent evt) { toggleFocusMode(); }
        });

        fullscreenToggleBtn = new JLabel(Localization.get("Fullscreen"));
        fullscreenToggleBtn.setFont(new Font("Century Gothic", Font.BOLD, 13));
        fullscreenToggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fullscreenToggleBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { fullscreenToggleBtn.setForeground(Main.globalTheme.accent); }
            public void mouseExited(MouseEvent evt) { fullscreenToggleBtn.setForeground(isFullscreen ? Main.globalTheme.accent : Main.globalTheme.fg); }
            public void mouseClicked(MouseEvent evt) { toggleFullscreen(); }
        });

        rightStatusPanel.add(wordCountLabel);
        rightStatusPanel.add(focusToggleBtn);
        rightStatusPanel.add(fullscreenToggleBtn);
        statusPanel.add(rightStatusPanel, BorderLayout.EAST);
        add(statusPanel, BorderLayout.SOUTH);

        setupMenus();
        setupSlidingMenuLogic();
        setupKeyBindings();
        setupDocumentFilter();
        setupListeners();

        applyTheme();
        applyFont();

        // Ensure the entire document respects the saved Global Alignment on startup
        changeAlignment(Main.globalAlignment);
        applyCurrentFormatting();
    }

    private void checkUnsavedAndExecute(Runnable action) {
        if (!textPane.getText().trim().isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(this, Localization.get("Unsaved"), Localization.get("Warning"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                boolean successfullySaved = saveFile();
                if (successfullySaved) action.run();
            } else if (opt == JOptionPane.NO_OPTION) {
                action.run();
            }
        } else {
            action.run();
        }
    }

    private void generateTexture() {
        if (Main.globalTheme.noiseAlpha == 0) { paperTexture = null; return; }
        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Main.globalTheme.bg); g.fillRect(0, 0, 128, 128);
        g.setColor(new Color(0, 0, 0, Main.globalTheme.noiseAlpha));
        for (int i = 0; i < 4000; i++) g.fillRect((int)(Math.random() * 128), (int)(Math.random() * 128), 1, 1);
        g.dispose();
        paperTexture = new TexturePaint(img, new Rectangle(0, 0, 128, 128));
    }

    private void setupMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        fileMenu = new JMenu(); viewMenu = new JMenu(); themeMenu = new JMenu();
        fontMenu = new JMenu(); sizeMenu = new JMenu(); langMenu = new JMenu(); audioMenu = new JMenu();

        newItem = new JMenuItem(); newItem.addActionListener(e -> checkUnsavedAndExecute(() -> { textPane.setText(""); wordCount = 0; updateStatus(); }));
        openItem = new JMenuItem(); openItem.addActionListener(e -> checkUnsavedAndExecute(this::openFile));
        saveItem = new JMenuItem(); saveItem.addActionListener(e -> saveFile());
        printItem = new JMenuItem(); printItem.addActionListener(e -> printFile());
        changeTypeItem = new JMenuItem(); changeTypeItem.addActionListener(e -> checkUnsavedAndExecute(() -> { this.dispose(); Main.showLauncher(); }));
        exitMenuItem = new JMenuItem(); exitMenuItem.addActionListener(e -> checkUnsavedAndExecute(() -> System.exit(0)));

        fileMenu.add(newItem); fileMenu.add(openItem); fileMenu.addSeparator();
        fileMenu.add(saveItem); fileMenu.add(printItem); fileMenu.addSeparator();
        fileMenu.add(changeTypeItem); fileMenu.addSeparator(); fileMenu.add(exitMenuItem);

        fullscreenItem = new JMenuItem(); fullscreenItem.addActionListener(e -> toggleFullscreen());
        focusItem = new JMenuItem(); focusItem.addActionListener(e -> toggleFocusMode());

        // --- TEXT ALIGNMENT & FIXED CARRIAGE MENUS ---
        alignMenu = new JMenu();
        leftAlign = new JMenuItem(); leftAlign.addActionListener(e -> changeAlignment(StyleConstants.ALIGN_LEFT));
        centerAlign = new JMenuItem(); centerAlign.addActionListener(e -> changeAlignment(StyleConstants.ALIGN_CENTER));
        rightAlign = new JMenuItem(); rightAlign.addActionListener(e -> changeAlignment(StyleConstants.ALIGN_RIGHT));
        alignMenu.add(leftAlign); alignMenu.add(centerAlign); alignMenu.add(rightAlign);

        fixedCarriageToggle = new JCheckBoxMenuItem();
        fixedCarriageToggle.setState(Main.isFixedCarriage);
        fixedCarriageToggle.addActionListener(e -> {
            Main.isFixedCarriage = fixedCarriageToggle.getState();
            Main.savePreferences();
            centerCarriage(); // Immediately scroll the screen to center when toggled
        });

        viewMenu.add(fullscreenItem); viewMenu.add(focusItem); viewMenu.addSeparator();
        viewMenu.add(alignMenu); viewMenu.add(fixedCarriageToggle);

        for (Theme t : Theme.values()) {
            JMenuItem item = new JMenuItem();
            item.addActionListener(e -> { Main.globalTheme = t; Main.savePreferences(); applyTheme(); });
            themeMenu.add(item);
        }

        String[] allFonts = {"Courier New", "Consolas", "Monospaced", "Georgia", "Times New Roman", "Arial", "Helvetica", "Century Gothic", "Verdana", "Tahoma", "Trebuchet MS", "Garamond", "Comic Sans MS"};
        for (String f : allFonts) {
            JMenuItem item = new JMenuItem(f);
            item.addActionListener(e -> { Main.globalFontName = f; Main.savePreferences(); applyFont(); });
            fontMenu.add(item);
        }

        for (int s : new int[]{10, 12, 14, 16, 18, 20, 22, 24, 28}) {
            JMenuItem item = new JMenuItem(s + " pt");
            item.addActionListener(e -> { Main.globalFontSize = s; Main.savePreferences(); applyFont(); });
            sizeMenu.add(item);
        }

        for (Localization.Lang l : Localization.Lang.values()) {
            JMenuItem item = new JMenuItem(l.name());
            item.addActionListener(e -> { Localization.current = l; Main.savePreferences(); updateLanguage(); });
            langMenu.add(item);
        }

        clackToggle = new JCheckBoxMenuItem(); clackToggle.setState(TypewriterSound.isClackEnabled);
        clackToggle.addActionListener(e -> { TypewriterSound.isClackEnabled = clackToggle.getState(); Main.savePreferences(); });
        bellToggle = new JCheckBoxMenuItem(); bellToggle.setState(TypewriterSound.isBellEnabled);
        bellToggle.addActionListener(e -> { TypewriterSound.isBellEnabled = bellToggle.getState(); Main.savePreferences(); });
        returnToggle = new JCheckBoxMenuItem(); returnToggle.setState(TypewriterSound.isReturnEnabled);
        returnToggle.addActionListener(e -> { TypewriterSound.isReturnEnabled = returnToggle.getState(); Main.savePreferences(); });
        audioMenu.add(clackToggle); audioMenu.add(bellToggle); audioMenu.add(returnToggle);

        menuBar.add(fileMenu); menuBar.add(viewMenu); menuBar.add(themeMenu);
        menuBar.add(fontMenu); menuBar.add(sizeMenu); menuBar.add(audioMenu); menuBar.add(langMenu);

        topMenuContainer = new JPanel(new BorderLayout());
        topMenuContainer.add(menuBar, BorderLayout.CENTER);
        topMenuContainer.setPreferredSize(new Dimension(getWidth(), 40));
        add(topMenuContainer, BorderLayout.NORTH);

        updateLanguage();
    }

    private void setupSlidingMenuLogic() {
        menuAnimator = new Timer(10, e -> {
            int h = topMenuContainer.getPreferredSize().height;
            int target = isMenuShowing ? 40 : 0;
            if (h != target) {
                h += (isMenuShowing ? 5 : -5);
                h = Math.max(0, Math.min(40, h));
                topMenuContainer.setPreferredSize(new Dimension(getWidth(), h));
                topMenuContainer.revalidate();
            } else {
                ((Timer)e.getSource()).stop();
            }
        });

        MouseAdapter slideListener = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (isFocusMode) return;
                Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), ScreenwriterUI.this);

                boolean isMenuOpen = false;
                for (MenuElement el : menuBar.getSubElements()) {
                    if (el.getComponent() instanceof JMenu && ((JMenu) el).isSelected()) {
                        isMenuOpen = true; break;
                    }
                }

                if (p.y <= 50) {
                    if (!isMenuShowing) { isMenuShowing = true; menuAnimator.start(); }
                } else if (p.y > 150 && !isMenuOpen) {
                    if (isMenuShowing) { isMenuShowing = false; menuAnimator.start(); }
                }
            }
        };

        textPane.addMouseMotionListener(slideListener);
        menuBar.addMouseMotionListener(slideListener);
        statusPanel.addMouseMotionListener(slideListener);
    }

    // --- NEW: TYPEWRITER SIMULATION MECHANICS ---

    // 1. Instantly forces alignment across the entire document
    private void changeAlignment(int alignment) {
        Main.globalAlignment = alignment;
        Main.savePreferences();
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, alignment);
        doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
        textPane.repaint();
    }

    // 2. Mathematically calculates exact bounds to force the virtual paper to slide under the cursor
    private void centerCarriage() {
        if (Main.isFixedCarriage && scrollPane != null) {
            SwingUtilities.invokeLater(() -> {
                try {
                    Rectangle rect = textPane.modelToView2D(textPane.getCaretPosition()).getBounds();
                    JViewport viewport = scrollPane.getViewport();

                    int extentWidth = viewport.getExtentSize().width;
                    int extentHeight = viewport.getExtentSize().height;

                    int viewX = Math.max(0, rect.x - (extentWidth / 2));
                    int viewY = Math.max(0, rect.y - (extentHeight / 2));

                    // Cap bounds to ensure smooth scrolling
                    int maxX = Math.max(0, textPane.getWidth() - extentWidth);
                    int maxY = Math.max(0, textPane.getHeight() - extentHeight);

                    viewport.setViewPosition(new Point(Math.min(viewX, maxX), Math.min(viewY, maxY)));
                } catch (Exception ex) {}
            });
        }
    }

    private void toggleFullscreen() {
        dispose();
        setUndecorated(!isFullscreen);
        if (!isFullscreen) setExtendedState(JFrame.MAXIMIZED_BOTH);
        else { setExtendedState(JFrame.NORMAL); setSize(1100, 850); setLocationRelativeTo(null); }
        isFullscreen = !isFullscreen;
        fullscreenToggleBtn.setForeground(isFullscreen ? Main.globalTheme.accent : Main.globalTheme.fg);
        setVisible(true);
    }

    private void toggleFocusMode() {
        isFocusMode = !isFocusMode;
        if (isFocusMode) {
            isMenuShowing = false;
            topMenuContainer.setPreferredSize(new Dimension(getWidth(), 0));
        } else {
            isMenuShowing = true;
            topMenuContainer.setPreferredSize(new Dimension(getWidth(), 40));
        }
        topMenuContainer.revalidate();
        focusToggleBtn.setForeground(isFocusMode ? Main.globalTheme.accent : Main.globalTheme.fg);
    }

    private void styleMenuComponents(MenuElement[] elements) {
        Border shadowBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 6, 6, new Color(0, 0, 0, 80)),
                BorderFactory.createLineBorder(Main.globalTheme.accent, 1)
        );

        for (MenuElement el : elements) {
            Component c = el.getComponent();
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.setOpaque(true);
                jc.setBackground(Main.globalTheme.statusBg);
                jc.setForeground(Main.globalTheme.fg);
                if (jc instanceof JMenuItem) jc.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
            }
            if (c instanceof JMenu) {
                JPopupMenu popup = ((JMenu) c).getPopupMenu();
                popup.setBorder(shadowBorder);
                popup.setBackground(Main.globalTheme.statusBg);
            }
            if (el.getSubElements().length > 0) styleMenuComponents(el.getSubElements());
        }
    }

    private void applyTheme() {
        generateTexture();
        textPane.setBackground(Main.globalTheme.bg);
        textPane.setForeground(Main.globalTheme.fg); textPane.setCaretColor(Main.globalTheme.accent);

        statusPanel.setBackground(Main.globalTheme.statusBg);
        statusLabel.setForeground(Main.globalTheme.fg);
        wordCountLabel.setForeground(Main.globalTheme.fg);
        focusToggleBtn.setForeground(isFocusMode ? Main.globalTheme.accent : Main.globalTheme.fg);
        fullscreenToggleBtn.setForeground(isFullscreen ? Main.globalTheme.accent : Main.globalTheme.fg);

        statusPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Main.globalTheme.marginColor),
                BorderFactory.createEmptyBorder(6, 20, 6, 20)
        ));

        topMenuContainer.setBackground(Main.globalTheme.statusBg);
        menuBar.setOpaque(true);
        menuBar.setBackground(Main.globalTheme.statusBg);
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Main.globalTheme.marginColor));

        styleMenuComponents(menuBar.getSubElements());

        suggestionMenu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 6, 6, new Color(0, 0, 0, 80)),
                BorderFactory.createLineBorder(Main.globalTheme.accent, 1)
        ));
        suggestionMenu.setBackground(Main.globalTheme.statusBg);

        getContentPane().setBackground(Main.globalTheme.bg);
        textPane.repaint();
    }

    private void applyFont() { textPane.setFont(new Font(Main.globalFontName, Font.PLAIN, Main.globalFontSize)); }

    private void updateLanguage() {
        setTitle("TYPEWRITER - " + Localization.get(documentType.langKey));

        fileMenu.setText(Localization.get("File"));
        newItem.setText(Localization.get("New"));
        openItem.setText(Localization.get("Open"));
        saveItem.setText(Localization.get("Save"));
        printItem.setText(Localization.get("Print"));
        changeTypeItem.setText(Localization.get("ChangeType"));
        exitMenuItem.setText(Localization.get("Exit"));

        viewMenu.setText(Localization.get("View"));
        fullscreenItem.setText(Localization.get("Fullscreen"));
        focusItem.setText(Localization.get("Focus"));

        alignMenu.setText(Localization.get("Alignment"));
        leftAlign.setText(Localization.get("AlignLeft"));
        centerAlign.setText(Localization.get("AlignCenter"));
        rightAlign.setText(Localization.get("AlignRight"));
        fixedCarriageToggle.setText(Localization.get("FixedCarriage"));

        themeMenu.setText(Localization.get("Theme"));
        fontMenu.setText(Localization.get("Font"));
        sizeMenu.setText(Localization.get("FontSize"));
        langMenu.setText(Localization.get("Language"));
        audioMenu.setText(Localization.get("Audio"));

        clackToggle.setText(Localization.get("SoundClack"));
        bellToggle.setText(Localization.get("SoundBell"));
        returnToggle.setText(Localization.get("SoundReturn"));

        focusToggleBtn.setText(Localization.get("Focus"));
        fullscreenToggleBtn.setText(Localization.get("Fullscreen"));

        for (int i = 0; i < Theme.values().length; i++) themeMenu.getItem(i).setText(Localization.get(Theme.values()[i].langKey));
        updateStatus();
    }

    private void updateStatus() {
        statusLabel.setText(Localization.get("Mode") + " [" + currentMode.getLabel() + "]    " + Localization.get("TabHint"));
        wordCountLabel.setText(Localization.get("Words") + wordCount);
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileManager.openFromTxt(textPane.getStyledDocument(), documentType, chooser.getSelectedFile().getAbsolutePath());
                wordCountLabel.setText(Localization.get("Words") + textPane.getText().split("\\s+").length);
            } catch (Exception ex) {}
        }
    }

    private boolean saveFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileManager.saveToTxt(textPane.getStyledDocument(), chooser.getSelectedFile().getAbsolutePath());
                return true;
            } catch (Exception ex) { return false; }
        }
        return false;
    }

    private void printFile() {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job.printDialog()) {
            new Thread(() -> {
                try {
                    boolean complete = textPane.print(null, null, false, job.getPrintService(), null, true);
                    if (complete) SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, Localization.get("Success")));
                } catch (Exception ex) {}
            }).start();
        }
    }

    private String[] getSuggestionsForCurrentMode() {
        if (currentMode == ElementType.SCENE_HEADING || currentMode == ElementType.AUDIO_SCENE) return new String[]{"INT. ", "EXT. ", "INT/EXT. "};
        else if (currentMode == ElementType.TRANSITION) return new String[]{"CUT TO:", "FADE IN:", "FADE OUT:", "DISSOLVE TO:"};
        else if (currentMode == ElementType.CHAPTER_HEADING) return new String[]{"CHAPTER "};
        else if (currentMode == ElementType.PAGE_PANEL) return new String[]{"PAGE ", "PANEL "};
        else if (currentMode == ElementType.ACT_SCENE) return new String[]{"ACT ", "SCENE "};
        return null;
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

                if (text.trim().isEmpty()) { currentSuggestion = null; return; }

                suggestionMenu.removeAll();
                boolean foundMatch = false;

                for (String word : dict) {
                    if (word.startsWith(text) && !word.equals(text)) {
                        JMenuItem item = new JMenuItem(word + "  (Enter)");
                        item.setFocusable(false); item.setBackground(Main.globalTheme.statusBg);
                        item.setForeground(Main.globalTheme.accent);
                        item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
                        suggestionMenu.add(item);
                        currentSuggestion = word; foundMatch = true; break;
                    }
                }

                if (foundMatch) {
                    Rectangle rect = textPane.modelToView2D(caretOffset).getBounds();
                    suggestionMenu.show(textPane, rect.x, rect.y + rect.height + 5);
                    textPane.requestFocusInWindow();
                } else currentSuggestion = null;
            } catch (Exception e) {}
        });
    }

    private void setupListeners() {
        textPane.addCaretListener(e -> {
            StyledDocument doc = textPane.getStyledDocument();
            javax.swing.text.Element p = doc.getCharacterElement(textPane.getCaretPosition()).getParentElement();
            String modeName = (String) p.getAttributes().getAttribute("ElementType");
            if (modeName != null) { try { currentMode = ElementType.valueOf(modeName); updateStatus(); } catch (Exception ignored) {} }

            // Re-centers the viewport seamlessly while typing or clicking if Fixed Carriage is ON
            centerCarriage();
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
                suggestionMenu.setVisible(false); currentSuggestion = null;
                currentMode = currentMode.next(); applyCurrentFormatting(); updateStatus(); TypewriterSound.playClick();
            }
        });

        im.put(KeyStroke.getKeyStroke("ENTER"), "enter-action");
        am.put("enter-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (suggestionMenu.isVisible() && currentSuggestion != null) {
                    suggestionMenu.setVisible(false);
                    try {
                        javax.swing.text.Element p = textPane.getStyledDocument().getParagraphElement(textPane.getCaretPosition());
                        int start = p.getStartOffset(); int end = textPane.getCaretPosition();
                        textPane.getStyledDocument().remove(start, end - start);
                        textPane.getStyledDocument().insertString(start, currentSuggestion, textPane.getCharacterAttributes());
                        TypewriterSound.playClick();
                    } catch (Exception ex) {}
                    currentSuggestion = null; return;
                }

                suggestionMenu.setVisible(false);
                textPane.replaceSelection("\n"); currentMode = currentMode.getSmartNext();
                applyCurrentFormatting(); updateStatus(); TypewriterSound.playReturn();
            }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "fullscreen-action");
        am.put("fullscreen-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { toggleFullscreen(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc-action");
        am.put("esc-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isFocusMode) toggleFocusMode();
                if (isFullscreen) toggleFullscreen();
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
                super.insertString(fb, offset, string, attr); TypewriterSound.playClick(); triggerAutocompleteCheck(offset + string.length());
            }

            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && !processHardStop(fb, offset, text)) return;
                if (currentMode.isAutoCapitalize() && text != null) text = text.toUpperCase();
                super.replace(fb, offset, length, text, attrs);
                if (text != null && text.length() == 1) TypewriterSound.playClick(); triggerAutocompleteCheck(offset + (text != null ? text.length() : 0));
            }

            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); TypewriterSound.playClick(); triggerAutocompleteCheck(offset);
            }
        });
    }

    private void applyCurrentFormatting() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setLeftIndent(attrs, currentMode.getSwingIndent());
        StyleConstants.setFirstLineIndent(attrs, currentMode.getFirstLineIndent());

        // Tells the engine to check memory and align the text correctly (Left, Center, or Right)
        StyleConstants.setAlignment(attrs, Main.globalAlignment);

        attrs.addAttribute("ElementType", currentMode.name());
        doc.setParagraphAttributes(textPane.getCaretPosition(), 0, attrs, false);
    }
}