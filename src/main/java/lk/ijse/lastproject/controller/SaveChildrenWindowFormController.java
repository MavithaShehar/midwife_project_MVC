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
import lk.ijse.lastproject.dto.ChildrenDTO;
import lk.ijse.lastproject.dto.tm.ChildrenTM;
import lk.ijse.lastproject.model.ChildrenModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class SaveChildrenWindowFormController implements Initializable {

    @FXML
    private AnchorPane root1;
    @FXML
    private TextField txtChildrenID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtBirthDay;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtBirthWeight;
    @FXML
    private TextField txtHospital;
    @FXML
    private TextField txtMotherName;
    @FXML
    private TextField txtFatherName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtMidwifeID;
    @FXML
    private Label count;
    // get value frome tabel
    @FXML
    private TableView<ChildrenTM> tabelSaveChildren;
    @FXML
    private TableColumn<?, ?> colMidwifeId;
    @FXML
    private TableColumn<?, ?> colChildrenID;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colBod;
    @FXML
    private TableColumn<?, ?> colGender;
    @FXML
    private TableColumn<?, ?> colAge;
    @FXML
    private TableColumn<?, ?> colBirthWeight;
    @FXML
    private TableColumn<?, ?> colHospital;
    @FXML
    private TableColumn<?, ?> colMotherName;
    @FXML
    private TableColumn<?, ?> colFatherName;
    @FXML
    private TableColumn<?, ?> colAddress;

    ObservableList<ChildrenTM> childrenTMS = FXCollections.observableArrayList();


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCellValueFactory();
        loadTable();
    }

    private void loadTable() throws SQLException {

        ChildrenModel childrenModel = new ChildrenModel();

        try (ResultSet rs = childrenModel.getData()) {
            while (rs.next()) {
                ChildrenTM addChildrenTM = new ChildrenTM(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)

                );
                childrenTMS.add(addChildrenTM);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tabelSaveChildren.setItems(childrenTMS);
        count.setText(childrenTMS.size()+"");
    }

    private void loadCellValueFactory() throws SQLException {

        colMidwifeId.setCellValueFactory(new PropertyValueFactory<>("midwifeId"));
        colChildrenID.setCellValueFactory(new PropertyValueFactory<>("ChildrenId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBod.setCellValueFactory(new PropertyValueFactory<>("bod"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBirthWeight.setCellValueFactory(new PropertyValueFactory<>("birthWeight"));
        colHospital.setCellValueFactory(new PropertyValueFactory<>("hospital"));
        colMotherName.setCellValueFactory(new PropertyValueFactory<>("motherName"));
        colFatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

    }
    // [01]. save data to database
    @FXML
    void btnChildrenSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(! txtChildrenID.getText().matches("(C)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Children ID !!!").show();
        }else if(! txtMidwifeID.getText().matches("(M)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Midwife ID !!!").show();
        }else if(txtName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Name !!!").show();
        }else if(txtBirthDay.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Birth day !!!").show();
        }/*else if(! txtGender.getText().matches("^M(?:ALE)?$") ){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Text to Gender  !!!").show();
        }else if(! txtGender.getText().matches("^F(?:EMALE)?$")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Text to Gender  !!!").show();
        }*/else if(! txtAge.getText().matches("^[1-9]|1[0-2]$")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Age  !!!").show();
        }else if(! txtBirthWeight.getText().matches("^(?:[1-9]|[1-4]\\d|50)(?:\\.\\d)?0?$")){
            new Alert(Alert.AlertType.ERROR," Please Check Weight  !!!").show();
        }else  if(txtHospital.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Hospital  !!!").show();
        }else if(txtMotherName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Mother Name  !!!").show();
        }else if(txtFatherName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Father Name  !!!").show();
        }else if( txtAddress.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Address !!!").show();
        }else {
            String midwifeID = txtMidwifeID.getText();
            String ChildrenId = txtChildrenID.getText();
            String name = txtName.getText();
            String bod = txtBirthDay.getText();
            String gender = txtGender.getText();
            int age = Integer.parseInt(txtAge.getText());
            double birthWeight =Double.parseDouble(txtBirthWeight.getText());
            String hospital = txtHospital.getText();
            String motherName = txtMotherName.getText();
            String fatherName = txtFatherName.getText();
            String address = txtAddress.getText();

            boolean isAdded = false;
            try {
                ChildrenModel childrenModel = new ChildrenModel();
                isAdded = childrenModel.saveChildren(new ChildrenDTO(midwifeID, ChildrenId,name,bod,gender,age,
                        birthWeight,hospital,motherName,fatherName,address));
                if(isAdded){
                    ChildrenTM addChildrenTM = new ChildrenTM(midwifeID,ChildrenId,name, bod, gender, age,
                            birthWeight,hospital,motherName,fatherName,address);
                    childrenTMS.add(addChildrenTM);
                    tabelSaveChildren.setItems(childrenTMS);

                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();

                }else{
                    new Alert(Alert.AlertType.ERROR,"Not Registerd !!!").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR," You Enter Data is Not Valet !!!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {

        if(! txtChildrenID.getText().matches("(C)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Children ID !!!").show();
        }else if(! txtMidwifeID.getText().matches("(M)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Midwife ID !!!").show();
        }else if(txtName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Name !!!").show();
        }else if(txtBirthDay.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Birth day !!!").show();
        }/*else if(! txtGender.getText().matches("^M(?:ALE)?$") || ! txtGender.getText().matches("^F(?:EMALE)?$")){
           new Alert(Alert.AlertType.ERROR," Please Enter Right Text to Gender  !!!").show();
        }*/else if(! txtAge.getText().matches("^[1-9]|1[0-2]$")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Age  !!!").show();
        }else if(! txtBirthWeight.getText().matches("^(?:[1-9]|[1-4]\\d|50)(?:\\.\\d)?0?$")){
            new Alert(Alert.AlertType.ERROR," Please Check Weight  !!!").show();
        }else  if(txtHospital.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Hospital  !!!").show();
        }else if(txtMotherName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Mother Name  !!!").show();
        }else if(txtFatherName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter Father Name  !!!").show();
        }else if( txtAddress.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Address !!!").show();
        }else {

            String midwifeID = txtMidwifeID.getText();
            String childrenId = txtChildrenID.getText();
            String name = txtName.getText();
            String bod = txtBirthDay.getText();
            String gender = txtGender.getText();
            int age = Integer.parseInt(txtAge.getText());
            double birthWeight =Double.parseDouble(txtBirthWeight.getText());
            String hospital = txtHospital.getText();
            String motherName = txtMotherName.getText();
            String fatherName = txtFatherName.getText();
            String address = txtAddress.getText();

            ChildrenModel childrenModel = new ChildrenModel();
            try {
                ChildrenDTO childrenDTO = new ChildrenDTO(midwifeID, childrenId, name, bod ,gender ,
                        age,birthWeight, hospital,motherName,fatherName,address );   //type inference

                boolean isUpdated = childrenModel.update(childrenDTO);
                if (isUpdated) {
                    ChildrenTM childrenTM=  new ChildrenTM(midwifeID, childrenId, name, bod ,gender ,
                            age,birthWeight, hospital,motherName,fatherName,address);

                    childrenTMS.add(childrenTM);
                    tabelSaveChildren.setItems(childrenTMS);
                    tabelSaveChildren.getItems().clear();
                    loadCellValueFactory();
                    loadTable();

                    new Alert(Alert.AlertType.CONFIRMATION, "your Data is Updated!").show();

                }else{
                    new Alert(Alert.AlertType.ERROR, "your Data is  NOT  Updated!").show();

                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Sory!! something happened!").show();
            }
            // tabelSaveChildren.refresh();

        }

    }

    @FXML
    void childrenIdSerchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String childId = txtChildrenID.getText();

        /*Connection con = DriverManager.getConnection(URL, props);
        String sql = "SELECT * FROM children WHERE childrenId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, childId);
        ResultSet resultSet = pstm.executeQuery();*/

        ChildrenModel childrenModel = new ChildrenModel();
        ChildrenDTO  childrenDTO =  childrenModel.searchById(childId);


        if(childrenDTO !=null) {
            String midwifeId = childrenDTO.getMidwifeID();
            String chidrenId = childrenDTO.getChildrenId();
            String Name  = childrenDTO.getName();
            String bod = childrenDTO.getBirthDay();
            String gender = childrenDTO.getGender();
            int age = childrenDTO.getAge();
            double birthweight = childrenDTO.getBirthWeight();
            String hospital = childrenDTO.getHospital();
            String motherName = childrenDTO.getMotherName();
            String fatherName = childrenDTO.getFatherName();
            String address = childrenDTO.getAddress();

            txtChildrenID.setText(chidrenId);
            txtMidwifeID.setText(midwifeId);
            txtName.setText(Name);
            txtBirthDay.setText(bod);
            txtGender.setText(gender);
            txtAge.setText(String.valueOf(age));
            txtBirthWeight.setText(String.valueOf(birthweight));
            txtHospital.setText(hospital);
            txtMotherName.setText(motherName);
            txtFatherName.setText(fatherName);
            txtAddress.setText(address);
        }

    }

    @FXML
    void btmDeletOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = txtChildrenID.getText();
        ChildrenModel childrenModel = new ChildrenModel();
        boolean isDelete = childrenModel.delete(id);

        if(isDelete ) {

            tabelSaveChildren.getItems().clear();
            loadCellValueFactory();
            loadTable();

            new Alert(Alert.AlertType.CONFIRMATION, " deleted Confome :)").show();
        }

    }

    @FXML
    void tableChildrenOnMouseClicked(MouseEvent event) throws SQLException {
        ChildrenTM addChildrenTM =  tabelSaveChildren.getSelectionModel().getSelectedItem();
        txtChildrenID.setText(addChildrenTM.getChildrenId());
        txtName.setText(addChildrenTM.getName());
        txtBirthDay.setText(addChildrenTM.getBod());
        txtGender.setText(addChildrenTM.getGender());
        txtAge.setText(String.valueOf(addChildrenTM.getAge()));
        txtBirthWeight.setText(String.valueOf(addChildrenTM.getBirthWeight()));
        txtHospital.setText(addChildrenTM.getHospital());
        txtMotherName.setText(addChildrenTM.getMotherName());
        txtFatherName.setText(addChildrenTM.getFatherName());
        txtMidwifeID.setText(addChildrenTM.getMidwifeId());
        txtAddress.setText(addChildrenTM.getAddress());

    }

}