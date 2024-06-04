package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Account {
    private int ID;
    private String accountName;
    private String fullName;
    private Date birthday;
    private String phone;
    private String email;
    private String password;
    private String address;
    private static int count = 1;

    public Account() {
    }

    public Account(String Email, String Password) {
        this.email = Email;
        this.password = Password;
    }

    public Account(String accountName, String fullName, Date Birthday, String Phone, String Email, String Password, String address) {
        this.ID = count++;
        this.accountName = accountName;
        this.fullName = fullName;
        this.birthday = Birthday;
        this.phone = Phone;
        this.email = Email;
        this.password = Password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "--------------------------------------------------->" + "\n" +
               ID + ". [" + accountName + "]\n" +
               "Email: " + email + "\n" +
               "Full name: " + fullName + "\n" +
               "Birthday: " + getBirthday() + "\n" +
               "Phone: " + phone + "\n" +
               "--------------------------------------------------->";
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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