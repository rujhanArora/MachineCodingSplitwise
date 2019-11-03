package com.splitwise.models;
// Models - how your data looks. entities, their relations, their attributes
// View - frontend - UI / website - how the user interacts
// Controller - bridge, business logic

import com.splitwise.models.expenses.Expense;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String hashedPass;
    private long uid; // unique ID (not user ID)

    private static long NEW_UID = 0;
    private List<Expense> expenses;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public User(String name, String email, String hashedPass) {
        this.setUid(NEW_UID++); // uid trick
        this.setName(name);
        this.setEmail(email);
        this.setHashedPass(hashedPass);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User)) return false;
        return getUid() == ((User) obj).getUid();
    }

    // when we compare objects, their memory addresses are compared


    @Override
    public String toString() {
        return "User(uid=" + getUid() + ", name=" + getName() + ", email=" + getEmail() + ")";
    }
}
