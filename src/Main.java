import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class Main {
    // Globally remembers your theme across the entire application
    public static Theme globalTheme = Theme.DARK;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Font modernUIFont = new Font("Segoe UI", Font.PLAIN, 13);
            setUIFont(new javax.swing.plaf.FontUIResource(modernUIFont));

            showLauncher();
        });
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
        JDialog launcher = new JDialog((Frame)null, "TYPEWRITER", true);
        launcher.setSize(650, 400);
        launcher.setLocationRelativeTo(null);
        launcher.setLayout(new BorderLayout());

        // Use global memory to instantly style the Hub
        launcher.getContentPane().setBackground(globalTheme.bg);

        JLabel title = new JLabel(Localization.get("HubTitle"), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(globalTheme.fg);
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 40, 0));
        launcher.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 60, 40));

        for (DocumentType type : DocumentType.values()) {
            JButton btn = new JButton(Localization.get(type.langKey));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
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
        launcher.setVisible(true);
    }
}