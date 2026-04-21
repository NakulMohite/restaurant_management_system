
import javax.swing.*;
import java.awt.*;

public class BillFrame {

    public BillFrame(String item, int qty, double price, double total) {

        JFrame frame = new JFrame("Bill Receipt");

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Restaurant Bill");
        title.setBounds(120, 20, 200, 30);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea billArea = new JTextArea();
        billArea.setBounds(50, 70, 300, 200);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);

        // 🧾 Bill Content
     billArea.setText(
        "======== 🍽 RESTAURANT BILL ========\n\n" +
        "Item      : " + item + "\n" +
        "Quantity  : " + qty + "\n" +
        "Price     : ₹" + price + "\n" +
        "------------------------------\n" +
        "Total     : ₹" + total + "\n" +
        "------------------------------\n\n" +
        "❤️ Thank you for ordering!"
);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(140, 280, 100, 30);

        closeBtn.addActionListener(e -> frame.dispose());

        panel.add(title);
        panel.add(billArea);
        panel.add(closeBtn);

        frame.add(panel);
        frame.setSize(400, 380);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}