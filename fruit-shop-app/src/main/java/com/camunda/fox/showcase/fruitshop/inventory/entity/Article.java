package com.camunda.fox.showcase.fruitshop.inventory.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;

/**
 *
 * @author nico.rehwaldt
 */
@Entity
public class Article extends AbstractEntity {
  
  private static final long serialVersionUID = 1L;

  private String name;
  
  @Lob
  private String description;
  
  @OneToOne(mappedBy="article", cascade= CascadeType.ALL)
  private InventoryItem inventoryItem;
  
  public Article() { }

  public Article(String name, String description, int availability) {
    this.name = name;
    this.description = description;
    
    this.inventoryItem = new InventoryItem(this, availability);
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
