package com.camunda.fox.showcase.fruitshop.inventory.boundary.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractResource;
import com.camunda.fox.showcase.fruitshop.inventory.boundary.ArticleDTO;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;
import com.camunda.fox.showcase.fruitshop.inventory.entity.InventoryItem;
import com.camunda.fox.showcase.fruitshop.inventory.repository.ArticleRepository;

/**
 * This is the main roundtrip rest controller which exposes roundtrip
 * <code>list</code>,
 * <code>get</code>,
 * <code>delete</code>,
 * <code>create</code> and<code>update</code> methods as well as some utilities to the cycle client application.
 *
 * The arrangement of methods is compatible with angular JS
 * <code>$resource</code>.
 *
 * @author nico.rehwaldt
 */
@LocalBean
@Stateless
@Path("/article")
public class ArticleResource extends AbstractResource {

  @Inject
  private ArticleRepository articleRepository;
  
  // $resource specific methods ////////////////////////////////////
  
  /**
   * Serves list (GET)
   * @return 
   */
  @GET
  public List<ArticleDTO> list() {
    List<Object[]> data = articleRepository.findArticlesAndAvailability();
    
    List<ArticleDTO> articles = new ArrayList<ArticleDTO>();
    for (Object[] entry: data) {
      
      articles.add(new ArticleDTO((Article) entry[0], (InventoryItem) entry[1]));
    }
    
    return articles;
  }

  /**
   * Serves get single (GET)
   * 
   * @param id
   * @return 
   */
  @GET
  @Path("{id}")
  public ArticleDTO get(@PathParam("id") long id) {
    return ArticleDTO.wrap(articleRepository.findById(id));
  }

  /**
   * Serves update (POST)
   * 
   * @param data
   * @return 
   */
  @POST
  @RolesAllowed("admin")
  @Path("{id}")
  public ArticleDTO update(ArticleDTO data) {
    long id = data.getId();

    Article article = articleRepository.findById(id);
    if (article == null) {
      throw notFound();
    }

    update(article, data);
    return ArticleDTO.wrap(article);
  }

  /**
   * Serves create (POST)
   * 
   * @param data
   * @return 
   */
  @POST
  @RolesAllowed("admin")
  public ArticleDTO create(ArticleDTO data) {
    Article article = new Article();
    update(article, data);
    return ArticleDTO.wrap(articleRepository.saveAndFlush(article));
  }
  
  /**
   * Serves delete (DELETE) 
   * 
   * @param id 
   */
  @DELETE
  @RolesAllowed("admin")
  @Path("{id}")
  public void delete(@PathParam("id") long id) {
    Article roundtrip = articleRepository.findById(id);
    if (roundtrip == null) {
      throw notFound();
    }
    
    articleRepository.delete(roundtrip);
  }

  // update logic ////////////////////////////////////////
  
  private void update(Article article, ArticleDTO data) {
    // not yet implemented
  }
  
  // database population //////////////////////////////////
  
  @PostConstruct
  private void postConstruct() {
    if (articleRepository.findAll().isEmpty()) {
      
      List<Article> articles = new ArrayList<Article>();
      
      articles.add(new Article("Cake", "A delicious cake. Home made", 5, 50, 
              "http://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Pound_layer_cake.jpg/309px-Pound_layer_cake.jpg"));
      articles.add(new Article("Salat", "A freshly harvested salat", 3, 4,
              "http://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Iceberg_lettuce_in_SB.jpg/320px-Iceberg_lettuce_in_SB.jpg"));
      articles.add(new Article("Cucumber", "Not a testing framework for Ruby", 0, 3,
              "http://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/PicklingCucumbers.jpg/320px-PicklingCucumbers.jpg"));
      articles.add(new Article("Salami", "Real german sausage", 10, 12,
              "http://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Salami_aka.jpg/320px-Salami_aka.jpg"));
      articles.add(new Article("Weißwurscht", "Bavarian special dish. Some call it sausage", 20, 10,
              "http://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Wei%C3%9Fwurst-1.jpg/315px-Wei%C3%9Fwurst-1.jpg"));
      
      for (Article article : articles) {
        articleRepository.saveAndFlush(article);
      }
    }
  }
}
