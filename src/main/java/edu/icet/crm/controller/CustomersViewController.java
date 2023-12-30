package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersViewController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

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

}
