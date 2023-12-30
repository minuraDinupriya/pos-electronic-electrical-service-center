package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerViewController {

    public JFXButton btnLogOut;
    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnSalesReports;

    @FXML
    private JFXButton btnOrderReports;

    @FXML
    private JFXButton btnCustomerReports;

    @FXML
    void btnCustomerReportsOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSalesReports.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerReportsView.fxml"))));
    }

    @FXML
    void btnEmployeesOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnEmployee.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeReportsView.fxml"))));
    }

    @FXML
    void btnOrderReportsOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnOrderReports.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderReportsView.fxml"))));
    }

    @FXML
    void btnSalesReportsOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSalesReports.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SalesReportsView.fxml"))));
    }


    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
    }
}
