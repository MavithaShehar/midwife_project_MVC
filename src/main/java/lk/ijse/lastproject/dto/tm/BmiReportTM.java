package lk.ijse.lastproject.dto.tm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.SQLException;

@Data
@AllArgsConstructor

public class BmiReportTM {

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