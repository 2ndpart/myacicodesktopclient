package com.myacico.ui.warehouse;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import com.myacico.sql.Database;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class warehouse {
	public void wwarehouse (List<warehouse> listawarehouse, HashMap<String, Object> order) throws JRException {
		System.out.println("****Orderwarehouse: " + order.get("order"));
		InputStream arq = warehouse.class.getResourceAsStream("/com/myacico/ui/frame/warehouse/warehouse.jrxml");
		/*JasperReport report = JasperCompileManager.compileReport(arq);
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(listawarehouse));
		JasperViewer.viewReport(print,false);*/
		
		try {
			JasperDesign jasperDesign = JRXmlLoader.load(arq);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Connection connection=Database.GetSQLConnection();
			JasperPrint print = JasperFillManager.fillReport(jasperReport, order, connection);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
}
