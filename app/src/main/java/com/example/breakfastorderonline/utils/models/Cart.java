package com.example.breakfastorderonline.utils.models;

import java.io.Serializable;

public class Cart implements Serializable {

  private User user;
  private Menu menuDish;
  private int count;
  private String note;

  public Cart(User user, Menu menuDish, int count, String note) {
    this.user = user;
    this.menuDish = menuDish;
    this.count = count;
    this.note = note;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Menu getMenuDish() {
    return menuDish;
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

  public void setMenuDish(Menu menuDish) {
    this.menuDish = menuDish;
  }
}
