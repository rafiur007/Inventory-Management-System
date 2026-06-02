package model;

public class StandardItem extends Item {
    public StandardItem(String id, String name, int quantity) {
        super(id, name, quantity);
    }

    @Override
    public String getType() {
        return "Standard";
    }
}