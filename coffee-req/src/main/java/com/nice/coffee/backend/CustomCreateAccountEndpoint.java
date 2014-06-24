package com.nice.coffee.backend;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ohadr.auth_flows.web.AuthenticationFlowsException;
import com.ohadr.auth_flows.web.CreateAccountEndpoint;


@Component
public class CustomCreateAccountEndpoint extends CreateAccountEndpoint
{
	private static Logger log = Logger.getLogger(CustomCreateAccountEndpoint.class);

	public void additionalValidations(String email, String password) throws AuthenticationFlowsException 
	{
		log.info("this is a custom message from CustomCreateAccountEndpoint");
		
		String[] parts = email.split("@");
		String domain = parts[1];
		if( ! domain.equalsIgnoreCase( "nice.com" ) )
		{
			log.error("domain is: " + domain);
			throw new AuthenticationFlowsException( "only emails with NICE domain are acceptable." );
		}
		
		super.additionalValidations(email, password);
	}

}
