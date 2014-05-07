package com.nice.coffee.repository;

import java.util.List;

import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;


public interface OrdersRepository
{
	/**
	 * 
	 * @param userOrder
	 * @return the updated entry for this user
	 */
	public UserOrder updateUserOrder(UserOrder userOrder);
	
    public void removeUsersOrders(List<String> usersToRemove);
    
	public UserOrder getUserEntry(String username);
	
	public List<TimedUserOrder> getAllUsersOrder();
}
