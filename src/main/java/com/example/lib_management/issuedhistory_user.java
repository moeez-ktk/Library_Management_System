package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import MainCode.Books;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class issuedhistory_user implements Initializable {
    private ObservableList<Books> issuedBooks = FXCollections.observableArrayList();

    @FXML
    private Label Date_time_label;

    @FXML
    private TableColumn<Books, String> category;

    @FXML
    private TableColumn<Books, String> author;

    @FXML
    private TableColumn<Books, String> issue_date;

    @FXML
    private TableView<Books> table;

    @FXML
    private TableColumn<Books, String> return_col;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Books, String> name_col;

    @FXML
    private Button dashboard, issuedbtn;

    @FXML
    private ImageView dashboard_icon, issued_icon;

    @FXML
    void goToDashBoard(ActionEvent event) throws IOException {
        App.changescene("userdashboard", event);
    }

    @FXML
    void issued(ActionEvent event) throws IOException {
        App.changescene("issued_user", event);
    }

    private void addListToTable() {

        name_col.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        issue_date.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedDate"));
        category.setCellValueFactory(new PropertyValueFactory<Books, String>("category"));
        return_col.setCellValueFactory(new PropertyValueFactory<Books, String>("returnDate"));
        author.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        for (Books book : App.books) {
            if (book.getIssuedBy().equals(App.userName))
                issuedBooks.add(book);
        }
        table.setItems(issuedBooks);

    }
    private ObservableList<Books> searchBooks=FXCollections.observableArrayList();
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


        search.setOnKeyTyped((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent arg0) {
                searchBooks.clear();
                for (Books books : App.books) {
                    try {
                        if(search.getText().isEmpty()){
                            addListToTable();    
                        }
                        if(books.getBookName().startsWith(search.getText()))
                                searchBooks.add(books);
                    } catch (Exception e) {     
                        e.printStackTrace();     
                    }                              
               }
               table.setItems(searchBooks);
            }            
        });



    }

}
