package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.LogInViewBo;
import edu.icet.crm.bo.custom.UserViewBo;
import edu.icet.crm.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class LoginViewController {

    private final LogInViewBo logInViewBo = BoFactory.getInstance().getBo(BoType.LOGIN_VIEW_BO);
    public AnchorPane pane;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    public void btnSubmitOnAction(ActionEvent actionEvent) throws IOException {
        String userName = txtEmail.getText();
        String enteredPassword = txtPassword.getText();

        UserDto userDto = logInViewBo.getUsers().stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (userDto != null) {
            String storedPassword = userDto.getPassword();

            if (storedPassword.equals(hashPassword(enteredPassword))) {
                String role = userDto.getRole();

                if ("employee".equals(role)) {
                    loadEmployeeView();
                } else {
                    showAlert("Login Failed", "Invalid role.");
                }
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void loadEmployeeView() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();

            for (byte b : hashedBytes) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void forgottenPasswordBtnOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Forgot Password");
        dialog.setHeaderText("Enter your email address");
        dialog.setContentText("Email:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::processEmailForPasswordReset);
    }

    private void processEmailForPasswordReset(String email) {

        UserDto userDto = logInViewBo.getUsers().stream()
                .filter(user -> user.getUserName().equals(email))
                .findFirst()
                .orElse(null);

        if (userDto != null) {

            String generatedOTP = generateOTP();
            sendOTPEmail(email, generatedOTP);

            openOTPVerificationWindow(email);
        } else {
            showAlert("Email Not Found", "The provided email address is not registered.");
        }
    }

    private String generateOTP() {
        return new OTPVerificationController().generateOTP();
    }

    private void sendOTPEmail(String email, String otp) {

        System.out.println("Sending OTP to " + email + ": " + otp);
    }

    private void openOTPVerificationWindow(String email) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OTPVerification.fxml"));
            Parent root = loader.load();

            OTPVerificationController otpVerificationController = loader.getController();
            otpVerificationController.setEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
