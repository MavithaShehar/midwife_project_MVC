package lk.ijse.lastproject.model;

import lk.ijse.lastproject.db.DBConnection;
import lk.ijse.lastproject.dto.ChildrenDTO;
import lk.ijse.lastproject.dto.ChildrenDataDTO;
import lk.ijse.lastproject.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChildrenModel {

    public  boolean saveChildren(ChildrenDTO childrenDTO ) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO children (midwifeID,ChildrenId,name,bod,gender,age,birthWeight,hospital," +
                "motherName,fatherName,address)VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,childrenDTO.getMidwifeID(),
                childrenDTO.getChildrenId(),
                childrenDTO.getName(),
                childrenDTO.getBirthDay(),
                childrenDTO.getGender(),
                childrenDTO.getAge(),
                childrenDTO.getBirthWeight(),
                childrenDTO.getHospital(),
                childrenDTO.getMotherName(),
                childrenDTO.getFatherName(),
                childrenDTO.getAddress());
    }

    public  ResultSet getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM children";
        return CrudUtil.execute(sql);
    }

    public boolean update(ChildrenDTO childrenDTO) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE children SET midwifeId = ?,name = ?," +
                "bod = ?,gender = ?,age = ?,birthWeight = ?,hospital = ?," +
                "motherName = ?,fatherName = ?,address = ? WHERE   childrenId = ?";

        return CrudUtil.execute(sql,
                childrenDTO.getMidwifeID(),
                childrenDTO.getName(),
                childrenDTO.getBirthDay(),
                childrenDTO.getGender(),
                childrenDTO.getAge(),
                childrenDTO.getBirthWeight(),
                childrenDTO.getHospital(),
                childrenDTO.getMotherName(),
                childrenDTO.getFatherName(),
                childrenDTO.getAddress(),
                childrenDTO.getChildrenId() );

    }

    public static ChildrenDTO searchById(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM children WHERE childrenId = ?";
        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){
            return new ChildrenDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6),
                    resultSet.getDouble(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            );
        }

        return null;
    }

    public  String searchByName(String childrenId) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM children WHERE childrenId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, childrenId);

        ResultSet resultSet = pstm.executeQuery();
        String childrenName = null;
        if (resultSet.next()) {
            childrenName = resultSet.getString(3);
        }
        return childrenName;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM children WHERE childrenId = ?";
        return CrudUtil.execute(sql,id);
    }

    public ChildrenDataDTO searchChildrenInfo(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.name, c.bod, c.gender, c.age, c.birthweight," +
                " c.hospital, c.motherName, c.fatherName, c.address, m.midwifeId, m.name " +
                "FROM children c INNER JOIN midwife m ON c.midwifeId = m.midwifeId WHERE c.childrenId = ? ";

        ResultSet resultSet = CrudUtil.execute(sql,id);

        if(resultSet.next()){
            return new ChildrenDataDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            );

        }

        return null;



    }
}