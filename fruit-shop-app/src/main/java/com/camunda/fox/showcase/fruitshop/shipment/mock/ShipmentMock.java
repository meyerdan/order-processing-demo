package com.camunda.fox.showcase.fruitshop.shipment.mock;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;

/**
 *  We periodically trigger all waiting executions for shipment
 */
@Stateless
public class ShipmentMock {
	
	@Inject
	private RuntimeService runtimeService;
	
    @Schedule(second = "*/15", minute = "*/1", hour = "*", persistent = false)
    public void shipmentPerformed() {
    	
    	List<Execution> waitingProcessInstances = runtimeService.createExecutionQuery()
    		.messageEventSubscriptionName("shipmentPerformed")
    		.list();
    	
    	for (Execution execution : waitingProcessInstances) {
			runtimeService.messageEventReceived("shipmentPerformed", execution.getId());
		}
    	
    }

}
