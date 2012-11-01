package com.camunda.fox.showcase.fruitshop.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author nico.rehwaldt
 */
@Entity
public class InventoryItem extends AbstractEntity {
  
  private static final long serialVersionUID = 1L;
  
  private int availability;
  
  @OneToOne
  private Article article;

  public InventoryItem() {
  }

  public InventoryItem(Article article, int availability) {
    this.article = article;
    
    this.availability = availability;
  }

  public Article getArticle() {
    return article;
  }

  public int getAvailability() {
    return availability;
  }

  public void setAvailability(int availability) {
    this.availability = availability;
  }
}
