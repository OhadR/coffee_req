package com.nice.coffee.web;

import javax.servlet.http.HttpServletResponse;

import com.nice.coffee.backend.CoffeeOrderHandler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nice.coffee.types.UserOrder;



@Controller
public class WebController 
{
	private static Logger log = Logger.getLogger(WebController.class);

    @Autowired
    CoffeeOrderHandler coffeeOrderHandler;


    @RequestMapping(value = "/order", method = RequestMethod.POST)
    protected void order(
            @RequestBody String json,
            HttpServletResponse response) throws Exception{
        log.info( "New order recieved: " + json);
        UserOrder userOrder = UserOrder.parseJson(json);
        try{
            coffeeOrderHandler.handleUserOrder(userOrder);
        }
        catch (Throwable throwable)
        {
            //todo return error status to the AJAX
            log.error( "error handling user's order", throwable);
    		response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
    		return;

        }
//        response.getWriter().println("OK"); //todo this is obviously what gives the error in the client side. need to fix with the help of Irit. I got the feeling that the signature of the method will not to be changed to return a response object.
        log.info( "finished handling user's order - OK");
        response.setContentType("text/html"); 
		response.setStatus(response.SC_OK);

    }

	@RequestMapping("/ping")	
	protected void ping(
			HttpServletResponse response) throws Exception{
		log.info( "got to ping" );
		response.getWriter().println("ping response: pong");
	}
	
	@RequestMapping("/secured/ping")	
	protected void securedPing(
			HttpServletResponse response) throws Exception{
		log.info( "got to secured ping" );
		response.getWriter().println("secured ping response: pong");
	}

	/*
	@RequestMapping("/getAuthenticatedUser")	
	protected void getAuthenticatedUser(
			HttpServletResponse response) throws Exception
	{
		log.info( "getAuthenticatedUser()" );
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName(); //get logged in username
		log.info( "logged in user: " + name );
		response.getWriter().println(name);
	}
	*/

}
