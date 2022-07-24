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
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class issued_user_controller implements Initializable {
    private ObservableList<Books> issuedBooks = FXCollections.observableArrayList();

    @FXML
    private Label Date_time_label, error_lbl;

    @FXML
    private Button Returnbook_user, dashboard, historybtn;

    @FXML
    private ImageView dashboard_icon, return_icon, history_icon;

    @FXML
    private TableColumn<Books, String> duedate_col;

    @FXML
    private TableColumn<Books, String> issue_date;

    @FXML
    private TableColumn<Books, String> name_col;

    @FXML
    private TableView<Books> issueduser_table;

    @FXML
    private TableColumn<Books, String> fine_col;

    @FXML
    private TextField search;

    @FXML
    public void history(ActionEvent event) throws IOException {
        App.changescene("issued_history_user", event);
    }

    @FXML
    void goToDashBoard(ActionEvent event) throws IOException {
        App.changescene("userdashboard", event);
    }

    @FXML
    void returnBook() throws SQLException {
        Connection connection = null;
        PreparedStatement insert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;

        try {
            Books b = issueduser_table.getSelectionModel().getSelectedItem();
            int id = b.getBookId();
            String name = b.getBookName();
            String author = b.getAuthor();
            String category = b.getCategory();
            String issueTo = b.getIssuedBy();
            String retrunDate = LocalDate.now().toString();
            String issueDate = b.getIssuedDate();

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                    "root",
                    "Nightmare111");
            // check = connection.prepareStatement("SELECT * FROM books WHERE name = ?");
            // check.setString(1, name);
            // resultSet = check.executeQuery();
            System.out.println("point1");

            insert = connection.prepareStatement("SELECT MAX( id ) AS max FROM history");
            resultSet = insert.executeQuery();
            int idd = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("max");
            }
            id++;
            insert = connection.prepareStatement(
                    "INSERT INTO history (id, author, bookname, membername, category, issuedate, returndate) VALUES(?,?,?,?,?,?,?)");

            insert.setInt(1, idd);
            insert.setString(2, author);
            insert.setString(3, name);
            insert.setString(4, issueTo);
            insert.setString(5, category);
            insert.setString(6, issueDate);
            insert.setString(7, retrunDate);
            System.out.println("point2");
            insert.executeUpdate();
            insert = connection.prepareStatement("UPDATE books SET issueto=?, issuedate=?, duedate=? WHERE name = ?");
            insert.setString(1, "-");
            insert.setString(2, "-");
            insert.setString(3, "-");
            insert.setString(4, name);
            insert.executeUpdate();
            insert = connection.prepareStatement(
                    "UPDATE members SET  booksissued = (booksissued - 1), requested=? WHERE name = ?");
            insert.setString(1, "-");
            insert.setString(2, issueTo);
            insert.executeUpdate();
            System.out.println("point33");
            long days = 0;
            try {

                days = ChronoUnit.DAYS.between(LocalDate.parse(b.getDueDate()), LocalDate.now());
            } catch (Exception e) {
                // TODO: handle exception
            }
            int fine = 0;
            if (days > 0) {
                System.out.println("point3");
                insert = connection.prepareStatement("SELECT fine FROM members WHERE name = ?");
                insert.setString(1, issueTo);
                resultSet = insert.executeQuery();
                if (resultSet.isBeforeFirst())
                    fine = resultSet.getInt("fine") + (((int) days) * 50);
                insert = connection
                        .prepareStatement("UPDATE members SET fine=? WHERE name = ?");
                insert.setInt(1, fine);
                insert.setString(2, issueTo);
                insert.executeUpdate();
                System.out.println("point1");
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
            if (check != null) {
                try {
                    check.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
        App.loadData(App.userName, "members");
    }

    private void addListToTable() {

        name_col.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
        issue_date.setCellValueFactory(new PropertyValueFactory<Books, String>("issuedDate"));
        duedate_col.setCellValueFactory(new PropertyValueFactory<Books, String>("dueDate"));
        fine_col.setCellValueFactory(Books ->

        {
            String s;
            s = Books.getValue().getDueDate();
            long days = 0;
            try {

                days = ChronoUnit.DAYS.between(LocalDate.parse(s), LocalDate.now());
            } catch (Exception e) {
                // TODO: handle exception
            }
            if (days < 0)
                days = 0;
            return new ReadOnlyStringWrapper(String.valueOf(days * 50));
        });
        for (Books book : App.books) {
            if (book.getIssuedBy().equals(App.userName))
                issuedBooks.add(book);
        }
        issueduser_table.setItems(issuedBooks);

    }

    private ObservableList<Books> searchBooks = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        error_lbl.setVisible(false);

        return_icon.setOnMouseEntered(event -> {
            if (issueduser_table.getSelectionModel().getSelectedItem() == null)
                error_lbl.setVisible(true);
        });

        return_icon.setOnMouseExited(event1 -> {
            error_lbl.setVisible(false);
        });
        Returnbook_user.setOnMouseEntered(event -> {
            if (issueduser_table.getSelectionModel().getSelectedItem() == null)
                error_lbl.setVisible(true);
        });

        Returnbook_user.setOnMouseExited(event1 -> {
            error_lbl.setVisible(false);
        });

        addListToTable();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Date_time_label
                        .setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE-MMM-d-yyyy hh:mm:ss a")));
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
                        if (books.getBookName().startsWith(search.getText())
                                && books.getIssuedBy().equals(App.userName))
                            searchBooks.add(books);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                issueduser_table.setItems(searchBooks);
            }
        });

    }

}
