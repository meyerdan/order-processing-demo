package com.camunda.fox.showcase.fruitshop.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract super class for all entities.
 * 
 * @author nico.rehwaldt
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue
  private Long id;
  
  public Long getId() {
    return id;
  }
}
