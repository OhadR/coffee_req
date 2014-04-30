package com.nice.coffee.types;

import java.util.Map;

public class UserOrder 
{
	protected String				username;

	//maps coffe-name to quantity
	protected Map<String, Integer>		order;


	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Integer> getOrder() {
		return order;
	}

	public void setOrder(Map<String, Integer> order) {
		this.order = order;
	}
	

}
