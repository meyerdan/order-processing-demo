package com.camunda.fox.showcase.fruitshop.application.dashboard.monitoring;

public class MonitoringEvent {

  private String activityId;

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private String type;

}
