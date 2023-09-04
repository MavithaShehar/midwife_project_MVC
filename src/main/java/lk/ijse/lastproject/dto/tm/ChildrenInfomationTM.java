package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ChildrenInfomationTM {


    private String name;
    private String birthDay;
    private String gender;
    private int age;
    private double birthWeight;
    private String hospital;
    private String motherName;
    private String fatherName;
    private String address;
    private String midwifeID;
    private String midwifeName;
}