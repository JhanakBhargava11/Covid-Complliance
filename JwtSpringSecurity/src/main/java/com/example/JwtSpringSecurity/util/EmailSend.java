package com.example.JwtSpringSecurity.util;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSend {
    public static void setEmail(String recipient, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setText(text);
        System.out.println("zxcv");
        sendMailUsingTLS(email);
    }

    public static void sendMailUsingTLS(SimpleMailMessage email) {
//        String host = "outlook.office365.com";
        String host = "smtp.gmail.com";
        String username = "jhanak.bhargava1998@gmail.com";
        String password = "jhanak.b";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");// 587
        System.out.println("asdf");
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
            System.out.println("qwe");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        EmailSend.setEmail("500053765@stu.upes.ac.in","otpgenerated","1234");
    }
}
