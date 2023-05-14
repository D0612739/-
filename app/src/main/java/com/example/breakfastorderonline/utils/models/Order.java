package com.example.breakfastorderonline.utils.models;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    private String id;
    private User user;
    private Date time1;
    private Date time2;
    private String note;
    private OrderState state;
    private int totalPrice;

    public Order(String id, User user, Date time1, Date time2, String note, OrderState state, int totalPrice) {
        this.id = id;
        this.user = user;
        this.time1 = time1;
        this.time2 = time2;
        this.note = note;
        this.state = state;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getTotalPrice() {
        // compute from database
         return totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
