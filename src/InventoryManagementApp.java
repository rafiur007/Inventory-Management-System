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