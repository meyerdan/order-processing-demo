package com.camunda.fox.showcase.fruitshop.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;

/**
 *
 * @author nico.rehwaldt
 */
@Entity
@Table(name="FS_ARTICLE_")
public class Article extends AbstractEntity {
  
  private static final long serialVersionUID = 1L;

  private String name;
  
  private int price;
  
  @Lob
  private String description;
  
  @OneToOne(mappedBy="article", cascade= CascadeType.ALL)
  private InventoryItem inventoryItem;
  
  public Article() { }

  public Article(String name, String description, int availability, int price) {
    this.name = name;
    this.description = description;
    
    this.price = price;
    this.inventoryItem = new InventoryItem(this, availability);
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public InventoryItem getInventoryItem() {
    return inventoryItem;
  }
  
  public void setInventoryItem(InventoryItem inventoryItem) {
    this.inventoryItem = inventoryItem;
  }
}
