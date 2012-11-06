package com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Daniel Meyer
 *
 */
public class OrderDTO {

  private String customer;

  private List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();

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
  
}
