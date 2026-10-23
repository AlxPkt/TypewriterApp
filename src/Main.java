import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception e) {}

            showLauncher();
        });
    }

    public static void showLauncher() {
        JDialog launcher = new JDialog((Frame)null, "Screenwriter Pro - Hub", true);
        launcher.setSize(600, 350);
        launcher.setLocationRelativeTo(null);
        launcher.setLayout(new BorderLayout());
        launcher.getContentPane().setBackground(Theme.DARK.bg);

        JLabel title = new JLabel(Localization.get("HubTitle"), SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Theme.DARK.fg);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        launcher.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 40, 30));

        for (DocumentType type : DocumentType.values()) {
            JButton btn = new JButton(Localization.get(type.langKey));
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));
            btn.setFocusPainted(false);
            btn.setBackground(Theme.DARK.statusBg);
            btn.setForeground(Theme.DARK.accent);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

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