package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

public class userdashboard_controller implements Initializable {

    @FXML
    private Label Date_time_label, user_name, issued_count, books_count;

    @FXML
    private Button aboutlib_info1;

    @FXML
    private Button books_button;

    @FXML
    private AnchorPane books_info;

    @FXML
    private Button dashboard_button;

    @FXML
    private Button issued_button;

    @FXML
    private Button issued_info;

    @FXML
    private Button postareq_button1;

    @FXML
    private TitledPane side_titlepane;

    @FXML
    private Button user_request;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_name.setText(App.userName);
        issued_count.setText(String.valueOf(App.issuedBooks.size()));
        books_count.setText(String.valueOf(App.books.size()));
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Date_time_label
                        .setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE-MMM-d-yyyy hh:mm:ss a")));
            }
        };
        timer.start();
    }

    @FXML
    private void goToBooks(ActionEvent event) throws IOException {
        App.changescene("bookspage_user", event);
    }

    @FXML
    private void goToIssued(ActionEvent event) throws IOException {
        App.changescene("issued_user", event);
    }

    @FXML
    private void goToHistory(ActionEvent event) throws IOException {
        App.changescene("issued_history_user", event);
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        App.books.clear();
        App.members.clear();
        App.issuedBooks.clear();
        App.newMembers.clear();
        App.booksRequests.clear();
        App.history.clear();
        App.changescene("loginpage", event);
    }

}
