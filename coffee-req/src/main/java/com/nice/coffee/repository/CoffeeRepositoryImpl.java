package com.nice.coffee.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

public class CoffeeRepositoryImpl implements CoffeeRepository 
{
	private static final String USERNAME_NAME = "username";
	private static final String ORDER_NAME = "Order";

	private static Logger log = Logger.getLogger(CoffeeRepositoryImpl.class);

	private static final String USER_DB_KIND = "User";

	private DatastoreService datastore;

	public CoffeeRepositoryImpl()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UserOrder updateUserOrder(UserOrder userOrder) 
	{
		Entity dbUser = new Entity(USER_DB_KIND, userOrder.getUsername());		//the username is the key

		dbUser.setProperty(USERNAME_NAME, userOrder.getUsername());
		dbUser.setProperty(ORDER_NAME, userOrder.getOrder());

		datastore.put(dbUser);
		
		return userOrder;		//TODO
		
	}

	public List<TimedUserOrder> getAllUsersOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeUsersOrders(List<String> usersToRemove) {
		// TODO Auto-generated method stub
		
	}
	
}
