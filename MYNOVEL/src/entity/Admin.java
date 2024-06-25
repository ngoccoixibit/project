package entity;

import java.util.Date;

public class Admin extends Account {
    public Admin() {
    }

    public Admin(String email, String name, String password, String fullName, Date birthday) {
        super(email, name, password, fullName, birthday);
    }

    public Admin(String email, String name, String password, String fullName, Date birthday, String Phone, String address) {
        super(email, name, password, fullName, birthday, Phone, address);
    }
}
