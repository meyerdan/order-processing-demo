package com.camunda.fox.showcase.fruitshop.order.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;


@Entity
@Table(name="FS_ORDER_ACTION_")
public class OrderUpdate extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  @ManyToOne
  private Order order;

  @NotNull
  private String message;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  public OrderUpdate() {
  }

  public OrderUpdate(Order order, String message) {
    this.order = order;
    this.message = message;
    
    this.created = new Date();
  }

  /**
   * @return the order
   */
  public Order getOrder() {
    return order;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the created
   */
  public Date getCreated() {
    return created;
  }

  /**
   * @param created the created to set
   */
  public void setCreated(Date created) {
    this.created = created;
  }
}
