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
import MainCode.Members;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class bookspage_user_controller implements Initializable {
    private ObservableList<Books> books = FXCollections.observableArrayList();

    @FXML
    private Button fresharrivals, fic_books, lit_books, Reference_books, sc_books, addbook_button, issue_button,
            remove_button, All_books, req_issue;

    @FXML
    private Label Date_time_label, error_lbl;

    @FXML
    private TableColumn<Books, String> author_col;

    @FXML
    private TableView<Books> books_table;

    @FXML
    private TableColumn<Books, String> cat_col;

    @FXML
    private TableColumn<Books, String> date_col;

    @FXML
    private TableColumn<Books, Integer> id_col;

    @FXML
    private TableColumn<Books, String> issue_col;

    @FXML
    private TableColumn<Books, String> name_col;

    @FXML
    private TableColumn<Books, String> dateadded_col;

    @FXML
    private TableColumn<Books, String> type_col;

    @FXML
    private ImageView edit_button, req_issue_img;

    @FXML
    private TextField search;

    private void addListToTable(String value, String type) {
        books.clear();
        id_col.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookId"));
        name_col.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        date_col.setCellValueFactory(new PropertyValueFactory<Books, String>("dateAdded"));
        cat_col.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        type_col.setCellValueFactory(new PropertyValueFactory<Books, String>("type"));
        issue_col.setCellValueFactory(Books ->

        {
            String s;
            if (Books.getValue().getIssuedBy() == "-") {
                s = "No";
            } else
                s = "Yes";
            return new ReadOnlyStringWrapper(s);
        });

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
    public void request() {
        ObservableList<Books> b = FXCollections.observableArrayList();
        b.clear();
        b.add(books_table.getSelectionModel().selectedItemProperty().get());

        Connection connection = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;

        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                    "root",
                    "Nightmare111");
            check = connection.prepareStatement("SELECT requested FROM members WHERE name = ?");
            check.setString(1, App.userName);
            resultSet = check.executeQuery();

            if (resultSet.next()) {

                String p = null;
                try {
                    p = resultSet.getString("requested");
                } catch (Exception e) {
                    System.out.println(e);
                }

                if (p == null || p == "") {

                    check = connection.prepareStatement("UPDATE members SET requested = ? WHERE name = ?");
                    b.get(0).getBookName();
                    check.executeUpdate();
                    for (Members member : App.members) {
                        if (member.getName().equals(App.userName))
                            member.setRequest(b.get(0).getBookName());
                    }
                    check = connection.prepareStatement("UPDATE books SET requestedby = ? WHERE name = ?");
                    check.setString(1, App.userName);
                                    check.setString(2, b.get(0).getBookName());
                    check.executeUpdate();
                    App.loadData(App.userName, "members");

                } else {

                    final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("You Have Already Requested: " + p
                            + "\n\nYou Are Only Allowed To Request One Book At A Time\nPress \"OK\" To Discard Previous Request");
                    alert.setResizable(false);
                    alert.show();
                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
                            ButtonType result = alert.getResult();
                            String resultText = result.getText();
                            // result logic
                            if (resultText.equals("OK")) {
                                try {
                                    Connection connection = DriverManager.getConnection(
                                            "jdbc:mysql://localhost:3306/librarybooks",
                                            "root",
                                            "Nightmare111");
                                    ;
                                    PreparedStatement check = check = connection
                                            .prepareStatement("UPDATE members SET requested = ? WHERE name = ?");
                                    check.setString(1, b.get(0).getBookName());
                                    check.setString(2, App.userName);
                                    check.executeUpdate();
                                    for (Members member : App.members) {
                                        if (member.getName().equals(App.userName))
                                            member.setRequest(b.get(0).getBookName());
                                    }
                                    check = connection
                                            .prepareStatement("UPDATE books SET requestedby = ? WHERE name = ?");
                                    check.setString(1, App.userName);
                                    check.setString(2, b.get(0).getBookName());
                                    check.executeUpdate();
                                    App.loadData(App.userName, "members");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e);
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
        addListToTable("Fresh", "type");
    }

    @FXML
    public void showReferenceBooks() {
        addListToTable("Reference Book", "type");
    }

    @FXML
    public void goToDashboard(ActionEvent event) throws IOException {
        App.changescene("userdashboard", event);
    }

    private ObservableList<Books> searchBooks = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListToTable("", "");
        error_lbl.setVisible(false);
        req_issue_img.setOnMouseEntered(event -> {
            if (books_table.getSelectionModel().getSelectedItem() == null)
                error_lbl.setVisible(true);
        });
        req_issue_img.setOnMouseExited(event1 -> {
            error_lbl.setVisible(false);
        });
        req_issue.setOnMouseEntered(event -> {
            if (books_table.getSelectionModel().getSelectedItem() == null)
                error_lbl.setVisible(true);
        });
        req_issue.setOnMouseExited(event1 -> {
            error_lbl.setVisible(false);
        });

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
