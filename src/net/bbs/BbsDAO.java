package net.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	public String getDate() {
		String sql = "SELECT NOW()";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			if(resultSet.next())
				return resultSet.getString(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터 베이스 오류
	}

	public int getNext() {
		String sql = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			if(resultSet.next())
				return resultSet.getInt(1) + 1;
			return 1; // 첫번째 게시물인 경우
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터 베이스 오류
	}

	public int write(String bbsTitle, String userID, String bbsContent) {
		String sql = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setInt(1, getNext());
			statement.setString(2, bbsTitle);
			statement.setString(3, userID);
			statement.setString(4, getDate());
			statement.setString(5, bbsContent);
			statement.setInt(6, 1);
			return statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터 베이스 오류
	}
}
