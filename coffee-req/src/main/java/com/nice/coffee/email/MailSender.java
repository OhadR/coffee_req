package com.nice.coffee.email;

import java.util.List;

import com.nice.coffee.types.UserOrder;

public interface MailSender {

	public void sendOrderConfirmationEmail(UserOrder userOrder);
	
	public void sendFinalizedOrder(List<UserOrder> userOrders);
}
