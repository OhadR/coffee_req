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
            ordersRepository.removeUsersOrders(finalizedOrder.getUserOrders());
        }

        log.info(MessageFormat.format("Done handling user order {0}.", updatedUserOrder));
    }

    private FinalizedOrder tryToFinalizeOrder() {
        log.info("Trying to finalize order.");
        List<TimedUserOrder> allUsersOrdersList = ordersRepository.getAllUsersOrder();
        Collections.sort(allUsersOrdersList);
        return tryToFinalizeOrder(allUsersOrdersList, FIXED_TOTAL_ORDER_SIZE);
    }

    private FinalizedOrder tryToFinalizeOrder(List<TimedUserOrder> ordersList, int totalOrderSize) {
        if (ordersList.isEmpty() || totalOrderSize <= 0 ){
            return null;
        }
        UserOrder firstUserOrder = ordersList.get(0);
        int firstOrderSize = firstUserOrder.getTotalSize();
        if (totalOrderSize == firstOrderSize){
            log.info(MessageFormat.format("Creating group and adding user {0} ordering {1} coffee pieces.",
                    firstUserOrder.getEmail(), firstOrderSize));
            return new FinalizedOrder(Arrays.asList(firstUserOrder));
        }

        //try to find group that can fit the first order
        List<TimedUserOrder> listWithoutFirst = ordersList.subList(1, ordersList.size());
        FinalizedOrder finalizedOrder = tryToFinalizeOrder(listWithoutFirst, totalOrderSize - firstOrderSize);
        //check if found group for the first order
        if (finalizedOrder != null){
            log.info(MessageFormat.format("Adding user {0} ordering {1} coffee pieces.",
                    firstUserOrder.getEmail(), firstOrderSize));
            //add the first order to this group.
            finalizedOrder.getUserOrders().add(firstUserOrder);
        }
        else{
            //try to find finalized group of orders, without the first order
            finalizedOrder = tryToFinalizeOrder(listWithoutFirst, totalOrderSize);
        }

        return finalizedOrder; //either a null or a group of totalOrderSize orders
    }
}
