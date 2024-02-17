package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OwnerViewController {

    public JFXButton btnLogOut;
    public Label lblTime;
    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnSalesReports;

    @FXML
    private JFXButton btnOrderReports;

    @FXML
    private JFXButton btnCustomerReports;
    public void initialize(){
        calculateTime();
    }

    private void calculateTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ), new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

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
