package com.nice.coffee.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nice.coffee.email.MailSender;
import com.nice.coffee.repository.OrdersRepository;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

@Controller
public class TestController 
{
	private static Logger log = Logger.getLogger(TestController.class);

    @Autowired
	private MailSender mailSender;
    
    @Autowired
    private OrdersRepository ordersRepository;

	@RequestMapping("/test/mail")
	protected void mail(
			@RequestParam("to") String to,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		log.info( "sending email" );
		Map<String, Integer> order = new HashMap<String, Integer>();
		order.put("A", 1);
		order.put("B", 2);
		UserOrder userOrder = new UserOrder(to, order);
		mailSender.sendOrderConfirmationEmail(userOrder);
		response.getWriter().println("mail sent");

	}
	
	@RequestMapping("/test/logDb")
	protected void logDB(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		log.info( "logging DB" );
		List<TimedUserOrder> allOrders = ordersRepository.getAllUsersOrder();
    	StringBuffer sb = new StringBuffer();
		for(TimedUserOrder entry : allOrders)
    	{
    		sb.append(entry.getEmail() + " : " + entry.toString());
    		sb.append("\n");
    	}
		log.info(sb.toString());

	}

}
