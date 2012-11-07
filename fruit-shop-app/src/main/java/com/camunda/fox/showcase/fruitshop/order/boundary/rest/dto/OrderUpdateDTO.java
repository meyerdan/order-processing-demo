package com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.camunda.fox.showcase.fruitshop.order.entity.OrderUpdate;

/**
 * 
 * @author Nico Rehwaldt
 */
public class OrderUpdateDTO {
  
  private Date created;
  private String message;
  private Long orderId;

  public OrderUpdateDTO() {
  }

  public OrderUpdateDTO(OrderUpdate update) {
    created = update.getCreated();
    message = update.getMessage();
    
    orderId = update.getOrder().getId();
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
   * @return the orderId
   */
  public Long getOrderId() {
    return orderId;
  }

  /**
   * @param orderId the orderId to set
   */
  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  // static helpers /////////////////////////////
  
  public static List<OrderUpdateDTO> wrapAll(List<OrderUpdate> orderUpdates) {
    ArrayList<OrderUpdateDTO> result = new ArrayList<OrderUpdateDTO>();
    
    for (OrderUpdate orderUpdate : orderUpdates) {
      result.add(OrderUpdateDTO.wrap(orderUpdate));
    }
    
    return result;
  }
  
  private static OrderUpdateDTO wrap(OrderUpdate orderUpdate) {
    return new OrderUpdateDTO(orderUpdate);
  }
}
