import ConvenienceStore.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {

        //Initialize employees and cash register, add items in the shop

        Employee cashierAndrey = new Employee("Andrey Dimitrov", 5300);
        Employee cashierIvana = new Employee("Ivana Georgieva", 1200);

        CashRegister cashRegister1 = new CashRegister(cashierAndrey, 1);
        CashRegister cashRegister2 = new CashRegister(cashierIvana, 2);

        Item cheese = new Item("Cheese 1kg", 14.50, new GregorianCalendar(2023, Calendar.DECEMBER, 16).getTime(), "grocery");
        Item deodorant = new Item("Deodorant old spice", 2.40, new GregorianCalendar(2022, Calendar.AUGUST, 7).getTime(), "notGrocery");
        Item energyDrink = new Item("Energy Drink Red Bull", 2.60, new GregorianCalendar(2022, Calendar.NOVEMBER, 4).getTime(), "grocery");
        Item pizza = new Item("frozen Pizza 600g", 6.40, new GregorianCalendar(2022, Calendar.JULY, 21).getTime(), "grocery");
        Item bread = new Item("Bread", 1.60, new GregorianCalendar(2022, Calendar.SEPTEMBER, 30).getTime(), "grocery");

        Shop.AddItemsToInventory(new ItemInventory(cheese, 50));
        Shop.AddItemsToInventory(new ItemInventory(bread, 20));
        Shop.AddItemsToInventory(new ItemInventory(deodorant, 100));
        Shop.AddItemsToInventory(new ItemInventory(energyDrink, 30));
        Shop.AddItemsToInventory(new ItemInventory(pizza, 15));


        try {
            Receipt receipt = Shop.buyProducts(cashRegister1, new ItemInventory[]{
                new ItemInventory(cheese, 2),
                new ItemInventory(bread, 1),
                new ItemInventory(deodorant, 5),
                new ItemInventory(energyDrink, 1),
            }, BigDecimal.valueOf(150.5));

            receipt.showReceiptInTerminal();

            Receipt receipt2 = Shop.buyProducts(cashRegister2, new ItemInventory[]{
                new ItemInventory(cheese, 2),
                new ItemInventory(bread, 1),
                new ItemInventory(pizza, 1),
                new ItemInventory(energyDrink, 1),
            }, BigDecimal.valueOf(30.2));

            receipt2.showReceiptInTerminal();
        } catch (ExceptionItemNotAvailable e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}