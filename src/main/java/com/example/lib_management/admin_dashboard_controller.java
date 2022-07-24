package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import MainCode.Books;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class admin_dashboard_controller implements Initializable {


    @FXML
    private Label Date_time_label;

    @FXML
    private Button Members_button;

    @FXML
    private Button aboutlib_info;

    @FXML
    private Button booksRequestsbtn;

    @FXML
    private ImageView booksRequestsicon;

    @FXML
    private Button books_button;

    @FXML
    private Label books_count;

    @FXML
    private AnchorPane books_info;

    @FXML
    private Button dashboard_button;

    @FXML
    private Button issue_requestsbtn;

    @FXML
    private ImageView issue_requestsicon;

    @FXML
    private Button issued_button;

    @FXML
    private Label issued_count;

    @FXML
    private Button issued_info;

    @FXML
    private Button issued_info1;

    @FXML
    private Label issuerequests_count;

    @FXML
    private Label member_count;

    @FXML
    private Button members_info;

    @FXML
    private Label request_count;

    @FXML
    private Button returned_button;

    @FXML
    private TitledPane side_titlepane;

    @FXML
    public void addBook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addbook_popup.fxml"));
        Stage stage = new Stage();
        // FXMLLoader fxmlLoader = new
        // FXMLLoader(App.class.getResource("loginpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pioneer Atheneum");
        Image icon = new Image(getClass().getResourceAsStream("liblogo.jpg"));
        stage.getIcons().add(icon);
        stage.show();
    }

    @FXML
    public void goToHistory(ActionEvent event) throws IOException {
        App.changescene("issued_history_admin", event);
    }

    @FXML
    public void goToMembers(ActionEvent event) throws IOException {
        App.changescene("members_page", event);
    }

    @FXML
    public void goToBooks(ActionEvent event) throws IOException {
        App.changescene("bookspage_admin", event);
    }

    @FXML
    void goToIssue(ActionEvent event) throws IOException {
        App.changescene("issued_admin", event);
    }

    @FXML
    void goToRequests(ActionEvent event) throws IOException {
        App.changescene("members_request", event);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        books_count.setText(String.valueOf(App.books.size()));
        member_count.setText(String.valueOf(App.members.size()));

        issued_count.setText(String.valueOf(App.issuedBooks.size()));
        issuerequests_count.setText(String.valueOf(App.newMembers.size() + App.booksRequests.size()));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Date_time_label
                        .setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE-MMM-d-yyyy hh:mm:ss a")));
            }
        };
        timer.start();
    }

}
