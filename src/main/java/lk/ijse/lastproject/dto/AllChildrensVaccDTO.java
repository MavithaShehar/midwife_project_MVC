package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AllChildrensVaccDTO {

    private String childrenId;
    private String name;
    private String bod;
    private String gender;
    private String vacId;
    private String vacName;
    private String description;
    private int noofDose;
    private int dose;
    private String date;
    private String age;

}