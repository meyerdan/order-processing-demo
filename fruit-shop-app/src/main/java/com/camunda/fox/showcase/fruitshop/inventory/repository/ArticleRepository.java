package com.camunda.fox.showcase.fruitshop.inventory.repository;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractRepository;
import com.camunda.fox.showcase.fruitshop.inventory.entity.Article;

/**
 *
 * @author nico.rehwaldt
 */
@LocalBean
@Stateless
public class ArticleRepository extends AbstractRepository<Article> {

  public List<Object[]> findArticlesAndAvailability() {
    return em.createQuery("SELECT a, i FROM Article a JOIN a.inventoryItem i").getResultList();
  }
}
