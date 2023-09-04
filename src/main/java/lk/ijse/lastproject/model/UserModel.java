package lk.ijse.lastproject.model;

import lk.ijse.lastproject.dto.UserDTO;
import lk.ijse.lastproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    public UserDTO getUser(String userName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE userName = ?  ";
        ResultSet rs = null;
        rs = CrudUtil.execute(sql,userName);

        if(rs.next()){
            return new UserDTO(rs.getString(1) ,
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
            );
        }
        return null;

    }

    public boolean register(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user(userId,userName,password,email)VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,
                userDTO.getUserId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getEmail());

    }
}