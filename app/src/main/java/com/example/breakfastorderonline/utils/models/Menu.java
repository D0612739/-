package com.example.breakfastorderonline.utils.models;

public class Menu {

  private String name;
  private int price;
  private boolean has_addition;
  private boolean has_icehot;

  public Menu(String name, int price, boolean has_addition, boolean has_icehot) {
    this.name = name;
    this.price = price;
    this.has_addition = has_addition;
    this.has_icehot = has_icehot;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isHas_addition() {
    return has_addition;
  }

  public void setHas_addition(boolean has_addition) {
    this.has_addition = has_addition;
  }

  public boolean isHas_icehot() {
    return has_icehot;
  }

  public void setHas_icehot(boolean has_icehot) {
    this.has_icehot = has_icehot;
  }
}
