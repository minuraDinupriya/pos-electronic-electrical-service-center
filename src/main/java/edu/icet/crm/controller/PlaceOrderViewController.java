package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.PlaceOrderBo;
import edu.icet.crm.dto.tm.PlaceOrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaceOrderViewController {

    public JFXTextField txtCustomerName;
    public JFXTextField txtContactNumber;
    public JFXTextField txtEmail;
    public JFXTextField txtItemName;
    public Label lblOrderId;
    public TableView<PlaceOrderTm> table;
    public TableColumn colItemNo;
    public TableColumn colCategory;
    public TableColumn colOption;
    public TableColumn colItemName;
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
    PlaceOrderBo placeOrderBo= BoFactory.getInstance().getBo(BoType.PLACE_ORDER_BO);
    ObservableList<PlaceOrderTm> tmList = FXCollections.observableArrayList();

    public void initialize() {
        // Set up cell value factories for table columns
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        // Enable editing for the 'item name' column
        colItemName.setCellFactory(TextFieldTableCell.forTableColumn());
    }

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

//    private void handleCategorySelection() {
//
//        RadioButton selectedRadioButton = (RadioButton) category.getSelectedToggle();
//        if (selectedRadioButton != null) {
//            String selectedCategory = selectedRadioButton.getText();
//            System.out.println(electricalToggleBtn.isSelected());
//            System.out.println("Selected Category: " + selectedCategory);
//        }
//    }

    @FXML
    private void saveBtnOnAction() {
//        handleCategorySelection();

        if (isEmptyField(txtCustomerName) || isEmptyField(txtContactNumber) || isEmptyField(txtEmail)
                || isEmptyField(txtItemName) || !(electronicToggleBtn.isSelected() || electricalToggleBtn.isSelected()) ) {

            new Alert(Alert.AlertType.WARNING,"Please fill in all required fields and select a category.").show();
        } else {
            // Proceed with the "Add" functionality

            // Get the selected category
            RadioButton selectedRadioButton = (RadioButton) category.getSelectedToggle();
            String selectedCategory = selectedRadioButton.getText();
            System.out.println(selectedCategory);

            // Create a PlaceOrderTm object
            PlaceOrderTm placeOrderTm = new PlaceOrderTm(txtItemName.getText(), selectedCategory, createDeleteButton());

            // Add the PlaceOrderTm to the table
            tmList.add(placeOrderTm);
            table.setItems(tmList  );

            // Clear the input fields
            clearBtnOnAction();
        }
    }

    private JFXButton createDeleteButton() {
        JFXButton deleteButton = new JFXButton("Delete");
        deleteButton.setOnAction(event -> {
            PlaceOrderTm selectedItem = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedItem);
        });
        return deleteButton;
    }

    private boolean isEmptyField(JFXTextField textField) {
        System.out.println(textField.getText());
        return textField.getText().trim().isEmpty();
    }

    public void placeOrderBtnOnAction(ActionEvent actionEvent) {
        placeOrderBo.save();
    }

    public void clearBtnOnAction() {

//        txtCustomerName.clear();
//        txtContactNumber.clear();
//        txtEmail.clear();
        txtItemName.clear();
        category.selectToggle(null);

    }
}

