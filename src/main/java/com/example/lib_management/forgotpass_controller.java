package com.example.lib_management;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class forgotpass_controller implements Initializable {

    private static String ans = "";

    @FXML
    private Label Date_time_label;

    @FXML
    private TextField answer;

    @FXML
    private Label displaypass;

    @FXML
    private AnchorPane forgot_password_anchor;

    @FXML
    private ImageView forgot_password_bg_image;

    @FXML
    private TextField name;

    @FXML
    private Label question;

    @FXML
    void search() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                "Nightmare111");
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        try {

            insert = connection.prepareStatement(
                    "SELECT securityquestion, securityanswer FROM members WHERE name = ?");
            insert.setString(1, name.getText());
            resultSet = insert.executeQuery();
            if (resultSet.next()) {
                question.setText(resultSet.getString("securityquestion"));
                ans = resultSet.getString("securityanswer");
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Incorrect Username");
                alert.showAndWait();
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    @FXML
    void submit() throws SQLException {
        if (answer.getText() != null && answer.getText().equals(ans)) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                    "Nightmare111");
            PreparedStatement insert = null;
            ResultSet resultSet = null;

            try {
                insert = connection.prepareStatement("SELECT password FROM members WHERE name = ?");
                insert.setString(1, name.getText());
                resultSet = insert.executeQuery();
                if (resultSet.next())
                    displaypass.setText(resultSet.getString("password"));
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Incorrect Answer. Try again");
            alert.showAndWait();
        }
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
