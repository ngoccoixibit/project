package service;

import entity.Account;
import entity.Admin;

import entity.User;
import exception.InvailAccountException;
import util.Input;
import util.ReadFile;
import util.WriteFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountService {
    private static final AccountService ACCOUNT_SERVICE = new AccountService();
    private Account currentAccount;

    public static AccountService getInstance() {
        return ACCOUNT_SERVICE;
    }

    public static final List<User> USER_LIST = new ArrayList<>();
    public static final List<Admin> ADMIN_LIST = new ArrayList<>();
    public static final List<Account> ACCOUNT_LIST = new ArrayList<>();

    static {
        List<User> userList = ReadFile.readFileUser("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\user.csv");
        USER_LIST.addAll(userList);
        List<Admin> adminList = ReadFile.readFileAdmin("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\admin.csv");
        ADMIN_LIST.addAll(adminList);
        ACCOUNT_LIST.addAll(USER_LIST);
        ACCOUNT_LIST.addAll(ADMIN_LIST);
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void checkExist(String s) {
        for (Account account : ACCOUNT_LIST) {
            if (account.getEmail().equalsIgnoreCase(s)) {
                throw new RuntimeException(s + " is already exist!");
            }
        }
    }

    public void displayUserList() {
        try {
            System.out.println("\n==> [USER LIST]");
            for (Account account : ACCOUNT_LIST) {
                if (account instanceof User) {
                    System.out.println(account);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logIn() {
        do {
            try {
                String accountEmail = Input.scanStringLine("Enter your email: ");
                String accountPassword = Input.scanStringLine("Enter your password: ");
                String confirm = Input.scanStringLine("Confirm login (Y/N) ");
                if (confirm.equalsIgnoreCase("Y")) {
                    for (Account account : ACCOUNT_LIST) {
                        if (accountEmail.equalsIgnoreCase(account.getEmail())) {
                            for (User user : USER_LIST) {
                                if (accountEmail.equals(user.getAccountName())) {
                                    if (accountPassword.equals(user.getPassword())) {
                                        System.out.println("\nLogged in successfully! Welcome User [" + user.getFullName() + "] (^u^)");
                                        currentAccount = user;
                                        return;
                                    }
                                }
                            }
                            for (Admin admin : ADMIN_LIST) {
                                if (accountEmail.equalsIgnoreCase(admin.getEmail())) {
                                    if (accountPassword.equals(admin.getPassword())) {
                                        System.out.println("\nLogged in successfully! Welcome Admin [" + admin.getFullName() + "] (^u^)");
                                        currentAccount = admin;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    throw new InvailAccountException("Wrong name or password! Please try again");
                } else return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void signIn() {
        System.out.print("""
                \nCreate account...
                    [1] USER
                    [2] ADMIN
                    [3] Exit
                """);
        int choice = Input.scanIntegerLine("Enter your choice: ");
        do {
            try {
                switch (choice) {
                    case 1 -> {
                        String email = Input.scanLineRegex("Enter email: ", "EMAIL").toLowerCase();
                        boolean checkExistUser = false;
                        for (Account account : ACCOUNT_LIST) {
                            if (email.equalsIgnoreCase(account.getEmail())) {
                                checkExistUser = true;
                                break;
                            }
                        }
                        if (checkExistUser) {
                            throw new InvailAccountException("This email is already used!");
                        } else {
                            String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, @\nEnter password: ", "PASSWORD");
                            String name = Input.scanStringLine("Enter username: ");
                            checkExist(name);
                            String fullName = Input.scanStringLine("Enter full name: ").toUpperCase();
                            Date birthday = new SimpleDateFormat("dd-MM-yyyy").parse(Input.scanStringLine("Enter birthday (dd-MM-yyyy): "));
                            String phone = Input.scanLineRegex("Enter phone (0xxxxxxxxx): ", "PHONE");
                            checkExist(phone);
                            String address = Input.scanStringLine("Enter address: ");
                            User newUser = new User(name, fullName, birthday, phone, email, password, address);
                            WriteFile.WriteFileAccount("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\user.csv", newUser);
                            USER_LIST.add(newUser);
                            ACCOUNT_LIST.add(newUser);
                            System.out.println("Create new account successfully! Go login now (>u<)");
                        }
                        return;
                    }
                    case 2 -> {
                        String email = Input.scanLineRegex("Enter email: ", "EMAIL").toLowerCase();
                        boolean checkExistAdmin = false;
                        for (Account account : ACCOUNT_LIST) {
                            if (email.equalsIgnoreCase(account.getEmail())) {
                                checkExistAdmin = true;
                                break;
                            }
                        }
                        if (checkExistAdmin) {
                            throw new InvailAccountException("This email is already used!");
                        } else {
                            String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, [@#$%^&+=]\nEnter password: ", "PASSWORD");
                            String name = Input.scanStringLine("Enter username: ");
                            checkExist(name);
                            String fullName = Input.scanStringLine("Enter full name: ").toUpperCase();
                            Date birthday = new SimpleDateFormat("dd-MM-yyyy").parse(Input.scanStringLine("Enter birthday (dd-MM-yyyy): "));
                            String phone = Input.scanLineRegex("Enter phone: ", "PHONE");
                            checkExist(phone);
                            String address = Input.scanStringLine("Enter address: ");
                            Admin newAdmin = new Admin(name, fullName, birthday, phone, email, password, address);
                            WriteFile.WriteFileAccount("C:\\Users\\ngocc\\PROJECT JAVA\\MYNOVEL\\src\\data\\admin.csv", newAdmin);
                            ADMIN_LIST.add(newAdmin);
                            ACCOUNT_LIST.add(newAdmin);
                            System.out.println("Create new account successfully! Go login now (>u<)");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    public void displayYourDetailInformation() {
        try {
            System.out.println("\n==> [YOUR INFORMATION]");
            for (Account account : ACCOUNT_LIST) {
                if (account instanceof User) {
                    if (currentAccount.getAccountName().equals(account.getAccountName())) {
                        System.out.println(account);
                    }
                }
                if (account instanceof Admin) {
                    if (currentAccount.getAccountName().equals(account.getAccountName())) {
                        System.out.println(account);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayYourInformation() {
        do {
            try {
                displayYourDetailInformation();
                String edit = Input.scanStringLine("Do you want to edit your information? (Y/N) ");
                if (edit.equalsIgnoreCase("Y")) {
                    System.out.print("""
                            \nYou want to edit...
                                [1] Account name
                                [2] Full name
                                [3] Birthday
                                [4] Phone
                                [5] Address
                                [6] Email
                                [7] Password
                                [8] Return
                            """);
                    int choice = Input.scanIntegerLine("Enter your choice: ");
                    switch (choice) {
                        case 1 -> {
                            String editName = Input.scanStringLine("Enter new name: ");
                            checkExist(editName);
                            currentAccount.setAccountName(editName);
                            System.out.println("Edit successfully!");
                        }
                        case 2 -> {
                            String editFullName = Input.scanStringLine("Enter new full name: ").toUpperCase();
                            currentAccount.setFullName(editFullName);
                            System.out.println("Edit successfully!");
                        }
                        case 3 -> {
                            String editBirthday = Input.scanStringLine("Enter new birthday (dd-MM-yyyy): ");
                            currentAccount.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(editBirthday));
                            System.out.println("Edit successfully!");
                        }
                        case 4 -> {
                            String editPhone = Input.scanLineRegex("Enter new phone (0xxxxxxxxx): ", "PHONE");
                            checkExist(editPhone);
                            currentAccount.setPhone(editPhone);
                            System.out.println("Edit successfully!");
                        }
                        case 5 -> {
                            String editAddress = Input.scanStringLine("Enter new address: ");
                            currentAccount.setAddress(editAddress);
                            System.out.println("Edit successfully!");
                        }
                        case 6 -> {
                            String editEmail = Input.scanLineRegex("Enter new email: ", "EMAIL").toLowerCase();
                            checkExist(editEmail);
                            currentAccount.setEmail(editEmail);
                            System.out.println("Edit successfully!");
                        }
                        case 7 -> {
                            String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, [@#$%^&+=]\nEnter new password: ", "PASSWORD");
                            currentAccount.setEmail(password);
                            System.out.println("Edit successfully!");
                        }
                        case 8 -> { return; }
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + " Please try again");
            }
        } while (true);
    }
}
