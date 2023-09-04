package lk.ijse.lastproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.lastproject.dto.BmiDTO;
import lk.ijse.lastproject.dto.tm.BmiTM;
import lk.ijse.lastproject.dto.tm.MidwifeTM;
import lk.ijse.lastproject.model.BmiModel;
import lk.ijse.lastproject.model.MidwifeModel;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
public class BMIWindowFormController implements Initializable {

    @FXML
    private TextField btnBmiId;
    @FXML
    private TextField btnChildrenId;
    @FXML
    private TextField btnAge;
    @FXML
    private TextField btnDate;
    @FXML
    private TextField btnWeight;
    @FXML
    private TextField btnHeight;
    @FXML
    private TextField btnTriposha;

    //save colome
    @FXML
    private TableView<BmiTM> tabelBMIform;
    @FXML
    private TableColumn<?, ?> colBMIId;
    @FXML
    private TableColumn<?, ?> colChildrenId;
    @FXML
    private TableColumn<?, ?> colAge;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colWeight;
    @FXML
    private TableColumn<?, ?> colHeight;
    @FXML
    private TableColumn<?, ?> colBMItype;
    @FXML
    private TableColumn<?, ?> colTriposha;
    @FXML
    private TableColumn<?, ?> colBmiReange;

    ObservableList<BmiTM> bmiTMS = FXCollections.observableArrayList();

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!btnBmiId.getText().matches("(B)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right BMI ID !!!").show();
        } else if (!btnChildrenId.getText().matches("(C)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Children ID !!!").show();
        } else if (!btnAge.getText().matches("^[1-9]|1[0-2]$")) {
            new Alert(Alert.AlertType.ERROR, " Please Enter Right Age  !!!").show();
        } else if (btnDate.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, " Please Enter date !!!").show();
        } else if (!btnWeight.getText().matches("^(?:[1-9]|[1-4]\\d|50)(?:\\.\\d)?0?$")) {
            new Alert(Alert.AlertType.ERROR, " Please Check Weight  !!!").show();
        } else if (!btnHeight.getText().matches("^(0\\.[1-9]|[1-4]\\.[0-9]|5\\.0)$")) {
            new Alert(Alert.AlertType.ERROR, " Please Check Height  !!!").show();
        } else {

            String bmiID = btnBmiId.getText();
            int triposha = Integer.parseInt(btnTriposha.getText());
            String childrenId = btnChildrenId.getText();
            int age = Integer.parseInt(btnAge.getText());
            String date = btnDate.getText();
            double weight = Double.parseDouble(btnWeight.getText());
            double height = Double.parseDouble(btnHeight.getText());
            double bmiType = weight / (height * height);
            String bmiReang = null;

            if (bmiType >= 26) {
                bmiReang = " Bad ";
            } else if (bmiType >= 18) {
                bmiReang = " Normal ";
            } else {
                bmiReang = " Weak ";
            }

            boolean isADDed = false;

            try {
                BmiModel bmiModel = new BmiModel();
                isADDed = bmiModel.saveBmi(new BmiDTO(bmiID, triposha, childrenId, age, height, weight, bmiType, date, bmiReang));

                if (isADDed) {
                    BmiTM bmiTM = new BmiTM(bmiID, triposha, childrenId, age, height, weight, bmiType, date, bmiReang);
                    bmiTMS.add(bmiTM);
                    tabelBMIform.setItems(bmiTMS);
                    new Alert(Alert.AlertType.CONFIRMATION, "Registerd !!!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Not Registerd !!!").show();
                }
            } catch (SQLException e) {

                new Alert(Alert.AlertType.ERROR, "BMI ID is already Exited !!!").show();
            }
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCellValueFactory();
        loadTable();
    }

    private void loadCellValueFactory() {

        colChildrenId.setCellValueFactory(new PropertyValueFactory<>("childrenID"));
        colBMIId.setCellValueFactory(new PropertyValueFactory<>("bmiId"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        colBMItype.setCellValueFactory(new PropertyValueFactory<>("bmiType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTriposha.setCellValueFactory(new PropertyValueFactory<>("triposha"));
        colBmiReange.setCellValueFactory(new PropertyValueFactory<>("bmiReang"));
    }

    private void loadTable() throws SQLException {

        BmiModel bmiModel = new BmiModel();

        try (ResultSet rs = bmiModel.getData()) {

            while (rs.next()) {
                BmiTM bmiTM = new BmiTM(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9)
                );

                bmiTMS.add(bmiTM);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tabelBMIform.setItems(bmiTMS);
    }
    @FXML
    void bmiIdSerchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = btnBmiId.getText();

        BmiModel bmiModel = new BmiModel();
        BmiTM bmiTM =bmiModel.searchBmiId(id);

        if(bmiTM != null) {

            btnBmiId.setText(bmiTM.getBmiId());
            btnTriposha.setText(String.valueOf(bmiTM.getTriposha()));
            btnChildrenId.setText(bmiTM.getChildrenID());
            btnAge.setText(String.valueOf(bmiTM.getAge()));
            btnHeight.setText(String.valueOf(bmiTM.getHeight()));
            btnWeight.setText(String.valueOf(bmiTM.getWeight()));
            btnDate.setText(bmiTM.getDate());

        }else {
            new Alert(Alert.AlertType.ERROR, " invalid ID ").show();
        }
    }

    @FXML
    void bntUpdateOnAction(ActionEvent event) {

        if(! btnBmiId.getText().matches("(B)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right BMI ID !!!").show();
        }else if(! btnChildrenId.getText().matches("(C)[0-9]{3}")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Children ID !!!").show();
        }else if(! btnAge.getText().matches("^[1-9]|1[0-2]$")){
            new Alert(Alert.AlertType.ERROR," Please Enter Right Age  !!!").show();
        }else if(btnDate.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR," Please Enter date !!!").show();
        }else if(! btnWeight.getText().matches("^(?:[1-9]|[1-4]\\d|50)(?:\\.\\d)?0?$")){
            new Alert(Alert.AlertType.ERROR," Please Check Weight  !!!").show();
        }else if(! btnHeight.getText().matches("^(0\\.[1-9]|[1-4]\\.[0-9]|5\\.0)$")){
            new Alert(Alert.AlertType.ERROR," Please Check Height  !!!").show();

        } else {
            String bmiID = btnBmiId.getText();
            int triposha = Integer.parseInt(btnTriposha.getText());
            String childrenId = btnChildrenId.getText();
            int age = Integer.parseInt(btnAge.getText());
            String date = btnDate.getText();
            double weight = Double.parseDouble(btnWeight.getText());
            double height = Double.parseDouble(btnHeight.getText());
            double bmiType = weight / (height * height);
            String bmiReang = null;

            if (bmiType >= 26) {
                bmiReang = " Bad ";
            } else if (bmiType >= 18) {
                bmiReang = " Normal ";
            } else {
                bmiReang = " Weak ";
            }
            BmiModel bmiModel = new BmiModel();
            try {
                boolean isUpdate = bmiModel.update(new BmiDTO(bmiID, triposha, childrenId, age, height, weight, bmiType, date, bmiReang));
                if (isUpdate) {
                    BmiTM bmiTM = new BmiTM(bmiID, triposha, childrenId, age, height, weight, bmiType, date, bmiReang);
                    bmiTMS.add(bmiTM);
                    tabelBMIform.setItems(bmiTMS);
                    tabelBMIform.getItems().clear();
                    loadCellValueFactory();
                    new Alert(Alert.AlertType.CONFIRMATION, "Registerd !!!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Not Registerd !!!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {

                new Alert(Alert.AlertType.ERROR, "BMI ID is already Exited !!!").show();
            }
        }

    }

    @FXML
    void btnDeletOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = btnBmiId.getText();
        BmiModel bmiModel = new BmiModel();
        boolean isdelete = bmiModel.deleteBMI(id);

        if(isdelete) {
            tabelBMIform.getItems().clear();
            loadCellValueFactory();
            loadTable();
            new Alert(Alert.AlertType.CONFIRMATION, "Conform Deleted :)").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "This Data Already Delete !!! :)").show();
        }
    }
}