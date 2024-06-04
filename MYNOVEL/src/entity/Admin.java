package entity;

import java.util.Date;

public class Admin extends Account {
    public Admin() {
    }

    public Admin(String Email, String Password) {
        super(Email, Password);
    }

    public Admin(String accountName, String fullName, Date Birthday, String Phone, String Email, String Password, String address) {
        super(accountName, fullName, Birthday, Phone, Email, Password, address);
    }
}
