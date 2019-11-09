package com.houarizegai.mailssender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MailsSender.fxml"));

        stage.setScene(new Scene(root));
        stage.setTitle("Mail Sender");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
