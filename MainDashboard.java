import javax.swing.*;
import java.awt.*;

public class MainDashboard {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Restaurant System");

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18, 18, 22));

        // 🔥 Title (bigger)
        JLabel title = new JLabel("🍽 Restaurant System");
        title.setBounds(60, 30, 400, 40);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        // 🔥 Card Panel (bigger)
        JPanel card = new JPanel(null);
        card.setBounds(80, 100, 300, 350); // increased size
        card.setBackground(new Color(35, 35, 45));
        card.setBorder(BorderFactory.createLineBorder(new Color(60,60,70)));

        // 🔥 Bigger Buttons
        JButton addBtn = createButton("➕ Add Item", 30);
        JButton viewBtn = createButton("📋 View Menu", 100);
        JButton orderBtn = createButton("🛒 Place Order", 170);
        JButton historyBtn = createButton("🧾 Order History", 240);

        // Actions
        addBtn.addActionListener(e -> new AddItemForm());
        viewBtn.addActionListener(e -> new ViewMenu());
        orderBtn.addActionListener(e -> new PlaceOrder());
        historyBtn.addActionListener(e -> new OrderHistory());

        card.add(addBtn);
        card.add(viewBtn);
        card.add(orderBtn);
        card.add(historyBtn);

        panel.add(title);
        panel.add(card);

        frame.add(panel);

        // 🔥 Bigger Frame
        frame.setSize(600, 500);   // increased
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    static JButton createButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(40, y, 220, 45); // bigger button

        btn.setBackground(new Color(255, 87, 34));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16)); // bigger font
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 120, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(255, 87, 34));
            }
        });

        return btn;
    }
}