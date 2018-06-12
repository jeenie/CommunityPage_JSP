package net.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

public class UserDAO {
	public int login(String userID, String userPassword) throws Exception {
		String sql = "SELECT userPassword FROM user WHERE userID = ?";
		try (Connection connection = DB.getConnection("BBS");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, userID);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					if (resultSet.getString(1).equals(userPassword))
						return 1; // 로그인 성공
					else
						return 0; // 비밀번호 불일치
				}
				return -1; // id 없음
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2;
	}

	public int join(User user) {
		String sql = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = DB.getConnection("BBS");
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user.getUserID());
			statement.setString(2, user.getUserPassword());
			statement.setString(3, user.getUserName());
			statement.setString(4, user.getUserGender());
			statement.setString(5, user.getUserEmail());
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
