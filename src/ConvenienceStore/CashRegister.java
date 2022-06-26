package ConvenienceStore;

import java.math.BigDecimal;

public class CashRegister {
    private Employee currentEmployee;
    final int cashRegisterNumber;

    public CashRegister(Employee Employee, int cashRegisterNumber) {
        this.currentEmployee = Employee;
        this.cashRegisterNumber = cashRegisterNumber;
    }

    public Employee getCurrentEmployee() {
        return this.currentEmployee;
    }


    public void changeEmployee(Employee Employee) {
        this.currentEmployee = Employee;
    }

    public synchronized Receipt payForProducts(ItemInventory[] products) {

        // New Receipt
        Receipt receipt = new Receipt(this.currentEmployee, products, cashRegisterNumber);

        // Update availability
        for (ItemInventory ItemInventory : products) {
            Shop.decreaseAvailabilityOfItems(ItemInventory);
        }

        // Add Employee Salary
        Shop.totalEmployeeSalary = Shop.totalEmployeeSalary.add(BigDecimal.valueOf(currentEmployee.getSalary()));

        // Delivery price per item
        Shop.deliveryPrice = Shop.deliveryPrice.add(Item.totalSpentOnInventory);

        // Add store revenue
        Shop.revenue = Shop.revenue.add(receipt.getTotalPrice());

        //Let Employee know if client have enough cash to proceed
        if (Shop.availableCash.doubleValue() < receipt.getTotalPrice().doubleValue()) {
            System.out.println("\nClient does not have enough cash to pay for the products\n");
        }

        //Put inside the if statement if you wish the item to not be sold
        return receipt;
    }
}
