package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class OneChileBmiDTO {

    private String date;
    private double weight;
    private double height;
    private int age;
    private double bmiValue;
    private String bmiType;
    private int triposhaCount;
}