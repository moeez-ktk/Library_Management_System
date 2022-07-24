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
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import MainCode.Books;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class bookspage_controller implements Initializable {
    private ObservableList<Books> books = FXCollections.observableArrayList();
    private ObservableList<Books> searchBooks = FXCollections.observableArrayList();

    @FXML
    private TextField search;

    @FXML
    private Button All_books;

    @FXML
    private Label Date_time_label;

    @FXML
    private TableColumn<Books, Integer> ID_col;

    @FXML
    private Button Reference_books;

    @FXML
    private Button addbook_button, removebtn, editbtn;

    @FXML
    private TableColumn<Books, String> author_col;

    @FXML
    private TableView<Books> books_table;

    @FXML
    private TableColumn<Books, String> category_col;

    @FXML
    private TableColumn<Books, String> dateadded_col;

    @FXML
    private ImageView edit_button;

    @FXML
    private Button fic_books;

    @FXML
    private Button fresharrivals;

    @FXML
    private Label hidden_lbl;

    @FXML
    private Button lit_books;

    @FXML
    private TableColumn<Books, String> name_col;

    @FXML
    private Button remove_button;

    @FXML
    private TableColumn<Books, String> requested_col;

    @FXML
    private Button sc_books;

    @FXML
    private TableColumn<Books, String> type_col;

    private void addListToTable(String value, String type) {

        books.clear();
        ID_col.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookId"));
        name_col.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        dateadded_col.setCellValueFactory(new PropertyValueFactory<Books, String>("dateAdded"));
        category_col.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        type_col.setCellValueFactory(new PropertyValueFactory<Books, String>("type"));
        requested_col.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedBy"));

        if (value.equals("") && value.equals(""))
            books_table.setItems(App.books);
        else if (type.length() == 0) {
            for (Books book : App.books) {
                if (value.equals(book.getCategory()))
                    books.add(book);
            }
            books_table.setItems(books);
        } else {
            for (Books book : App.books) {

                if (type.equals("fresh")) {
                    String s;
                    s = book.getDateAdded();
                    long days = 0;
                    try {

                        days = ChronoUnit.DAYS.between(LocalDate.parse(s), LocalDate.now());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    if (days >= 90)
                        books.add(book);
                } else if (book.getType().equals("Reference Book"))
                    books.add(book);

            }
            books_table.setItems(books);
        }

    }

    @FXML
    public void edit() throws IOException {
        if (books_table.getSelectionModel().getSelectedItem() != null) {
            App.bookName = books_table.getSelectionModel().getSelectedItem().getBookName();
            App.bookAuthor = books_table.getSelectionModel().getSelectedItem().getAuthor();
            App.bookType = books_table.getSelectionModel().getSelectedItem().getType();
            App.bookCategory = books_table.getSelectionModel().getSelectedItem().getCategory();
            App.bookID = books_table.getSelectionModel().getSelectedItem().getBookId();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editBook.fxml"));
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
    }

    @FXML
    public void remove() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks", "root",
                "Nightmare111");
        ;
        PreparedStatement insert = null;
        ResultSet resultSet = null;

        try {

            insert = connection.prepareStatement(
                    "DELETE FROM books WHERE ( id= ? )");
            insert.setInt(1, books_table.getSelectionModel().getSelectedItem().getBookId());
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

    @FXML
    public void showScienceBooks() {
        addListToTable("Sciences", "");
    }

    @FXML
    public void showLiteratureBooks() {
        addListToTable("Literature", "");
    }

    @FXML
    public void showFictionBooks() {
        addListToTable("Fiction", "");
    }

    @FXML
    public void showAllBooks() {
        addListToTable("", "");
    }

    @FXML
    public void showFreshArrivals() {
        addListToTable("Fresh", "fresh");
    }

    @FXML
    public void showReferenceBooks() {
        addListToTable("Reference Book", "refer");
    }

    @FXML
    public void goToDashboard(ActionEvent event) throws IOException {
        App.changescene("admindashboard", event);
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
        addListToTable("", "");
        search.setOnKeyTyped((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                searchBooks.clear();
                for (Books books : App.books) {
                    try {
                        if (search.getText().isEmpty()) {
                            addListToTable("", "");
                        }
                        if (books.getBookName().startsWith(search.getText()))
                            searchBooks.add(books);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                books_table.setItems(searchBooks);
            }
        });
    }

}
