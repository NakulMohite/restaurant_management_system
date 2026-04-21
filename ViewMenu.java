import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMenu {

    public ViewMenu() {

        JFrame frame = new JFrame("Menu");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(24, 24, 28)); // 🔥 dark theme

        // 🔥 Title
        JLabel title = new JLabel("📋 Menu", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 🔥 Table Model
        String[] cols = {"ID", "Item", "Price ₹"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        JTable table = new JTable(model);

        // 🔥 Table Styling
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(new Color(35, 35, 45));
        table.setForeground(Color.WHITE);

        // Header style
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(255, 87, 34));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🔽 Fetch data
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM menu");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("item_name"),
                        "₹ " + String.format("%.2f", rs.getDouble("price"))
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(450, 320);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}