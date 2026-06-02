package gui;

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

        JPanel displayPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Quantity", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        itemTable = new JTable(tableModel);
        displayPanel.add(new JScrollPane(itemTable), BorderLayout.CENTER);

        JButton btnDelete = new JButton("Delete Selected Row");
        displayPanel.add(btnDelete, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.WEST);
        add(displayPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText().trim();
                    String name = txtName.getText().trim();
                    String type = (String) cmbType.getSelectedItem();
                    String qtyText = txtQty.getText().trim();

                    manager.addItem(id, name, type, qtyText);
                    refreshTable();
                    clearForm();

                } catch (InventoryException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = itemTable.getSelectedRow();
                    manager.deleteItem(selectedRow);
                    refreshTable();

                } catch (InventoryException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Selection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : manager.getDatabase()) {
            Object[] rowData = {
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getType()
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementApp().setVisible(true));
    }
}