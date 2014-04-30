package com.nice.coffee.types;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class UserOrder 
{
	private String email;
	//maps coffee-name to quantity
	private Map<String, Integer> order;

    /**
     * for destabilization
     */
    public UserOrder() {}


    /**
     *  Create UserOrder instance out of Json
     *  Example for valid json:   {"email":"zbeniash@gmail.com","order":{"Dark Chocolate":1,"Caramel":2,"Bukeela":1}}
     * @param json
     */
    static public UserOrder parseJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserOrder userOrder = mapper.readValue(json, UserOrder.class);
        return userOrder;
    }

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
