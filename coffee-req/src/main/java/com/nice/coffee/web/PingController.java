package com.nice.coffee.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PingController 
{
	private static Logger log = Logger.getLogger(PingController.class);

	@RequestMapping(value = "/ping")	
	protected void ping(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		log.info( "got to ping" );
		System.out.print( "pong" );
		response.getWriter().println("ping response: pong");

	}
}
