package com.nice.coffee.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

public class OrdersRepositoryImpl implements OrdersRepository
{

	private static Logger log = Logger.getLogger(OrdersRepositoryImpl.class);
	private static final String USERNAME_NAME = "username";
	private static final String ORDER_NAME = "Order";

	private static final String USER_DB_KIND = "User";

	private DatastoreService datastore;

	public OrdersRepositoryImpl()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UserOrder updateUserOrder(UserOrder userOrder) {
		Entity dbUser = new Entity(USER_DB_KIND, userOrder.getEmail());		//the username is the key

		dbUser.setProperty(USERNAME_NAME, userOrder.getEmail());
		dbUser.setProperty(ORDER_NAME, userOrder.getOrder());

		datastore.put(dbUser);
		
		return userOrder;		//TODO
	}

	public List<TimedUserOrder> getAllUsersOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeUsersOrders(List<UserOrder> usersToRemove) {
		// TODO Auto-generated method stub
		
	}
	
}
