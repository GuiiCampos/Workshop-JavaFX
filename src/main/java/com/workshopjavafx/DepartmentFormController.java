package com.workshopjavafx;

import com.workshopjavafx.db.DbException;
import com.workshopjavafx.listeners.DataChangeListener;
import com.workshopjavafx.model.entities.Department;
import com.workshopjavafx.model.exceptions.ValidationException;
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
import java.util.*;

public class DepartmentFormController implements Initializable {

    private Department enitty;
    private DepartmentService dpService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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

    public void subscriteDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
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
            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Department getFormData() {
        Department obj = new Department();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInteger(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

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

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
}
