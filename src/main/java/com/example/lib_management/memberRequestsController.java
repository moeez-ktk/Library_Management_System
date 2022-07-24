package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import MainCode.Books;
import MainCode.Members;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class memberRequestsController implements Initializable {
    @FXML
    private Label Date_time_label;

    @FXML
    private Button addbtn;

    @FXML
    private ImageView addicon;

    @FXML
    private TableColumn<Books, String> bookAuthor;

    @FXML
    private TableColumn<Books, String> bookName;

    @FXML
    private TableView<Books> bookTable;

    @FXML
    private Button dashboard_btn;

    @FXML
    private ImageView dashboard_icon;

    @FXML
    private Button issuebtn;

    @FXML
    private ImageView issueicon;

    @FXML
    private TableColumn<Members, String> memberContact;

    @FXML
    private TableColumn<Members, String> memberName;

    @FXML
    private TableView<Members> memberTable;

    @FXML
    private ImageView rejectIcon;

    @FXML
    private Button rejectbtn;

    @FXML
    private Button removebtn;

    @FXML
    private ImageView removeicon;

    @FXML
    private TableColumn<Books, String> requestedBy;

    @FXML
    void dashboard(ActionEvent event) throws IOException {
        App.changescene("admindashboard", event);
    }

    @FXML
    void giveBook(ActionEvent event) throws SQLException {
        if (bookTable.getSelectionModel().getSelectedItem() != null) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                    "Nightmare111");
            ;
            PreparedStatement insert = null;
            ResultSet resultSet = null;

            try {
                insert = connection.prepareStatement(
                        "UPDATE books SET issueto = ?, issuedate=?, requestedby=?, duedate=? WHERE name = ?");
                insert.setString(1, bookTable.getSelectionModel().getSelectedItem().getRequestedBy());
                insert.setString(2, LocalDate.now().toString());
                insert.setString(3, "-");
                insert.setString(4, LocalDate.now().plusDays(14).toString());
                insert.setString(5, bookTable.getSelectionModel().getSelectedItem().getBookName());
                insert.executeUpdate();

                insert = connection.prepareStatement(
                        "UPDATE members SET booksissued = (booksissued + 1) WHERE name = ?");
                insert.setString(1, bookTable.getSelectionModel().getSelectedItem().getRequestedBy());
                insert.executeUpdate();
                App.booksRequests.remove(bookTable.getSelectionModel().getSelectedItem());
                App.loadData("", "admin");
            } catch (Exception e) {

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

    }

    @FXML
    void rejectBook(ActionEvent event) throws SQLException {
        if (bookTable.getSelectionModel().getSelectedItem() != null) {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                    "Nightmare111");
            ;
            PreparedStatement insert = null;
            ResultSet resultSet = null;

            try {
                insert = connection.prepareStatement(
                        "UPDATE books SET requestedby=? WHERE name = ?");

                insert.setString(1, "-");
                insert.setString(2, bookTable.getSelectionModel().getSelectedItem().getBookName());
                insert.executeUpdate();
                App.loadData("", "admin");
            } catch (Exception e) {

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
    }

    @FXML
    void addUser(ActionEvent event) throws SQLException {
        if (memberTable.getSelectionModel().getSelectedItem() != null) {
            App.newMembers.remove(bookTable.getSelectionModel().getSelectedItem());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                    "Nightmare111");
            ;
            PreparedStatement insert = null;
            ResultSet resultSet = null;

            try {
                insert = connection.prepareStatement("SELECT MAX( id ) AS max FROM members");
                resultSet = insert.executeQuery();
                int id = 0;
                while (resultSet.next()) {
                    id = resultSet.getInt("max");
                }
                id++;

                insert = connection.prepareStatement(
                        "INSERT INTO members (id, name, joindate, contact, password, securityquestion, securityanswer) VALUES(?, ?, ?, ?, ?, ?, ?)");
                insert.setInt(1, id);
                insert.setString(2, (memberTable.getSelectionModel().getSelectedItem().getName()));
                insert.setString(3, LocalDate.now().toString());
                insert.setString(4, memberTable.getSelectionModel().getSelectedItem().getContact());
                insert.setString(5, memberTable.getSelectionModel().getSelectedItem().getPassword());
                insert.setString(6, memberTable.getSelectionModel().getSelectedItem().getSecurityQuestion());
                insert.setString(7, memberTable.getSelectionModel().getSelectedItem().getSecurityAnswer());
                insert.executeUpdate();

                insert = connection.prepareStatement(
                        "DELETE FROM newmembers WHERE ( name= ? )");
                insert.setString(1, memberTable.getSelectionModel().getSelectedItem().getName());
                insert.executeUpdate();
                App.loadData("", "admin");

            } catch (Exception e) {

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
    }

    @FXML
    void rejectUser(ActionEvent event) throws SQLException {
        if (memberTable.getSelectionModel().getSelectedItem() != null) {
            App.newMembers.remove(bookTable.getSelectionModel().getSelectedItem());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                    "Nightmare111");
            ;
            PreparedStatement insert = null;
            ResultSet resultSet = null;

            try {

                insert = connection.prepareStatement(
                        "DELETE FROM newmembers WHERE ( name= ? )");
                insert.setString(1, memberTable.getSelectionModel().getSelectedItem().getName());
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
    }

    private void addListToTable() {
        memberName.setCellValueFactory(new PropertyValueFactory<Members, String>("name"));
        memberContact.setCellValueFactory(new PropertyValueFactory<Members, String>("contact"));
        bookName.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        requestedBy.setCellValueFactory(new PropertyValueFactory<Books, String>("requestedBy"));

        bookTable.setItems(App.booksRequests);
        memberTable.setItems(App.newMembers);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListToTable();
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
