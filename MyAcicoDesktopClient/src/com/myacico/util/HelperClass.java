package com.myacico.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import com.myacico.sql.Database;

public class HelperClass {
	public static DefaultTableModel transModelViewer;
	public static DefaultTableModel paidTransModelViewer;
	public static String loginAs;
	
	public static TransactionDetail GetTransactionDetail(String orderID)
	{
		PreparedStatement statement = null;
	    ResultSet rs = null;
	    Connection c = Database.GetSQLConnection();
	    String invoiceDetail = null;
	    TransactionDetail detail = new TransactionDetail();
	    try
	    {
	    	statement = c.prepareStatement("SELECT * FROM adempiere.retrieve_detail_invoice2(?)");
	    	statement.setString(1, orderID);
	    	rs = statement.executeQuery();
	    	
	    	while(rs.next())
	    	{
	    		detail.setDetail(rs.getString("inv_detail_product"));
                detail.setGrandTotal(rs.getBigDecimal("grand_total"));
                detail.setInvoiceNumber(rs.getString("invoice_number"));
                detail.setOrderNumber(rs.getString("order_number"));
                detail.setToName(rs.getString("shipping_name"));
                detail.setToAddress(rs.getString("s_address"));
                detail.setToPhone(rs.getString("s_phone"));
                detail.setFromAddress(rs.getString("b_address"));
                detail.setFromName(rs.getString("billing_name"));
                detail.setFromPhone(rs.getString("b_phone"));
                detail.setTransactionTime(rs.getString("transaction_time"));
                detail.setPaymentMethod(rs.getString("payment_method"));
                detail.setCourier(rs.getString("courier"));
                detail.setCourierAmount(rs.getBigDecimal("courier_amount"));
	    	}
	    }
	    catch(Exception ex)
	    {
	    	
	    }
	    return detail;
	}
	
	public static TransactionDetail getiInvoiceDetail(Long transId) throws SQLException {
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    Connection c = Database.GetSQLConnection();
	    
	    TransactionDetail detail = new TransactionDetail();
	    try 
	    {
            statement = c.prepareCall("SELECT courier_amount,courier,payment_method,grand_total,invoice_number,order_number,transaction_time,shipping_name,s_address,s_phone,billing_name,b_address,b_phone,inv_detail_product FROM adempiere.retrieve_detail_invoice(?)");
            statement.setLong(1, transId);
            System.out.println("sss "+statement.toString());
            rs = statement.executeQuery();
            while (rs.next()) 
            {
                detail.setDetail(rs.getString("inv_detail_product"));
                detail.setGrandTotal(rs.getBigDecimal("grand_total"));
                detail.setInvoiceNumber(rs.getString("invoice_number"));
                detail.setOrderNumber(rs.getString("order_number"));
                detail.setToName(rs.getString("shipping_name"));
                detail.setToAddress(rs.getString("s_address"));
                detail.setToPhone(rs.getString("s_phone"));
                detail.setFromAddress(rs.getString("b_address"));
                detail.setFromName(rs.getString("billing_name"));
                detail.setFromPhone(rs.getString("b_phone"));
                detail.setTransactionTime(rs.getString("transaction_time"));
                detail.setPaymentMethod(rs.getString("payment_method"));
                detail.setCourier(rs.getString("courier"));
                detail.setCourierAmount(rs.getBigDecimal("courier_amount"));
            }
	    } 
	    catch (Exception e) 
	    {
	        System.out.println("ee"+e);
	    }
	    return detail;
	}
}
