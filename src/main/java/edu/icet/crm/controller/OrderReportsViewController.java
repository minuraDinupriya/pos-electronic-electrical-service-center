package edu.icet.crm.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.icet.crm.bo.BoFactory;
import edu.icet.crm.bo.BoType;
import edu.icet.crm.bo.custom.OrderReportsViewBo;
import edu.icet.crm.dto.OrderBean;
import edu.icet.crm.dto.OrderDto;
import edu.icet.crm.dto.tm.OrderReportsViewTm;
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

public class OrderReportsViewController {

    @FXML
    private TableColumn<OrderReportsViewTm, String> colOrderId;

    @FXML
    private TableColumn<OrderReportsViewTm, String> colNote;

    @FXML
    private TableColumn<OrderReportsViewTm, String> colDate;

    @FXML
    private TableColumn<OrderReportsViewTm, String> colOrderStatus;

    @FXML
    private TableColumn<OrderReportsViewTm, String> colCustomerID;

    @FXML
    private TableView<OrderReportsViewTm> tblOrderReports;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private BorderPane pane;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnLogOut;

    private OrderReportsViewBo orderReportsViewBo = BoFactory.getInstance().getBo(BoType.ORDER_REPORTS_VIEW_BO);

    private ObservableList<OrderReportsViewTm> orderData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        populateTable();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));
    }

    private void populateTable() {
        orderData.clear();

        List<OrderDto> orderList = orderReportsViewBo.getAllOrders();

        for (OrderDto orderDto : orderList) {
            OrderReportsViewTm orderReportsViewTm = new OrderReportsViewTm(
                    orderDto.getOrderId(),
                    orderDto.getStatus(),
                    orderDto.getCustomerId(),
                    orderDto.getOrderDate(),
                    orderDto.getNote()
            );

            orderData.add(orderReportsViewTm);
        }

        tblOrderReports.setItems(orderData);
    }

    private void filterTable(String searchCriteria) {
        ObservableList<OrderReportsViewTm> filteredList = FXCollections.observableArrayList();

        for (OrderReportsViewTm order : orderData) {
            if (order.getOrderId().toLowerCase().contains(searchCriteria.toLowerCase())) {
                filteredList.add(order);
            }
        }

        tblOrderReports.setItems(filteredList);
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
    void generateReportsBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/orderReports.jrxml"));
            Map<String, Object> parameters = new HashMap<>();
            JRDataSource dataSource = createOrderDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setTitle("Order Reports");
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private JRDataSource createOrderDataSource() {
        List<OrderDto> orderList = orderReportsViewBo.getAllOrders();

        List<OrderBean> reportData = new ArrayList<>();

        for (OrderDto orderDto : orderList) {
            OrderBean bean = new OrderBean(
                    orderDto.getOrderId(),
                    orderDto.getStatus(),
                    orderDto.getCustomerId(),
                    orderDto.getOrderDate(),
                    orderDto.getNote()
            );
            reportData.add(bean);
        }

        return new JRBeanCollectionDataSource(reportData);
    }
}
