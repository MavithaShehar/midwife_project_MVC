package lk.ijse.lastproject.dto.tm;

import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneChildVaccIfomationTM {

    private String vaccName;
    private String vaccId;
    private String descrip;
    private  int noOfDose;
    private int dose;
    private int age = 01 ;
    private String date;
    private String nextDate;

}