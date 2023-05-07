package com.example.breakfastorderonline.utils.models;

import java.util.Date;

public class Notification {

  private Date time;
  private long orderId;
  private String title;
  private String content;

  public Notification(Date time, long orderId, String title, String content) {
    this.time = time;
    this.orderId = orderId;
    this.title = title;
    this.content = content;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
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
