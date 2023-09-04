package lk.ijse.lastproject.model;

import lk.ijse.lastproject.db.DBConnection;

import java.sql.*;
import lk.ijse.lastproject.dto.MidwifeDTO;
import lk.ijse.lastproject.util.CrudUtil;

public class MidwifeModel {

    public boolean saveMidwife(MidwifeDTO midwifeDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO midwife(midwifeID,nic,name,address,position,servesArea,contact)VALUES(?,?,?,?,?,?,?)";

        return CrudUtil.execute(sql,
                midwifeDTO.getMidwifeID(),
                midwifeDTO.getNic(),
                midwifeDTO.getName(),
                midwifeDTO.getAddress(),
                midwifeDTO.getPosition(),
                midwifeDTO.getServesArea(),
                midwifeDTO.getContact()
        );
    }

    public ResultSet getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM midwife ";

        return CrudUtil.execute(sql);

    }

//********************************* UPDATE ****************

    public boolean update(MidwifeDTO midwifeDTO) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE midwife SET nic = ?,name = ?,address = ?,position = ?,servesArea = ?,contact = ? WHERE midwifeID = ?";
        return CrudUtil.execute(sql,
                midwifeDTO.getNic(),
                midwifeDTO.getName(),
                midwifeDTO.getAddress(),
                midwifeDTO.getPosition(),
                midwifeDTO.getServesArea(),
                midwifeDTO.getContact(),
                midwifeDTO.getMidwifeID()

        );

    }

    public MidwifeDTO searchById(String midwifeID) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM midwife WHERE midWifeId = ?";
        ResultSet resultSet = null;

        resultSet = CrudUtil.execute(sql, midwifeID);

        if (resultSet.next()) {
            return new MidwifeDTO(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7));
        }

        return null;
    }
    public static String searchByMidwifeNameFromID(String midwifeID) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM midwife WHERE midWifeId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, midwifeID);

        ResultSet resultSet = pstm.executeQuery();
        String midWifeName = null;
        if (resultSet.next()) {
            midWifeName = resultSet.getString(2);
        }
        return midWifeName;
    }

    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM midwife WHERE midWifeId = ?";

        return CrudUtil.execute(sql,id);


    }


}