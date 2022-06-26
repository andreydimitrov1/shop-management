package ConvenienceStore;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Scanner;

public class Receipt {
    private int number;
    private Employee Employee;
    private int cashRegisterNumber;
    private Date dateIssued;
    private ItemInventory[] products;
    private BigDecimal totalPrice;

    public Receipt(Employee employee, ItemInventory[] products, int cashRegisterNumber) {
        this.number = ++Shop.receiptIssued;
        this.Employee = employee;
        this.products = products;
        this.dateIssued = new Date();
        this.cashRegisterNumber = cashRegisterNumber;

        this.totalPrice = new BigDecimal(0);
        this.totalPrice = this.totalPrice.setScale(2, RoundingMode.HALF_EVEN);
        this.setTotalPrice();

        try {
            this.addReceiptToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTotalPrice() {

        for (ItemInventory ItemInventory : this.products) {
            this.totalPrice = this.totalPrice.add(ItemInventory.getTotal());
        }
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    private void addReceiptToFile() throws IOException {
        try {
            
            FileWriter receiptFile = new FileWriter(this.getPathToReceiptFile());
            receiptFile.write("Cash register number: " + this.cashRegisterNumber + "\n");
            receiptFile.write("Receipt number: " + this.number + "\n");
            receiptFile.write("Employee: " + this.Employee.getName() + "\n");
            receiptFile.write("Issue Date and Time: " + this.dateIssued.toString() + "\n");
            receiptFile.write("\n");

            receiptFile.write("Items:\n");

            for (ItemInventory ItemInventory : this.products) {
                receiptFile.write(
                        ItemInventory.getItem().getName() + ", " +
                            "Item Price: " + ItemInventory.getItem().getPrice() + ", " +
                            "Item Count: " + ItemInventory.getInventory() + ", " +
                            "Price: " + ItemInventory.getTotal() + "\n"
                );
            }

            receiptFile.write("\n");

            receiptFile.write("Total: " + this.totalPrice + " \n");
            receiptFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showReceiptInTerminal() {
        try {
            FileReader receiptFile = new FileReader(this.getPathToReceiptFile());
            Scanner scanner = new Scanner(receiptFile);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getPathToReceiptFile() {
        return "reports/receipt_"+this.number+".txt";
    }
}
