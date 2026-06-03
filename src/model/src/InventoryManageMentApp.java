import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import manager.InventoryManager;
import model.Item;
import exception.InventoryException;

public class InventoryManagementApp extends JFrame {

    private InventoryManager manager = new InventoryManager();

    private JTextField txtId, txtName, txtQty;
    private JComboBox<String> cmbType;
    private DefaultTableModel tableModel;
    private JTable itemTable;

    public InventoryManagementApp() {

        setTitle("Simple Inventory System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        inputPanel.add(new JLabel(" Type:"));
        cmbType = new JComboBox<>(new String[]{"Standard", "Premium"});
        inputPanel.add(cmbType);

        inputPanel.add(new JLabel(" Item ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel(" Item Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel(" Quantity:"));
        txtQty = new JTextField();
        inputPanel.add(txtQty);

        JButton btnAdd = new JButton("Add Item");
        inputPanel.add(btnAdd);
    }
}
