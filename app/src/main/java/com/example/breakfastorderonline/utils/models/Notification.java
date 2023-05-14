package com.example.breakfastorderonline.utils.models;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

  private Date time;
  private Order order;
  private String title;
  private String content;

  public Notification(Date time, Order order, String title, String content) {
    this.time = time;
    this.order = order;
    this.title = title;
    this.content = content;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
