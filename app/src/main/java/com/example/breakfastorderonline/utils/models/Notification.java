package com.example.breakfastorderonline.utils.models;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {

  private Date time;
  private Order order;
  private String title;
  private String content;
  private boolean userRead;

  public Notification(Date time, Order order, String title, String content, boolean userRead) {
    this.time = time;
    this.order = order;
    this.title = title;
    this.content = content;
    this.userRead = userRead;
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

  public boolean isUserRead() {
    return userRead;
  }

  public void setUserRead(boolean userRead) {
    this.userRead = userRead;
  }
}
