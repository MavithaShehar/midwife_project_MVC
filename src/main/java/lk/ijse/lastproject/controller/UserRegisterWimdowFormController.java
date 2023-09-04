package lk.ijse.lastproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.lastproject.dto.UserDTO;
import lk.ijse.lastproject.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class UserRegisterWimdowFormController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML

    public void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Login_Window_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        if(!userId.matches("(U)[0-9]{3}")) {
            new Alert(Alert.AlertType.ERROR, "your userId is no match !!!").show();
        }else if(!email.matches("^(.+)@(.+)$")){
            new Alert(Alert.AlertType.ERROR, "your Email is no match !!!").show();
        }else {
            boolean isAdded= false;

            UserModel userModel = new UserModel();

            try {
                isAdded = userModel.register(new UserDTO(userId,userName,password,email));
                if(isAdded){
                    new Alert(Alert.AlertType.CONFIRMATION,"Registerd !!!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Not Registerd !!!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR,"UserDTO Name is already Exited !!!").show();
            }

        }

    }
}