package com.camunda.fox.showcase.fruitshop.order.boundary.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.camunda.fox.showcase.fruitshop.application.common.AbstractResource;
import com.camunda.fox.showcase.fruitshop.order.boundary.OrderService;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.NewOrderResponse;
import com.camunda.fox.showcase.fruitshop.order.boundary.rest.dto.OrderDTO;

/**
 * 
 * @author Daniel Meyer
 * 
 */
@LocalBean
@Stateless
@Path("/order")
public class OrderResource extends AbstractResource {

  private Logger log = Logger.getLogger(OrderResource.class.getName());

  @Inject
  private OrderService orderService;

  @POST
  public Response newOrder(OrderDTO orderDTO) {

    try {

      String newOrder = orderService.newOrder(orderDTO);
      NewOrderResponse newOrderResponse = new NewOrderResponse();
      newOrderResponse.setOrderId(newOrder);

      return Response.ok(newOrderResponse).build();

    } catch (Exception e) {
      log.log(Level.WARNING, "error while accepting new order: " + e.getMessage(), e);
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }

  }

}