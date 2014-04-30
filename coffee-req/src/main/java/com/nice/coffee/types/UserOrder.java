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

    public int getTotalSize(){
        int totalOrderSize = 0;
        for (Integer coffeeQuantity : order.values()) {
            totalOrderSize += coffeeQuantity;
        }
        return totalOrderSize;
    }

    @Override
    public String toString() {
        StringBuilder ordersString = new StringBuilder("(");
        String comma = "";
        for (Map.Entry<String, Integer> orderEntry : order.entrySet()) {
            ordersString.append(comma);
            ordersString.append(orderEntry.getKey());
            ordersString.append(":");
            ordersString.append(orderEntry.getValue());
            comma = ", ";
        }
        ordersString.append(")");

        return "UserOrder{" +
                "email='" + email + '\'' +
                ", order=" + ordersString +
                '}';
    }

}
