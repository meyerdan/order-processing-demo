package com.camunda.fox.showcase.fruitshop.repository;

import java.util.List;

/**
 *
 * @author nico.rehwaldt
 */
public class ArticleRepository extends AbstractRepository<Article> {
  
  public List<String> findAllModelerNames() {
    return em.createQuery("SELECT DISTINCT b.modeler FROM BpmnDiagram b", String.class).getResultList();
  }
}
