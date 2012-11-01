package com.camunda.fox.showcase.fruitshop.repository;

import java.util.List;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.camunda.fox.showcase.fruitshop.entity.AbstractEntity;
import com.camunda.fox.showcase.fruitshop.util.ClassUtil;


/**
 * Abstract repository which holds the base information
 * 
 * @author nico.rehwaldt
 */
public class AbstractRepository<T extends AbstractEntity> {

  @PersistenceContext
  protected EntityManager em;
  
  private Class<T> entityClass;
  
  @SuppressWarnings("unchecked")
  protected AbstractRepository() {
    this.entityClass = (Class<T>) ClassUtil.extractParameterizedType(getClass());
  }
  
  /**
   * Save the given entity and flush
   * 
   * @return 
   */
  public T saveAndFlush(T entity) {
    
    // Persist entity unless it exists
    if (!em.contains(entity)) {
      em.persist(entity);
    }
    
    em.flush();
    return entity;
  }

  /**
   * Delete a given entity by id
   * 
   * @return 
   */
  public void delete(Long id) {
    T e = em.find(entityClass, id);
    if (e != null) {
      em.remove(e);
    }
  }
  
  /**
   * Delete a given entity from
   * 
   * @return 
   */
  public void delete(T entity) {
    if (em.contains(entity)) {
      em.remove(entity);
    } else {
      delete(entity.getId());
    }
  }
  
  /**
   * Find a given entity by id
   * 
   * @return the element or null if the element was not found
   */
  public T findById(long id) {
    return em.find(entityClass, id);
  }
  
  /**
   * Find all entities
   * 
   * @return 
   */
  public List<T> findAll() {
    return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
  }
  
  /**
   * Delete all entities
   * BEWARE: It doesn't trigger cascade or removeOrphan functionality for the deleted entites, 
   *         because it is an JQL statement, not an @{link EntityManager} method. 
   * 
   * @return 
   */
  public int deleteAll() {
    return em.createQuery("DELETE FROM " + entityClass.getSimpleName()).executeUpdate();
  }
}
