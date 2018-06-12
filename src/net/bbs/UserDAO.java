package net.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDAO {
	public int login(String userID, String userPassword) throws Exception{
		String sql = "SELECT * FROM user WHERE userID = ?";
		try(Connection connection = DB.getConnection("BBS");
			PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, userID);
				try(ResultSet resultSet = statement.executeQuery()) {
					if(resultSet.next()) {
						if(resultSet.getString(1).equals(userPassword))
							return 1; // �α��� ����
						else 
							return 0; // ��й�ȣ ����ġ
					}
					return -1; //id ����
				}
			}
	}
}
