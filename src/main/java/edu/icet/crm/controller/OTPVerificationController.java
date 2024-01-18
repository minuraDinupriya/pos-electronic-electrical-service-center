package edu.icet.crm.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.util.Random;

public class OTPVerificationController {

    @FXML
    private JFXTextField txtOTP;

    private String email;
    private String generatedOTP;

    public void setEmail(String email) {
        this.email = email;
        this.generatedOTP = generateOTP();
        System.out.println("Generated OTP: " + generatedOTP);
    }

    @FXML
    void btnVerifyOTP(ActionEvent event) {

        String enteredOTP = txtOTP.getText();

        if (enteredOTP.equals(generatedOTP)) {
            showAlert("OTP Verification", "OTP verified successfully. You can now reset your password.");
        } else {
            showAlert("OTP Verification", "Invalid OTP. Please try again.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    String generateOTP() {

        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
