package lk.ijse.lastproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class VaccDescripDTO {

    private String vacId;
    private String vacName;
    private String description;
    private int noDose;
    private int stock;
}