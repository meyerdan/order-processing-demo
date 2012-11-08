package com.camunda.fox.showcase.fruitshop.payment.delegate.mock;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("config/payment/mock")
public class MockPaymentServiceToggle {

	@Inject
	private MockPaymentServiceDelegate paymentServiceDelegate;
	
	@GET
	@Path("/toggle")
	public String toggle() {
		boolean enabled = !paymentServiceDelegate.isEnabled();
		paymentServiceDelegate.setEnabled(enabled);
		return "Payment service is now " + (enabled ? "enabled":"disabled");
	}
}
