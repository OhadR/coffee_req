package com.nice.coffee.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

public class OrdersRepositoryImpl implements OrdersRepository
{
	private static Logger log = Logger.getLogger(OrdersRepositoryImpl.class);

	private DatastoreService datastore;

	public OrdersRepositoryImpl()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UserOrder updateUserOrder(UserOrder userOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TimedUserOrder> getAllUsersOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeUsersOrders(List<String> usersToRemove) {
		// TODO Auto-generated method stub
		
	}


}