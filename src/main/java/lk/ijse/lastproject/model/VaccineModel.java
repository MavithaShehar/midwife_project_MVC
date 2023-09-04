package lk.ijse.lastproject.model;

import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.AllChildrensVaccDTO;
import lk.ijse.lastproject.dto.VaccDescripDTO;
import lk.ijse.lastproject.dto.tm.VaccDescripTM;
import lk.ijse.lastproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VaccineModel {
    public  boolean saveVaccdes(VaccDescripDTO vaccDescripDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO vaccine (vacId,vacName,description,noDose,Stocks)VALUES(?,?,?,?,?)";
        return CrudUtil.execute(sql,
                vaccDescripDTO.getVacId(),
                vaccDescripDTO.getVacName(),
                vaccDescripDTO.getDescription(),
                vaccDescripDTO.getNoDose(),
                vaccDescripDTO.getStock());
    }

    public ResultSet getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM vaccine";
        return CrudUtil.execute(sql);
    }

    public boolean update(VaccDescripDTO vaccDescripDTO) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE vaccine SET vacName = ?,description = ?,noDose = ?, Stocks = ? WHERE vacId = ?";

        return CrudUtil.execute(sql,vaccDescripDTO.getVacName(),
                vaccDescripDTO.getDescription(),
                vaccDescripDTO.getNoDose(),
                vaccDescripDTO.getStock(),
                vaccDescripDTO.getVacId()
        );

    }


    public static VaccDescripDTO searchById(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM vaccine WHERE vacId = ?";

        ResultSet resultSet = null;
        resultSet = CrudUtil.execute(sql, id);

        if(resultSet.next()){
            return new VaccDescripDTO(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5)

            );
        }

        return null;
    }

    public  List<AllChildrensVaccDTO> serchByYear(String year) throws SQLException, ClassNotFoundException {

        String sql = "SELECT c.childrenId,c.name,c.bod,c.gender,b.vacId," +
                "v.vacName,v.description,v.noDose,b.dose,b.date,b.age " +
                "FROM children c " +
                "INNER JOIN baby_vaccine b ON c.childrenId = b.childrenId " +
                "INNER JOIN vaccine v ON b.vacId = v.vacId " +
                "WHERE YEAR(date) = ?";

        List<AllChildrensVaccDTO> table=new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql, year);

        while (resultSet.next()){

            table.add(new AllChildrensVaccDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9),
                            resultSet.getString(10),
                            resultSet.getString(11)
                    )
            );

        }

        return table;
    }

    public  boolean stockUpdate(String vaccineId) throws SQLException, ClassNotFoundException {
        VaccDescripDTO vaccDescripDTO = VaccineModel.searchById(vaccineId);

        System.out.println(vaccDescripDTO.getStock());
        String sql = "UPDATE vaccine SET stocks = ? WHERE vacId = ?";

        int currentStock = vaccDescripDTO.getStock();
        if (currentStock <= 0){
            currentStock = 1;
        }
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setInt(1 , (currentStock - 1));
        pstm.setString(2 , vaccineId);

        return pstm.executeUpdate() > 0;

    }

    public boolean deleteVacc(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM vaccine WHERE vacId = ?";
        return CrudUtil.execute(sql,id);

    }

}