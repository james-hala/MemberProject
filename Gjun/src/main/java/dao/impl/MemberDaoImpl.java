package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.MemberDao;
import model.Member;
import util.DbConnection;

public class MemberDaoImpl implements MemberDao
{
	public static void main(String[] args) 
	{
		/*
		 * Member m=new Member("a","yy","789","台北","456","0000");
		 * new MemberDaoImpl().addMember(m);//新增資料
		 */
		/*
		 * System.out.println(new MemberDaoImpl().findByUsernameAndPassword("abc", "123"));//註冊帳號,看帳號是否重複
		 */
		System.out.println(new MemberDaoImpl().findByUsername("abc"));

	}
	
	private static Connection conn=DbConnection.getDb();
	
	@Override
	public void addMember(Member member) 
	{
		/*
		 * 1.SQL語法
		 * 2.交給PreparedStatement
		 * 3.executeUpdate
		 */
		String SQL="insert into member(name,username,password,address,phone,mobile)"
				+ "values(?,?,?,?,?,?)";
		try 
		{
			PreparedStatement preparedstatement=conn.prepareStatement(SQL);
			preparedstatement.setString(1, member.getName());
			preparedstatement.setString(2, member.getUsername());
			preparedstatement.setString(3, member.getPassword());
			preparedstatement.setString(4, member.getAddress());
			preparedstatement.setString(5, member.getPhone());
			preparedstatement.setString(6, member.getMobile());
			preparedstatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	@Override
	public Member findByUsernameAndPassword(String username, String password) 
	{
		/*
		 * 1.SQL語法
		 * 2.Preparedstatement-->executeQuery()
		 * 3.ResultSet-->Member
		 * 
		 */
		String SQL="select * from member where username=? and password=?";
		Member member=null;
		try 
		{
			PreparedStatement preparedStatement=conn.prepareStatement(SQL);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				member=new Member();
				member.setId(resultSet.getInt("id"));
				member.setName(resultSet.getString("name"));
				member.setUsername(resultSet.getString("username"));
				member.setPassword(resultSet.getString("password"));
				member.setAddress(resultSet.getString("address"));
				member.setPhone(resultSet.getString("phone"));
				member.setMobile(resultSet.getString("mobile"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return member;
	}
	@Override
	public boolean findByUsername(String username) 
	{
		/*
		 * 1.SQL語法,boolean變數
		 * 2.PreparedStatement-->executeQuery
		 * 3.ResultSet-->true/false
		 */
		String SQL="select * from member where username=?";
		boolean isUsernameBeenUsed=false;
		try 
		{
			PreparedStatement preparedStatement=conn.prepareStatement(SQL);
			preparedStatement.setString(1, username);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) isUsernameBeenUsed=true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isUsernameBeenUsed;
	}
}
