package com.houarizegai.mailssender.controller;

import com.houarizegai.mailssender.App;
import com.houarizegai.mailssender.engine.MailsSenderEngine;
import com.houarizegai.mailssender.global.Constants;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailsSenderController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private JFXTextField fieldEmail;

    @FXML
    private JFXPasswordField fieldPassword;

    @FXML
    private JFXTextField fieldSubject;

    @FXML
    private TextArea areaTo, areaContent;

    private JFXSnackbar toastErrorMsg;

    private FileChooser fileChooser;
    private FileChooser.ExtensionFilter extensionFilter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Init file chooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select *.txt File");

        extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Init snackbar
        toastErrorMsg = new JFXSnackbar(root);
    }

    @FXML
    private void onLoad() {
        File file = fileChooser.showOpenDialog(App.stage);

        if(file != null) {
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }

                areaTo.setText(getEmailsFromString(sb.toString(), "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void onSend() {
        // need to make inputs validation

        new MailsSenderEngine()
                .setSenderMail(fieldEmail.getText().trim())
                .setSenderPassword(fieldPassword.getText())
                .setMessageSubject(fieldSubject.getText().trim())
                .setMessageContent(areaContent.getText().trim())
                .setMessageType("text/html")
                .setMessageRecipients(areaTo.getText().trim().split("[ ,;\n]*"))
                .send();
    }

    private String getEmailsFromString(String stringMails, String separator) {
        Pattern pattern = Pattern.compile(Constants.EMAIL_REGEX);
        Matcher matcher = pattern.matcher(stringMails);

        StringBuilder emailsBuilder = new StringBuilder();
        while(matcher.find()) {
            emailsBuilder
                    .append(stringMails.substring(matcher.start(), matcher.end()))
                    .append(separator);
        }

        return emailsBuilder.toString();
    }

}
