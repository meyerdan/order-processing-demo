package com.camunda.fox.showcase.fruitshop.inventory.boundary;

import java.util.ArrayList;
import java.util.List;

import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;
import com.camunda.fox.showcase.fruitshop.inventory.entity.InventoryItem;

/**
 *
 * @author nico.rehwaldt
 */
public class ArticleDTO {

  private Long id;
  
  private String name;
  
  private String description;
  
  private int price;
  
  private int available;
  
  public ArticleDTO(Article article) {
    this.id = article.getId();
    this.name = article.getName();
    this.description = article.getDescription();
    
    this.price = article.getPrice();
  }

  public ArticleDTO(Article article, InventoryItem inventoryItem) {
    this(article);
    
    this.available = inventoryItem.getAvailable();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * @return the available
   */
  public int getAvailable() {
    return available;
  }

  /**
   * @param available the available to set
   */
  public void setAvailable(int available) {
    this.available = available;
  }
  
  // static helpers /////////////////////////////////////////////////////
  
  /**
   * Wraps a single article into its respective data object
   * 
   * @param article
   * @return 
   */
  public static ArticleDTO wrap(Article article) {
    return new ArticleDTO(article);
  }

  /**
   * Wraps a list of articles into their respective data objects
   * 
   * @param articles
   * @return 
   */
  public static List<ArticleDTO> wrapAll(List<Article> articles) {
    
    ArrayList<ArticleDTO> dtos = new ArrayList<ArticleDTO>();
    
    for (Article article : articles) {
      dtos.add(wrap(article));
    }
    
    return dtos;
  }
  
}
