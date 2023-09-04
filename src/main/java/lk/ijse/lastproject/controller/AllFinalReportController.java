package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.FinalCountDTO;
import lk.ijse.lastproject.dto.tm.FinalReportTM;
import lk.ijse.lastproject.model.BmiModel;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AllFinalReportController implements Initializable {

    @FXML
    private AnchorPane root1;
    @FXML
    private TableView<FinalReportTM> tablFinal;
    @FXML
    private TableColumn<?, ?> colYear;
    @FXML
    private TableColumn<?, ?> colTotalCount;
    @FXML
    private TableColumn<?, ?> colUnderWeight;
    @FXML
    private TableColumn<?, ?> colHealthy;
    @FXML
    private TableColumn<?, ?> colOverWeight;
    @FXML
    private TableColumn<?, ?> colObese;
    @FXML
    private TableColumn<?, ?> colSeveralObese;
    @FXML
    private TableColumn<?, ?> colTriposhaCount;

    public void finalReport() throws SQLException, ClassNotFoundException {

        ObservableList<FinalReportTM> obList = FXCollections.observableArrayList();
        List<FinalCountDTO> table = BmiModel.searchByCount();

        for(FinalCountDTO finalCount : table){

            FinalReportTM tm = new FinalReportTM(
                    finalCount.getYear(),
                    finalCount.getUnderweightCount(),
                    finalCount.getHealthlyCount(),
                    finalCount.getOverweightCount(),
                    finalCount.getObeseCount(),
                    finalCount.getSeverelyObese(),
                    finalCount.getTriposha(),
                    finalCount.getTotalCount()

            );
            obList.add(tm);
        }
        tablFinal.setItems(obList);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        finalReport();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colTotalCount.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
        colUnderWeight.setCellValueFactory(new PropertyValueFactory<>("underweightCount"));
        colHealthy.setCellValueFactory(new PropertyValueFactory<>("healthlyCount"));
        colOverWeight.setCellValueFactory(new PropertyValueFactory<>("overweightCount"));
        colObese.setCellValueFactory(new PropertyValueFactory<>("obeseCount"));
        colSeveralObese.setCellValueFactory(new PropertyValueFactory<>("severelyObese"));
        colTriposhaCount.setCellValueFactory(new PropertyValueFactory<>("triposha"));

    }

    public void btnGetReportOnAction(ActionEvent actionEvent) {
        InputStream is = this.getClass().getResourceAsStream("/report/final_report.jrxml");
        try {
            JasperReport jr = JasperCompileManager.compileReport(is);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DBConnection.getInstance().getConnection());
//            JasperPrintManager.printReport(jp, true);
            JasperViewer.viewReport(jp, true);

        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }

//        String input = "2023-04-05";
//
//        InputStream is = this.getClass().getResourceAsStream("/report/one_single_report.jrxml");
//        HashMap hm = new HashMap<>();
//        hm.put("paraDate", input);
//
//        try {
//            JasperReport jr = JasperCompileManager.compileReport(is);
//            JasperPrint jp = JasperFillManager.fillReport(jr, hm, DBConnection.getInstance().getConnection());
////            JasperPrintManager.printReport(jp, true);
//
//            JasperViewer.viewReport(jp, false);
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}