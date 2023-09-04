package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.AllChildrensVaccDTO;
import lk.ijse.lastproject.dto.tm.VaccReportTM;
import lk.ijse.lastproject.model.VaccineModel;
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

public class VaccineReportFormController implements Initializable {
    @FXML
    private AnchorPane root1;
    @FXML
    private TextField serchYear;
    @FXML
    private TableView<VaccReportTM> tabelVaccReport;
    @FXML
    private TableColumn<?, ?> colChildrenId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colBod;
    @FXML
    private TableColumn<?, ?> colGender;
    @FXML
    private TableColumn<?, ?> colVaccId;
    @FXML
    private TableColumn<?, ?> colVaccName;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colNoOfDose;
    @FXML
    private TableColumn<?, ?> colDose;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colAge;
    @FXML
    private Button btnGetReport;

    ObservableList<VaccReportTM> oblist = FXCollections.observableArrayList();

    @FXML
    void serchYearOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String year = serchYear.getText();

        VaccineModel vaccineModel = new VaccineModel();
        List<AllChildrensVaccDTO> table = vaccineModel.serchByYear(year);

        for(AllChildrensVaccDTO allChildrensVacc : table){

            VaccReportTM tm = new VaccReportTM(
                    allChildrensVacc.getChildrenId(),
                    allChildrensVacc.getName(),
                    allChildrensVacc.getBod(),
                    allChildrensVacc.getGender(),
                    allChildrensVacc.getVacId(),
                    allChildrensVacc.getVacName(),
                    allChildrensVacc.getDescription(),
                    allChildrensVacc.getNoofDose(),
                    allChildrensVacc.getDose(),
                    allChildrensVacc.getDate(),
                    allChildrensVacc.getAge()
            );
            oblist.add(tm);
        }
        tabelVaccReport.setItems(oblist);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValueFactory();
    }

    private void setCellValueFactory() {

        colChildrenId.setCellValueFactory(new PropertyValueFactory<>("childrenId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBod.setCellValueFactory(new PropertyValueFactory<>("bod"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colVaccId.setCellValueFactory(new PropertyValueFactory<>("vacId"));
        colVaccName.setCellValueFactory(new PropertyValueFactory<>("vacName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colNoOfDose.setCellValueFactory(new PropertyValueFactory<>("noofDose"));
        colDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) {

        try {
            Connection con = DBConnection.getInstance().getConnection();

            InputStream input = new FileInputStream(new File("C:/Intellij IDEA 2021.2.3/LastProject/src/main/resources/report/Vaccine_Report.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> data = new HashMap();
            data.put("selectYear", serchYear.getText());

            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, data, con);
            JasperViewer.viewReport(fillReport, false);

        } catch (JRException | FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}