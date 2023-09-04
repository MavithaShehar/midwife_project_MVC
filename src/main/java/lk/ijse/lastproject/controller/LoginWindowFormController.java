package lk.ijse.lastproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.lastproject.dto.UserDTO;
import lk.ijse.lastproject.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginWindowFormController {

    public TextField userName;
    public Button loginButtn;
    public PasswordField userPassword;
    public Button colseButton;
    public Label loginMessageLabel;
    public AnchorPane root;

    UserModel userModel= new UserModel();

    @FXML
    void userNameOnAction(ActionEvent event) {
        userPassword.requestFocus();
    }

    @FXML
    void userPasswordOnAction(ActionEvent event) {
        LoginButtnOnAction(event);
    }


    public void colseButtonOnAction(ActionEvent actionEvent) {

        Stage stage = (Stage) colseButton.getScene().getWindow();
        stage.close();

    }

    public void LoginButtnOnAction(ActionEvent actionEvent) {

        if (loginMessageLabel.getText().isBlank() == false && userPassword.getText().isBlank() == false) {
            loginMessageLabel.setText(" You Try To Login  ");
        } else {
            loginMessageLabel.setText(" Please UserDTO Name and Password ");
        }

        String usrname = userName.getText().trim();
        String userpassword = userPassword.getText().trim();

        if(usrname.isEmpty() || userpassword.isEmpty() ){
            new Alert(Alert.AlertType.INFORMATION,
                    " Input Name and Passwword  ? ",
                    ButtonType.OK
            ).show();


        }else {


            try {
                UserDTO user = userModel.getUser(userName.getText());

                if (user != null) {
                    if (user.getPassword().equals(userPassword.getText())) {
                        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Main_Window__Form.fxml"));
                        Scene scene = new Scene(anchorPane);
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        // stage.setFullScreen(true);

                    } else {
                        new Alert(Alert.AlertType.INFORMATION,
                                " Sorry Wrong Passwword ? ",
                                ButtonType.OK
                        ).show();

                    }

                } else {
                    new Alert(Alert.AlertType.INFORMATION,
                            " Wrong UserDTO Name Entered !! ",
                            ButtonType.OK
                    ).show();

                }

            } catch (SQLException | IOException | ClassNotFoundException e) {

                System.out.println(e);

                new Alert(Alert.AlertType.INFORMATION,
                        " SQL ERROR ? ? ? ? ",
                        ButtonType.OK
                ).show();
            }
        }

    }





    public void btnAddNewOnaction(ActionEvent actionEvent) throws IOException {
    }
}