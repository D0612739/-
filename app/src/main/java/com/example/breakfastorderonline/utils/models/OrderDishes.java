package com.example.breakfastorderonline.utils.models;

public class OrderDishes {

    private Order order;
    private Menu menuDish;
    private int count;
    private String note;

    public OrderDishes(Order order, Menu menuDish, int count, String note) {
        this.order = order;
        this.menuDish = menuDish;
        this.count = count;
        this.note = note;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Menu getMenuDish() {
        return menuDish;
    }

    public void setMenuDish(Menu menuDish) {
        this.menuDish = menuDish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
