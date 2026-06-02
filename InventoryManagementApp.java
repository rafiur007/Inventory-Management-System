import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// =========================================================================
// TASK 2: CUSTOM EXCEPTION (Easy Exception Handling)
// =========================================================================
class InventoryException extends Exception {
    public InventoryException(String message) {
        super(message);
    }
}

// =========================================================================
// TASK 1: CORE CLASS HIERARCHY (Simplified OOP)
// =========================================================================

// ABSTRACTION: Abstract base class
abstract class Item {
    // ENCAPSULATION: Private variables
    private String id;
    private String name;
    private int quantity;

    public Item(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // ENCAPSULATION: Public getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    // POLYMORPHISM: Abstract method to be overridden by child classes
    public abstract String getType();
}

// INHERITANCE: Standard Item Subclass
class StandardItem extends Item {
    public StandardItem(String id, String name, int quantity) {
        super(id, name, quantity); // Calls parent constructor
    }

    @Override
    public String getType() {
        return "Standard"; // POLYMORPHISM: Specific behavior
    }
}

// INHERITANCE: Premium Item Subclass
class PremiumItem extends Item {
    public PremiumItem(String id, String name, int quantity) {
        super(id, name, quantity); // Calls parent constructor
    }

    @Override
    public String getType() {
        return "Premium"; // POLYMORPHISM: Specific behavior
    }
}

// =========================================================================
// TASK 3: GUI FRONTEND & BACKEND COMBINED (Java Swing)
// =========================================================================
public class InventoryManagementApp extends JFrame {
    // Backend Database Storage Layer
    private ArrayList<Item> database = new ArrayList<>();

    // GUI Visual Components
    private JTextField txtId, txtName, txtQty;
    private JComboBox<String> cmbType;
    private DefaultTableModel tableModel;
    private JTable itemTable;

    public InventoryManagementApp() {
        // Basic Window Setup
        setTitle("Simple Inventory System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL 1: Input Form (Left Side) ---
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

        // --- PANEL 2: Table Display (Right Side) ---
        JPanel displayPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Quantity", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        itemTable = new JTable(tableModel);
        displayPanel.add(new JScrollPane(itemTable), BorderLayout.CENTER);

        JButton btnDelete = new JButton("Delete Selected Row");
        displayPanel.add(btnDelete, BorderLayout.SOUTH);

        // Add both main panels to window frame
        add(inputPanel, BorderLayout.WEST);
        add(displayPanel, BorderLayout.CENTER);

        // --- TASK 2 & 3: BUTTON ACTIONS & ERROR HANDLING ---

        // ADD BUTTON ACTION
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Try-Catch block handles interface and rule errors gracefully
                try {
                    String id = txtId.getText().trim();
                    String name = txtName.getText().trim();
                    String type = (String) cmbType.getSelectedItem();

                    // 1. Validation Rule: Check for empty text inputs
                    if (id.isEmpty() || name.isEmpty()) {
                        throw new InventoryException("Fields cannot be empty!");
                    }

                    // 2. Validation Rule: Check for number formatting numbers
                    int qty;
                    try {
                        qty = Integer.parseInt(txtQty.getText().trim());
                    } catch (NumberFormatException nfe) {
                        throw new InventoryException("Quantity must be a valid number!");
                    }

                    // 3. Validation Rule: Check for negative values
                    if (qty < 0) {
                        throw new InventoryException("Quantity cannot be negative!");
                    }

                    // 4. Validation Rule: Check for duplicates
                    for (Item item : database) {
                        if (item.getId().equalsIgnoreCase(id)) {
                            throw new InventoryException("Item ID already exists!");
                        }
                    }

                    // Create object using polymorphism
                    Item newItem;
                    if (type.equals("Standard")) {
                        newItem = new StandardItem(id, name, qty);
                    } else {
                        newItem = new PremiumItem(id, name, qty);
                    }

                    // Add to system storage array list
                    database.add(newItem);

                    // Refresh visual layout grid
                    refreshTable();
                    clearForm();

                } catch (InventoryException ex) {
                    // Display clean structured error message to screen
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // DELETE BUTTON ACTION
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = itemTable.getSelectedRow();

                try {
                    if (selectedRow == -1) {
                        throw new InventoryException("Please select a row to delete!");
                    }

                    // Remove from list array using index reference mapping
                    database.remove(selectedRow);
                    refreshTable();

                } catch (InventoryException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Selection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Simple table helper method
    private void refreshTable() {
        tableModel.setRowCount(0); // clear UI rows
        for (Item item : database) {
            Object[] rowData = {
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getType() // Polymorphism call handles child text dynamically
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
    }

    // MAIN PROGRAM RUNNER
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementApp().setVisible(true));
    }
}