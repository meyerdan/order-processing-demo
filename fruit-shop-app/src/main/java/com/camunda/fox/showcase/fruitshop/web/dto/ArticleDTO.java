package com.camunda.fox.showcase.fruitshop.web.dto;

import java.util.ArrayList;
import java.util.List;
import com.camunda.fox.showcase.fruitshop.entity.Article;

/**
 *
 * @author nico.rehwaldt
 */
public class ArticleDTO {

  private Long id;
  
  private String name;
  
  private String description;
  
  public ArticleDTO(Article article) {
    this.id = article.getId();
    this.name = article.getName();
    this.description = article.getDescription();
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

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
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
