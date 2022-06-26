package ConvenienceStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Item {
    private String id;
    private String name;
    private String category;
    private BigDecimal price;
    private Date expiryDate;
    public static BigDecimal totalSpentOnInventory = new BigDecimal(0);


    public Item(String name, double price, Date expiryDate, String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
        this.price = new BigDecimal(Double.toString(price));
        this.price = this.price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public String getId() {
        return this.id;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public BigDecimal getDeliveryPrice() {
        return BigDecimal.valueOf((price.doubleValue() * (price.doubleValue() * 0.40)));
    }

    public BigDecimal getSoldPrice() {
        return BigDecimal.valueOf((price.doubleValue() + (price.doubleValue() * (Objects.equals(getCategory(), "grocery") ? 0.40 : 0.70))));
    }

    public BigDecimal getPrice() {
        if (daysBetween(Calendar.getInstance().getTime(), expiryDate) < 30) {
            return BigDecimal.valueOf((getSoldPrice().doubleValue() - (getSoldPrice().doubleValue() * 0.15)));
        } else {
            return getSoldPrice();
        }
    }
}
