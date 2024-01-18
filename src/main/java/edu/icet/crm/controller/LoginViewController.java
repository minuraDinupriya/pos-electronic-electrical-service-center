package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.UserViewBo;
import edu.icet.crm.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginViewController {

    private final UserViewBo userViewBo = BoFactory.getInstance().getBo(BoType.USERS_VIEW_BO);
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

        UserDto userDto = userViewBo.getUsers().stream()
                .filter(user -> user.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (userDto != null) {
            String storedPassword = userDto.getPassword();

            if (storedPassword.equals(hashPassword(enteredPassword))) {
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OwnerView.fxml"))));
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
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

    }
}
