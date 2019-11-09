package com.houarizegai.mailssender.engine;

public class MailsSenderEngineDemo {
    public static void main(String[] args) {
        new MailsSenderEngine().setSenderMail("youremail@gmail.com")
                .setSenderPassword("yourPassword")
                .setMessageSubject("JavaFX GUI Development Course")
                .setMessageContent(new StringBuilder().append("<h1>JavaFX GUI Development Course Updates</h1>").append("<br />")
                        .append("<p> We want to tell you in an important update that we will start Wednesday 13/02/2019 and not Monday 11/02/2019, the rest pieces of information are the same.</p>").append("<br />")
                        .append("<p>Again, If you have any questions about something in the course, please feel free to contact us through ")
                        .append("<a href='https://www.fb.com/GeekHouari'>Houari page</a></p>")
                        .append("<p>See you in class :)</p>").toString())
                .setMessageType("text/html")
                .setMessageRecipients("houarizegai14@gmail.com", "wrkhouari@gmail.com")
                .send();
    }
}
