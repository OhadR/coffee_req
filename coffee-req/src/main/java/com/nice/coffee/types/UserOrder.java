package com.nice.coffee.types;

import java.util.Map;

public class UserOrder 
{
	private String				username;

	//maps coffee-name to quantity
	private Map<String, Integer>		order;


    public UserOrder(String username, Map<String, Integer> order) {
        this.username = username;
        this.order = order;
    }

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
