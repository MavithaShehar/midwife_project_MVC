package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BmiDTO {

    private String bmiId;
    private int triposha;
    private String childrenID;
    private int age;
    private double height;
    private double weight;
    private double bmiType;
    private String date;
    private String bmiReang;
}