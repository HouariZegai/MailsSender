package com.houarizegai.mailssender.engine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class MailsSenderEngine {
    private static String senderMail;
    private static String senderPassword;

    // Message sending information
    private List<String> msgRecipients;
    private String msgSubject;
    private String msgContent;
    private String msgType;

    public MailsSenderEngine() {
        this.msgType = "text/html";
        msgRecipients = new LinkedList<>();
    }

    /* Start sender information */

    public MailsSenderEngine setSenderMail(String senderMail) {
        this.senderMail = senderMail;
        return this;
    }

    public MailsSenderEngine setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
        return this;
    }

    /* End sender information */

    /* Start message information */

    public MailsSenderEngine setMessageRecipients(List<String> msgRecipients) {
        this.msgRecipients = msgRecipients;
        return this;
    }

    public MailsSenderEngine setMessageRecipients(String... msgRecipients) {
        this.msgRecipients.addAll(Arrays.asList(msgRecipients));
        return this;
    }

    public MailsSenderEngine setMessageSubject(String msgSubject) {
        this.msgSubject = msgSubject;
        return this;
    }

    public MailsSenderEngine setMessageContent(String msgContent) {
        this.msgContent = msgContent;
        return this;
    }

    public MailsSenderEngine setMessageType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    /* End message information */

    public void send() {
        System.out.println("Prepare sending msg ...");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMail, senderPassword);
            }
        });

        Message msg = prepareMessage(session);

        try {
            Transport.send(msg);
            System.out.println("Message send successfully !");
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private Message prepareMessage(Session session) {
        Message msg = null;
        try {
            msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderMail));

            InternetAddress[] recipientAddress = new InternetAddress[msgRecipients.size()];
            for(int i = 0; i < msgRecipients.size(); i++)
                recipientAddress[i] = new InternetAddress(msgRecipients.get(i));
            msg.setRecipients(Message.RecipientType.TO, recipientAddress);

            msg.setSubject(msgSubject);
            msg.setContent(msgContent, msgType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

}
