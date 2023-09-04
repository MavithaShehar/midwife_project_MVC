package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ChildrenVaccineTM {

    private String childrenId;
    private String vacId;
    private int dose;
    private String date;
    private String nextDate;
    private int age;
    private String ID;

}