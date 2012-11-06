package com.camunda.fox.showcase.fruitshop.order.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;


@Entity
public class OrderItem extends AbstractEntity {

  public static final String STATUS_CONFIRMED = "confirmed";
  public static final String STATUS_UNCONFIRMED = "unconfirmed";
  
  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Article article;

  private int amount;
  
  private String reservationStatus;

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }
  
  public String getReservationStatus() {
    return reservationStatus;
  }
  
  public void setReservationStatus(String reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

}
