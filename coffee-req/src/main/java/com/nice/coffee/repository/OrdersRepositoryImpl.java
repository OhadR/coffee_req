package com.nice.coffee.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.*;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

@Component
public class OrdersRepositoryImpl implements OrdersRepository
{

	private static Logger log = Logger.getLogger(OrdersRepositoryImpl.class);
	private static final String USERNAME_NAME = "username";
	private static final String ORDER__DATE_NAME = "Order";

	private static final String USER_DB_KIND = "User";

	private DatastoreService datastore;

	public OrdersRepositoryImpl()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UserOrder updateUserOrder(UserOrder userOrder) 
	{
		log.info("storing the user's order: " + userOrder);
		
		Entity dbUser = new Entity(USER_DB_KIND, userOrder.getEmail());		//the username is the key

		dbUser.setProperty(USERNAME_NAME, userOrder.getEmail());
		for(Map.Entry<String, Integer> entry : userOrder.getOrder().entrySet())
		{
			dbUser.setProperty(entry.getKey(), entry.getValue());
		}
		dbUser.setProperty(ORDER__DATE_NAME, new Date( System.currentTimeMillis()) );

		datastore.put(dbUser);
		
		return userOrder;		//TODO
	}

	public List<TimedUserOrder> getAllUsersOrder() 
	{
		return new ArrayList<TimedUserOrder>();
		
/*		Key userKey = KeyFactory.createKey(USER_DB_KIND, username);
		Entity entity;
		try 
		{
			entity = datastore.get(userKey);
			log.debug("got entity of " + username + ": " + entity);
		} 
		catch (EntityNotFoundException e) 
		{
			log.error("entity of " + username + " not found");
			throw new UsernameNotFoundException(username, e);
		}
*/	}

	public void removeUsersOrders(List<UserOrder> usersToRemove) {
		// TODO Auto-generated method stub
		
	}
	
}
