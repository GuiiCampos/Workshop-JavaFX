module com.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.workshopjavafx to javafx.fxml;
    exports com.workshopjavafx;
}