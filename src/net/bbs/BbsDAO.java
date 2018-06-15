package net.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
		return ""; //������ ���̽� ����
	}

	public int getNext() {
		String sql = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();) {
			if(resultSet.next())
				return resultSet.getInt(1) + 1;
			return 1; // ù��° �Խù��� ���
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //������ ���̽� ����
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
		return -1; //������ ���̽� ����
	}

	public ArrayList<Bbs> getList(int pageNumber) {
		String sql = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setInt(1, getNext() - (pageNumber - 1) * 10);
			try(ResultSet resultSet = statement.executeQuery();) {
				while(resultSet.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(resultSet.getInt(1));
					bbs.setBbsTitle(resultSet.getString(2));
					bbs.setUserID(resultSet.getString(3));
					bbs.setBbsDate(resultSet.getString(4));
					bbs.setBbsContent(resultSet.getString(5));
					bbs.setBbsAvailable(resultSet.getInt(6));
					list.add(bbs);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String sql = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable =1";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setInt(1, getNext() - (pageNumber - 1) * 10);
			try(ResultSet resultSet = statement.executeQuery();) {
				if(resultSet.next()) {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String sql = "SELECT * FROM BBS WHERE bbsID = ?";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setInt(1, bbsID);
			try(ResultSet resultSet = statement.executeQuery();) {
				if(resultSet.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(resultSet.getInt(1));
					bbs.setBbsTitle(resultSet.getString(2));
					bbs.setUserID(resultSet.getString(3));
					bbs.setBbsDate(resultSet.getString(4));
					bbs.setBbsContent(resultSet.getString(5));
					bbs.setBbsAvailable(resultSet.getInt(6));
					return bbs;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String sql = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setString(1, bbsTitle);
			statement.setString(2, bbsContent);
			statement.setInt(3, bbsID);
			return statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //������ ���̽� ����
	}
	
	public int delete(int bbsID) {
		String sql = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
		try(Connection connection = DB.getConnection("BBS");
				PreparedStatement statement= connection.prepareStatement(sql)){
			statement.setInt(1, bbsID);
			return statement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //������ ���̽� ����
	}
}
