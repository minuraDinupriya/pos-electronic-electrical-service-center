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
    public Label lblOrderID;
    public JFXComboBox comboStatus;
    public TableView<OrdersViewTm> table;
    public JFXTextField txtSearch;
    public TableColumn colTotal;
    public TableColumn colReturnOrder;
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
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colReturnOrder.setCellValueFactory(new PropertyValueFactory<>("returnOrderButton"));

        loadDataToTable();

        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                lblOrderID.setText(newValue.getOrderId());
            }
        });
        ObservableList<String> statusOptions = FXCollections.observableArrayList("PENDING", "PROCESSING", "COMPLETED");
        comboStatus.setItems(statusOptions);
    }

    private void loadDataToTable() {
        ObservableList<OrdersViewTm> tmList = FXCollections.observableArrayList();

        List<OrderDto> ordersList = ordersViewBo.getOrdersViewDto();
        if (ordersList != null) {
            for (OrderDto order : ordersList) {
                JFXButton returnButton = new JFXButton("Return");
                JFXButton closeOrderButton = new JFXButton("Close Order");

                OrdersViewTm ordersViewTm = new OrdersViewTm(
                        order.getOrderId(),
                        order.getStatus(),
                        order.getCustomerId(),
                        order.getOrderDate(),
                        order.getNote(),
                        closeOrderButton,
                        order.getTotal(),
                        returnButton
                );

                if ("CLOSED".equals(order.getStatus())) {
                    closeOrderButton.setDisable(true);
                }

                closeOrderButton.setOnAction(ActionEvent -> {
                    showTotalInputDialog(ordersViewTm);
                });

                tmList.add(ordersViewTm);
            }
        }

        table.setItems(tmList);
    }

    private void showTotalInputDialog(OrdersViewTm selectedOrder) {
        Dialog<Double> dialog = new Dialog<>();
        dialog.setTitle("Enter Labour Charge");
        dialog.setHeaderText("Enter the Labour Charge for the order:");

        TextField totalTextField = new TextField();
        totalTextField.setPromptText("Total");
        dialog.getDialogPane().setContent(new HBox(totalTextField));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    return Double.parseDouble(totalTextField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid number for the labour charge.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(enteredTotal -> {
            if (enteredTotal != null) {

                double newTotal = enteredTotal + selectedOrder.getTotal();

                boolean updateSuccess = ordersViewBo.updateOrder(new OrderDto(
                        selectedOrder.getOrderId(),
                        "CLOSED",
                        enteredTotal
                ));

                if (updateSuccess) {
                    selectedOrder.setStatus("CLOSED");
                    selectedOrder.setTotal(newTotal);
                    selectedOrder.getCloseOrderButton().setDisable(true);
                    table.refresh();
                    showAlert("Total Entered", "Total for the order has been entered and saved successfully.");
                } else {
                    showAlert("Update Failed", "Failed to update total for the order.");
                }
            }
        });
    }


    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeView.fxml"))));
//        ordersViewBo.getOrdersViewDto();
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
            boolean updateSuccess = ordersViewBo.updateOrder(new OrderDto(
                    selectedOrder.getOrderId(),
                    selectedStatus,
                    selectedOrder.getTotal()
            ));

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
