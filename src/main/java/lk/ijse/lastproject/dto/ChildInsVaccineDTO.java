package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ChildInsVaccineDTO {

    private String childrenId;
    private String vacId;
    private int dose;
    private String date;
    private String nextDate;
    private int age;
    private String ID;

}