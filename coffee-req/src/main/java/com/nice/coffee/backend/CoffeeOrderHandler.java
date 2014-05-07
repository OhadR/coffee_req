package com.nice.coffee.backend;

import com.nice.coffee.email.MailSender;
import com.nice.coffee.repository.OrdersRepository;
import com.nice.coffee.types.FinalizedOrder;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CoffeeOrderHandler {
    private static Logger log = Logger.getLogger(CoffeeOrderHandler.class);

    private static final int FIXED_TOTAL_ORDER_SIZE = 10;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MailSender mailSender;

    public void handleUserOrder(UserOrder userOrder){
        log.info(MessageFormat.format("Handling new user order {0}.", userOrder));

        UserOrder updatedUserOrder = ordersRepository.updateUserOrder(userOrder);

        mailSender.sendOrderConfirmationEmail(updatedUserOrder);

        FinalizedOrder finalizedOrder = tryToFinalizeOrder();

        if (finalizedOrder != null){
            log.info(MessageFormat.format("Publishing FinalizedOrder: {0}.", finalizedOrder));
            mailSender.sendFinalizedOrder(finalizedOrder);
        }

        log.info(MessageFormat.format("Done handling user order {0}.", updatedUserOrder));
    }

    private FinalizedOrder tryToFinalizeOrder() {
        log.info("Trying to finalize order.");
        List<TimedUserOrder> allUsersOrdersList = ordersRepository.getAllUsersOrder();
        Collections.sort(allUsersOrdersList);
        return tryToFinalizeOrder(allUsersOrdersList, FIXED_TOTAL_ORDER_SIZE);
    }

    private FinalizedOrder tryToFinalizeOrder(List<TimedUserOrder> ordersList, int totalOrderSize) 
    {
    	int totalSize = 0;
    	int totalNumerOfSleeves = getTotalNumerOfSleeves(ordersList);
    	log.info("totalNumerOfSleeves: "+ totalNumerOfSleeves);
    	if(totalNumerOfSleeves < totalOrderSize)
    	{
    		return null;
    	}
    	
    	List<UserOrder> finalizedOrder = new ArrayList<>();
    	for(TimedUserOrder tuo : ordersList)
    	{
    		//delete this item from the DB, and add it to the "finalized order" list:
    		List<String> userToRemove = new ArrayList<>();
    		userToRemove.add(tuo.getEmail()) ;
    		ordersRepository.removeUsersOrders( userToRemove );
    		finalizedOrder.add(tuo);
    		
    		totalSize += tuo.getTotalSize();
    		
    		if(totalSize >= totalOrderSize)
    		{
    			//we can order!
    			break;
    		}
    	}
    	
    	return new FinalizedOrder(finalizedOrder);
    }

    	
	public UserOrder getOrderPerUser(String username) 
	{
		return ordersRepository.getUserEntry(username);
	}
	
	/**
	 * get total number of "sleeves" to order. instead of iterating "all" the DB each time, 
	 * we hold the total number so we can easily know whether we are ready to make a purchase from 
	 * the coffee supplier
	 * @param ordersList 
	 */
	public int getTotalNumerOfSleeves(List<TimedUserOrder> ordersList)
	{
		int totalSize = 0;
		for(TimedUserOrder tuo : ordersList)
		{
			totalSize += tuo.getTotalSize();
			
		}
		return totalSize;		
	}

}
