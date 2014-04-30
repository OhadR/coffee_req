package com.nice.coffee.types;


import java.util.List;

public class FinalizedOrder {
    private List<UserOrder> userOrders;

    public FinalizedOrder(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    public List<UserOrder> getUserOrders() {
        return userOrders;
    }

    public void setUserOrders(List<UserOrder> userOrders) {
        this.userOrders = userOrders;
    }

    @Override
    public String toString() {
        StringBuilder userOrdersString = new StringBuilder("{");
        String comma = "";
        for (UserOrder userOrder : userOrders) {
            userOrdersString.append(comma);
            userOrdersString.append(userOrder.toString());
            comma = ", ";
        }
        userOrdersString.append("}");

        return "FinalizedOrder{" +
                "userOrders=" + userOrdersString +
                '}';
    }
}
