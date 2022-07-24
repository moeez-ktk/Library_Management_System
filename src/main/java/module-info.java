module com.example.lib_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lib_management to javafx.fxml;
    opens MainCode to javafx.base;

    exports com.example.lib_management;
}