package ConvenienceStore;

import java.util.concurrent.Callable;

public class ItemsSell implements Callable<Receipt> {
    private CashRegister cashRegister;
    private ItemInventory[] ItemInventory;

    public ItemsSell(CashRegister cashRegister, ItemInventory[] ItemInventory) {
        this.cashRegister = cashRegister;
        this.ItemInventory = ItemInventory;
    }

    @Override
    public Receipt call() throws Exception {
        return cashRegister.payForProducts(ItemInventory);
    }
}
