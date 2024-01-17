
package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.ItemsViewBo;
import edu.icet.crm.dto.ItemsViewDto;
import edu.icet.crm.dto.tm.ItemsViewTm;
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
import java.util.List;
import java.io.IOException;

public class ItemsViewController {

    public TableColumn colItemId;
    public TableColumn colStatus;
    public TableColumn colCategory;
    public TableColumn colName;
    public TableColumn colOrderId;
    public TableColumn colDelete;
    public TableView tblItems;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

    ItemsViewBo itemsViewBo= BoFactory.getInstance().getBo(BoType.ITEMS_VIEW_BO);

    private ObservableList<ItemsViewTm> itemsData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        populateTable();
    }

    private void populateTable() {

        itemsData.clear();

        List<ItemsViewDto> itemsList = itemsViewBo.getAllItems();

        for (ItemsViewDto itemDto : itemsList) {
            JFXButton deleteButton = new JFXButton("Delete");
            deleteButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
            deleteButton.setOnAction(event -> deleteItem(itemDto.getItemId()));

            ItemsViewTm itemTm = new ItemsViewTm(
                    itemDto.getItemId(),
                    itemDto.getStatus(),
                    itemDto.getCategory(),
                    itemDto.getName(),
                    itemDto.getOrderId(),
                    deleteButton
            );

            itemsData.add(itemTm);
        }

        tblItems.setItems(itemsData);
    }

    private void showDeletionPopup(boolean deletionResult) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion Result");

        if (deletionResult) {
            alert.setContentText("Item deleted successfully!");
            populateTable();
        } else {
            alert.setContentText("Failed to delete item.");
        }

        alert.showAndWait();
    }

    private void deleteItem(String itemId) {

        boolean deletionResult = itemsViewBo.deleteItem(itemId);
        showDeletionPopup(deletionResult);

    }

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