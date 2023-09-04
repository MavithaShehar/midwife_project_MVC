package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.lastproject.dto.MidwifeDTO;
import lk.ijse.lastproject.dto.tm.MidwifeTM;
import lk.ijse.lastproject.model.MidwifeModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class SaveMidwifeWindowForm implements Initializable {

    public Label count;
    @FXML
    private AnchorPane root1;
    @FXML
    private TextField txtMidwifeID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtNIC;
    @FXML
    private TextField txtPosition;
    @FXML
    private TextField txtservesArea;
    @FXML
    private TextField txtAddress;
    @FXML
    private TableColumn<?, ?> colContact;
    @FXML
    private TextField txtContact;
    // save values to Tabel
    @FXML
    private TableView<MidwifeTM> tabelSaveMidwife;
    @FXML
    private TableColumn<?, ?> colMidwifeID;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colNIC;
    @FXML
    private TableColumn<?, ?> colPosition;
    @FXML
    private TableColumn<?, ?> colServesArea;
    @FXML
    private TableColumn<?, ?> colAddrwess;
    public AnchorPane getRoot1() {
        return root1;
    }

    ObservableList<MidwifeTM> midwifeTMS = FXCollections.observableArrayList();

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCellValueFactory();
        loadTable();
    }

    private void loadTable() throws SQLException {

        MidwifeModel midwifeModel = new MidwifeModel();

        try (ResultSet rs = midwifeModel.getData()) {

            while (rs.next()) {
                MidwifeTM midwifeTM = new MidwifeTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );

                midwifeTMS.add(midwifeTM);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tabelSaveMidwife.setItems(midwifeTMS);
        count.setText(midwifeTMS.size()+"");
    }

    @FXML
    void tableMidwifeOnMouseClicked(MouseEvent event) {
        MidwifeTM midwifeTM = tabelSaveMidwife.getSelectionModel().getSelectedItem();
        txtMidwifeID.setText(midwifeTM.getMidwifeID());
        txtNIC.setText(midwifeTM.getNic());
        txtName.setText(midwifeTM.getName());
        txtAddress.setText(midwifeTM.getAddress());
        txtPosition.setText(midwifeTM.getPosition());
        txtservesArea.setText(midwifeTM.getServesArea());
        txtContact.setText(midwifeTM.getContact());
    }
    private void loadCellValueFactory() {
        colMidwifeID.setCellValueFactory(new PropertyValueFactory<>("midwifeID"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colServesArea.setCellValueFactory(new PropertyValueFactory<>("servesArea"));
        colAddrwess.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    //*********** Save data to database *********************

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {

        if (!txtMidwifeID.getText().matches("(M)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Right Midwife ID !!!").show();
        }else if( txtName.getText().isEmpty() ){
            new Alert(Alert.AlertType.ERROR, "Please Enter Midwife Name !!!").show();
        } else if ( !txtNIC.getText().matches("^([0-9]{9}[x|X|v|V]|[0-9]{12})$")) {
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Midwife NIC Number !!!").show();
        } else if(txtPosition.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Position Txtfeld !!!").show();
        }else if( txtservesArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Serves Area Txtfeld !!!").show();
        }else if(txtservesArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Address Txtfeld !!!").show();
        }else if (!txtContact.getText().matches("^7|0|(?:\\+94)[0-9]{9,10}$")) {
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Contact Number !!!").show();
        }else {
            String midwifeID = txtMidwifeID.getText();
            String name = txtName.getText();
            String NIC = txtNIC.getText();
            String position = txtPosition.getText();
            String servesArea = txtservesArea.getText();
            String address = txtAddress.getText();
            String contact = txtContact.getText();
            boolean isAdded = false;


            try {
                MidwifeModel midwifeModel = new MidwifeModel();
                isAdded = midwifeModel.saveMidwife(new MidwifeDTO( midwifeID,name,NIC,position,servesArea,address,contact ) );
                if(isAdded){
                    MidwifeTM midwifeTM = new MidwifeTM(midwifeID, NIC, name, address, position, servesArea,contact);
                    midwifeTMS.add(midwifeTM);
                    tabelSaveMidwife.setItems(midwifeTMS);

                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Not Registerd !!!").show();
                }
            }catch (SQLException e) {

                new Alert(Alert.AlertType.ERROR,"Midwife ID is already Exited !!!").show();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //*********************************** UPDATE ******************
    @FXML
    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if (!txtMidwifeID.getText().matches("(M)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Right Midwife ID !!!").show();
        }else if( txtName.getText().isEmpty() ){
            new Alert(Alert.AlertType.ERROR, "Please Enter Midwife Name !!!").show();
        } else if ( !txtNIC.getText().matches("^([0-9]{9}[x|X|v|V]|[0-9]{12})$")) {
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Midwife NIC Number !!!").show();
        } else if(txtPosition.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Position Txtfeld !!!").show();
        }else if( txtservesArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Serves Area Txtfeld !!!").show();
        }else if(txtservesArea.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Address Txtfeld !!!").show();
        }else if (!txtContact.getText().matches("^7|0|(?:\\+94)[0-9]{9,10}$")) {
            new Alert(Alert.AlertType.ERROR, "Please Ckeck Contact Number !!!").show();
        }else {
            String midwifeID = txtMidwifeID.getText();
            String NIC = txtNIC.getText();
            String name = txtName.getText();
            String address = txtAddress.getText();
            String position = txtPosition.getText();
            String area = txtservesArea.getText();
            String contact = txtContact.getText();

            MidwifeModel midwifeModel = new MidwifeModel();
            try {
//            boolean isUpdated = ItemModel.update(code, description, unitPrice, qtyOnHand)

                MidwifeDTO midwifeDTO = new MidwifeDTO(midwifeID, NIC, name, address ,position ,area,contact);
                boolean isUpdated = midwifeModel.update(midwifeDTO);
                if (isUpdated) {
                    MidwifeTM midwifeTM = new MidwifeTM(midwifeID, NIC, name, address, position, area, contact);
                    midwifeTMS.add(midwifeTM);
                    tabelSaveMidwife.setItems(midwifeTMS);
                    tabelSaveMidwife.getItems().clear();
                    loadCellValueFactory();
                    loadTable();
                    new Alert(Alert.AlertType.CONFIRMATION, "your Data is Updated!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Sory!! something happened!").show();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("aaaaaaaa"+e);
            }

        }
    }


    public void midwifeIdSerchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = txtMidwifeID.getText();

        MidwifeModel midwifeModel = new MidwifeModel();

        // Call the searchById method in MidwifeModel to get the MidwifeDTO object
        MidwifeDTO midwifeDTO = midwifeModel.searchById(id);

        if (midwifeDTO != null) {
            String midwifeId = midwifeDTO.getMidwifeID();
            String midwifeNic = midwifeDTO.getNic();
            String midwifeName = midwifeDTO.getName();
            String address = midwifeDTO.getAddress();
            String position = midwifeDTO.getPosition();
            String servesArea = midwifeDTO.getServesArea();
            String contact = midwifeDTO.getContact();

            txtMidwifeID.setText(midwifeId);
            txtNIC.setText(midwifeNic);
            txtName.setText(midwifeName);
            txtAddress.setText(address);
            txtPosition.setText(position);
            txtservesArea.setText(servesArea);
            txtContact.setText(String.valueOf(contact));
        }else {

            new Alert(Alert.AlertType.ERROR, " invalid ID ").show();
        }
    }
    public void buttDeletOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String id = txtMidwifeID.getText();

        MidwifeModel midwifeModel = new MidwifeModel();
        boolean isdelete = midwifeModel.delete(id);

        if(isdelete) {

            tabelSaveMidwife.getItems().clear();
            loadCellValueFactory();
            loadTable();

            new Alert(Alert.AlertType.CONFIRMATION, "Conform Deleted :)").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "This Data Already Delete !!! :)").show();

        }
    }

}