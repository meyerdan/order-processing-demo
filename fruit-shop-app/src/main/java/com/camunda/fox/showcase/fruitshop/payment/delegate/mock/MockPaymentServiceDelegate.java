package com.camunda.fox.showcase.fruitshop.payment.delegate.mock;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import com.camunda.fox.showcase.fruitshop.payment.boundary.PaymentServiceDelegate;

/**
 * This is just a MOCK that simulates the payment service
 * 
 * @author Daniel Meyer
 *
 */
@ApplicationScoped
public class MockPaymentServiceDelegate implements PaymentServiceDelegate {

	private boolean enabled = true;
	
	public String performPayment(double amount, String customer) {
		
		if(!enabled) {
			throw new RuntimeException("The payment service is unaccessible");
		
		} else {
			
			int nextInt = new Random().nextInt();
			if(nextInt %3 == 0) {
				return "PAYMENT_FAILED";
			
			} else {
				return "PAYMENT_STATUS_OK";
			}
			
		}
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

}
