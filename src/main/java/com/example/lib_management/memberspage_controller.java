package com.example.lib_management;

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

import MainCode.Members;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class memberspage_controller implements Initializable {

    @FXML
    private ImageView Add_member;

    @FXML
    private Label Date_time_label;

    @FXML
    private Button Remove_member, add_member;

    @FXML
    private TableColumn<Members, String> contact;

    @FXML
    private TableColumn<Members, String> bookreq;

    @FXML
    private TableColumn<Members, Integer> id;

    @FXML
    private TableColumn<Members, String> join_date;

    @FXML
    private TableView<Members> members_table;

    @FXML
    private TableColumn<Members, String> name;

    @FXML
    private TableColumn<Members, Integer> noOfIssues;

    @FXML
    private TableColumn<Members, Integer> fine_col;

    @FXML
    private TextField search;

    @FXML
    void goToDashBoard(ActionEvent event) throws IOException {
        App.changescene("admindashboard", event);
    }

    @FXML
    public void remove() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                "Nightmare111");
        ;
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        try {
            if (members_table.getSelectionModel().getSelectedItem().getFine() < 2000) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Cannot Remove User.\nAccumulated Fine Is Below 2000Rs.");
                alert.show();
                return;
            }

            insert = connection.prepareStatement(
                    "DELETE FROM members WHERE ( id= ? )");
            insert.setInt(1, members_table.getSelectionModel().getSelectedItem().getMemberId());
            insert.executeUpdate();
            App.loadData("user", "admin");
        } catch (

        Exception e) {
            System.out.println(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    // TODO: handle exception
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

    private void addListToTable() {

        Connection connection = null;
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        try {

            id.setCellValueFactory(new PropertyValueFactory<Members, Integer>("memberId"));
            name.setCellValueFactory(new PropertyValueFactory<Members, String>("name"));
            join_date.setCellValueFactory(new PropertyValueFactory<Members, String>("dateJoined"));
            contact.setCellValueFactory(new PropertyValueFactory<Members, String>("contact"));
            bookreq.setCellValueFactory(new PropertyValueFactory<Members, String>("request"));
            noOfIssues.setCellValueFactory(new PropertyValueFactory<Members, Integer>("noOfBooks"));
            fine_col.setCellValueFactory(new PropertyValueFactory<Members, Integer>("fine"));

            members_table.setItems(App.members);

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

    private ObservableList<Members> searchMembers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListToTable();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Date_time_label
                        .setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE-MMM-d-yyyy HH:mm:ss")));
            }
        };
        timer.start();

        search.setOnKeyTyped((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                searchMembers.clear();
                for (Members member : App.members) {
                    try {
                        if (search.getText().isEmpty()) {
                            addListToTable();
                        }
                        if (member.getName().startsWith(search.getText()))
                            searchMembers.add(member);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                members_table.setItems(searchMembers);
            }
        });
    }

}
