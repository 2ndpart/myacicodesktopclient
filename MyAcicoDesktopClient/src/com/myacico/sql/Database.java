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
            	String pass = "d809f6b9bae795becbe4c3e85d5c8de3";
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
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return affectedRecord;
	}
	
	public static String GetShippingForGudang(long id, Connection conn)
	{
		String addedQuery = "SELECT shipping_name ,shipping_address1, shipping_address2, shipping_region, shipping_city, shipping_district, shipping_village, shipping_postal, shipping_phone1, shipping_phone2 FROM app_transaction WHERE transaction_id=" + id;
		String shippingAddress = null;
		try 
		{
			Statement stat = conn.createStatement();
			ResultSet rSet = stat.executeQuery(addedQuery);			
			while(rSet.next())
			{
				shippingAddress = rSet.getString("shipping_name") + "\n" + rSet.getString("shipping_address1") +
									"\n" + rSet.getString("shipping_address1") + "\n" + rSet.getString("shipping_address2") +
									"\n" + rSet.getString("shipping_region") + "\n" + rSet.getString("shipping_city") +
									"\n" + rSet.getString("shipping_district") + "\n" + rSet.getString("shipping_village") +
									"\n" + rSet.getString("shipping_postal") + "\n" + rSet.getString("shipping_phone1") +
									"\n" + rSet.getString("shipping_phone2") ;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return shippingAddress;
	}
	
	public static String GetBillingAddressForGudang(long id, Connection conn)
	{
		String billingAddress = null;
		String addedQuery = "SELECT billing_name, billing_address1, billing_address2, billing_country, billing_region, billing_city, billing_district, billing_village, billing_postal, billing_phone1, billing_phone2 FROM app_transaction WHERE transaction_id=" + id;
		try {
			Statement stat = conn.createStatement();
			ResultSet rSet = stat.executeQuery(addedQuery);
			
			while(rSet.next())
			{
				billingAddress = rSet.getString("billing_name") +
								 "\n" + rSet.getString("billing_address1") + "\n" + rSet.getString("billing_address2") +
								 "\n" + rSet.getString("billing_country") + " " + rSet.getString("billing_region") + " " + rSet.getString("billing_city") +
								 "\n" + rSet.getString("billing_district") + " " + rSet.getString("billing_village") + " " + rSet.getString("billing_postal") +
								 "\n" + rSet.getString("billing_phone1") + " \\ " + rSet.getString("billing_phone2");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return billingAddress;
	}
	
	public static String GetAWBForGudang(long transID)
	{
		String awbNumber = "";
		
		return awbNumber;
	}
}
