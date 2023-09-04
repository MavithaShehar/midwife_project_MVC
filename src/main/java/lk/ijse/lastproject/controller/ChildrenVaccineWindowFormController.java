package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.ChildInsVaccineDTO;
import lk.ijse.lastproject.dto.MidwifeDTO;
import lk.ijse.lastproject.dto.tm.ChildrenVaccineTM;
import lk.ijse.lastproject.dto.tm.MidwifeTM;
import lk.ijse.lastproject.model.ChildrenVaccineModel;
import lk.ijse.lastproject.model.MidwifeModel;
import lk.ijse.lastproject.model.VaccineModel;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.Properties;

public class ChildrenVaccineWindowFormController{

    @FXML
    private TextField txtChildrenId;
    @FXML
    private TextField txtVaccineId;
    @FXML
    private TextField txtNoDose;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtNextDate;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtID;
    @FXML
    private TableView<ChildrenVaccineTM> tabelSaveChilVacc;
    @FXML
    private TableColumn<?, ?> colChildrenId;
    @FXML
    private TableColumn<?, ?> colVaccineId;
    @FXML
    private TableColumn<?, ?> colNoDose;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colNextDate;
    @FXML
    private TableColumn<?, ?> colAge;
    @FXML
    private TableColumn<?, ?> colVaccinetId;

    ObservableList<ChildrenVaccineTM> childrenVaccineTMS = FXCollections.observableArrayList();

    // **** get values from textfiled and set it database ****
    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(! txtChildrenId.getText().matches("(C)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Children ID !!!").show();
        }else if(! txtID.getText().matches("(V)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Vaccinet Nomber !!!").show();
        }else if(! txtNoDose.getText().matches("[1-9]")){
            new Alert(Alert.AlertType.ERROR," check Stock No of Dose !!!").show();
        }else if(! txtAge.getText().matches("^[1-9]|1[0-2]$")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Age  !!!").show();
        }else if(txtVaccineId.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Vaccine ID  !!!").show();
        }else if(txtDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Date  !!!").show();
        }else if(txtNextDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Next Date  !!!").show();

        }else{


            String childrenId = txtChildrenId.getText();
            String vaccineId = txtVaccineId.getText();
            int noDose = Integer.parseInt(txtNoDose.getText());
            String date = txtDate.getText();
            String nxtDate = txtNextDate.getText();
            int age = Integer.parseInt(txtAge.getText());
            String ID = txtID.getText();

            try {
                ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
                boolean isAdded = childrenVaccineModel.register(new ChildInsVaccineDTO(childrenId,vaccineId,noDose,date,nxtDate,age,ID));

                if(isAdded){

                    ChildrenVaccineTM ChildrenVaccineTM = new ChildrenVaccineTM(childrenId,vaccineId,noDose,date,nxtDate,age,ID);
                    childrenVaccineTMS.add(ChildrenVaccineTM);
                    tabelSaveChilVacc.setItems(childrenVaccineTMS);

                    VaccineModel vaccineModel = new VaccineModel();
                    boolean isUpdated = vaccineModel.stockUpdate(vaccineId);

                    System.out.println(isUpdated);
                    if (isUpdated){
                        ChildrenVaccineTM childrenVaccineTM = new ChildrenVaccineTM(childrenId, vaccineId, noDose, date, nxtDate , age,ID);
                        childrenVaccineTMS.add(childrenVaccineTM);
                        tabelSaveChilVacc.setItems(childrenVaccineTMS);
                        Connection con = null;
                        con = DBConnection.getInstance().getConnection();
                        con.setAutoCommit(false);
                        con.commit();
                    }

                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Not Registerd !!!").show();
                }
            }catch (SQLException | ClassNotFoundException e) {

                new Alert(Alert.AlertType.ERROR, "Vaccine ID is already Exited !!!"+"\n"+ "! Check the Vaccine Dose Count").show();
            }


/*
            boolean isAdded = false;
            Connection con = null;
            try {
                con = DBConnection.getInstance().getConnection();
                con.setAutoCommit(false);
                isAdded = ChildrenVaccineModel.register(childrenId,vaccineId,noDose,date,nxtDate,age,ID);

                System.out.println(isAdded+" IsAdded");

                VaccineModel vaccineModel = new VaccineModel();

                if(isAdded){

                    boolean isUpdated = vaccineModel.stockUpdate(vaccineId);

                    System.out.println(isUpdated);
                    if (isUpdated){
                        ChildrenVaccineTM childrenVaccineTM = new ChildrenVaccineTM(childrenId, vaccineId, noDose, date, nxtDate , age,ID);
                        childrenVaccineTMS.add(childrenVaccineTM);
                        tabelSaveChilVacc.setItems(childrenVaccineTMS);
                        con.commit();
                    }

                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();

                }else{
                    new Alert(Alert.AlertType.ERROR," NOT Registerd !!!").show();

                }

            } catch (SQLException | ClassNotFoundException e) {
                con.rollback();
                System.out.println(e);

            }finally {
                con.rollback();
                con.setAutoCommit(true);
            }*/
        }

    }

    // get values from database and set to the table

    @SneakyThrows
    @FXML
    public void initialize(){
        loadCellValueFactory();
        loadTable();

    }

    private void loadCellValueFactory() {

        colChildrenId.setCellValueFactory(new PropertyValueFactory<>("childrenId"));
        colVaccineId.setCellValueFactory(new PropertyValueFactory<>("vacId"));
        colNoDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNextDate.setCellValueFactory(new PropertyValueFactory<>("nextDate"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colVaccinetId.setCellValueFactory(new PropertyValueFactory<>("ID"));


    }

    private void loadTable() throws SQLException, ClassNotFoundException {

        ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
        ResultSet rs = childrenVaccineModel.getData();

        while (rs.next()) {
            ChildrenVaccineTM childrenVaccineTM = new ChildrenVaccineTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7)
            );
            childrenVaccineTMS.add(childrenVaccineTM);

        }
        tabelSaveChilVacc.setItems(childrenVaccineTMS);


    }

    public void childrenIdSerchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = txtID.getText();
        ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
        ChildInsVaccineDTO childInsVaccineDTO = childrenVaccineModel.searchChildrenVaccID(id);
        if(childInsVaccineDTO != null) {

            txtChildrenId.setText(childInsVaccineDTO.getChildrenId());
            txtVaccineId.setText(childInsVaccineDTO.getVacId());
            txtNoDose.setText(String.valueOf(childInsVaccineDTO.getDose()));
            txtDate.setText(String.valueOf(childInsVaccineDTO.getDate()));
            txtNextDate.setText(String.valueOf(childInsVaccineDTO.getNextDate()));
            txtAge.setText(String.valueOf(childInsVaccineDTO.getAge()));
            txtID.setText(childInsVaccineDTO.getID());

        }

    }

    public void binUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!txtChildrenId.getText().matches("(C)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Children ID !!!").show();
        } else if (!txtID.getText().matches("(V)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Vaccinet Nomber !!!").show();
        } else if (!txtNoDose.getText().matches("[1-9]")) {
            new Alert(Alert.AlertType.ERROR, " check Stock No of Dose !!!").show();
        } else if (!txtAge.getText().matches("^[1-9]|1[0-2]$")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Age  !!!").show();
        } else if (txtVaccineId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Vaccine ID  !!!").show();
        } else if (txtDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Date  !!!").show();
        } else if (txtNextDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Next Date  !!!").show();

        } else {

            String childrenId = txtChildrenId.getText();
            String vacId = txtVaccineId.getText();
            int dose = Integer.parseInt(txtNoDose.getText());
            String date = txtDate.getText();
            String nextDate = txtNextDate.getText();
            int age = Integer.parseInt(txtAge.getText());
            String ID = txtID.getText();

            ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
            boolean isUpdated = childrenVaccineModel.update(new ChildInsVaccineDTO(childrenId, vacId, dose, date, nextDate, age, ID));   //type inference

            try {

                if (isUpdated) {

                    ChildrenVaccineTM childrenVaccineTM = new ChildrenVaccineTM(childrenId, vacId, dose, date, nextDate, age, ID);
                    childrenVaccineTMS.add(childrenVaccineTM);
                    tabelSaveChilVacc.setItems(childrenVaccineTMS);
                    tabelSaveChilVacc.getItems().clear();
                    loadCellValueFactory();
                    loadTable();

                    new Alert(Alert.AlertType.CONFIRMATION, "your Data is Updated!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Sory!! something happened!").show();
            }

        }
    }


    public void btnDeletOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id =txtID.getText();
        ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
        boolean idDelete = childrenVaccineModel.deleteChildrenVaccine(id);
        if(idDelete ) {
            tabelSaveChilVacc.getItems().clear();
            loadCellValueFactory();
            loadTable();

            new Alert(Alert.AlertType.CONFIRMATION, "deleted Comfome!!  :)").show();
        }

    }
}