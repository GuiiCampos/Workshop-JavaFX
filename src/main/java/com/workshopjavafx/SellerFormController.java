package com.workshopjavafx;

import com.workshopjavafx.db.DbException;
import com.workshopjavafx.listeners.DataChangeListener;
import com.workshopjavafx.model.entities.Department;
import com.workshopjavafx.model.entities.Seller;
import com.workshopjavafx.model.exceptions.ValidationException;
import com.workshopjavafx.model.services.DepartmentService;
import com.workshopjavafx.model.services.SellerService;
import com.workshopjavafx.util.Alerts;
import com.workshopjavafx.util.Constraints;
import com.workshopjavafx.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller enitty;
    private SellerService sellerService;
    private DepartmentService dpService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorBirthDate;
    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private ObservableList<Department> obsList;

    public void setEnitty(Seller enitty) {
        this.enitty = enitty;
    }

    public void setServices(SellerService sellerService, DepartmentService dpService) {
        this.sellerService = sellerService;
        this.dpService = dpService;
    }

    public void subscriteDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (enitty == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (sellerService == null) {
            throw new IllegalStateException("Service was null");
        }

        try {
            enitty = getFormData();
            sellerService.saveOrUpdate(enitty);
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

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryParseToInteger(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addError("email", "Field can't be empty");
        }
        obj.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null) {
            exception.addError("birthDate", "Field can't be empty");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
            exception.addError("baseSalary", "Field can't be empty");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

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
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {
        if (enitty == null) {
            throw new IllegalStateException("Enitty is null");
        }

        txtId.setText(String.valueOf(enitty.getId()));
        txtName.setText(enitty.getName());
        txtEmail.setText(enitty.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", enitty.getBaseSalary()));
        if (enitty.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(enitty.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        if (enitty.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(enitty.getDepartment());
        }
    }

    public void loadAssociatedObjects() {
        if (dpService == null) {
            throw new IllegalStateException("DepartmentService was null");
        }
        List<Department> list = dpService.findAll();
        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorName.setText((fields.contains("name") ? errors.get("name") : ""));
        labelErrorBaseSalary.setText((fields.contains("baseSalary") ? errors.get("baseSalary") : ""));
        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
        labelErrorBirthDate.setText((fields.contains("birthDate") ? errors.get("birthDate") : ""));

    }
}
