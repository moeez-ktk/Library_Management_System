package com.example.lib_management;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class signuppage_controller implements Initializable {

    @FXML
    private Button Createacc_button, loginback;

    @FXML
    private Label Date_time_label;

    @FXML
    private ImageView lib_logo;

    @FXML
    private ImageView login_image;

    @FXML
    private TextField signup_password, security_ques, answer_text, contact, lastname_text, firstname_text;

    @FXML
    private CheckBox terms_checkbox;

    @FXML
    public void createAccount() {

        if (firstname_text.getText().isEmpty()||contact.getText().isEmpty()||signup_password.getText().isEmpty()||security_ques.getText().isEmpty()||answer_text.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Some Fields Are Empty.");
            alert.setContentText("First Name Must Not Be Empty.");
            alert.showAndWait();
            return;
        }
        String name;
        if (!lastname_text.getText().isEmpty())
            name = firstname_text.getText() + " " + lastname_text.getText();
        else
            name = firstname_text.getText();

        Connection connection = null;
        PreparedStatement insert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;
        ResultSet resultset = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                    "root",
                    "Nightmare111");
            check = connection.prepareStatement("SELECT * FROM members WHERE name = ?");
            check.setString(1, name);
            resultSet = check.executeQuery();
            insert = connection.prepareStatement("SELECT * FROM newmembers WHERE name = ?");
            insert.setString(1, name);
            resultset = insert.executeQuery();
            System.out.println("point1");

            if (resultSet.isBeforeFirst() || resultset.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username Already exists.");
                alert.show();
            } else {
                {
                    System.out.println("point2");
                    if (terms_checkbox.isSelected()) {
                        insert = connection.prepareStatement(
                                "INSERT INTO newmembers (name,  contact, password, securityquestion, securityanswer) VALUES( ?, ?, ?, ?, ?)");

                        System.out.println("point3");
                        insert.setString(1, name);
                        insert.setString(2, contact.getText());
                        insert.setString(3, signup_password.getText());
                        insert.setString(4, security_ques.getText());
                        insert.setString(5, answer_text.getText());
                        insert.executeUpdate();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(
                                "Your Request For Account Creation Has Been Sent.\nYou Will Be Able to Login When Your Request Is Accepted");
                        alert.show();
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if (resultset != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if (check != null) {
                try {
                    check.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (insert != null) {
                try {
                    insert.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }

    }

    public void login(ActionEvent event) throws IOException {
        App.changescene("loginpage", event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Date_time_label
                        .setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE-MMM-d-yyyy HH:mm:ss")));
            }
        };
        timer.start();

    }

}
