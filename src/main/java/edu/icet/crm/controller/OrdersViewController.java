package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.OrdersViewBo;
import edu.icet.crm.dto.OrderDto;
import edu.icet.crm.dto.tm.OrdersViewTm;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.util.List;

public class OrdersViewController {

    public TableColumn colOrderId;
    public TableColumn colStatus;
    public TableColumn colCustomerId;
    public TableColumn colOrderDate;
    public TableColumn colNote;
    public TableColumn colCloseBtn;
    public TableColumn colReturnBtn;
    public Label lblOrderID;
    public JFXComboBox comboStatus;
    public TableView<OrdersViewTm> table;
    public JFXTextField txtSearch;
    public TableColumn colTotal;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

    private OrdersViewBo ordersViewBo= BoFactory.getInstance().getBo(BoType.ORDERS_VIEW_BO);



    @FXML
    private void initialize() {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colCloseBtn.setCellValueFactory(new PropertyValueFactory<>("closeOrderButton"));
        colReturnBtn.setCellValueFactory(new PropertyValueFactory<>("returnButton"));


        loadDataToTable();


        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblOrderID.setText(newValue.getOrderId());
            }
        });


    }

    private void loadDataToTable() {
        ObservableList<OrdersViewTm> tmList = FXCollections.observableArrayList();

        List<OrderDto> ordersList = ordersViewBo.getOrdersViewDto();
        if (ordersList != null) {
            for (OrderDto order : ordersList) {
                JFXButton returnButton = new JFXButton("Return");
                JFXButton closeOrderButton = new JFXButton("Close Order");
                closeOrderButton.setOnAction(ActionEvent->{

                });

                tmList.add(new OrdersViewTm(
                        order.getOrderId(),
                        order.getStatus(),
                        order.getCustomerId(),
                        order.getOrderDate(),
                        order.getNote(),
                        returnButton,
                        closeOrderButton
                ));
            }
        }

        table.setItems(tmList);
    }

//    private JFXButton createCloseOrderButton() {
//        JFXButton deleteButton = new JFXButton("Close Order");
//        deleteButton.setOnAction(event -> {
//            PlaceOrderTm selectedItem = table.getSelectionModel().getSelectedItem();
//            table.getItems().remove(selectedItem);
//        });
//        return deleteButton;
//    }


    @FXML
    void closeOrderBtnOnAction(ActionEvent actionEvent) {
        OrdersViewTm selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            // Show the dialog for entering total value
            showTotalInputDialog(selectedOrder);
        }
    }

    private void showTotalInputDialog(OrdersViewTm selectedOrder) {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Enter Total Value");
        dialog.setHeaderText("Enter the total value for the order:");

        TextField totalTextField = new TextField();
        totalTextField.setPromptText("Total");
        dialog.getDialogPane().setContent(new HBox(totalTextField));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return Double.parseDouble(totalTextField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for the total.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(total -> {
            if (total != null) {

                selectedOrder.setTotal(total+"");
                table.refresh();
                showAlert("Total Entered", "Total for the order has been entered successfully.");
            }
        });
    }


    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeView.fxml"))));
        ordersViewBo.getOrdersViewDto();
    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
    }

    @FXML
    void updateBtnOnAction(ActionEvent actionEvent) {
        OrdersViewTm selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            String selectedStatus = comboStatus.getValue().toString();
            boolean updateSuccess = ordersViewBo.updateOrderStatus(selectedOrder.getOrderId(), selectedStatus);

            if (updateSuccess) {

                showAlert("Update Successful", "Order status updated successfully.");
                loadDataToTable();
            } else {

                showAlert("Update Failed", "Failed to update order status.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void statusComboOnAction(ActionEvent actionEvent) {

    }

    public void seachOrderOnAction(ActionEvent actionEvent) {
    }
}
