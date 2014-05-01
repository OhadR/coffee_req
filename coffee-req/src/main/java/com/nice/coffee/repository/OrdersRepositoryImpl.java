package com.nice.coffee.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private static final String ORDER_DATE_NAME = "Order";

	private static final String USER_DB_KIND = "User";

	private DatastoreService datastore;

	public OrdersRepositoryImpl()
	{
		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	public UserOrder updateUserOrder(UserOrder userOrder) 
	{
		log.debug("storing the user's order: " + userOrder);
		
		Entity dbUser = getUserEntity( userOrder.getEmail() );
		
		//create a new one if does not exist:
		if( dbUser == null )
		{
			log.info("creating a new entity for user " + userOrder.getEmail());
			
			dbUser = new Entity(USER_DB_KIND, userOrder.getEmail());		//the username is the key
		}

		
		dbUser.setProperty(USERNAME_NAME, userOrder.getEmail());
		for(Map.Entry<String, Integer> entry : userOrder.getOrder().entrySet())
		{
			dbUser.setProperty(entry.getKey(), entry.getValue());
		}
		dbUser.setProperty(ORDER_DATE_NAME, new Date( System.currentTimeMillis()) );

		datastore.put(dbUser);
		
		return userOrder;		//TODO
	}

	public List<TimedUserOrder> getAllUsersOrder() 
	{
		log.debug("getAllUserOrder");
		Query q = new Query(USER_DB_KIND);
		PreparedQuery pq = datastore.prepare(q);  

		List<TimedUserOrder> retVal = new ArrayList<TimedUserOrder>();
		
		StringBuffer sb = new StringBuffer();
		for (Entity result : pq.asIterable()) {   
			String username = (String) result.getProperty(USERNAME_NAME);   
			Date date = (Date) result.getProperty(ORDER_DATE_NAME);
			Map<String, Integer> order = new HashMap<String, Integer>();
			//TODO: insert items from DS to order obj.
			TimedUserOrder tuo = new TimedUserOrder(username, order, date);
			retVal.add(tuo);
			sb.append(username + " : " + date );
		}
		
		log.info( sb.toString() );

		return retVal;
	}

	public void removeUsersOrders(List<UserOrder> usersToRemove) 
	{
		for(UserOrder userOrder : usersToRemove)
		{
			Key userKey = KeyFactory.createKey(USER_DB_KIND, userOrder.getEmail());
			log.info( "deleting user " + userOrder.getEmail());
			datastore.delete(userKey);
		}
		
	}
	
	private UserOrder getUserEntry(String username)
	{
		Entity entity = getUserEntity(username);

		UserOrder retVal = new UserOrder();
		retVal.setEmail(username);
		Map<String, Object> properties = entity.getProperties();
		Map<String, Integer> order = new HashMap<String, Integer>();
		for(Map.Entry<String, Object> entry : properties.entrySet())
		{
			log.info("putting " + entry.getKey() + " : " + (Integer) entry.getValue());
			order.put(entry.getKey(), (Integer) entry.getValue());
		}
		retVal.setOrder(order);
		return retVal;
	}

	private Entity getUserEntity(String username)
	{
		Key userKey = KeyFactory.createKey(USER_DB_KIND, username);
		Entity entity;
		try 
		{
			entity = datastore.get(userKey);
			log.debug("got entity of " + username + ": " + entity);
		} 
		catch (EntityNotFoundException e) 
		{
			log.error("entity of " + username + " not found");
			entity = null;
		}

		return entity;
	}

	@Override
	public void logRepositryContent() {
		// TODO Auto-generated method stub
		
	}
}
