module com.example.taskmanage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.taskmanage to javafx.fxml;
    exports com.example.taskmanage;
}