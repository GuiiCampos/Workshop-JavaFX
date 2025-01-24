module com.workshopjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.workshopjavafx to javafx.fxml;
    exports com.workshopjavafx;
    exports com.workshopjavafx.util;
    opens com.workshopjavafx.util to javafx.fxml;
    opens com.workshopjavafx.model.entities to javafx.base;

}