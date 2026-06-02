import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Exception handling
class InventoryException extends Exception {
    public InventoryException(String message) {
        super(message);
    }
}

// ABSTRACTION---BASE class
abstract class Item {
    // ENCAPSULATION---Private
    private String id;
    private String name;
    private int quantity;

    public Item(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // ENCAPSULATION---Public getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    // POLYMORPHISM---Abstract override by child
    public abstract String getType();
}

// INHERITANCE---standard item subclass
class StandardItem extends Item {
    public StandardItem(String id, String name, int quantity) {
        super(id, name, quantity); //parent constructor call
    }

    @Override
    public String getType() {
        return "Standard"; 
    }
}

// INHERITANCE---premium item subclass 
class PremiumItem extends Item {
    public PremiumItem(String id, String name, int quantity) {
        super(id, name, quantity); //parent constructor call
    }

    @Override
    public String getType() {
        return "Premium";
    }
}

//GUI
public class InventoryManagementApp extends JFrame {
    // backend database storage layer
    private ArrayList<Item> database = new ArrayList<>();

    // visual components
    private JTextField txtId, txtName, txtQty;
    private JComboBox<String> cmbType;
    private DefaultTableModel tableModel;
    private JTable itemTable;

    public InventoryManagementApp() {
        // Window create
        setTitle("Simple Inventory System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // left side input form
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

        // right side table layout 
        JPanel displayPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Quantity", "Type"};
        tableModel = new DefaultTableModel(columns, 0);
        itemTable = new JTable(tableModel);
        displayPanel.add(new JScrollPane(itemTable), BorderLayout.CENTER);

        JButton btnDelete = new JButton("Delete Selected Row");
        displayPanel.add(btnDelete, BorderLayout.SOUTH);

        // adding panels to frame 
        add(inputPanel, BorderLayout.WEST);
        add(displayPanel, BorderLayout.CENTER);

        // button actions and validations 

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText().trim();
                    String name = txtName.getText().trim();
                    String type = (String) cmbType.getSelectedItem();

                    if (id.isEmpty() || name.isEmpty()) {
                        throw new InventoryException("Fields cannot be empty!");
                    }

                    int qty;
                    try {
                        qty = Integer.parseInt(txtQty.getText().trim());
                    } catch (NumberFormatException nfe) {
                        throw new InventoryException("Quantity must be a valid number!");
                    }

                    if (qty < 0) {
                        throw new InventoryException("Quantity cannot be negative!");
                    }

                    // checking for duplicates
                    for (Item item : database) {
                        if (item.getId().equalsIgnoreCase(id)) {
                            throw new InventoryException("Item ID already exists!");
                        }
                    }

                    // create object 
                    Item newItem;
                    if (type.equals("Standard")) {
                        newItem = new StandardItem(id, name, qty);
                    } else {
                        newItem = new PremiumItem(id, name, qty);
                    }

                    // add to system storage array list
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
        tableModel.setRowCount(0); 
        for (Item item : database) {
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
