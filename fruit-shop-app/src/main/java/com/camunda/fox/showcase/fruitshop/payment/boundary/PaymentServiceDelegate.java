package com.camunda.fox.showcase.fruitshop.payment.boundary;

public interface PaymentServiceDelegate {
	
	public String performPayment(double d, String customer);

}
