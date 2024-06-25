package builder;

import entity.Invoice;

import java.text.ParseException;
import java.util.Date;

public interface InvoiceBuilder {
    InvoiceBuilder setAccountName(String accountNameName);
    InvoiceBuilder setNovelName(String novelName);
    InvoiceBuilder setPrice(int price);
    InvoiceBuilder setDateOfPurchase(String dateOfPurchase) throws ParseException;
    Invoice build();
}
