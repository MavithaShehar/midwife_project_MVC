package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ChildrenTM {

    private String midwifeId;
    private String childrenId;
    private String name;
    private String bod;
    private String gender;
    private int age;
    private double birthWeight;
    private String hospital;
    private String motherName;
    private String fatherName;
    private String address;




}