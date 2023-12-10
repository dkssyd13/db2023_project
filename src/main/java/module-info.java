module com.example.db2023_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.db2023_project to javafx.fxml;
    exports com.example.db2023_project;
}