package com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto;

public class OrderItemDTO {

  private String articleId;

  private String amount;

  public String getAmount() {
    return amount;
  }

  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

}
