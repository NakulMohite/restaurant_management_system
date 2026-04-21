import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OrderHistory {

    public OrderHistory() {

        JFrame frame = new JFrame("Order History");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(24, 24, 28)); // dark theme

        // 🔥 Title
        JLabel title = new JLabel("🧾 Order History", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 🔥 Table Model
        String[] cols = {"ID", "Item", "Quantity", "Total ₹"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        JTable table = new JTable(model);

        // 🔥 Table Styling
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(new Color(35, 35, 45));
        table.setForeground(Color.WHITE);

        // Header style
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(255, 87, 34)); // orange
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🔽 Fetch data from DB
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM orders");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        rs.getInt("quantity"),
                        "₹ " + String.format("%.2f", rs.getDouble("total_price"))
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(520, 360);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}