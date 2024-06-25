package service;

import entity.Account;
import entity.Admin;

import entity.User;
import exception.InvailAccountException;
import exception.InvalidChoiceException;
import util.Input;
import util.ReadFile;
import util.WriteFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountService {
    private static final AccountService ACCOUNT_SERVICE = new AccountService();
    private Account user;

    public static AccountService getInstance() {
        return ACCOUNT_SERVICE;
    }

    public static final List<User> USER_LIST = new ArrayList<>();
    public static final List<Admin> ADMIN_LIST = new ArrayList<>();
    public static final List<Account> ACCOUNT_LIST = new ArrayList<>();

    static {
        List<User> userList = ReadFile.readFileUser("src\\data\\user.csv");
        USER_LIST.addAll(userList);
        List<Admin> adminList = ReadFile.readFileAdmin("src\\data\\admin.csv");
        ADMIN_LIST.addAll(adminList);
        ACCOUNT_LIST.addAll(USER_LIST);
        ACCOUNT_LIST.addAll(ADMIN_LIST);
    }

    public Account getCurrentAccount() {
        return user;
    }

    public String checkExistEmail(String email) {
        boolean isExist = false;
        do {
            for (Account account : ACCOUNT_LIST) {
                if (account.getEmail().equalsIgnoreCase(email)) {
                    isExist = true;
                    System.out.println("Email [" + email + "] is already used!");
                    email = Input.scanLineRegex("Enter email: ", "EMAIL");
                }
            }
        } while (isExist);
        return email;
    }

    public String checkExistName(String name) {
        boolean isExist = false;
        do {
            for (Account account : ACCOUNT_LIST) {
                if (account.getName().equalsIgnoreCase(name)) {
                    isExist = true;
                    System.out.println("Account name [" + name + "] is already used!");
                    name = Input.scanLineRegex("* Account name must have at least 3 letter *\nEnter account name: ", "NAME");
                }
            }
        } while (isExist);
        return name;
    }

    public String checkExistPhone(String phone) {
        boolean isExist = false;
        do {
            for (Account account : ACCOUNT_LIST) {
                if (account.getPhone().equalsIgnoreCase(phone)) {
                    isExist = true;
                    System.out.println("Phone number [" + phone + "] is already used!");
                    phone = Input.scanLineRegex("* Phone format [0xxxxxxxxx] *\nEnter phone: ", "PHONE");
                }
            }
        } while (isExist);
        return phone;
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
                String email = Input.scanStringLine("Enter your email OR account name: ");
                String password = Input.scanStringLine("Enter your password: ");
                String confirm = Input.scanStringLine("Confirm login (Y -> continue / Any key -> Return): ");
                if (confirm.equalsIgnoreCase("Y")) {
                    for (Account account : ACCOUNT_LIST) {
                        if (account instanceof User) {
                            for (User user : USER_LIST) {
                                if (email.equalsIgnoreCase(user.getEmail()) || email.equalsIgnoreCase(user.getName())) {
                                    if (password.equals(user.getPassword())) {
                                        System.out.println("\nLogged in successfully! Welcome User [" + user.getFullName() + "] (^u^)");
                                        this.user = user;
                                        return;
                                    }
                                }
                            }
                        } else if (account instanceof Admin) {
                            for (Admin admin : ADMIN_LIST) {
                                if (email.equalsIgnoreCase(admin.getEmail()) || email.equalsIgnoreCase(admin.getName())) {
                                    if (password.equals(admin.getPassword())) {
                                        System.out.println("\nLogged in successfully! Welcome Admin [" + admin.getFullName() + "] (^u^)");
                                        user = admin;
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
                    [3] Return
                """);
        int choice = Input.scanIntegerLine("Enter your choice: ");
        do {
            try {
                if (choice == 1 || choice == 2) {
                    String email = Input.scanLineRegex("Enter email: ", "EMAIL");
                    email = checkExistEmail(email);
                    String name = Input.scanLineRegex("* Account name must have at least 3 letter\nEnter account name: ", "NAME");
                    name = checkExistName(name);
                    String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, @\nEnter password: ", "PASSWORD");
                    String fullName = Input.scanStringLine("Enter full name: ").toUpperCase();
                    Date birthday = new SimpleDateFormat("dd-MM-yyyy").parse(Input.scanLineRegex("* Birthday format [dd-MM-yyyy]\nEnter birthday: ", "DATE"));
                    switch (choice) {
                        case 1 -> {
                            User newUser = new User(email, name, password, fullName, birthday);
                            WriteFile.WriteFileAccount("src\\data\\user.csv", newUser);
                            USER_LIST.add(newUser);
                            ACCOUNT_LIST.add(newUser);
                            System.out.println("\nCreate new account successfully! Go login now (>,<)\n");
                            return;
                        }
                        case 2 -> {
                            Admin newAdmin = new Admin(email, name, password, fullName, birthday);
                            WriteFile.WriteFileAccount("src\\data\\admin.csv", newAdmin);
                            ADMIN_LIST.add(newAdmin);
                            ACCOUNT_LIST.add(newAdmin);
                            System.out.println("\nCreate new account successfully! Go login now (>,<)\n");
                            return;
                        }
                    }
                } else if (choice == 3)
                    return;
                else
                    throw new RuntimeException("There isn't such a choice!");
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayYourDetailInformation() {
        try {
            System.out.println("\n==> [YOUR INFORMATION]");
            for (Account account : ACCOUNT_LIST) {
                if (account instanceof User) {
                    if (user.getName().equals(account.getName())) {
                        System.out.println(account);
                    }
                }
                if (account instanceof Admin) {
                    if (user.getName().equals(account.getName())) {
                        System.out.println(account);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editYourInformation() {
        do {
            try {
                displayYourDetailInformation();
                String edit = Input.scanStringLine("Do you want to edit your information? (Y -> continue / Any key -> Return): ");
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
                            String editName = Input.scanLineRegex("* Account name must have at least 3 letter *\nEnter new name: ", "NAME");
                            editName = checkExistName(editName);
                            user.setName(editName);
                            System.out.println("Edit successfully!");
                        }
                        case 2 -> {
                            String editFullName = Input.scanStringLine("Enter new full name: ").toUpperCase();
                            user.setFullName(editFullName);
                            System.out.println("Edit successfully!");
                        }
                        case 3 -> {
                            String editBirthday = Input.scanLineRegex("* Birthday format [dd-MM-yyyy] *\nEnter new birthday: ", "DATE");
                            user.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(editBirthday));
                            System.out.println("Edit successfully!");
                        }
                        case 4 -> {
                            String editPhone = Input.scanLineRegex("* Phone format [0xxxxxxxxx] *\nEnter new phone: ", "PHONE");
                            editPhone = checkExistPhone(editPhone);
                            user.setPhone(editPhone);
                            System.out.println("Edit successfully!");
                        }
                        case 5 -> {
                            String editAddress = Input.scanStringLine("Enter new address: ");
                            user.setAddress(editAddress);
                            System.out.println("Edit successfully!");
                        }
                        case 6 -> {
                            String editEmail = Input.scanLineRegex("Enter new email: ", "EMAIL").toLowerCase();
                            editEmail = checkExistEmail(editEmail);
                            user.setEmail(editEmail);
                            System.out.println("Edit successfully!");
                        }
                        case 7 -> {
                            String oldPassWordVerify = Input.scanStringLine("Enter your password: ");
                            if (oldPassWordVerify.equals(user.getPassword())) {
                                String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, [@#$%^&+=] *\nEnter new password: ", "PASSWORD");
                                user.setEmail(password);
                                System.out.println("Edit successfully!");
                            } else throw new RuntimeException("Wrong password!");
                        }
                        case 8 -> {
                            return;
                        }
                        default -> throw new InvalidChoiceException("There isn't such a choice!");
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error! " + e.getMessage() + ". Please try again");
            }
        } while (true);
    }

    public void displayUserPage() {
        do {
            try {
                ACCOUNT_SERVICE.displayUserList();
                System.out.print("""
                        \n==> [USER PAGE]
                            [1] Edit user
                            [2] Delete user (chưa làm)
                            [3] Back
                        """);
                int choice = Input.scanIntegerLine("Enter choice: ");
                switch (choice) {
                    case 1 -> editUserInformation();
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

    public void editUserInformation() {
        do {
            try {
                int ID = Input.scanIntegerLine("Enter user ID to edit: ");
                boolean isExistID = USER_LIST.stream().anyMatch(user -> user.getID() == ID);
                if (!isExistID) {
                    throw new RuntimeException("ID not found!");
                } else {
                    System.out.println("""
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
                    int choice = Input.scanIntegerLine("Enter choice: ");
                    for (User user : USER_LIST) {
                        if (user.getID() == ID) {
                            switch (choice) {
                                case 1 -> {
                                    System.out.println("Old account name: " + user.getName());
                                    String editName = Input.scanLineRegex("* Account name must have at least 3 letter *\nEnter new name: ", "NAME");
                                    editName = checkExistName(editName);
                                    user.setName(editName);
                                    System.out.println("Edit successfully!");
                                }
                                case 2 -> {
                                    System.out.println("Old full name: " + user.getFullName());
                                    String editFullName = Input.scanStringLine("Enter new full name: ").toUpperCase();
                                    user.setFullName(editFullName);
                                    System.out.println("Edit successfully!");
                                }
                                case 3 -> {
                                    System.out.println("Old birthday: " + user.getBirthday());
                                    String editBirthday = Input.scanLineRegex("* Birthday format [dd-MM-yyyy] *\nEnter new birthday: ", "DATE");
                                    user.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(editBirthday));
                                    System.out.println("Edit successfully!");
                                }
                                case 4 -> {
                                    System.out.println("Old phone number: " + user.getPhone());
                                    String editPhone = Input.scanLineRegex("* Phone format [0xxxxxxxxx] *\nEnter new phone: ", "PHONE");
                                    editPhone = checkExistPhone(editPhone);
                                    user.setPhone(editPhone);
                                    System.out.println("Edit successfully!");
                                }
                                case 5 -> {
                                    System.out.println("Old address: " + user.getAddress());
                                    String editAddress = Input.scanStringLine("Enter new address: ");
                                    user.setAddress(editAddress);
                                    System.out.println("Edit successfully!");
                                }
                                case 6 -> {
                                    System.out.println("Old email: " + user.getEmail());
                                    String editEmail = Input.scanLineRegex("Enter new email: ", "EMAIL").toLowerCase();
                                    editEmail = checkExistEmail(editEmail);
                                    user.setEmail(editEmail);
                                    System.out.println("Edit successfully!");
                                }
                                case 7 -> {
                                    System.out.println("Old password: " + user.getPassword());
                                    String password = Input.scanLineRegex("* Password must have at least 1 a->z, A->Z, 0->9, [@#$%^&+=] *\nEnter new password: ", "PASSWORD");
                                    user.setEmail(password);
                                    System.out.println("Edit successfully!");
                                }
                                case 8 -> {
                                    return;
                                }
                                default -> throw new InvalidChoiceException("There isn't such a choice!");
                            }
                        }
                    }
                    String repeat = Input.scanStringLine("Do you you want to continue edit? (Y -> continue/Any key -> Return): ");
                    if (!repeat.equalsIgnoreCase("Y")) {
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}