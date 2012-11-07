package com.camunda.fox.showcase.fruitshop.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;

/**
 * 
 * @author nico.rehwaldt
 */
@Entity
@Table(name="FS_INVENTORY_ITEM_")
public class InventoryItem extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  private int available;

  private int reserved;

  @OneToOne
  private Article article;

  public InventoryItem() {
  }

  public InventoryItem(Article article, int available) {
    this.article = article;

    this.available = available;
  }

  public Article getArticle() {
    return article;
  }

  public int getAvailable() {
    return available;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  public int getReserved() {
    return reserved;
  }

  public void setReserved(int reserved) {
    this.reserved = reserved;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

}
