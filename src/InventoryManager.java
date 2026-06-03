manager package - package manager;

import model.Item;
import model.StandardItem;
import model.PremiumItem;
import exception.InventoryException;
import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<Item> database = new ArrayList<>();

    public void addItem(String id, String name, String type, String qtyText) throws InventoryException {
        if (id.isEmpty() || name.isEmpty()) {
            throw new InventoryException("Fields cannot be empty!");
        }

        int qty;
        try {
            qty = Integer.parseInt(qtyText.trim());
        } catch (NumberFormatException nfe) {
            throw new InventoryException("Quantity must be a valid number!");
        }

        if (qty < 0) {
            throw new InventoryException("Quantity cannot be negative!");
        }

        for (Item item : database) {
            if (item.getId().equalsIgnoreCase(id)) {
                throw new InventoryException("Item ID already exists!");
            }
        }

        Item newItem;
        if (type.equals("Standard")) {
            newItem = new StandardItem(id, name, qty);
        } else {
            newItem = new PremiumItem(id, name, qty);
        }

        database.add(newItem);
    }

    public void deleteItem(int index) throws InventoryException {
        if (index == -1) {
            throw new InventoryException("Please select a row to delete!");
        }

        database.remove(index);
    }

    public ArrayList<Item> getDatabase() {
        return database;
    }
}
