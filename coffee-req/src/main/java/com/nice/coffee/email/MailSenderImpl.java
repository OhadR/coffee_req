package com.nice.coffee.email;

import com.nice.coffee.coffee.CoffeeProvider;
import com.nice.coffee.coffee.UnknownCoffeeException;
import com.nice.coffee.types.FinalizedOrder;
import com.nice.coffee.types.UserOrder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

	private static Logger log = Logger.getLogger(MailSenderImpl.class);

    @Autowired
    private CoffeeProvider coffeeProvider;

    private Session session;

	public MailSenderImpl(){
		final String username = "nice.platform@gmail.com";
		final String password = "0545460321";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		//    	props.put("mail.smtp.port", "465");

//		session = Session.getDefaultInstance(props, null);
		session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		});

	}

	protected void send(SimpleMailMessage msg) throws MailException{
		log.info(msg.toString());

		try 
		{
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress("ohadr.developer@gmail.com", "ohadr.com Admin"));
		    message.addRecipient(Message.RecipientType.TO,
		    	     new InternetAddress( msg.getTo()[0] ));		//Spring's getTo returns String[]

		    message.setRecipients(Message.RecipientType.TO, 
					InternetAddress.parse( msg.getTo().toString() ));
			
		    message.setSubject( msg.getSubject() );
			message.setText( msg.getText() );

			Transport.send(message);
		} 
		catch (MessagingException e) 
		{
			log.error("MessagingException: ", e);
			throw new RuntimeException(e);
		} 
		catch (UnsupportedEncodingException e) 
		{
			log.error("UnsupportedEncodingException: ", e);
			e.printStackTrace();
		}    	
	}

	public void sendOrderConfirmationEmail(UserOrder userOrder) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(userOrder.getEmail());
		msg.setSubject("Nicepresso Order Confirmation");

		StringBuffer messageBodyBuffer = new StringBuffer();
        messageBodyBuffer.append("You have placed your order request as follow:");
        messageBodyBuffer.append("\n");
        printUserOrder(messageBodyBuffer, userOrder);
                messageBodyBuffer.append("The total price of your expected order is:" + calculatePrice(userOrder));
        messageBodyBuffer.append("\n");
        messageBodyBuffer.append("We will send you another mail when an order of 10 slaves will include you.");
        messageBodyBuffer.append("\n\n");
        messageBodyBuffer.append("Yours,\nNicepresso.");

        msg.setText(messageBodyBuffer.toString());
		send(msg);
	}

    private String calculatePrice(UserOrder userOrder) {
        double totalOrderPrice = 0;
        for(Map.Entry<String, Integer> coffeeSleeveOrder : userOrder.getOrder().entrySet())
        {
            try {
                double coffeeSleevePrice = coffeeProvider.getCoffeePrice(coffeeSleeveOrder.getKey());
                totalOrderPrice += (coffeeSleevePrice * coffeeSleeveOrder.getValue());
            } catch (UnknownCoffeeException e) {
                return "Could not calculate total order price";
            }
        }

        return Double.toString(totalOrderPrice);
    }

    public void sendFinalizedOrder(FinalizedOrder finalizedOrder)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		
		List<UserOrder> usersOrders = finalizedOrder.getUserOrders();
		String[] tos = new String[usersOrders.size()];
		int i = 0;
		for(UserOrder userOrder : usersOrders)
		{
			String to = userOrder.getEmail();
			tos[i] = to;
			i++;
		}

		msg.setTo(tos);
		send(msg);
		
		
		msg.setSubject("Nicepresso final order found!");
        StringBuffer messageBodyBuffer = new StringBuffer();

        messageBodyBuffer.append("this is a message to let you know that there are enough orders so you can purchase coffee!\n");
        messageBodyBuffer.append("This are the emails of the people you should make the purchase with: ");
        String comma = "";
        for (UserOrder userOrder : finalizedOrder.getUserOrders()) {
            messageBodyBuffer.append(comma);
            messageBodyBuffer.append(userOrder.getEmail());
            comma = ", ";
        }
        messageBodyBuffer.append("\n\n");
        messageBodyBuffer.append("Please find below the detailed list of the order to make:");
        printFinalizedOrder(messageBodyBuffer, finalizedOrder.getUserOrders());
        messageBodyBuffer.append("\n\n");
        messageBodyBuffer.append("All is left to do is that one of you will make the order :)\n\n");
        messageBodyBuffer.append("Thanks you for using Nicepresso.");

        msg.setText(messageBodyBuffer.toString());

		send(msg);
	}

    private void printFinalizedOrder(StringBuffer messageBodyBuffer, List<UserOrder> userOrders) {
        for (UserOrder userOrder : userOrders) {
            messageBodyBuffer.append("Order of user: " + userOrder.getEmail() + "\n");
            printUserOrder(messageBodyBuffer, userOrder);
            messageBodyBuffer.append("\n");
            messageBodyBuffer.append("User " + userOrder.getEmail() + " needs to pay " + calculatePrice(userOrder));
            messageBodyBuffer.append("\n\n");

        }
    }

    private void printUserOrder(StringBuffer messageBodyBuffer, UserOrder userOrder) {
        for(Map.Entry<String, Integer> coffeeSleeveOrder : userOrder.getOrder().entrySet()){
            messageBodyBuffer.append(coffeeSleeveOrder.getKey());
            messageBodyBuffer.append("\t");
            messageBodyBuffer.append(coffeeSleeveOrder.getValue() + " Coffee Sleeves");
            messageBodyBuffer.append("\t");
            try {
                double coffeeSleevePrice = coffeeProvider.getCoffeePrice(coffeeSleeveOrder.getKey());
                messageBodyBuffer.append(coffeeSleevePrice * coffeeSleeveOrder.getValue());
            } catch (UnknownCoffeeException e) {
                messageBodyBuffer.append("UNKNOWN PRICE");
            }
            messageBodyBuffer.append("\n");
        }
    }

}
