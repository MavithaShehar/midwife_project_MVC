package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneChildInsVaccineDTO {
    private String vaccName;
    private String vaccId;
    private String descrip;
    private  int noOfDose;
    private int dose;
    private int age ;
    private String date;
    private String nextDate;
}