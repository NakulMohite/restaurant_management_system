import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PlaceOrder {

    JComboBox<String> itemBox;
    JTextField qtyField;
    JLabel priceLabel, totalLabel;

    double price = 0;

    public PlaceOrder() {

        JFrame frame = new JFrame("Place Order");

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(24, 24, 28)); // dark bg

        // 🔥 Title
        JLabel title = new JLabel("🛒 Place Order", JLabel.CENTER);
        title.setBounds(0, 20, 400, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);

        // 🔥 Card Panel (like dashboard)
        JPanel card = new JPanel(null);
        card.setBounds(40, 70, 310, 240);
        card.setBackground(new Color(35, 35, 45));
        card.setBorder(BorderFactory.createLineBorder(new Color(60,60,70)));

        // Labels
        JLabel itemLabel = new JLabel("Select Item:");
        itemLabel.setBounds(30, 30, 100, 25);
        itemLabel.setForeground(Color.WHITE);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setBounds(30, 70, 100, 25);
        qtyLabel.setForeground(Color.WHITE);

        JLabel priceText = new JLabel("Price:");
        priceText.setBounds(30, 110, 100, 25);
        priceText.setForeground(Color.WHITE);

        JLabel totalText = new JLabel("Total:");
        totalText.setBounds(30, 150, 100, 25);
        totalText.setForeground(Color.WHITE);

        // Inputs
        itemBox = new JComboBox<>();
        itemBox.setBounds(140, 30, 130, 28);
        itemBox.setBackground(Color.WHITE);

        qtyField = new JTextField();
        qtyField.setBounds(140, 70, 130, 28);
        qtyField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        priceLabel = new JLabel("₹ 0.00");
        priceLabel.setBounds(140, 110, 130, 25);
        priceLabel.setForeground(Color.GREEN);

        totalLabel = new JLabel("₹ 0.00");
        totalLabel.setBounds(140, 150, 130, 25);
        totalLabel.setForeground(Color.GREEN);

        // 🔥 Button
        JButton orderBtn = new JButton("Place Order");
        orderBtn.setBounds(80, 185, 150, 35);

        orderBtn.setBackground(new Color(255, 87, 34));
        orderBtn.setForeground(Color.WHITE);
        orderBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderBtn.setFocusPainted(false);
        orderBtn.setBorder(BorderFactory.createEmptyBorder());

        // Hover
        orderBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                orderBtn.setBackground(new Color(255, 120, 60));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                orderBtn.setBackground(new Color(255, 87, 34));
            }
        });

        // Add to card
        card.add(itemLabel);
        card.add(qtyLabel);
        card.add(priceText);
        card.add(totalText);
        card.add(itemBox);
        card.add(qtyField);
        card.add(priceLabel);
        card.add(totalLabel);
        card.add(orderBtn);

        panel.add(title);
        panel.add(card);

        frame.add(panel);
        frame.setSize(400, 360);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loadItems();

        // Events
        itemBox.addActionListener(e -> {
            fetchPrice();
            calculateTotal();
        });

        qtyField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calculateTotal();
            }
        });

        orderBtn.addActionListener(e -> placeOrder());
    }

    void loadItems() {
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT item_name FROM menu");

            while (rs.next()) {
                itemBox.addItem(rs.getString("item_name"));
            }

            if (itemBox.getItemCount() > 0) {
                itemBox.setSelectedIndex(0);
                fetchPrice();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fetchPrice() {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT price FROM menu WHERE item_name=?");
            ps.setString(1, (String) itemBox.getSelectedItem());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("price");
                priceLabel.setText("₹ " + String.format("%.2f", price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void calculateTotal() {
        try {
            String qtyText = qtyField.getText().trim();

            if (qtyText.isEmpty()) {
                totalLabel.setText("₹ 0.00");
                return;
            }

            int qty = Integer.parseInt(qtyText);
            double total = qty * price;

            totalLabel.setText("₹ " + String.format("%.2f", total));

        } catch (Exception e) {
            totalLabel.setText("₹ 0.00");
        }
    }

    void placeOrder() {
        try {
            String item = (String) itemBox.getSelectedItem();
            String qtyText = qtyField.getText().trim();

            if (qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter quantity!");
                return;
            }

            int qty = Integer.parseInt(qtyText);
            double total = qty * price;

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO orders(item_name, quantity, total_price) VALUES (?, ?, ?)");
            ps.setString(1, item);
            ps.setInt(2, qty);
            ps.setDouble(3, total);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Order Placed Successfully!");

            new BillFrame(item, qty, price, total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}