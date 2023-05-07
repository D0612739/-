package com.example.breakfastorderonline.utils.models;

public class Cart {

  private String dish_name;
  private int count;
  private String addition;
  private String icehot;
  private String note;

  public Cart(String dish_name, int count, String addition, String icehot, String note) {
    this.dish_name = dish_name;
    this.count = count;
    this.addition = addition;
    this.icehot = icehot;
    this.note = note;
  }

  public String getDish_name() {
    return dish_name;
  }

  public void setDish_name(String dish_name) {
    this.dish_name = dish_name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getAddition() {
    return addition;
  }

  public void setAddition(String addition) {
    this.addition = addition;
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
}
