package com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.camunda.fox.showcase.fruitshop.order.entity.Order;
import com.camunda.fox.showcase.fruitshop.order.entity.Order.Status;

/**
 * 
 * @author Daniel Meyer
 *
 */
public class OrderDTO {

  private Long id;
  
  private String customer;

  private List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();

  private Order.Status status;

  private Date created;
  
  public OrderDTO() {
  }
  
  public OrderDTO(Order order) {
    this.customer = order.getCustomer();
    this.id = order.getId();
    
    this.orderItems = OrderItemDTO.wrapAll(order.getOrderItems());
    this.status = order.getStatus();
    
    this.created = order.getCreated();
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCusomer(String customerName) {
    this.customer = customerName;
  }

  public List<OrderItemDTO> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItemDTO> orderItems) {
    this.orderItems = orderItems;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }
}
