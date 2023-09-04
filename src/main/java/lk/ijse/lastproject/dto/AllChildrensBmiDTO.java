package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AllChildrensBmiDTO {

    private String childrenId;
    private String name;
    private int age;
    private String date;
    private double weight;
    private double height;
    private String bmiRenge;
    private double bmiType;
    private int triposha;


}