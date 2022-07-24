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
import javafx.scene.image.ImageView;


public class editBookController implements Initializable{

    @FXML
    private Label add_book_label;

    @FXML
    private TextField auth_txt;

    @FXML
    private ToggleGroup book_type;

    @FXML
    private ToggleGroup cat;

    @FXML
    private Button editbtn;

    @FXML
    private ImageView editicon;

    @FXML
    private Label error_lbl;

    @FXML
    private RadioButton fic;

    @FXML
    private RadioButton lit;

    @FXML
    private TextField name_txt;

    @FXML
    private RadioButton nor;

    @FXML
    private RadioButton ref;

    @FXML
    private RadioButton sc;

    @FXML
    void edit() {
        Connection connection = null;
        PreparedStatement insert = null;


        try {
            String aut = auth_txt.getText();
            String name = name_txt.getText();
            String date = String.valueOf(LocalDate.now());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarybooks",
                    "root",
                    "Nightmare111");

            insert=connection.prepareStatement("UPDATE books SET name=? , author=? , type=?, category=? WHERE id=? ");
            insert.setString(1, name_txt.getText());
            insert.setString(2, auth_txt.getText());
            insert.setInt(5, App.bookID);

            if (ref.isSelected())
                    insert.setString(3, "Reference Book");
                else
                    insert.setString(3, "Non-Reference");

                if (sc.isSelected())
                    insert.setString(4, "Sciences");
                else if (fic.isSelected())
                    insert.setString(4, "Fiction");
                else
                    insert.setString(4, "Literature");
insert.executeUpdate();

                App.loadData("", "admin");
            

        } catch (Exception e) {
            System.out.println(e);
        } 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        auth_txt.setText(App.bookAuthor);
        name_txt.setText(App.bookName);
        if(App.bookCategory.equals("Science"))
            sc.setSelected(true);
            if(App.bookCategory.equals("Literature"))
            lit.setSelected(true);
            if(App.bookCategory.equals("Fiction"))
            fic.setSelected(true);
            if(App.bookType.equals("Reference Book"))
            ref.setSelected(true);
            if(App.bookType.equals("Non-Reference Book"))
            nor.setSelected(true);
        
    }
}
