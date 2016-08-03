package com.eragano.eraganoapps.lupapassword;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Deztu on 6/30/2016.
 */
public class Gmail {
    final String emailPort= "587"; //gmail smtp port
    final String smtpAuth= "true";
    final String starttls= "true";
    final String emailHost= "smtp.gmail.com";

    String fromEmail= null;
    String fromPassword= null;
    String toEmailList= null;
    String emailSubject= null;
    String emailBody= null;

    Properties emailProperties= null;
    Session mailSession= null;
    MimeMessage emailMessage= null;

    public Gmail(){

    }

    public Gmail(String fromEmail, String fromPassword, String toEmailList, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmailList = toEmailList;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties= System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", smtpAuth);
        emailProperties.put("mail.smtp.starttls.enable", starttls);
    }

    public MimeMessage createEmailMessage() throws AddressException, MessagingException, UnsupportedEncodingException{
        mailSession= Session.getDefaultInstance(emailProperties, null);
        emailMessage= new MimeMessage(mailSession);

        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailList));
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");

        return emailMessage;
    }

    public void senEmail() throws AddressException, MessagingException {
        Transport transport= mailSession.getTransport("smtp");
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }
}
