import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Enumeration;
import java.util.prefs.Preferences;

public class Main {
    public static Theme globalTheme = Theme.DARK;
    public static String globalFontName = "Courier New";
    public static int globalFontSize = 18;

    public static int globalAlignment = StyleConstants.ALIGN_LEFT;
    public static boolean isFixedCarriage = false;
    public static boolean isMenuLocked = false;

    public static Preferences prefs = Preferences.userNodeForPackage(Main.class);

    public static void main(String[] args) {
        loadPreferences();

        SwingUtilities.invokeLater(() -> {
            Font popFont = new Font("Century Gothic", Font.BOLD, 14);
            setUIFont(new javax.swing.plaf.FontUIResource(popFont));
            showLauncher();
        });
    }

    public static void loadPreferences() {
        try { globalTheme = Theme.valueOf(prefs.get("theme", "DARK")); } catch(Exception e) {}
        try { Localization.current = Localization.Lang.valueOf(prefs.get("lang", "ENGLISH")); } catch(Exception e) {}
        globalFontName = prefs.get("fontName", "Courier New");
        globalFontSize = prefs.getInt("fontSize", 18);
        TypewriterSound.isClackEnabled = prefs.getBoolean("clack", true);
        TypewriterSound.isBellEnabled = prefs.getBoolean("bell", true);
        TypewriterSound.isReturnEnabled = prefs.getBoolean("return", true);
        TypewriterSound.isScrollEnabled = prefs.getBoolean("scroll", true);

        globalAlignment = prefs.getInt("alignment", StyleConstants.ALIGN_LEFT);
        isFixedCarriage = prefs.getBoolean("fixedCarriage", false);
        isMenuLocked = prefs.getBoolean("menuLocked", false);
    }

    public static void savePreferences() {
        prefs.put("theme", globalTheme.name());
        prefs.put("lang", Localization.current.name());
        prefs.put("fontName", globalFontName);
        prefs.putInt("fontSize", globalFontSize);
        prefs.putBoolean("clack", TypewriterSound.isClackEnabled);
        prefs.putBoolean("bell", TypewriterSound.isBellEnabled);
        prefs.putBoolean("return", TypewriterSound.isReturnEnabled);
        prefs.putBoolean("scroll", TypewriterSound.isScrollEnabled);

        prefs.putInt("alignment", globalAlignment);
        prefs.putBoolean("fixedCarriage", isFixedCarriage);
        prefs.putBoolean("menuLocked", isMenuLocked);
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void showLauncher() {
        JFrame launcher = new JFrame("TYPEWRITER");
        launcher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        launcher.setSize(850, 540);
        launcher.setLocationRelativeTo(null);
        launcher.setLayout(new BorderLayout());
        launcher.getContentPane().setBackground(globalTheme.bg);

        JLabel title = new JLabel("T Y P E W R I T E R", SwingConstants.CENTER);
        title.setFont(new Font("Century Gothic", Font.BOLD, 32));
        title.setForeground(globalTheme.fg);
        title.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 2, 0, globalTheme.accent),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        JLabel subtitle = new JLabel(Localization.get("HubTitle"), SwingConstants.CENTER);
        subtitle.setFont(new Font("Century Gothic", Font.PLAIN, 15));
        subtitle.setForeground(globalTheme.accent);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 20, 80));
        headerPanel.add(title);
        headerPanel.add(subtitle);
        launcher.add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        for (DocumentType type : DocumentType.values()) {
            JButton btn = new JButton(Localization.get(type.langKey));
            btn.setFont(new Font("Century Gothic", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setBackground(globalTheme.statusBg);
            btn.setForeground(globalTheme.accent);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(globalTheme.marginColor, 1));

            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(globalTheme.accent); btn.setForeground(globalTheme.bg); }
                public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(globalTheme.statusBg); btn.setForeground(globalTheme.accent); }
            });

            btn.addActionListener(e -> {
                launcher.dispose();
                ScreenwriterUI app = new ScreenwriterUI(type);
                app.setLocationRelativeTo(null);
                app.setVisible(true);
            });
            buttonPanel.add(btn);
        }
        launcher.add(buttonPanel, BorderLayout.CENTER);

        JLabel copyright = new JLabel("© 2026 Alex Paukert. All rights reserved.", SwingConstants.CENTER);
        copyright.setFont(new Font("Century Gothic", Font.PLAIN, 11));
        Color c = globalTheme.fg;
        copyright.setForeground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 120));
        copyright.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        launcher.add(copyright, BorderLayout.SOUTH);

        launcher.setVisible(true);
    }
}