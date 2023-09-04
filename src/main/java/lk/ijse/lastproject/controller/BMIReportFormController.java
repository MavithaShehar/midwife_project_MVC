package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.AllChildrensBmiDTO;
import lk.ijse.lastproject.dto.tm.BmiReportTM;
import lk.ijse.lastproject.model.BmiModel;
import lk.ijse.lastproject.model.ChildrenModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BMIReportFormController implements Initializable {

    @FXML
    private TableView<BmiReportTM> tbelBmiReport;
    @FXML
    private TableColumn<?, ?> colChildrenId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colAge;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colWeight;
    @FXML
    private TableColumn<?, ?> colHeight;
    @FXML
    private TableColumn<?, ?> colBmiReang;
    @FXML
    private TableColumn<?, ?> colBmiType;
    @FXML
    private TableColumn<?, ?> colTriposha;
    @FXML
    private TextField txtYear;
    @FXML
    private Label lblCount;
    @FXML
    private Label lblTcount;
    @FXML
    private Button getReport;
    @FXML
    void getReportOnAction(ActionEvent event) throws SQLException, FileNotFoundException, JRException {

        try {

            Connection con = DBConnection.getInstance().getConnection();
            InputStream input = new FileInputStream(new File("C:/Intellij IDEA 2021.2.3/LastProject/src/main/resources/report/BmiReport.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap();
            data.put("SerchBmi", txtYear.getText());

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, data, con);
            JasperViewer.viewReport(fillReport, false);


        } catch (JRException | FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void inputYearSerchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String year = txtYear.getText();
        ObservableList<BmiReportTM> obList = FXCollections.observableArrayList();

        BmiModel bmiModel = new BmiModel();
        List<AllChildrensBmiDTO> table = bmiModel.searchByYear(year);

        int count = 0;
        int count2 =0;
        int count3 =0;
        int triposhaCount = 0;

        for (AllChildrensBmiDTO allChildrensBmi : table) {


            BmiReportTM tm = new BmiReportTM(
                    allChildrensBmi.getChildrenId(),
                    allChildrensBmi.getName(),
                    allChildrensBmi.getAge(),
                    allChildrensBmi.getDate(),
                    allChildrensBmi.getWeight(),
                    allChildrensBmi.getHeight(),
                    allChildrensBmi.getBmiRenge(),
                    allChildrensBmi.getBmiType(),
                    allChildrensBmi.getTriposha()


            );

            obList.add(tm);
            lblTcount.setText(String.valueOf(triposhaCount));
            lblCount.setText(table.size()+"");

        }
        tbelBmiReport.setItems(obList);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();

    }

    private void setCellValueFactory() {


        colChildrenId.setCellValueFactory(new PropertyValueFactory<>("childrenId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colBmiReang.setCellValueFactory(new PropertyValueFactory<>("bmiRenge"));
        colBmiType.setCellValueFactory(new PropertyValueFactory<>("bmiType"));
        colTriposha.setCellValueFactory(new PropertyValueFactory<>("triposha"));



    }


}