package com.example.breakfastorderonline.utils.models;

public class Cart {

  private Menu menuDish;
  private int count;
  private String icehot;
  private String note;

  public Cart(Menu menuDish, int count, String icehot, String note) {
    this.menuDish = menuDish;
    this.count = count;
    this.icehot = icehot;
    this.note = note;
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

  public String getIcehot() {
    return icehot;
  }

  public void setIcehot(String icehot) {
    this.icehot = icehot;
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
