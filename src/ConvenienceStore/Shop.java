package ConvenienceStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

import static ConvenienceStore.Item.daysBetween;

public class Shop {
    public static List<ItemInventory> currentlyAvailableItems = new ArrayList<ItemInventory>();
    public static List<Employee> Employees = new ArrayList<Employee>();
    public static int receiptIssued = 0;
    public static BigDecimal revenue = new BigDecimal(0);
    public static BigDecimal deliveryPrice = new BigDecimal(0);
    public static BigDecimal totalEmployeeSalary = new BigDecimal(0);
    public static BigDecimal availableCash = new BigDecimal(0);

    static {
        Shop.revenue = Shop.revenue.setScale(2, RoundingMode.HALF_EVEN);
        Shop.deliveryPrice = Shop.deliveryPrice.setScale(2, RoundingMode.HALF_EVEN);
        Shop.totalEmployeeSalary = Shop.totalEmployeeSalary.setScale(2, RoundingMode.HALF_EVEN);
    }

        public static void AddItemsToInventory(ItemInventory itemInventoryToAdd) {
        for (ItemInventory currentAvailableProduct: Shop.currentlyAvailableItems) {
            if (currentAvailableProduct.getItem().getId() != itemInventoryToAdd.getItem().getId()) {
                continue;
            }

            currentAvailableProduct.increaseCount(itemInventoryToAdd.getInventory());
            return;
        }

        Shop.currentlyAvailableItems.add(itemInventoryToAdd);
    }

    public static void decreaseAvailabilityOfItems(ItemInventory ItemInventory) {
        // Also remove all expired products so they cannot be sold
        for (ItemInventory availableProduct: Shop.currentlyAvailableItems) {
            if (availableProduct.getItem().getId() != ItemInventory.getItem().getId()) {
                continue;
            }

            availableProduct.decreaseCount(ItemInventory.getInventory());
        }

        // Also remove all expired products so they cannot be sold
        for (ItemInventory availableProduct: Shop.currentlyAvailableItems) {
                if (daysBetween(Calendar.getInstance().getTime(), ItemInventory.getItem().getExpiryDate()) < 1) {
                continue;
            }
            availableProduct.decreaseCount(ItemInventory.getInventory());
        }

    }

    public static int getNumberOfAvailableItems(Item product) {
        for (ItemInventory availableProduct: Shop.currentlyAvailableItems) {
            if (availableProduct.getItem().getId() != product.getId()) {
                continue;
            }

            return availableProduct.getInventory();
        }

        return 0;
    }

    public static Receipt buyProducts(CashRegister cashRegister, ItemInventory[] products, BigDecimal availableCash) throws Exception {

        Shop.availableCash = availableCash;

        for (ItemInventory ItemInventory : products) {
            if (!Shop.checkForItemAvailability(ItemInventory)) {
                throw Shop.getExceptionItemNotAvailable(ItemInventory);
            }
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ItemsSell task = new ItemsSell(cashRegister, products);
        Future<Receipt> receiptIssued = executorService.submit(task);
        return receiptIssued.get();
    }

    private static Exception getExceptionItemNotAvailable(ItemInventory itemInventory) {
        int requestedCount = itemInventory.getInventory();
        int availableCount = Shop.getNumberOfAvailableItems(itemInventory.getItem());
        int difference = requestedCount - availableCount;

        return new ExceptionItemNotAvailable("There is "+difference+ "not enough of " + itemInventory.getItem().getName()+".");
    }
    private static boolean checkForItemAvailability(ItemInventory ItemInventory) {
        for (ItemInventory availableProduct: Shop.currentlyAvailableItems) {
            if (availableProduct.getItem().getId() != ItemInventory.getItem().getId()) {
                continue;
            }

            if (availableProduct.getInventory() >= ItemInventory.getInventory()) {
                return true;
            }

            break;
        }

        return false;
    }

    public static BigDecimal calculateProfit() {
        return BigDecimal.valueOf((revenue.doubleValue() - deliveryPrice.doubleValue()) - totalEmployeeSalary.doubleValue());//return BigDecimal.valueOf((price.doubleValue() * (price.doubleValue() * 0.40)));
    }

}
