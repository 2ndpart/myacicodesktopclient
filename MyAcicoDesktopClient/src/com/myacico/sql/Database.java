package com.myacico.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	static String GET_ALL_QUERY  = "select * from adempiere.ad_user left join adempiere.c_bpartner_location on ad_user.c_bpartner_location_id = c_bpartner_location.c_bpartner_location_id" + 
			" left join adempiere.c_location on c_location.c_location_id = c_bpartner_location.c_location_id" + 
			" where adempiere.ad_user.ad_user_id =";
	public static Connection GetSQLConnection()
	{
		Connection conn = null;
		try
		{
			DriverManager.registerDriver(new org.postgresql.Driver());
			String url = "jdbc:postgresql://db.myacico.co.id:5432/idempiere-myacico";
            	String user = "adempiere";
            	String pass = "adempiere";
            	conn = DriverManager.getConnection(url, user, pass);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return conn;
	}
	
	public static String GetStringFromDB(String query, Connection conn)
	{
		String queryResult = "";
		try {
			Statement stat = conn.createStatement();
			ResultSet rSet = stat.executeQuery(query);
			while(rSet.next())
			{
				queryResult = rSet.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return queryResult;
	}
	
	public static String GetShippingAddress(String id, Connection conn)
	{
		String shippingAddress = "";
		String addedQuery = GET_ALL_QUERY + "'" + id + "'";
		try {
			Statement stat = conn.createStatement();
			ResultSet rSet = stat.executeQuery(addedQuery);
			
			while(rSet.next())
			{
				shippingAddress = rSet.getString("address1") + " " + rSet.getString("address2") + " " + rSet.getString("city");
			}
			rSet.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shippingAddress;
	}
	
	public static String GetBillingAddress(String id, Connection conn)
	{
		String billingAddress = "";
		String addedQuery = GET_ALL_QUERY + "'" + id + "'";
		try {
			Statement stat = conn.createStatement();
			ResultSet rSet = stat.executeQuery(addedQuery);
			
			while(rSet.next())
			{
				billingAddress = rSet.getString("address1") + " " + rSet.getString("address2") + " " + rSet.getString("city");
			}
			rSet.close();
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return billingAddress;
	}
	
	public static int UpdateDataToServer(String query, Connection conn)
	{
		int affectedRecord = 0;
		
		try
		{
			Statement stat = conn.createStatement();
			affectedRecord = stat.executeUpdate(query);
			stat.close();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return affectedRecord;
	}
	
}
