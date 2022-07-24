package com.example.lib_management;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import MainCode.Books;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class issued_history_controller implements Initializable {

    @FXML
    private Label Date_time_label;

    @FXML
    private ImageView dashboard_icon;

    @FXML
    private TableColumn<Books, String> duedate_history;

    @FXML
    private TableColumn<Books, String> fine_history;

    @FXML
    private TableColumn<Books, String> isssuedto_history;

    @FXML
    private TableColumn<Books, String> issue_datehistory;

    @FXML
    private TableColumn<Books, String> issued_bookhistory;

    @FXML
    private TableView<Books> issuedhistory_table;

    @FXML
    private TableColumn<Books, String> return_history;

    @FXML
    private TextField search;

    @FXML
    void dashboard(ActionEvent event) throws IOException {
        App.changescene("admindashboard", event);
    }

    private void addListToTable() {
        issued_bookhistory.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        return_history.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        issue_datehistory.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedDate"));
        duedate_history.setCellValueFactory(new PropertyValueFactory<Books, String>("returnDate"));
        isssuedto_history.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedBy"));
        fine_history.setCellValueFactory(Books ->

        {
            long days = 0;
            try {

                days = ChronoUnit.DAYS.between(LocalDate.parse(Books.getValue().getDueDate()),
                        LocalDate.parse(Books.getValue().getReturnDate()));
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (days < 0)
                days = 0;

            return new ReadOnlyStringWrapper(String.valueOf(days * 50));
        });

        issuedhistory_table.setItems(App.history);
    }

    private ObservableList<Books> searchBooks = FXCollections.observableArrayList();

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
                        if(books.getBookName().startsWith(search.getText())&&books.getIssuedBy().length()!=1)
                                searchBooks.add(books);
                    } catch (Exception e) {     
                        e.printStackTrace();     
                    }                              
               }
               issuedhistory_table.setItems(searchBooks);
            }            
        });


        addListToTable();
    }

}
