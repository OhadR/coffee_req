package com.nice.coffee.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nice.coffee.email.MailSender;
import com.nice.coffee.types.UserOrder;




@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);
	
	@Autowired
	private MailSender mailSender;

	@RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");

	}
	
	@RequestMapping("/mail")	
	protected void mail(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		log.info( "sending email" );
		Map<String, Integer> order = new HashMap<String, Integer>();
		order.put("A", 1);
		order.put("B", 2);
		UserOrder userOrder = new UserOrder("ohad.redlich@nice.com", order);
		mailSender.sendOrderConfirmationEmail(userOrder);
		response.getWriter().println("mail sent");

	}

	@RequestMapping("/order")	
	protected void order(
			@RequestParam("order") String userOrder,
			HttpServletResponse response) throws Exception{
		log.info( "got to order: " + userOrder );
		response.getWriter().println("OK");

	}

}
