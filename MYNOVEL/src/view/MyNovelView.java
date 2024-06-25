package view;

import entity.Admin;
import entity.User;
import exception.InvalidChoiceException;
import service.AccountService;
import service.CartService;
import service.InvoiceService;
import service.NovelService;
import util.Input;

public class MyNovelView {
    private static final MyNovelView MY_NOVEL_VIEW = new MyNovelView();

    private MyNovelView() {
    }

    public static MyNovelView getInstance() {
        return MY_NOVEL_VIEW;
    }

    private static final AccountService ACCOUNT_SERVICE = AccountService.getInstance();
    private static final NovelService NOVEL_SERVICE = NovelService.getInstance();
    private static final CartService CART_SERVICE = CartService.getInstance();
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();

    public void displayMainMenu() {
        do {
            try {
                System.out.print("""
                        ==> [WELCOME TO MYNOVEL]
                            [1] Login
                            [2] SignUp
                            [3] Exit
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> {
                        ACCOUNT_SERVICE.logIn();
                        if (ACCOUNT_SERVICE.getCurrentAccount() instanceof User) {
                            displayUserMenu();
                        } else if (ACCOUNT_SERVICE.getCurrentAccount() instanceof Admin) {
                            displayAdminMenu();
                        }
                    }
                    case 2 -> ACCOUNT_SERVICE.signIn();
                    case 3 -> System.exit(0);
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayUserMenu() {
        do {
            try {
                System.out.print("""
                        \n==> [USER MENU]
                            [1] Homepage
                            [2] Your Novel
                            [3] Your account
                            [4] Logout
                            [5] Exit
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> displayUserHomepage();
                    case 2 -> displayYourNovel();
                    case 3 -> displayUserAccount();
                    case 4 -> displayMainMenu();
                    case 5 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayUserHomepage() {
        do {
            try {
                NOVEL_SERVICE.displayPublicNovelList();
                System.out.print("""
                        \n==> [HOME PAGE]
                            [1] Search novel
                            [2] Sort novel
                            [3] Add to your cart
                            [4] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> NOVEL_SERVICE.searchNovel();
                    case 2 -> NOVEL_SERVICE.sortNovel();
                    case 3 -> NOVEL_SERVICE.displayNovelDetail();
                    case 4 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayYourNovel() {
        do {
            try {
                boolean isExistNovel = NOVEL_SERVICE.displayYourNovelList();
                if (isExistNovel) {
                    System.out.print("""
                            \n==> [MY NOVEL]
                                [1] Edit novel
                                [2] Delete novel
                                [3] Create novel
                                [4] Profit
                                [5] Return
                            """);
                    int choice = Input.scanIntegerLine("Enter your choice: ");
                    switch (choice) {
                        case 1 -> NOVEL_SERVICE.editYourNovel();
                        case 2 -> NOVEL_SERVICE.deleteYourNovel();
                        case 3 -> NOVEL_SERVICE.createYourNovel();
                        case 4 -> INVOICE_SERVICE.novelProfits();
                        case 5 -> {
                            return;
                        }
                        default -> throw new InvalidChoiceException("There isn't such a choice!");
                    }
                } else {
                    System.out.println("""
                            This function is unavailable because you don't have any novels yet!
                            Let's create our first novel to begin!
                                [Y] Create new novel
                                [Any key] Return
                            """);
                    String choice = Input.scanStringLine("Enter your choice: ");
                    if (choice.equalsIgnoreCase("Y")) {
                        NOVEL_SERVICE.createYourNovel();
                    } else return;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayUserAccount() {
        do {
            try {
                System.out.print("""
                        \n==> [YOUR ACCOUNT]
                            [1] Information
                            [2] History
                            [3] Your cart
                            [4] Logout
                            [5] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> ACCOUNT_SERVICE.editYourInformation();
                    case 2 -> INVOICE_SERVICE.displayUserInvoiceHistory();
                    case 3 -> CART_SERVICE.displayCartMenu();
                    case 4 -> displayMainMenu();
                    case 5 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayAdminMenu() {
        do {
            try {
                System.out.print("""
                        \n==> [ADMIN MENU]
                            [1] Homepage
                            [2] History (chưa làm)
                            [3] Your account
                            [4] Log out
                            [5] Exit
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> displayAdminHomepage();
                    case 2 -> displayAdminHistory();
                    case 3 -> displayAdminAccount();
                    case 4 -> displayMainMenu();
                    case 5 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayAdminHomepage() {
        do {
            try {
                System.out.print("""
                        \n==> [HOME PAGE]
                            [1] Novel page
                            [2] User page
                            [3] Back
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> NOVEL_SERVICE.displayNovelPage();
                    case 2 -> ACCOUNT_SERVICE.displayUserPage();
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayAdminHistory() {
    }

    public void displayAdminAccount() {
        do {
            try {
                System.out.print("""
                        \n==> [YOUR ACCOUNT]
                            [1] Information
                            [2] Logout
                            [3] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> ACCOUNT_SERVICE.editYourInformation();
                    case 2 -> displayMainMenu();
                    case 3 -> {
                        return;
                    }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }
}
