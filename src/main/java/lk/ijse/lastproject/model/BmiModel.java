package lk.ijse.lastproject.model;

import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.*;
import lk.ijse.lastproject.dto.tm.BmiTM;
import lk.ijse.lastproject.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BmiModel {


    public boolean saveBmi(BmiDTO bmiDTO) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO bmi (bmiId,triposha,childrenId,age,height,weight,bmiType,date,bmiReang)VALUES(?,?,?,?,?,?,?,?,?)";
        return CrudUtil.execute(sql,
                bmiDTO.getBmiId(),
                bmiDTO.getTriposha(),
                bmiDTO.getChildrenID(),
                bmiDTO.getAge(),
                bmiDTO.getHeight(),
                bmiDTO.getWeight(),
                bmiDTO.getBmiType(),
                bmiDTO.getDate(),
                bmiDTO.getBmiReang());
    }

    public  ResultSet getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bmi ";
        return CrudUtil.execute(sql);
    }

    public  boolean update(BmiDTO bmiDTO) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE bmi SET triposha = ?,childrenId = ?,age = ?,height = ?,Weight = ?,bmiType = ? ,date = ?,bmiReang = ?  WHERE bmiId = ?";
        return CrudUtil.execute(sql,
                bmiDTO.getTriposha(),
                bmiDTO.getChildrenID(),
                bmiDTO.getAge(),
                bmiDTO.getHeight(),
                bmiDTO.getWeight(),
                bmiDTO.getBmiType(),
                bmiDTO.getDate(),
                bmiDTO.getBmiReang(),
                bmiDTO.getBmiId());
    }


    public  List<OneChileBmiDTO> searchChildrenBmiInfo(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT b.date,b.weight,b.height,b.age,b.bmiType,b.bmiReang,b.triposha" +
                "        FROM bmi b INNER JOIN children c ON c.childrenId = b.childrenId " +
                "        WHERE c.childrenId = ?";

        List<OneChileBmiDTO> table3 = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql, id);

        while (resultSet.next()) {
            // System.out.println("hello");
            table3.add(new OneChileBmiDTO(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getInt(7))
            );

        }

        return table3;
    }

    public  List<AllChildrensBmiDTO> searchByYear(String year) throws SQLException, ClassNotFoundException {

        String sql = "SELECT c.childrenId,c.name,b.age,b.date,b.weight,b.height," +
                "b.bmiReang,b.bmiType,b.triposha " +
                "FROM children c INNER JOIN bmi b ON c.childrenId = b.childrenId " +
                "WHERE YEAR(date) = ?";
        List<AllChildrensBmiDTO> table = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql, year);

        while (resultSet.next()) {
            // System.out.println("hello");
            table.add(new AllChildrensBmiDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6),
                            resultSet.getString(7),
                            resultSet.getDouble(8),
                            resultSet.getInt(9)
                    )
            );
        }

        return table;

    }

    public static List<FinalCountDTO> searchByCount() throws SQLException, ClassNotFoundException {

        String sql = "SELECT \n" +
                "  YEAR(date) AS year, \n" +
                "  SUM(CASE WHEN bmiType < 18.5 THEN 1 ELSE 0 END) AS underweightCount,\n" +
                "  SUM(CASE WHEN bmiType >= 18.5 AND bmiType < 24.9 THEN 1 ELSE 0 END) AS healthlyCount,\n" +
                "  SUM(CASE WHEN bmiType >= 25 AND bmiType < 29.9 THEN 1 ELSE 0 END) AS overweightCount,\n" +
                "  SUM(CASE WHEN bmiType >= 30 AND bmiType < 39.9 THEN 1 ELSE 0 END) AS obeseCount,\n" +
                " SUM(CASE WHEN bmiType >= 40 THEN 1 ELSE 0 END) AS severelyObese,\n" +
                "  SUM(triposha) AS triposha,\n" +
                "  COUNT(*) AS totalCount\n" +
                "FROM bmi\n" +
                "GROUP BY YEAR(date)";
        List<FinalCountDTO> table = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            // System.out.println("hello");
            table.add(new FinalCountDTO(
                            resultSet.getString(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8)
                    )
            );

        }

        return table;

    }

    public boolean deleteBMI(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM bmi WHERE bmiId = ?";
        return CrudUtil.execute(sql,id);
    }

    public BmiTM searchBmiId(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM bmi WHERE bmiId = ?";
        ResultSet resultSet = null;
        resultSet = CrudUtil.execute(sql, id);

        if (resultSet.next()) {
            return new BmiTM(resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getDouble(7),
                    resultSet.getString(8),
                    resultSet.getString(9));

        }

        return null;
    }

}