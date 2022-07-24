package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class login_controller implements Initializable {

    @FXML
    private Label Date_time_label;

    @FXML
    private Button LOGIN_BUTTON;

    @FXML
    private RadioButton admin_radiobutton;

    @FXML
    private ToggleGroup check;

    @FXML
    private Button forgotpass_button;

    @FXML
    private Label forgotpass_label;

    @FXML
    private ImageView lib_logo;

    @FXML
    private ImageView login_image;

    @FXML
    private AnchorPane loginpage_anchor1;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button signup_BUTTON;

    @FXML
    private RadioButton user_radiobutton;

    @FXML
    private TextField username_textfield;

    @FXML
    void login(ActionEvent event) {

    }

    @FXML
    void setForgotpass_label(ActionEvent event) throws IOException {
        Stage sigupstage1 = new Stage();
        FXMLLoader fxmlLoader12 = new FXMLLoader(App.class.getResource("forgot_password.fxml"));
        Scene scene12 = new Scene(fxmlLoader12.load());
        sigupstage1.setScene(scene12);
        sigupstage1.setTitle("Pioneer Atheneum");
        Image icon = new Image(getClass().getResourceAsStream("liblogo.jpg"));
        sigupstage1.getIcons().add(icon);
        sigupstage1.setResizable(false);
        sigupstage1.show();
    }

    @FXML
    void signupbutton(ActionEvent event) throws IOException {
        App.changescene("signuppage", event);
    }

    @FXML
    void loginUser(ActionEvent event) {

        String name = username_textfield.getText();
        String pass = passwordfield.getText();

        Connection connection = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;

        try {

            if (user_radiobutton.isSelected()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                        "root",
                        "Nightmare111");
                check = connection.prepareStatement("SELECT name , password FROM members WHERE name = ?");
                check.setString(1, name);
                resultSet = check.executeQuery();

                if (resultSet.next()) {
                    String p = resultSet.getString("password");
                    if (pass.equals(p)) {
                        App.userName = name;
                        App.loadData(name, "members");
                        App.changescene("userdashboard", event);
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Incorrect Username or Password");
                    alert.showAndWait();
                }

            } else if (admin_radiobutton.isSelected()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                        "root",
                        "Nightmare111");
                check = connection.prepareStatement("SELECT name , password FROM admin WHERE name = ?");
                check.setString(1, name);
                resultSet = check.executeQuery();

                if (resultSet.next()) {
                    String p = resultSet.getString("password");
                    if (pass.equals(p)) {
                        App.userName = name;
                        App.loadData("", "admin");
                        App.changescene("admindashboard", event);
                    }
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Incorrect Username or Password");
                    alert.showAndWait();
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
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
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
