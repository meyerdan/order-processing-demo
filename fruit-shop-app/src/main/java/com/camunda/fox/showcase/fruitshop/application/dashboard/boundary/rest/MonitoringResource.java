package com.camunda.fox.showcase.fruitshop.application.dashboard.boundary.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dashboard/monitor")
public class MonitoringResource {

  @Inject
  private BroadCaster broadCaster;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response toggle() {
    String state = broadCaster.getState();
    return Response.ok(state).build();
  }
}
