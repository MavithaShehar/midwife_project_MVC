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
import lk.ijse.lastproject.dto.*;
import lk.ijse.lastproject.dto.tm.*;
import lk.ijse.lastproject.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ChildrenInfomationFormController implements Initializable {

    @FXML
    private TableView<ChildrenInfomationTM> tabelChilData;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colbod;
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
    private TableColumn<?, ?> colfatherName;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colMidwifeId;
    @FXML
    private TableColumn<?, ?> colMidwifeName;
    @FXML
    private TextField txtId;
    @FXML
    private TableView<OneChildVaccIfomationTM> tabelVaccData;
    @FXML
    private TableColumn<?, ?> colVaccName;
    @FXML
    private TableColumn<?, ?> colVaccineId;
    @FXML
    private TableColumn<?, ?> colDescription;
    @FXML
    private TableColumn<?, ?> colNoOfDose;
    @FXML
    private TableColumn<?, ?> colDose;
    @FXML
    private TableColumn<?, ?> colNewAge;
    @FXML
    private TableColumn<?, ?> colDate;
    @FXML
    private TableColumn<?, ?> colNextDate;
    @FXML
    private TableView<OneChileBmiTM> tabelBmiData;
    @FXML
    private TableColumn<?, ?> colBmiDate;
    @FXML
    private TableColumn<?, ?> colWeight;
    @FXML
    private TableColumn<?, ?> colHeight;
    @FXML
    private TableColumn<?, ?> colBmiAge;
    @FXML
    private TableColumn<?, ?> colBmiValue;
    @FXML
    private TableColumn<?, ?> colBmiType;
    @FXML
    private TableColumn<?, ?> colTripo;

    ObservableList<ChildrenInfomationTM> obList = FXCollections.observableArrayList();
    ObservableList<OneChildVaccIfomationTM> obList02 = FXCollections.observableArrayList();
    ObservableList<OneChileBmiTM> obList03 = FXCollections.observableArrayList();

    @FXML
    void inputIdSerchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String id = txtId.getText();
        ChildrenModel childrenModel = new ChildrenModel();
        ChildrenDataDTO childrenDataDTO = childrenModel.searchChildrenInfo(id);

        if (childrenDataDTO == null) {
            new Alert(Alert.AlertType.ERROR, " invalid ID ").show();

        } else {

            ChildrenInfomationTM childrenInfomationTM = new ChildrenInfomationTM(childrenDataDTO.getName(), childrenDataDTO.getBirthDay(), childrenDataDTO.getGender(),
                    childrenDataDTO.getAge(), childrenDataDTO.getBirthWeight(), childrenDataDTO.getHospital(), childrenDataDTO.getMotherName(),
                    childrenDataDTO.getFatherName(), childrenDataDTO.getAddress(), childrenDataDTO.getMidwifeID(), childrenDataDTO.getMidwifeName());

            obList.add(childrenInfomationTM);

            tabelChilData.setItems(obList);
        }

        //******************************   02    ***************************************

        try {

            ChildrenVaccineModel childrenVaccineModel = new ChildrenVaccineModel();
            List<OneChildInsVaccineDTO> table = childrenVaccineModel.searchChildrenVaccineInfo(id);

            if (table == null) {

                new Alert(Alert.AlertType.ERROR, " invalid ID 02 ").show();

            } else {

               /* VaccineModel vaccineModel = new VaccineModel();
                VaccDescripDTO vaccDescripDTO = vaccineModel.searchById(childInsVaccineDTO.getVacId());
*/
                for (OneChildInsVaccineDTO oneChildInsVaccineDTO : table){
                    OneChildVaccIfomationTM oneChildVaccIfomationTM = new OneChildVaccIfomationTM(
                            oneChildInsVaccineDTO.getVaccName(),
                            oneChildInsVaccineDTO.getVaccId(),
                            oneChildInsVaccineDTO.getDescrip(),
                            oneChildInsVaccineDTO.getNoOfDose(),
                            oneChildInsVaccineDTO.getDose(),
                            oneChildInsVaccineDTO.getAge(),
                            oneChildInsVaccineDTO.getDate(),
                            oneChildInsVaccineDTO.getNextDate()
                    );
                    obList02.add(oneChildVaccIfomationTM);

                }


                tabelVaccData.setItems(obList02);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //***********************************   03    ******************************************

        try{
            BmiModel bmiModel = new BmiModel();
            List<OneChileBmiDTO> table3 = bmiModel.searchChildrenBmiInfo(id);

            for (OneChileBmiDTO oneChileBmi : table3) {

                OneChileBmiTM tm = new OneChileBmiTM(
                        oneChileBmi.getDate(),
                        oneChileBmi.getWeight(),
                        oneChileBmi.getHeight(),
                        oneChileBmi.getAge(),
                        oneChileBmi.getBmiValue(),
                        oneChileBmi.getBmiType(),
                        oneChileBmi.getTriposhaCount()

                );
                obList03.add(tm);
            }
            tabelBmiData.setItems(obList03);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValueFactoryFirstTabel();
        setCellValueFactorySecondTabel();
        setCellValueFactoryThirdTabel();

    }

    private void setCellValueFactoryThirdTabel() {
        colBmiDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colBmiAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBmiValue.setCellValueFactory(new PropertyValueFactory<>("bmiValue"));
        colBmiType.setCellValueFactory(new PropertyValueFactory<>("bmiType"));
        colTripo.setCellValueFactory(new PropertyValueFactory<>("triposhaCount"));

    }

    private void setCellValueFactorySecondTabel() {
        colVaccName.setCellValueFactory(new PropertyValueFactory<>("vaccName"));
        colVaccineId.setCellValueFactory(new PropertyValueFactory<>("vaccId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descrip"));
        colNoOfDose.setCellValueFactory(new PropertyValueFactory<>("noOfDose"));
        colDose.setCellValueFactory(new PropertyValueFactory<>("dose"));
        colNewAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colNextDate.setCellValueFactory(new PropertyValueFactory<>("nextDate"));

    }

    private void setCellValueFactoryFirstTabel() {

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbod.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colBirthWeight.setCellValueFactory(new PropertyValueFactory<>("birthWeight"));
        colHospital.setCellValueFactory(new PropertyValueFactory<>("hospital"));
        colMotherName.setCellValueFactory(new PropertyValueFactory<>("motherName"));
        colfatherName.setCellValueFactory(new PropertyValueFactory<>("fatherName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMidwifeName.setCellValueFactory(new PropertyValueFactory<>("midwifeName"));
        colMidwifeId.setCellValueFactory(new PropertyValueFactory<>("midwifeID"));

    }
}