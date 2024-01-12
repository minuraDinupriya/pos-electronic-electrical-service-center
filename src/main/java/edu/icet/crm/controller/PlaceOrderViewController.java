package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaceOrderViewController {

    public JFXTextField txtCustomerName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
    public JFXTextField txtItemName;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXRadioButton electronicToggleBtn;

    @FXML
    private ToggleGroup category;

    @FXML
    private JFXRadioButton electricalToggleBtn;



    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeView.fxml"))));
    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
    }

    private void handleCategorySelection() {
        // Get the selected radio button's text
        RadioButton selectedRadioButton = (RadioButton) category.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedCategory = selectedRadioButton.getText();
            System.out.println("Selected Category: " + selectedCategory);
        }
    }

    @FXML
    private void addBtnOnAction() {

        if (isEmptyField(txtCustomerName) || isEmptyField(txtContactNumber) || isEmptyField(txtEmail)
                || isEmptyField(txtItemName) || !electricalToggleBtn.isSelected()|| !electricalToggleBtn.isSelected()) {

            new Alert(Alert.AlertType.WARNING,"Please fill in all required fields and select a category.").show();
        } else {
            // Proceed with the "Add" functionality
            // ...
        }
    }

    private boolean isEmptyField(JFXTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {

    }
}

