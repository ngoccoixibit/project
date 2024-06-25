package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Invoice {
    private int serial = 0;
    private String accountName;
    private String novelName;
    private int price;
    private Date dateOfPurchase;

    public Invoice() {}
    public Invoice(String accountName, String novelName, int price, Date dateOfPurchase) {
        serial++;
        this.accountName = accountName;
        this.novelName = novelName;
        this.price = price;
        this.dateOfPurchase = dateOfPurchase;
    }

    @Override
    public String toString() {
        return serial + ". " + "Novel [" + novelName + "] - Price: " + price + " VND - Date: " + getFormatDateOfPurchase();
    }

    public String getFormatDateOfPurchase(){
        return new SimpleDateFormat("dd-MM-yyyy").format(dateOfPurchase);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
