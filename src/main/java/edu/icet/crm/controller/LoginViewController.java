package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.LogInViewBo;
import edu.icet.crm.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

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
                } else if ("admin".equals(role)) {
                    loadAdminView();
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

    private void loadAdminView() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OwnerView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

    private void loadEmployeeView() throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
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
            boolean isOTPVerified = showOTPVerificationDialog(email, generatedOTP);

            if (isOTPVerified) {
                showPasswordResetDialog(email);
            }
        } else {
            showAlert("Email Not Found", "The provided email address is not registered.");
        }
    }

    private boolean showOTPVerificationDialog(String email, String generatedOTP) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("OTP Verification");
        dialog.setHeaderText("Enter the OTP sent to your email");
        dialog.setContentText("OTP:");

        Optional<String> result = dialog.showAndWait();

        return result.map(enteredOTP -> enteredOTP.equals(generatedOTP)).orElse(false);
    }

    private void showPasswordResetDialog(String email) {
        JFXPasswordField newPasswordField = new JFXPasswordField();
        JFXPasswordField confirmPasswordField = new JFXPasswordField();

        newPasswordField.setPromptText("New Password");
        confirmPasswordField.setPromptText("Confirm Password");

        VBox content = new VBox(10); //10 pixel spacing
        content.getChildren().addAll(newPasswordField, confirmPasswordField);

        Alert passwordResetDialog = new Alert(Alert.AlertType.CONFIRMATION);
        passwordResetDialog.setTitle("Password Reset");
        passwordResetDialog.setHeaderText("Enter your new password");
        passwordResetDialog.getDialogPane().setContent(content);

        ButtonType confirmButton = new ButtonType("Reset Password", ButtonType.OK.getButtonData());
        passwordResetDialog.getButtonTypes().setAll(confirmButton, ButtonType.CANCEL);

        Stage stage = (Stage) passwordResetDialog.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);

        passwordResetDialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == confirmButton) {
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                if (newPassword.equals(confirmPassword)) {
                    boolean passwordUpdateResult = resetPasswordInDatabase(email, newPassword);

                    if (passwordUpdateResult) {
                        showAlert("Password Reset", "Password updated successfully!");
                    } else {
                        showAlert("Password Reset Failed", "Failed to update password. Please try again.");
                    }
                } else {
                    showAlert("Password Reset Failed", "Passwords do not match. Please enter matching passwords.");
                }
            }
        });
    }


    private boolean resetPasswordInDatabase(String email, String newPassword) {
        String hashedPassword=hashPassword(newPassword);
        return logInViewBo.updatePassword(email,hashedPassword);
    }

    private void sendOTPEmail(String toEmail, String otp) {

        final String username = "minura21173@gmail.com";
        final String password = "debt dqbn vjyk anxu";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset OTP");
            message.setText("Your OTP for password reset is: " + otp);

            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
