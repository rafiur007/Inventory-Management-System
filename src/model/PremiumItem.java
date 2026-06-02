package model;

public class PremiumItem extends Item {
    public PremiumItem(String id, String name, int quantity) {
        super(id, name, quantity);
    }

    @Override
    public String getType() {
        return "Premium";
    }
}