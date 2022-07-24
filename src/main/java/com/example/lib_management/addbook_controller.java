package com.example.lib_management;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class addbook_controller implements Initializable {

    @FXML
    private Label error_lbl, iderror_lbl;

    @FXML
    private Button add_btn;

    @FXML
    private TextField auth_txt, name_txt;;

    @FXML
    private ToggleGroup book_type, cat;

    @FXML
    private RadioButton nor;

    @FXML
    private RadioButton sc, fic, lit;

    @FXML
    private RadioButton ref;

    @FXML
    void add(ActionEvent event) {

        Connection connection = null;
        PreparedStatement insert = null;
        PreparedStatement check = null;
        ResultSet resultSet = null;

        try {
            String aut = auth_txt.getText();
            String name = name_txt.getText();
            String date = String.valueOf(LocalDate.now());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                    "root",
                    "Nightmare111");
            check = connection.prepareStatement("SELECT * FROM books WHERE name = ?");
            check.setString(1, name);
            resultSet = check.executeQuery();

            if (resultSet.isBeforeFirst()) {
                iderror_lbl.setVisible(true);
            } else {
                PreparedStatement m = connection.prepareStatement("SELECT MAX( id ) AS max FROM books");
                ResultSet r = m.executeQuery();
                int id = 0;
                while (r.next()) {
                    id = r.getInt("max");
                }
                id++;

                insert = connection.prepareStatement(
                        "INSERT INTO books (id, author, name, dateadded, category, issueto, issuedate, returndate, duedate, type) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                insert.setInt(1, id);
                insert.setString(2, aut);
                insert.setString(3, name);
                insert.setString(4, date);
                insert.setString(6, "");
                insert.setString(7, "");
                insert.setString(8, "");
                insert.setString(9, "");

                if (ref.isSelected())
                    insert.setString(10, "Reference Book");
                else
                    insert.setString(10, "Non-Reference");

                if (sc.isSelected())
                    insert.setString(5, "Sciences");
                else if (fic.isSelected())
                    insert.setString(5, "Fiction");
                else
                    insert.setString(5, "Literature");

                insert.executeUpdate();

                auth_txt.setText("");
                name_txt.setText("");
                App.loadData("", "admin");
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        error_lbl.setVisible(false);
        iderror_lbl.setVisible(false);

        add_btn.setOnMouseEntered(event1 -> {
            if (auth_txt.getText() == null || auth_txt.getText() == ""
                    || name_txt.getText() == "" || name_txt.getText() == null)
                error_lbl.setVisible(true);
        });

        add_btn.setOnMouseExited(event2 -> {
            error_lbl.setVisible(false);
        });

        name_txt.setOnMouseClicked(event1 -> {
            iderror_lbl.setVisible(false);
        });

    }

}
