
package edu.icet.crm.controller;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PasswordResetController {

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField confirmPasswordField;

    @FXML
    void resetPassword(ActionEvent event) {

        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.equals(confirmPassword)) {

            boolean passwordUpdateResult = resetPasswordInDatabase(newPassword);

            if (passwordUpdateResult) {

                System.out.println("Password updated successfully!");
            } else {

                System.out.println("Failed to update password. Please try again.");
            }
        } else {

            System.out.println("Passwords do not match. Please enter matching passwords.");
        }
    }

    private boolean resetPasswordInDatabase(String newPassword) {

        return true;
    }
}
