package com.nice.coffee.types;

import java.util.Map;

public class UserOrder 
{
	private String email;
	//maps coffee-name to quantity
	private Map<String, Integer> order;

    public UserOrder(String email, Map<String, Integer> order) {
        this.email = email;
        this.order = order;
    }
	
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<String, Integer> order) {
        this.order = order;
    }
}
