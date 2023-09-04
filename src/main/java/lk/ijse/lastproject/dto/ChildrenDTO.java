package lk.ijse.lastproject.dto;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ChildrenDTO {

    private String midwifeID;
    private String childrenId;
    private String name;
    private String birthDay;
    private String gender;
    private int age;
    private double birthWeight;
    private String hospital;
    private String motherName;
    private String fatherName;
    private String address;


}