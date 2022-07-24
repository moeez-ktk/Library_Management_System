package com.example.lib_management;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import MainCode.Books;
import MainCode.Members;

public class App extends Application {
    public static String userName = "admin1";
    // public static String userName = "haseeb haseeb";
    public static ObservableList<Books> books = FXCollections.observableArrayList();
    public static ObservableList<Books> booksRequests = FXCollections.observableArrayList();
    public static ObservableList<Books> issuedBooks = FXCollections.observableArrayList();
    public static ObservableList<Books> history = FXCollections.observableArrayList();
    public static ObservableList<Members> members = FXCollections.observableArrayList();
    public static ObservableList<Members> newMembers = FXCollections.observableArrayList();

    public static String bookName;
    public static String bookAuthor;
    public static String bookType;
    public static String bookCategory;
    public static int bookID;

    public static Members m;
    static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        // FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userdashboard.fxml"));
        // loadData("haseeb haseeb", "members");
        loadData("", "admin");
        FXMLLoader fxmlLoader = new
        FXMLLoader(App.class.getResource("admindashboard.fxml"));

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

    public static void changescene(String fxml_file, ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml_file + ".fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pioneer Atheneum");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void loadData(String user, String Type) throws SQLException {
        members.clear();
        books.clear();
        issuedBooks.clear();
        history.clear();
        newMembers.clear();
        booksRequests.clear();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                "Nightmare111");
        ;
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        try {

            insert = connection.prepareStatement(
                    "SELECT * FROM books");

            resultSet = insert.executeQuery();

            while (resultSet.next()) {
                int bookid = resultSet.getInt("id");
                String bookName = resultSet.getString("name");
                String author = resultSet.getString("author");
                String dateAdded = resultSet.getString("dateadded");
                String issueTo = resultSet.getString("issueto");
                String issueDate = resultSet.getString("issuedate");
                String returnDate = resultSet.getString("returnDate");
                String dueDate = resultSet.getString("dueDate");
                String category = resultSet.getString("category");
                String type = resultSet.getString("type");
                String requestedBy = resultSet.getString("requestedby");
                books.add(new Books(bookName, author, bookid, category, type, dateAdded,
                        issueTo, returnDate, issueDate, dueDate, requestedBy));

                if (Type.contains("admin") && (!issueTo.equals("-")))
                    issuedBooks.add(new Books(bookName, author, bookid, category, type, dateAdded,
                            issueTo, returnDate, issueDate, dueDate, requestedBy));

                if (Type.contains("members") && issueTo.equals(user))
                    issuedBooks.add(new Books(bookName, author, bookid, category, type, dateAdded,
                            issueTo, returnDate, issueDate, dueDate, requestedBy));

            }

            // ********************************************
            // ********************************************

            if (Type.contains("members")) {
                insert = connection.prepareStatement(
                        "SELECT * FROM history WHERE membername = ?");
                insert.setString(1, user);
            } else {
                insert = connection.prepareStatement(
                        "SELECT * FROM history");
            }
            resultSet = insert.executeQuery();
            while (resultSet.next()) {
                int bookid = resultSet.getInt("id");
                String bookName = resultSet.getString("bookname");
                String author = resultSet.getString("author");
                String issueTo = resultSet.getString("membername");
                String issueDate = resultSet.getString("issuedate");
                String returnDate = resultSet.getString("returndate");
                String category = resultSet.getString("category");

                history.add(new Books(bookName, author, bookid, category,
                        returnDate, issueDate, issueTo));

            }

            // ********************************************
            // ********************************************

            if (Type.contains("members")) {
                insert = connection.prepareStatement(
                        "SELECT * FROM members WHERE name = ?");
                insert.setString(1, user);
            } else {
                insert = connection.prepareStatement(
                        "SELECT * FROM members");

            }

            resultSet = insert.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int fine = resultSet.getInt("fine");
                int noOfBooks = resultSet.getInt("booksissued");
                String name = resultSet.getString("name");
                String dateJoined = resultSet.getString("joindate");
                String password = resultSet.getString("password");
                String question = resultSet.getString("securityquestion");
                String answer = resultSet.getString("securityanswer");
                String requested = resultSet.getString("requested");
                String contact = resultSet.getString("contact");

                if (Type.contains("members"))
                    m = new Members(name, id, dateJoined, contact, password, question, answer, fine, noOfBooks,
                            requested);
                if (Type.contains("admin")) {
                    members.add(
                            new Members(name, id, dateJoined, contact, password, question, answer, fine, noOfBooks,
                                    requested));
                }
            }

            if (Type.contains("admin")) {
                insert = connection.prepareStatement(
                        "SELECT * FROM newmembers");
                resultSet = insert.executeQuery();
                while (resultSet.next()) {
                    String contact = resultSet.getString("contact");
                    String password = resultSet.getString("password");
                    String question = resultSet.getString("securityquestion");
                    String answer = resultSet.getString("securityanswer");
                    String name = resultSet.getString("name");
                    newMembers.add(new Members(name, contact, password, answer, question));
                }
            }

            for (Books book : App.books) {
                if (!book.getRequestedBy().equals("-")) {
                    App.booksRequests.add(book);
                }
            }

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