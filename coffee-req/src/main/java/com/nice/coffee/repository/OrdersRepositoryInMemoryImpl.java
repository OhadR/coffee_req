package com.nice.coffee.repository;

import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

@Deprecated
public class OrdersRepositoryInMemoryImpl implements OrdersRepository {
	private static Logger log = Logger.getLogger(OrdersRepositoryInMemoryImpl.class);
    final private static Map<String, TimedUserOrder> inMemoryCache = new HashMap<String, TimedUserOrder>();

    @Override
    public UserOrder updateUserOrder(UserOrder newUserOrder) {
        TimedUserOrder userOrderInCache = inMemoryCache.get(newUserOrder.getEmail());
        if (userOrderInCache == null){
            userOrderInCache = new TimedUserOrder(newUserOrder.getEmail(),newUserOrder.getOrder(), new Date());
            inMemoryCache.put(newUserOrder.getEmail(), userOrderInCache);

        }
        else { //already exists user in the cache --> extend order
            for (Map.Entry<String, Integer> newCoffeeSleevesOrder : newUserOrder.getOrder().entrySet()) {
                Integer prevAmount = userOrderInCache.getOrder().get(newCoffeeSleevesOrder.getKey());
                if (prevAmount == null){
                    prevAmount = 0;
                }
                userOrderInCache.getOrder().put(newCoffeeSleevesOrder.getKey(), prevAmount + newCoffeeSleevesOrder.getValue());
            }
        }

        return userOrderInCache;
    }

    @Override
    public List<TimedUserOrder> getAllUsersOrder() {
        return new ArrayList<TimedUserOrder>(inMemoryCache.values());
    }

    @Override
    public void removeUsersOrders(List<String> usersToRemove) {
        for (String user : usersToRemove) {
            inMemoryCache.remove(user);
        }
    }
    
    
	@Override
	public UserOrder getUserEntry(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}
