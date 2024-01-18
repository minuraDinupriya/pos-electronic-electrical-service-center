package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.UserViewBo;
import edu.icet.crm.dto.UserDto;
import edu.icet.crm.dto.tm.UsersViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class EmployeeReportsViewController {

    private final UserViewBo usersViewBo = BoFactory.getInstance().getBo(BoType.USERS_VIEW_BO);

    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private TableColumn<UsersViewTm, String> colUserId;

    @FXML
    private TableColumn<UsersViewTm, String> colEmailAddress;

    @FXML
    private TableColumn<UsersViewTm, String> colPassword;

    @FXML
    private TableColumn<UsersViewTm, JFXButton> colOption;

    @FXML
    private TableView<UsersViewTm> tblUsers;

    @FXML
    void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        populateTable();
    }

    private void populateTable() {
        ObservableList<UsersViewTm> userData = FXCollections.observableArrayList();
        List<UserDto> userList = usersViewBo.getUsers();

        for (UserDto userDto : userList) {
            JFXButton deleteBtn = createDeleteButton(userDto.getUserId());
            UsersViewTm userViewTm = new UsersViewTm(
                    userDto.getUserId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    deleteBtn
            );

            deleteBtn.setOnAction(event -> deleteUser(userViewTm.getUserId()));

            userData.add(userViewTm);
        }

        tblUsers.setItems(userData);
    }

    private JFXButton createDeleteButton(String userId) {
        JFXButton deleteBtn = new JFXButton("Delete");
        deleteBtn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
        deleteBtn.setOnAction(event -> deleteUser(userId));
        return deleteBtn;
    }

    private void deleteUser(String userId) {
        boolean deletionResult = usersViewBo.deleteUser(userId);
        showDeletionPopup(deletionResult);
        populateTable();
    }

    private void showDeletionPopup(boolean deletionResult) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion Result");
        alert.setHeaderText(null);

        if (deletionResult) {
            alert.setContentText("User deleted successfully.");
        } else {
            alert.setContentText("Failed to delete user. Please try again.");
        }

        alert.showAndWait();
    }

    @FXML
    void backBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OwnerView.fxml"))));
    }

    @FXML
    void logOutBtnOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"))));
    }

    @FXML
    void addUserOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (!isValidPassword(password)) {
            showAlert("Invalid Password", "Password should be 8 characters or more, including numbers, letters, and symbols.");
            return;
        }

        if (!isValidEmailAddress(userName)) {
            showAlert("Invalid Email Address", "Email address should contain '@' symbol.");
            return;
        }


        String hashedPassword = hashPassword(password);

        int lastUserId=usersViewBo.getLastUserId();

        UserDto user = new UserDto(String.format("user%d", ++lastUserId), userName, hashedPassword,"employee");
        boolean result = usersViewBo.addUser(user);

        if (result) {
            showAlert("User Added", "User added successfully.");
            clearFields();
            populateTable();
        } else {
            showAlert("Error", "Failed to add user. Please try again.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        txtUserName.clear();
        txtPassword.clear();
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,}$");
    }

    private boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.contains("@");
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
}
