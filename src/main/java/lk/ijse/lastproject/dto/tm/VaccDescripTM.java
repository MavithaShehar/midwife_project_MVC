package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class VaccDescripTM {

    private String vacId;
    private String vacName;
    private String description;
    private int noDose;
    private int stock;

}