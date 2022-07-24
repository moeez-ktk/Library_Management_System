package com.example.lib_management;

import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import MainCode.Books;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class issued_admincontroller implements Initializable {

    @FXML
    private Label Date_time_label;

    @FXML
    private Button dashboard_btn;

    @FXML
    private ImageView dashboard_icon;

    @FXML
    private ImageView history_icon;

    @FXML
    private Button issued_history;

    @FXML
    private TableView<Books> issuedadmin_table;

    @FXML
    private TableColumn<Books, String> overdue_fine;

    @FXML
    private TableColumn<Books, String> isssued_person;

    @FXML
    private TableColumn<Books, String> issue_date;

    @FXML
    private TableColumn<Books, String> issued_book;

    @FXML
    private TableColumn<Books, String> author_col;

    @FXML
    private TableColumn<Books, String> duedate;

    @FXML
    private TextField search;

    @FXML
    void dashboard(ActionEvent event) throws IOException {
        App.changescene("admindashboard", event);
    }

    @FXML
    void history(ActionEvent event) throws IOException {
        App.changescene("issued_history_admin", event);

    }

    private void addListToTable() {
        issued_book.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        author_col.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        issue_date.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedDate"));
        duedate.setCellValueFactory(new PropertyValueFactory<Books, String>("returnDate"));
        isssued_person.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedBy"));
        overdue_fine.setCellValueFactory(Books ->

        {
            String s;
            s = Books.getValue().getDueDate();
            long days = 0;
            try {

                days = ChronoUnit.DAYS.between(LocalDate.parse(s), LocalDate.now());
            } catch (Exception e) {
                // TODO: handle exception
            }

            return new ReadOnlyStringWrapper(String.valueOf(days * 50));
        });

        issuedadmin_table.setItems(App.issuedBooks);
    }

    private ObservableList<Books> searchBooks = FXCollections.observableArrayList();

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
                searchBooks.clear();
                for (Books books : App.books) {
                    try {
                        if (search.getText().isEmpty()) {
                            addListToTable();
                        }
                        if (books.getBookName().startsWith(search.getText())&&books.getIssuedBy().length()!=1)
                            searchBooks.add(books);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                issuedadmin_table.setItems(searchBooks);
            }
        });
    }

}
