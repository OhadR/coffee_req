package com.nice.coffee.repository;

import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

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
    public void removeUsersOrders(List<UserOrder> usersToRemove) {
        for (UserOrder userOrder : usersToRemove) {
            inMemoryCache.remove(userOrder.getEmail());
        }
    }
    
    
	@Override
	public void logRepositryContent() 
	{
    	StringBuffer sb = new StringBuffer();
		for(Entry<String, TimedUserOrder> entry : inMemoryCache.entrySet())
    	{
    		sb.append(entry.getKey() + " : " + entry.getValue());
    		sb.append("\n");
    	}
		log.info(sb.toString());
	}
}
