package lk.ijse.lastproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainWindowFormController implements Initializable {


    public Label dateLabel;
    @FXML
    private AnchorPane root1;
    @FXML
    private AnchorPane root;

    public Label timeLabel;

    private void setTime()  {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> timeLabel.setText("  "+new SimpleDateFormat("hh : mm : ss : a").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void setDate() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> dateLabel.setText("  "+new SimpleDateFormat("yyyy / MMMMM(MM) / dd(EEE)").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void btnAddChildrenOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SaveChildren_Window_Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }

    public void btnBMIOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/BMI_Window_Form.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);

    }

    public void btnAllVaccinesOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Vaccine_Window_Form.fxml"));
        root1.getChildren().clear();

        root1.getChildren().add(anchorPane);

    }

    public void VaccinesFormOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Children_Vaccine_Window_Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }


    public void childrenInfomationOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Children_Infomation_Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
    }


    public void btnSaveMidwivesOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/SaveMidwife_Window_Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);
        //  root0.getChildren().add(anchorPane);
    }

    public void allBMIReportonOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/BMI_Report-Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);


    }

    public void vaccineReportOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Vaccine_Report_Form.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);

    }

    public void btnFinalReportOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/All_Final_Report.fxml"));
        root1.getChildren().clear();
        root1.getChildren().add(anchorPane);

    }

    public void btnLogougtOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Login_Window_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root1.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTime();
        setDate();

    }

    public void btnAddNewOnaction(ActionEvent actionEvent) throws IOException {

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/User_Register_Wimdow_Form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

}