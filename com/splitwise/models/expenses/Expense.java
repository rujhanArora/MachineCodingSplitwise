package com.splitwise.models.expenses;


import com.splitwise.Utils;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.imports.Geolocation;
import com.splitwise.imports.Image;
import com.splitwise.models.User;
import com.splitwise.models.splits.Split;

import java.util.Date;
import java.util.List;

public abstract class Expense {
    private long uid;
    private String name;
    private double totalAmount;
    private User paidBy;
    private User createdBy;
    private static ExpenseType type = null;
    private List<Split> splits; // runtime polymorphism

    private String notes;
    private Geolocation location;
    private Date created;
    private List<Image> images;

    private static int NEW_UID = 0;

    public Expense(String name, double totalAmount, User createdBy) throws IllegalSplitException {
        setUid(NEW_UID++); // UID trick
        setName(name);
        setTotalAmount(totalAmount);
        setCreatedBy(createdBy);
    }
    // todo: builder pattern <- tell this to your interviewer

    abstract void validateSplits() throws IllegalSplitException;

    boolean validate() {
        // common logic
        double sum = 0;
        for(Split s: splits)
            sum += s.getAmount();
        return Utils.isApproxEqual(sum, getTotalAmount());
    }

    abstract void recalculate() ; // no common logic

    public void setSplits(List<Split> splits) throws IllegalSplitException {
        this.splits = splits;
        validateSplits();
        recalculate();
    }

    public void addSpit(Split s) throws IllegalSplitException {
        splits.add(s);
        validateSplits();
        recalculate();
    }
    public void removeSpit(Split s) throws IllegalSplitException {
        splits.remove(s);
        recalculate();
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Geolocation getLocation() {
        return location;
    }

    public void setLocation(Geolocation location) {
        this.location = location;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) throws IllegalSplitException {
        this.totalAmount = totalAmount;
        recalculate();
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public ExpenseType getType() {
        return type;
    }

    public void setType(ExpenseType type) {
        this.type = type;
    }

    public List<Split> getSplits() {
        return splits;
    }
}
