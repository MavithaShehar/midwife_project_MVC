package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinalReportTM {

    private String year;
    private int underweightCount;
    private int healthlyCount;
    private int overweightCount;
    private int obeseCount;
    private int severelyObese;
    private int triposha;
    private int totalCount;

}