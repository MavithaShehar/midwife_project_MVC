package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lastproject.dto.VaccDescripDTO;
import lk.ijse.lastproject.dto.tm.VaccDescripTM;
import lk.ijse.lastproject.model.VaccineModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class VaccineWindowFormController implements Initializable {

    // get values from texfilde
    @FXML
    private TextField btnVaccineID;
    @FXML
    private TextField btnVaccineName;
    @FXML
    private TextField btnVaccineDescription;
    @FXML
    private TextField bntNoDose;

    // save data to tabel

    @FXML
    private TableView<VaccDescripTM> tableSaveVaccine;
    @FXML
    private TableColumn<?, ?> colVaccineId;
    @FXML
    private TableColumn<?, ?> colVaccineName;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colNoDose;
    @FXML
    private TableColumn<?, ?> colstock;
    @FXML
    private TextField bntStocks;

    ObservableList<VaccDescripTM>vaccDescripTMS = FXCollections.observableArrayList();

    // save data to database
    public void btnSaveOnAction(ActionEvent actionEvent) {

        if( btnVaccineID.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Vccinet ID !!!").show();
        }else if(! bntNoDose.getText().matches("[0-9]")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right NO of Dose !!!").show();
        }else if(! bntStocks.getText().matches("[1-999]")){
            new Alert(Alert.AlertType.ERROR," check Stock limit !!!").show();
        }else if(btnVaccineName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Vaccine Name !!!").show();
        }else if(btnVaccineDescription.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter About Vaccine  !!!").show();
        }else {
            try {
                String vaccineId = btnVaccineID.getText();
                String vaccineName = btnVaccineName.getText();
                String vaccineDescription = btnVaccineDescription.getText();
                int noDose = Integer.parseInt(bntNoDose.getText());
                int stock = Integer.parseInt(bntStocks.getText());

                boolean isAdded = false;

                VaccineModel vaccineModel = new VaccineModel();

                isAdded= vaccineModel.saveVaccdes(new VaccDescripDTO(vaccineId,vaccineName,vaccineDescription,noDose,stock));
                if(isAdded){
                    VaccDescripTM addVaccineTM = new VaccDescripTM(vaccineId, vaccineName, vaccineDescription, noDose,stock);
                    vaccDescripTMS.add(addVaccineTM);
                    tableSaveVaccine.setItems(vaccDescripTMS);

                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR," NOT Registerd !!!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR," Vaccine Id alrydy exite!!!").show();
            }
        }
    }

    @SneakyThrows
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCellValueFactory();
        loadTable();
    }

    private void loadCellValueFactory() {

        colVaccineId.setCellValueFactory(new PropertyValueFactory<>("vacId"));
        colVaccineName.setCellValueFactory(new PropertyValueFactory<>("vacName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colNoDose.setCellValueFactory(new PropertyValueFactory<>("noDose"));
        colstock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void loadTable() throws SQLException {

        VaccineModel vaccineModel = new VaccineModel();
        try (ResultSet rs = vaccineModel.getData()) {
            while (rs.next()) {
                VaccDescripTM addVaccineTM = new VaccDescripTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                );
                vaccDescripTMS.add(addVaccineTM);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tableSaveVaccine.setItems(vaccDescripTMS);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (btnVaccineID.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Vccinet ID !!!").show();
        } else if (!bntNoDose.getText().matches("[0-9]")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right NO of Dose !!!").show();
        } else if (!bntStocks.getText().matches("[1-999]")) {
            new Alert(Alert.AlertType.ERROR, " check Stock limit !!!").show();
        } else if (btnVaccineName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Vaccine Name !!!").show();
        } else if (btnVaccineDescription.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter About Vaccine  !!!").show();
        } else {

            String vaccineId = btnVaccineID.getText();
            String vaccineName = btnVaccineName.getText();
            String vaccineDescription = btnVaccineDescription.getText();
            int noDose = Integer.parseInt(bntNoDose.getText());
            int stock = Integer.parseInt(bntStocks.getText());

            VaccineModel vaccineModel = new VaccineModel();

            boolean isUpdated = vaccineModel.update(new VaccDescripDTO(vaccineId, vaccineName, vaccineDescription, noDose, stock));

            if (isUpdated) {

                VaccDescripTM vaccDescripTM = new VaccDescripTM(vaccineId, vaccineName, vaccineDescription, noDose, stock);
                vaccDescripTMS.add(vaccDescripTM);
                tableSaveVaccine.setItems(vaccDescripTMS);
                tableSaveVaccine.getItems().clear();
                loadCellValueFactory();
                loadTable();

                new Alert(Alert.AlertType.CONFIRMATION, "your Data is Updated!").show();

            }else {

                new Alert(Alert.AlertType.ERROR, "Sory!! something happened!").show();
            }
        }
    }
    @FXML
    void btnDeletOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        VaccineModel vaccineModel = new VaccineModel();
        boolean isDelete = vaccineModel.deleteVacc(btnVaccineID.getText());

        if(isDelete) {

            tableSaveVaccine.getItems().clear();

            loadCellValueFactory();
            loadTable();

            new Alert(Alert.AlertType.CONFIRMATION, " Vaccine Data deleted !!! :)").show();
        }else {

            new Alert(Alert.AlertType.CONFIRMATION, " Wrong Vaccine Id . that Data Not inside This System !!! :)").show();
        }
    }

    @FXML
    void vaccineIdSerchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        VaccineModel vaccineModel = new VaccineModel();
        VaccDescripDTO vaccDescripDTO =  vaccineModel.searchById(btnVaccineID.getText());
        if(vaccDescripDTO != null) {

            btnVaccineID.setText(vaccDescripDTO.getVacId());
            btnVaccineName.setText(vaccDescripDTO.getVacName());
            btnVaccineDescription.setText(vaccDescripDTO.getDescription());
            bntNoDose.setText(String.valueOf(vaccDescripDTO.getNoDose()));
            bntStocks.setText(String.valueOf(vaccDescripDTO.getStock()));
        }
    }
}