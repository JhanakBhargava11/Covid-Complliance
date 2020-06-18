package com.xebia.innovationportal.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

public class EmailSender {

	public static void setEmail(String recipient, String subject, String text) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipient);
		email.setSubject(subject);
		email.setText(text);
		sendMailUsingTLS(email);
	}

	public static void sendMailUsingTLS(SimpleMailMessage email) {
		String host = "outlook.office365.com";
		String username = "noreply_helpdesk@xebia.com";
		String password = "rxjywnwmtswvpzbl";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");// 587
		sendMail(properties, username, password, email);
	}

	public static void sendMail(Properties properties, String username, String password, SimpleMailMessage email) {
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()[0]));
			msg.setSubject(email.getSubject());
			msg.setText(email.getText());
			Transport.send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
