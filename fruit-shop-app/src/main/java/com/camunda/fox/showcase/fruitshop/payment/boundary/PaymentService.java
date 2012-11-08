package com.camunda.fox.showcase.fruitshop.payment.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class PaymentService {
	
	@Inject 
	private PaymentServiceDelegate paymentServiceDelegate;

	public String performPayment(double d, String customer) {
		return paymentServiceDelegate.performPayment(d, customer);
	}
	

}
