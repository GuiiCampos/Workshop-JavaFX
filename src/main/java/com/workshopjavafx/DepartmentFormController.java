package com.workshopjavafx;

import com.workshopjavafx.db.DbException;
import com.workshopjavafx.model.entities.Department;
import com.workshopjavafx.model.services.DepartmentService;
import com.workshopjavafx.util.Alerts;
import com.workshopjavafx.util.Constraints;
import com.workshopjavafx.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {

    private Department enitty;
    private DepartmentService dpService;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    public void setEnitty(Department enitty) {
        this.enitty = enitty;
    }
    public void setDpService(DepartmentService dpService) {
        this.dpService = dpService;
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (enitty == null) {
            throw new IllegalStateException("Entity was null");
        }if (dpService == null) {
            throw new IllegalStateException("Service was null");
        }

        try {
            enitty = getFormData();
            dpService.saveOrUpdate(enitty);
            Utils.currentStage(event).close();

        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private Department getFormData() {
        Department obj = new Department();
        obj.setId(Utils.tryParseToInteger(txtId.getText()));
        obj.setName(txtName.getText());

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    public void updateFormData() {
        if (enitty == null) {
            throw new IllegalStateException("Enitty is null");
        }

        txtId.setText(String.valueOf(enitty.getId()));
        txtName.setText(enitty.getName());
    }

}
