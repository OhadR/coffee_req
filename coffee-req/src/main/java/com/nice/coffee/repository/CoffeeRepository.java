package com.nice.coffee.repository;

import java.util.List;

import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;


public interface CoffeeRepository 
{
	/**
	 * 
	 * @param userOrder
	 * @return the updated entry for this user
	 */
	public UserOrder updateUserOrder(UserOrder userOrder);
	
	public List<TimedUserOrder> getAllUsersOrder();

	public void removeUsersOrders(List<String> usersToRemove);
}
