package com.nice.coffee.email;

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
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

	private static Logger log = Logger.getLogger(MailSenderImpl.class);

    private Session session;

	
	public MailSenderImpl()
	{
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


	protected void send(SimpleMailMessage msg) throws MailException
	{
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


	public void sendOrderConfirmationEmail(UserOrder userOrder) 
	{
/*		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(userOrder.getEmail());
		msg.setSubject("Order Confirmation");

		StringBuffer buffer = new StringBuffer();
		Map<String, Integer> order = userOrder.getOrder();
		for(Map.Entry<String, Integer> entry : order.entrySet())
		{
			buffer.append( entry.toString() );
			buffer.append("\n");
		}

		msg.setText("this is a confirmation for your order:\n" + buffer.toString());
		send(msg);*/
	}

	public void sendFinalizedOrder(FinalizedOrder finalizedOrder) 
	{
/*		SimpleMailMessage msg = new SimpleMailMessage();
		
		List<UserOrder> usersOrders = finalizedOrder.getUserOrders();
		String[] tos = new String[usersOrders.size()];
		int i = 0;
		for(UserOrder userOrder : usersOrders)
		{
			String to = userOrder.getEmail();
			tos[i] = to;
			i++;
		}
//		msg.setTo(userOrder.getEmail());
		msg.setSubject("Order is Ready");
		msg.setText("this is a message to let you know that there are enough orders so you can purchase coffee!");
		send(msg);*/
	}

}
