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

    private JMenu fileMenu, settingsMenu, viewMenu, themeMenu, fontMenu, sizeMenu, langMenu, marginMenu, audioMenu;
    private JMenuItem newItem, openItem, saveItem, exportItem, printItem, exitMenuItem;
    private JMenuItem fullscreenItem, focusItem;
    private JCheckBoxMenuItem clackToggle, bellToggle, returnToggle;
    private JButton dedicatedExitButton;

    private ElementType currentMode = ElementType.SCENE_HEADING;
    private Theme currentTheme = Theme.VINTAGE;
    private TexturePaint paperTexture;

    private String currentFontName = "Courier New";
    private int currentFontSize = 18;
    private int currentMargin = 150;
    private int wordCount = 0;

    private boolean isFullscreen = false;
    private boolean isFocusMode = false;

    public ScreenwriterUI() {
        setTitle("Screenwriter Pro");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textPane = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (paperTexture != null) {
                    g2d.setPaint(paperTexture);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g2d.setColor(getBackground());
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }

                // Draw physical lines with a 15px visual buffer OUTSIDE the text bounding box
                g2d.setColor(currentTheme.marginColor);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(currentMargin - 15, 0, currentMargin - 15, getHeight());
                g2d.drawLine(getWidth() - currentMargin + 15, 0, getWidth() - currentMargin + 15, getHeight());

                super.paintComponent(g);
            }
        };
        textPane.setOpaque(false);
        applyMargins();

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
        BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(currentTheme.bg);
        g.fillRect(0, 0, 128, 128);
        g.setColor(new Color(0, 0, 0, currentTheme.noiseAlpha));
        for (int i = 0; i < 4000; i++) {
            g.fillRect((int)(Math.random() * 128), (int)(Math.random() * 128), 1, 1);
        }
        g.dispose();
        paperTexture = new TexturePaint(img, new Rectangle(0, 0, 128, 128));
    }

    private void applyMargins() {
        textPane.setBorder(BorderFactory.createEmptyBorder(60, currentMargin, 60, currentMargin));
    }

    private void setupMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorderPainted(false);
        fileMenu = new JMenu(); settingsMenu = new JMenu();

        newItem = new JMenuItem();
        newItem.addActionListener(e -> { textPane.setText(""); wordCount = 0; updateStatus(); });

        openItem = new JMenuItem();
        openItem.addActionListener(e -> openFile());

        saveItem = new JMenuItem();
        saveItem.addActionListener(e -> saveFile());

        exportItem = new JMenuItem(); exportItem.setEnabled(false);
        printItem = new JMenuItem(); printItem.setEnabled(false);
        exitMenuItem = new JMenuItem();
        exitMenuItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem); fileMenu.add(openItem); fileMenu.addSeparator();
        fileMenu.add(saveItem); fileMenu.add(exportItem); fileMenu.add(printItem); fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        viewMenu = new JMenu();
        fullscreenItem = new JMenuItem();
        fullscreenItem.addActionListener(e -> toggleFullscreen());
        focusItem = new JMenuItem();
        focusItem.addActionListener(e -> toggleFocusMode());
        viewMenu.add(fullscreenItem); viewMenu.add(focusItem);

        themeMenu = new JMenu();
        for (Theme t : Theme.values()) {
            JMenuItem item = new JMenuItem();
            item.addActionListener(e -> { currentTheme = t; applyTheme(); });
            themeMenu.add(item);
        }

        fontMenu = new JMenu();
        String[] fonts = {"Courier New", "Consolas", "Monospaced"};
        for (String f : fonts) {
            JMenuItem item = new JMenuItem(f);
            item.addActionListener(e -> { currentFontName = f; applyFont(); });
            fontMenu.add(item);
        }

        sizeMenu = new JMenu();
        for (int s : new int[]{14, 16, 18, 22, 28}) {
            JMenuItem item = new JMenuItem(s + " pt");
            item.addActionListener(e -> { currentFontSize = s; applyFont(); });
            sizeMenu.add(item);
        }

        marginMenu = new JMenu();
        for (int m : new int[]{80, 150, 250}) {
            JMenuItem item = new JMenuItem();
            item.addActionListener(e -> { currentMargin = m; applyMargins(); textPane.repaint(); });
            marginMenu.add(item);
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
        settingsMenu.add(themeMenu); settingsMenu.add(fontMenu); settingsMenu.add(sizeMenu); settingsMenu.add(marginMenu);
        settingsMenu.addSeparator(); settingsMenu.add(audioMenu); settingsMenu.addSeparator(); settingsMenu.add(langMenu);

        dedicatedExitButton = new JButton();
        dedicatedExitButton.setFocusPainted(false); dedicatedExitButton.setBorderPainted(false);
        dedicatedExitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dedicatedExitButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        dedicatedExitButton.addActionListener(e -> System.exit(0));

        menuBar.add(fileMenu); menuBar.add(settingsMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(dedicatedExitButton);
        setJMenuBar(menuBar);

        updateLanguage();
    }

    private void toggleFullscreen() {
        dispose();
        setUndecorated(!isFullscreen);
        if (!isFullscreen) { setExtendedState(JFrame.MAXIMIZED_BOTH); }
        else { setExtendedState(JFrame.NORMAL); setSize(1200, 850); setLocationRelativeTo(null); }
        isFullscreen = !isFullscreen;
        setVisible(true);
    }

    private void toggleFocusMode() {
        isFocusMode = !isFocusMode;
        menuBar.setVisible(!isFocusMode);
        statusPanel.setVisible(!isFocusMode);
    }

    private void applyTheme() {
        generateTexture();
        textPane.setForeground(currentTheme.fg);
        textPane.setCaretColor(currentTheme.accent);
        statusPanel.setBackground(currentTheme.statusBg);
        statusLabel.setForeground(currentTheme.accent);
        wordCountLabel.setForeground(currentTheme.fg);
        menuBar.setBackground(currentTheme.statusBg);
        fileMenu.setForeground(currentTheme.accent);
        settingsMenu.setForeground(currentTheme.accent);
        dedicatedExitButton.setBackground(currentTheme.statusBg);
        dedicatedExitButton.setForeground(new Color(220, 60, 60));
        textPane.repaint();
    }

    private void applyFont() { textPane.setFont(new Font(currentFontName, Font.PLAIN, currentFontSize)); }

    private void updateLanguage() {
        fileMenu.setText(Localization.get("File"));
        newItem.setText(Localization.get("New")); openItem.setText(Localization.get("Open"));
        saveItem.setText(Localization.get("Save")); exportItem.setText(Localization.get("Export"));
        printItem.setText(Localization.get("Print")); exitMenuItem.setText(Localization.get("Exit"));
        dedicatedExitButton.setText(" " + Localization.get("Exit") + " ");

        settingsMenu.setText(Localization.get("Settings"));
        viewMenu.setText(Localization.get("View"));
        fullscreenItem.setText(Localization.get("Fullscreen")); focusItem.setText(Localization.get("Focus"));

        themeMenu.setText(Localization.get("Theme")); fontMenu.setText(Localization.get("Font"));
        sizeMenu.setText(Localization.get("FontSize")); marginMenu.setText(Localization.get("Margins"));
        langMenu.setText(Localization.get("Language")); audioMenu.setText(Localization.get("Audio"));

        clackToggle.setText(Localization.get("SoundClack")); bellToggle.setText(Localization.get("SoundBell"));
        returnToggle.setText(Localization.get("SoundReturn"));

        marginMenu.getItem(0).setText(Localization.get("Narrow"));
        marginMenu.getItem(1).setText(Localization.get("Standard"));
        marginMenu.getItem(2).setText(Localization.get("Wide"));

        for (int i = 0; i < Theme.values().length; i++) { themeMenu.getItem(i).setText(Localization.get(Theme.values()[i].langKey)); }
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
                FileManager.openFromTxt(textPane.getStyledDocument(), chooser.getSelectedFile().getAbsolutePath());
                wordCountLabel.setText(Localization.get("Words") + textPane.getText().split("\\s+").length);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, Localization.get("Error") + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                FileManager.saveToTxt(textPane.getStyledDocument(), chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, Localization.get("Success"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, Localization.get("Error") + ex.getMessage());
            }
        }
    }

    private void setupListeners() {
        textPane.addCaretListener(e -> {
            StyledDocument doc = textPane.getStyledDocument();
            javax.swing.text.Element p = doc.getCharacterElement(textPane.getCaretPosition()).getParentElement();
            String modeName = (String) p.getAttributes().getAttribute("ElementType");
            if (modeName != null) {
                try { currentMode = ElementType.valueOf(modeName); updateStatus(); } catch (Exception ignored) {}
            }
        });

        textPane.getDocument().addDocumentListener(new DocumentListener() {
            private void updateCount() {
                String text = textPane.getText().trim();
                wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;
                updateStatus();
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
                currentMode = currentMode.next(); applyCurrentFormatting(); updateStatus();
                TypewriterSound.playClick();
            }
        });
        im.put(KeyStroke.getKeyStroke("ENTER"), "enter-action");
        am.put("enter-action", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                textPane.replaceSelection("\n"); currentMode = currentMode.getSmartNext();
                applyCurrentFormatting(); updateStatus();
                TypewriterSound.playReturn();
            }
        });
    }

    private void setupDocumentFilter() {
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(new DocumentFilter() {
            private boolean processHardStop(FilterBypass fb, int offset, String textToInsert) throws BadLocationException {
                if (textToInsert.contains("\n")) return true; // Always allow Enter key

                javax.swing.text.Element p = ((StyledDocument)fb.getDocument()).getParagraphElement(offset);
                int currentLength = p.getEndOffset() - p.getStartOffset() - 1; // Exclude \n
                int newLength = currentLength + textToInsert.length();

                if (newLength > currentMode.getMaxChars()) {
                    return false; // Hard Carriage Lock! Block typing.
                }

                if (newLength == currentMode.getMaxChars() - 10) {
                    TypewriterSound.playBell();
                }
                return true;
            }

            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (!processHardStop(fb, offset, string)) return;
                if (currentMode.isAutoCapitalize() && string != null) string = string.toUpperCase();
                super.insertString(fb, offset, string, attr);
                TypewriterSound.playClick();
            }

            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null && !processHardStop(fb, offset, text)) return;
                if (currentMode.isAutoCapitalize() && text != null) text = text.toUpperCase();
                super.replace(fb, offset, length, text, attrs);
                if (text != null && text.length() == 1) TypewriterSound.playClick();
            }

            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); TypewriterSound.playClick();
            }
        });
    }

    private void applyCurrentFormatting() {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setLeftIndent(attrs, currentMode.getSwingIndent());
        attrs.addAttribute("ElementType", currentMode.name());
        doc.setParagraphAttributes(textPane.getCaretPosition(), 0, attrs, false);
    }
}