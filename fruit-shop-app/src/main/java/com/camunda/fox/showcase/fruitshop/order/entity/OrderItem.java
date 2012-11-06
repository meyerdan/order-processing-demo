package com.camunda.fox.showcase.fruitshop.order.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;


@Entity
public class OrderItem extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Article article;

  private int amount;

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

}
