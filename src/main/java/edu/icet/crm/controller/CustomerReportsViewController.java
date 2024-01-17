package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.CustomerReportsViewBo;
import edu.icet.crm.dto.CustomerBean;
import edu.icet.crm.dto.CustomerDto;

import edu.icet.crm.dto.tm.CustomerReportsViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerReportsViewController {

    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colContactNumber;
    public TableColumn colEmailAddress;
    public TableColumn colDeleteBtn;
    public TableView<CustomerReportsViewTm> tblCustomerReports;
    public JFXTextField txtSearch;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;
    CustomerReportsViewBo customerReportsViewBo = BoFactory.getInstance().getBo(BoType.CUSTOMER_REPORTS_VIEW_BO);

    private ObservableList<CustomerReportsViewTm> customerData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
//        colDeleteBtn.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));

        populateTable();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    }

    private void populateTable() {
        customerData.clear();

        List<CustomerDto> customerList = customerReportsViewBo.getCustomers();

        for (CustomerDto customerDto : customerList) {
            JFXButton deleteBtn = new JFXButton("Delete");
            deleteBtn.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");

            CustomerReportsViewTm customerReportsViewTm = new CustomerReportsViewTm(
                    customerDto.getCustomerId(),
                    customerDto.getName(),
                    customerDto.getContactNumber(),
                    customerDto.getEmailAddress(),
                    deleteBtn
            );
            deleteBtn.setOnAction(event -> deleteCustomer(customerReportsViewTm.getCustomerId()));

            customerData.add(customerReportsViewTm);
        }

        tblCustomerReports.setItems(customerData);
    }

    private void deleteCustomer(String customerId) {
        boolean deletionResult = customerReportsViewBo.deleteCustomer(customerId);
        showDeletionPopup(deletionResult);
    }

    private void showDeletionPopup(boolean deletionResult) {

    }

    private void filterTable(String searchCriteria) {
        ObservableList<CustomerReportsViewTm> filteredList = FXCollections.observableArrayList();

        for (CustomerReportsViewTm customer : customerData) {
            if (customer.getName().toLowerCase().contains(searchCriteria.toLowerCase())) {
                filteredList.add(customer);
            }
        }

        tblCustomerReports.setItems(filteredList);
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

    public void generateReportsBtnOnAction(ActionEvent actionEvent) {
        try {

            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/customerReports.jrxml"));

            Map<String, Object> parameters = new HashMap<>();

            JRDataSource dataSource = createDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Customer Reports");
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createDataSource() {
        List<CustomerDto> customerList = customerReportsViewBo.getCustomers();

        List<CustomerBean> reportData = new ArrayList<>();

        for (CustomerDto customerDto : customerList) {
            CustomerBean bean = new CustomerBean(
                    customerDto.getCustomerId(),
                    customerDto.getName(),
                    customerDto.getContactNumber(),
                    customerDto.getEmailAddress()
            );
            reportData.add(bean);
        }

        return new JRBeanCollectionDataSource(reportData);
    }

}
