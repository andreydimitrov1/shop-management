package ConvenienceStore;

import java.math.BigDecimal;

public class ItemInventory {
    private Item product;
    private int count;

    public ItemInventory(Item product, int count) {
        this.product = product;
        this.count = count;
    }

    public Item getItem() {
        return this.product;
    }

    public int getInventory() {
        return this.count;
    }

    public void increaseCount(int increaseInventory) {
        this.count += increaseInventory;
    }

    public void decreaseCount(int decreaseInventory) {
        this.count -= decreaseInventory;
    }

    public BigDecimal getTotal() {
        return this.getItem().getPrice().multiply(new BigDecimal(this.getInventory()));
    }
}
