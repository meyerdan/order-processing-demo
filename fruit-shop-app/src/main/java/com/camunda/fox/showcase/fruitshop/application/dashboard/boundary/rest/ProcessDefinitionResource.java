package com.camunda.fox.showcase.fruitshop.application.dashboard.boundary.rest;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.IoUtil;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * 
 * @author Daniel Meyer
 *
 */
@Path("/dashboard/process-definition")
public class ProcessDefinitionResource {
  
  private Logger logger = Logger.getLogger(ProcessDefinitionResource.class.getName());
  
  @Inject
  private RepositoryService repositoryService;
  
  @GET
  @Path("/image")
  @Produces("image/png")
  public Response getProcessDefinitionImage() {
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
      .processDefinitionKey("sid-E1DEF3AA-1321-4DBE-9877-D18DDF51AC6D")
      .latestVersion()
      .singleResult();
    
    InputStream inputStream = repositoryService.getProcessDiagram(processDefinition.getId());
    
    if(inputStream != null) {
      try {
        
        byte[] bytes = IoUtil.readInputStream(inputStream, "Process Definition Image");
        
        return Response
          .ok(bytes)
          .build();
        
      }catch (Exception e) {
        logger.log(Level.WARNING, e.getMessage(), e);
        return Response.status(Status.INTERNAL_SERVER_ERROR).build();
      } finally {
        IoUtil.closeSilently(inputStream);
      }
    } else {
      logger.log(Level.INFO, "Could not find process definition image");
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    }
      
  }
  

  @GET
  @Path("/layout")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSingleInstance() {
    
    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
      .processDefinitionKey("sid-E1DEF3AA-1321-4DBE-9877-D18DDF51AC6D")
      .latestVersion()
      .singleResult();
    
    DiagramLayout processDiagramLayout = repositoryService.getProcessDiagramLayout(processDefinition.getId());
    
    return Response
      .ok()
      .entity(processDiagramLayout)
      .type(MediaType.APPLICATION_JSON)
      .build();
    
  }


}
