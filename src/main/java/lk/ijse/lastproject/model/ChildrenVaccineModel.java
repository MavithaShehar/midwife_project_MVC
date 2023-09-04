package lk.ijse.lastproject.model;
import lk.ijse.lastproject.dto.ChildInsVaccineDTO;
import lk.ijse.lastproject.dto.OneChildInsVaccineDTO;
import lk.ijse.lastproject.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChildrenVaccineModel {
    public  boolean register(ChildInsVaccineDTO childInsVaccineDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO baby_vaccine (childrenId,vacid,dose,date,nextDate,age,ID)VALUES(?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql,
                childInsVaccineDTO.getChildrenId(),
                childInsVaccineDTO.getVacId(),
                childInsVaccineDTO.getDose(),
                childInsVaccineDTO.getDate(),
                childInsVaccineDTO.getNextDate(),
                childInsVaccineDTO.getAge(),
                childInsVaccineDTO.getID());

    }

    public  ResultSet getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM baby_vaccine ";
        return CrudUtil.execute(sql);
    }

    public List<OneChildInsVaccineDTO> searchChildrenVaccineInfo(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT v.vacName,b.vacId,v.description,v.noDose,b.dose,b.age,b.date,b.nextDate FROM children c INNER JOIN baby_vaccine b ON c.childrenId = b.childrenId INNER JOIN vaccine v ON b.vacId = v.vacId WHERE c.childrenId = ?";

        List<OneChildInsVaccineDTO> table=new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql,id);
        while(resultSet.next()){
            table.add (new OneChildInsVaccineDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getInt(6),
                    resultSet.getString(7),
                    resultSet.getString(8))
            );
        }

        return table;
    }
    public boolean update(ChildInsVaccineDTO ChildInsVaccineDTO) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE baby_vaccine  SET  childrenId = ? ,vacId = ?,dose = ?,date = ?,nextDate = ?,age = ? WHERE ID = ?";
        return CrudUtil.execute(sql,
                ChildInsVaccineDTO.getChildrenId(),
                ChildInsVaccineDTO.getVacId(),
                ChildInsVaccineDTO.getDose(),
                ChildInsVaccineDTO.getDate(),
                ChildInsVaccineDTO.getNextDate(),
                ChildInsVaccineDTO.getAge(),
                ChildInsVaccineDTO.getID());

    }

    public boolean deleteChildrenVaccine(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM baby_vaccine WHERE ID = ?";
        return CrudUtil.execute(sql,id);

    }

    public ChildInsVaccineDTO searchChildrenVaccID(String id) throws SQLException, ClassNotFoundException {

        String sql =  "SELECT * FROM baby_vaccine WHERE ID = ?";
        ResultSet resultSet = null;
        resultSet = CrudUtil.execute(sql, id);
        if(resultSet.next()){
            return new ChildInsVaccineDTO(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getString(7)
            );
        }

        return null;

    }
}