package service;

import entity.Novel;
import exception.InvalidChoiceException;
import util.Input;
import util.WriteFile;
import view.MyNovelView;

import java.util.ArrayList;
import java.util.List;

import static service.NovelService.NOVEL_LIST;

public class CartService {
    private static final CartService CART_SERVICE = new CartService();

    private CartService() {}

    public static CartService getInstance() {
        return CART_SERVICE;
    }

    private static final AccountService accountService = AccountService.getInstance();
    private static final MyNovelView myNovelView = MyNovelView.getInstance();
    private static final List<Novel> NOVEL_CART = new ArrayList<>();

    public boolean isPaid = false;

    public boolean checkPaidCart() {
        return isPaid;
    }

    public int displayCartList() {
        int totalPayment = 0;
        try {
            System.out.println("\n==> [CART LIST]" +
                               "\n\t[Total novel: " + NOVEL_CART.size() + "]" +
                               "\n------------------------------------------------------------------------------------->");
            for (Novel novel : NOVEL_CART) {
                System.out.println(novel.getNovelID() + " - [" + novel.getNovelName() + "]" +
                                   " - Price: " + novel.getPrice());
                totalPayment = totalPayment + novel.getPrice();
            }
            System.out.println("------------------------------------------------------------------------------------->");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return totalPayment;
    }

    public void displayCartMenu() {
        do {
            try {
                System.out.print("""
                        \n==> [YOUR CART]
                            [1] Add novel to cart
                            [2] Checkout your cart
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> myNovelView.displayUserHomepage();
                    case 2 -> checkCart();
                    case 3 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void checkCart() {
        try {
            if (NOVEL_CART.isEmpty()) {
                System.out.println("Your cart is empty!");
                String choice = Input.scanStringLine("Do you want to add novel to your cart? (Y -> continue / Any key -> Return): ");
                if (choice.equalsIgnoreCase("Y")) {
                    myNovelView.displayUserHomepage();
                }
            } else {
                int totalPayment = displayCartList();
                System.out.println("Total payment: " + totalPayment);
                String confirm = Input.scanStringLine("Do you want to checkout cart? (Y -> continue / Any key -> Return): ");
                if (confirm.equalsIgnoreCase("Y")) {
                    checkoutCart();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void checkoutCart() {
            try {
                System.out.println("""
                        \n*******************
                        *******************
                        *******************
                        *******************
                        *******************
                        *******************
                        *******************
                        """);
                String choice = Input.scanStringLine("Confirm (Y -> continue / Any key -> Return): ");
                if (choice.equalsIgnoreCase("Y")) {
                    for (Novel novelCart : NOVEL_CART) {
                        for (Novel novel : NOVEL_LIST) {
                            if (novelCart.getNovelID() == (novel.getNovelID())) {
                                novel.setNumberOfNovelPurchase();
                                isPaid = true;
                            }
                        }
                    }
                    WriteFile.WriteFileInvoiceHistory("src\\data\\InvoiceHistory.csv", accountService.getCurrentAccount(), NOVEL_CART);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
    public void addCart(int ID) {
        if (NOVEL_CART.isEmpty()) {
            for (Novel novel : NOVEL_LIST) {
                if (novel.getNovelID() == ID) {
                    NOVEL_CART.add(novel);
                    System.out.println("Add successfully!");
                }
            }
        } else {
            for (Novel cart : NOVEL_CART) {
                for (Novel novel : NOVEL_LIST) {
                    if (cart.getNovelID() != novel.getNovelID()) {
                        if (novel.getNovelID() == ID) {
                            NOVEL_CART.add(novel);
                            System.out.println("Add successfully!");
                        }
                    } else {
                        throw new RuntimeException("This novel is already in your cart!\n");
                    }
                }
            }
        }
    }
}
