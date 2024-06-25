package service;

import entity.Account;
import entity.Invoice;
import entity.Novel;
import exception.InvalidChoiceException;
import util.Input;
import util.ReadFile;
import java.util.ArrayList;
import java.util.List;

import static service.NovelService.NOVEL_LIST;

public class InvoiceService {
    private static final InvoiceService INVOICE_SERVICE = new InvoiceService();

    public static InvoiceService getInstance() {
        return INVOICE_SERVICE;
    }

    private InvoiceService() {
    }

    private static final List<Invoice> INVOICE_LIST = new ArrayList<>();
    private static final NovelService NOVEL_SERVICE = NovelService.getInstance();
    private static final AccountService ACCOUNT_SERVICE = AccountService.getInstance();
    private static final CartService CART_SERVICE = CartService.getInstance();
    private Account currentAccount;

    static {
        List<Invoice> invoiceList = ReadFile.readFileInvoice("src\\data\\InvoiceHistory.csv");
        INVOICE_LIST.addAll(invoiceList);
    }

    public void displayUserInvoiceHistory() {
        do {
            try {
                currentAccount = ACCOUNT_SERVICE.getCurrentAccount();
                System.out.print("""
                        \n==> [HISTORY]
                            [1] Purchase history
                            [2] Sales history
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> novelPurchaseHistory();
                    case 2 -> novelSellingHistory();
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void novelPurchaseHistory() {
        currentAccount = ACCOUNT_SERVICE.getCurrentAccount();
        do {
            try {
                System.out.print("""
                        \nHow to view?
                            [1] All
                            [2] By month
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        System.out.println("\n==> [PURCHASE HISTORY]");
                        if (CART_SERVICE.checkPaidCart()) {
                            INVOICE_LIST.clear();
                            List<Invoice> invoiceList = ReadFile.readFileInvoice("src\\data\\InvoiceHistory.csv");
                            INVOICE_LIST.addAll(invoiceList);
                        }
                        INVOICE_LIST.stream().filter(Invoice -> Invoice.getAccountName().equalsIgnoreCase(currentAccount.getName())).forEach(System.out::println);
                    }
                    case 2 -> {
                        boolean monthExistence = false;
                        if (CART_SERVICE.checkPaidCart()) {
                            INVOICE_LIST.clear();
                            List<Invoice> invoiceList = ReadFile.readFileInvoice("src\\data\\InvoiceHistory.csv");
                            INVOICE_LIST.addAll(invoiceList);
                        }
                        int monthOfPurchase = Input.scanIntegerLine("Enter month: ") - 1;
                        System.out.println("\n==> [NOVEL PURCHASE HISTORY]");
                        for (Invoice invoice : INVOICE_LIST) {
                            if (invoice.getAccountName().equalsIgnoreCase(currentAccount.getName())) {
                                if (monthOfPurchase == invoice.getDateOfPurchase().getMonth()) {
                                    System.out.println(invoice);
                                    monthExistence = true;
                                }
                            }
                        }
                        if (!monthExistence) {
                            throw new InvalidChoiceException("Month not found!");
                        }
                    }
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void novelSellingHistory() {
        currentAccount = ACCOUNT_SERVICE.getCurrentAccount();
        do {
            try {
                System.out.print("""
                        \nHow to view?
                            [1] All
                            [2] By month
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        System.out.println("\n==> [NOVEL SELLING HISTORY]");
                        if (CART_SERVICE.checkPaidCart()) {
                            INVOICE_LIST.clear();
                            List<Invoice> invoiceList = ReadFile.readFileInvoice("src\\data\\invoiceHistory.csv");
                            INVOICE_LIST.addAll(invoiceList);
                        }
                        for (Invoice invoice : INVOICE_LIST) {
                            for (Novel novel : NOVEL_LIST) {
                                if (novel.getNovelName().equals(invoice.getNovelName()) && novel.getAuthor().equals(currentAccount.getName())) {
                                    System.out.println(invoice);
                                }
                            }
                        }
                    }
                    case 2 -> {
                        boolean monthExistence = false;
                        if (CART_SERVICE.checkPaidCart()) {
                            INVOICE_LIST.clear();
                            List<Invoice> invoiceList = ReadFile.readFileInvoice("src\\data\\invoiceHistory.csv");
                            INVOICE_LIST.addAll(invoiceList);
                        }
                        int monthOfPurchase = Input.scanIntegerLine("Enter month: ") - 1;
                        for (Invoice invoice : INVOICE_LIST) {
                            if (monthOfPurchase == invoice.getDateOfPurchase().getMonth()) {
                                for (Novel novel : NOVEL_LIST) {
                                    if (novel.getNovelName().equals(invoice.getNovelName()) && novel.getAuthor().equals(currentAccount.getName())) {
                                        System.out.println(invoice);
                                    }
                                }
                                monthExistence = true;
                            }
                        }
                        if (!monthExistence) {
                            throw new InvalidChoiceException("Month not found!");
                        }
                    }
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void novelProfits() {
        do {
            try {
                System.out.println("""
                        You want to view...
                            [1] Income
                            [2] Profit
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                int totalIncome = 0;
                double profitRate = 0.4;
                switch (choice) {
                    case 1 -> {
                        boolean monthIncomeExist = false;
                        int saleMonthCompareToFormatGetMonth = Input.scanIntegerLine("Enter month: ") - 1;
                        for (Invoice invoice : INVOICE_LIST) {
                            if (saleMonthCompareToFormatGetMonth == invoice.getDateOfPurchase().getMonth()) {
                                System.out.println(invoice);
                                totalIncome = totalIncome + invoice.getPrice();
                                monthIncomeExist = true;
                            }
                        }
                        if (!monthIncomeExist) {
                            throw new InvalidChoiceException("Month not found!");
                        } else {
                            System.out.println("Total income: " + totalIncome + "₫");
                        }
                    }
                    case 2 -> {
                        boolean monthProfitExist = false;
                        int profitMonthCompareToFormatGetMonth = Input.scanIntegerLine("Enter month: ") - 1;
                        for (Invoice invoice : INVOICE_LIST) {
                            if (profitMonthCompareToFormatGetMonth == invoice.getDateOfPurchase().getMonth()) {
                                System.out.println(invoice);
                                totalIncome = totalIncome + invoice.getPrice();
                                monthProfitExist = true;
                            }
                        }
                        if (!monthProfitExist) {
                            throw new InvalidChoiceException("Month not found!");
                        } else {
                            System.out.println("Total profit: " + (int) (totalIncome * profitRate) + "₫");
                        }
                    }
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}