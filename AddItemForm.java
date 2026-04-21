import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddItemForm {

    public AddItemForm() {

        JFrame frame = new JFrame("Add Item");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(24, 24, 28)); // 🔥 Dark theme

        // 🔥 Title
        JLabel title = new JLabel("➕ Add New Item");
        title.setBounds(110, 20, 200, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        // 🔹 Labels
        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        nameLabel.setForeground(Color.WHITE);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(50, 130, 100, 25);
        priceLabel.setForeground(Color.WHITE);

        // 🔹 Fields
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 80, 160, 30);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField priceField = new JTextField();
        priceField.setBounds(150, 130, 160, 30);
        priceField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // 🔥 Button
        JButton addBtn = new JButton("Add Item");
        addBtn.setBounds(120, 190, 150, 40);

        addBtn.setBackground(new Color(255, 87, 34)); // Swiggy orange
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder());

        // 🔥 Hover effect
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(new Color(255, 120, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(new Color(255, 87, 34));
            }
        });

        // 🔹 Add components
        panel.add(title);
        panel.add(nameLabel);
        panel.add(priceLabel);
        panel.add(nameField);
        panel.add(priceField);
        panel.add(addBtn);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 🔽 Button Action
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();

                if (name.isEmpty() || priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                    return;
                }

                double price = Double.parseDouble(priceText);

                Connection con = DBConnection.getConnection();
                String query = "INSERT INTO menu(item_name, price) VALUES (?, ?)";

                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, name);
                ps.setDouble(2, price);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Item Added Successfully! 🎉");

                // Clear fields
                nameField.setText("");
                priceField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding item!");
                ex.printStackTrace();
            }
        });
    }
}