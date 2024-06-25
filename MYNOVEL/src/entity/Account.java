package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Account {
    private int ID;
    private String name;
    private String fullName;
    private Date birthday;
    private String phone;
    private String email;
    private String password;
    private String address;
    private static int count = 1;

    public Account() {
    }

    public Account(String email, String name, String password, String fullName, Date birthday) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.fullName = fullName;
        this.birthday = birthday;
    }

    public Account(String email, String name, String password, String fullName, Date birthday, String Phone, String address) {
        this.ID = count++;
        this.name = name;
        this.fullName = fullName;
        this.birthday = birthday;
        this.phone = Phone;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "--------------------------------------------------->" + "\n" +
               ID + ". Account [" + name + "]\n" +
               "Email: " + email + "\n" +
               "Full name: " + fullName + "\n" +
               "Birthday: " + getBirthday() + "\n" +
               "Phone: " + phone + "\n" +
               "--------------------------------------------------->";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getBirthday() {
        return new SimpleDateFormat("dd-MM-yyyy").format(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}