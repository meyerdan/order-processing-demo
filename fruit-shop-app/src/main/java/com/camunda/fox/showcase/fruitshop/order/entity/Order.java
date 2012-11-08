package com.camunda.fox.showcase.fruitshop.order.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractEntity;


@Entity
@Table(name="FS_ORDER_")
public class Order extends AbstractEntity {

  private static final long serialVersionUID = 1L;

  public enum Status {
    NEW, 
    CONFIRMED, 
    CANCELLED,
    PAYMENT_PROCESSED,
    PAYMENT_PROBLEMS,
    SHIPPED,
  }

  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  
  @OneToMany(cascade={CascadeType.PERSIST})
  private List<OrderItem> orderItems = new ArrayList<OrderItem>();

  @OneToMany(mappedBy="order")
  private List<OrderUpdate> orderUpdates = new ArrayList<OrderUpdate>();

  @NotNull
  private String customer;

  private String processInstanceId;
  
  private double total;

  private Status status = Status.NEW;

  public Order() {
    this.created = new Date();
  }
  
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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<OrderUpdate> getOrderUpdates() {
    return orderUpdates;
  }

  public void setOrderUpdates(List<OrderUpdate> orderUpdates) {
    this.orderUpdates = orderUpdates;
  }

  public Date getCreated() {
    return created;
  }
  
  public double getTotal() {
	return total;
  }
  
  public void setTotal(double total) {
	this.total = total;
  }

}
