package com.nice.coffee.backend;

import com.nice.coffee.email.MailSender;
import com.nice.coffee.repository.OrdersRepository;
import com.nice.coffee.types.FinalizedOrder;
import com.nice.coffee.types.TimedUserOrder;
import com.nice.coffee.types.UserOrder;

import java.util.Collections;
import java.util.List;

public class CoffeeOrderHandler {
    private OrdersRepository ordersRepository;
    private MailSender mailSender;

    public void handleUserOrder(UserOrder userOrder){
        UserOrder updatedUserOrder = ordersRepository.updateUserOrder(userOrder);

        mailSender.sendOrderConfirmationEmail(updatedUserOrder);

        FinalizedOrder finalizedOrder = tryToFinalizeOrder();

        if (finalizedOrder != null){
            mailSender.sendFinalizedOrder(finalizedOrder);
        }
    }

    private FinalizedOrder tryToFinalizeOrder() {
        List<TimedUserOrder> allUsersOrdersList = ordersRepository.getAllUsersOrder();
        Collections.sort(allUsersOrdersList);
        //todo implement
        return null;
    }
}
