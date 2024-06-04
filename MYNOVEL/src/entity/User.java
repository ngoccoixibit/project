package entity;

import java.util.Date;

public class User extends Account {
    public User() {
    }

    public User(String Email, String Password) {
        super(Email, Password);
    }

    public User(String accountName, String fullName, Date Birthday, String Phone, String Email, String Password, String address) {
        super(accountName, fullName, Birthday, Phone, Email, Password, address);
    }
}
