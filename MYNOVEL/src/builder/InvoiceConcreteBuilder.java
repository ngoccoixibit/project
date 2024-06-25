package builder;

import entity.Invoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceConcreteBuilder implements InvoiceBuilder {
    private String accountName;
    private String novelName;
    private int price;
    private Date dateOfPurchase;

    @Override
    public InvoiceBuilder setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    @Override
    public InvoiceBuilder setNovelName(String novelName) {
        this.novelName = novelName;
        return this;
    }

    @Override
    public InvoiceBuilder setPrice(int price) {
        this.price = price;
        return this;
    }

    @Override
    public InvoiceBuilder setDateOfPurchase(String dateOfPurchase) throws ParseException {
        this.dateOfPurchase = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfPurchase);
        return this;
    }

    @Override
    public Invoice build() {
        return new Invoice(accountName, novelName, price, dateOfPurchase);
    }
}
