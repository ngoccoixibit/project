package entity;

import java.util.Date;

public class User extends Account {
    public User() {
    }

    public User(String email, String name, String password, String fullName, Date birthday) {
        super(email, name, password, fullName, birthday);
    }

    public User(String email, String name, String password, String fullName, Date birthday, String Phone, String address) {
        super(email, name, password, fullName, birthday, Phone, address);
    }
}
