package com.camunda.fox.showcase.fruitshop.application.common;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author nico.rehwaldt
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AbstractResource {

  public WebApplicationException notFound() {
    return new WebApplicationException(Response.Status.NOT_FOUND);
  }
}
