package view;

import entity.Admin;
import entity.User;
import exception.InvalidChoiceException;
import service.AccountService;
import service.NovelService;
import util.Input;

public class MyNovelView {
    private static final MyNovelView myNovelView = new MyNovelView();

    public static MyNovelView getInstance() { return myNovelView; }

    AccountService accountService = AccountService.getInstance();
    NovelService novelService = NovelService.getInstance();

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
                        accountService.logIn();
                        if (accountService.getCurrentAccount() instanceof User) {
                            displayUserMenu();
                        } else if (accountService.getCurrentAccount() instanceof Admin) {
                            displayAdminMenu();
                        }
                    }
                    case 2 -> accountService.signIn();
                    case 3 -> System.exit(0);
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayUserHomepage() {
        do {
            try {
                novelService.displayPublicNovelList();
                System.out.print("""
                        \n==> [HOME PAGE]
                            [1] Search novel
                            [2] Sort novel
                            [3] History (chưa làm)
                            [4] Buy novel (chưa làm)
                            [5] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> novelService.searchNovel();
                    case 2 -> novelService.sortNovel();
                    case 5 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayYourNovel() {
        do {
            try {
                novelService.displayYourNovelList();
                System.out.print("""
                        \n==> [MY NOVEL]
                            [1] Edit novel
                            [2] Delete novel
                            [3] Create novel
                            [4] Profit (chưa làm)
                            [5] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> novelService.editYourNovel();
                    case 2 -> novelService.deleteYourNovel();
                    case 3 -> novelService.createYourNovel();
                    case 5 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayUserAccount() {
        do {
            try {
                System.out.print("""
                        \n==> [YOUR ACCOUNT]
                            [1] Information
                            [2] History (chưa làm)
                            [3] Cart (chưa làm)
                            [3] Logout
                            [4] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> accountService.displayYourInformation();
                    case 3 -> displayMainMenu();
                    case 4 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayAdminHomepage() {
        do {
            try {
                novelService.displayPublicNovelList();
                System.out.print("""
                        \n==> [HOME PAGE] (chưa làm)
                            [1] Search novel
                            [2] Sort novel
                            [3] History
                            [4] Profit
                            [5] Return
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 5 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayUserEditing() {
        do {
            accountService.displayUserList();
            System.out.print("""
                    \nYou want to...
                        [1] Edit user
                        [2] Delete user
                        [3] Create user
                        [4] Return
                    """);
            int choice = Input.scanIntegerLine("Enter your choice: ");
            switch (choice) {
                case 4 -> { return; }
                default -> throw new RuntimeException("There isn't such a choice!");
            }
        } while (true);
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
                    case 1 -> accountService.displayYourInformation();
                    case 2 -> displayMainMenu();
                    case 3 -> { return; }
                    default -> throw new InvalidChoiceException("There isn't such a choice!");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayAdminMenu() {
        do {
            try {
                System.out.print("""
                        \n==> [ADMIN MENU] (chưa làm)
                            [1] Homepage (chưa làm)
                            [2] User editing (chưa làm)
                            [3] Your account
                            [4] Log out
                            [5] Exit
                        """);
                int choice = Input.scanIntegerLine("Enter your choice: ");
                switch (choice) {
                    case 1 -> displayAdminHomepage();
                    case 2 -> displayUserEditing();
                    case 3 -> displayAdminAccount();
                    case 4 -> displayMainMenu();
                    case 5 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
