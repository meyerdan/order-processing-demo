package com.camunda.fox.showcase.fruitshop.order.repository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractRepository;
import com.camunda.fox.showcase.fruitshop.order.entity.Order;

/**
 * 
 * @author Daniel Meyer
 *
 */
@LocalBean
@Stateless
public class OrderRepository extends AbstractRepository<Order> {

}
