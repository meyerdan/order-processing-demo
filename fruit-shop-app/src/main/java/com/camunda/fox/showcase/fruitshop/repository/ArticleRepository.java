package com.camunda.fox.showcase.fruitshop.repository;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.camunda.fox.showcase.fruitshop.entity.Article;

/**
 *
 * @author nico.rehwaldt
 */
@LocalBean
@Stateless
public class ArticleRepository extends AbstractRepository<Article> {
  
}
