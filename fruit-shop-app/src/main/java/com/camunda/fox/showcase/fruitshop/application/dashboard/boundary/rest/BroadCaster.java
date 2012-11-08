package com.camunda.fox.showcase.fruitshop.application.dashboard.boundary.rest;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.DiagramNode;
import org.activiti.engine.repository.ProcessDefinition;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

import com.camunda.fox.showcase.fruitshop.application.dashboard.monitoring.MonitoringEvent;

@Singleton
@DependsOn("ProcessArchiveSupport")
public class BroadCaster {

  private static Logger LOGGER = Logger.getLogger(BroadCaster.class.getName());
  
  @Inject
  private HistoryService historyService;
  
  @Inject
  private RepositoryService repositoryService;
  
  @Inject
  private RuntimeService runtimeService;
  
  private DiagramLayout processDiagramLayout;
  private String processDefinitionId;
  private Map<String, Long> activityCounts;

  @PostConstruct
  public void refresh() {

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
      .processDefinitionKey("sid-E1DEF3AA-1321-4DBE-9877-D18DDF51AC6D")
      .latestVersion()
      .singleResult();
    processDefinitionId = processDefinition.getId();

    processDiagramLayout = repositoryService.getProcessDiagramLayout(processDefinitionId);

    Map<String, Long> activityInstanceCounts = new HashMap<String, Long>();

    for (int i = 0; i < processDiagramLayout.getNodes().size(); i++) {
      DiagramNode node = processDiagramLayout.getNodes().get(i);
      // TODO: get all the counts in a single DB query!!
      long count = 0;

      if (node.getId().contains("End")) {
        count = historyService.createHistoricActivityInstanceQuery()
          .activityId(node.getId())
          .processDefinitionId(processDefinitionId)
          .finished()
          .count();
      } else {
        count = runtimeService.createExecutionQuery()
          .activityId(node.getId())
          .processDefinitionId(processDefinitionId)
          .count();
      }
      activityInstanceCounts.put(node.getId(), count);
    }

    activityCounts = activityInstanceCounts;

  }

  @Asynchronous
  public void broadcast(MonitoringEvent monitoringEvent) {

    refresh();
    push();

  }

  public void push() {
    String state = getState();
    
    BroadcasterFactory broadcasterFactory = BroadcasterFactory.getDefault();
    if (broadcasterFactory == null) {
      LOGGER.log(Level.WARNING, "Not pushing because WebSocket broadCaster factory not found");
    } else {
      Broadcaster broadcaster = broadcasterFactory.lookup("control", true);
      if (broadcaster != null) {
        broadcaster.broadcast(state);
      }
    }
  }

  public String getState() {
    StringWriter jsonWriter = new StringWriter();
    jsonWriter.write("{");
    int i = 0;
    for (Entry<String, Long> count : activityCounts.entrySet()) {
      jsonWriter.write("\"" + count.getKey() + "\"");
      jsonWriter.write(" : ");
      jsonWriter.write(String.valueOf(count.getValue()));
      if (i != (processDiagramLayout.getNodes().size() - 1)) {
        jsonWriter.write(", ");
      }
      i++;
    }

    jsonWriter.write("}");
    return jsonWriter.toString();

  }
}
