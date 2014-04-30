package com.nice.coffee.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nice.coffee.backend.CoffeeOrderHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nice.coffee.email.MailSender;
import com.nice.coffee.types.UserOrder;



@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);

    @Autowired
    CoffeeOrderHandler coffeeOrderHandler;

    @Autowired
	private MailSender mailSender; //todo remove this after finish testing

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    protected void order(
            @RequestBody String json,
            HttpServletResponse response) throws Exception{
        log.info( "New order recieved: " + json);
        UserOrder userOrder = UserOrder.parseJson(json);
        try{
            coffeeOrderHandler.handleUserOrder(userOrder);
            response.getWriter().println("OK"); //todo this is obviously what gives the error in the client side. need to fix with the help of Irit. I got the feeling that the signature of the method will not to be changed to return a response object.
        }
        catch (Throwable throwable){
            //todo return error status to the AJAX
        }

    }

	@RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");

	}
	
	@RequestMapping("/mail")//todo remove this after finish testing
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
	



}
