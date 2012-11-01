package com.camunda.fox.showcase.fruitshop.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
public class Order extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  @OneToMany
  private List<OrderItem> orderItems = new ArrayList<OrderItem>();

  @NotNull
  private String customer;

  private String processInstanceId;

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

}
