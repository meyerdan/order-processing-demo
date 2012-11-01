package com.camunda.fox.showcase.fruitshop.web.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author nico.rehwaldt
 */
public class AbstractResource {

  public WebApplicationException notFound() {
    return new WebApplicationException(Response.Status.NOT_FOUND);
  }
}
