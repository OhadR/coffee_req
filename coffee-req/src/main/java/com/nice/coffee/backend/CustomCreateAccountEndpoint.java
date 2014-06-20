package com.nice.coffee.backend;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ohadr.auth_flows.web.AuthenticationFlowsException;


@Component
public class CustomCreateAccountEndpoint extends CreateAccountEndpoint
{
	private static Logger log = Logger.getLogger(CustomCreateAccountEndpoint.class);

	public void additionalValidations(String email, String password) throws AuthenticationFlowsException 
	{
		log.info("this is a custom message from CustomCreateAccountEndpoint");
		
		super.additionalValidations(email, password);
	}

}
