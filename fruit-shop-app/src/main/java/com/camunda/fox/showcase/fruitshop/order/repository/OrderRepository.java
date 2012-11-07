package com.camunda.fox.showcase.fruitshop.order.repository;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractRepository;
import com.camunda.fox.showcase.fruitshop.order.entity.Order;
import com.camunda.fox.showcase.fruitshop.order.entity.OrderUpdate;

/**
 * 
 * @author Daniel Meyer
 *
 */
@LocalBean
@Stateless
public class OrderRepository extends AbstractRepository<Order> {

  public Order findByIdFetchOrderItems(long id) {
    return em.createQuery("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.id = :id", Order.class)
      .setParameter("id", id)
      .getSingleResult();
  }

  public List<OrderUpdate> findOrderUpdatesByOrderId(long id) {
    return em.createQuery("SELECT a FROM Order o JOIN o.orderUpdates a WHERE o.id = :id", OrderUpdate.class)
      .setParameter("id", id)
      .getResultList();
  }
}
