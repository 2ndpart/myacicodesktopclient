package com.myacico.ui.warehouse;

import java.io.InputStream;
import java.util.List;

import com.myacico.ui.frame.DetailPackagingFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class warehouse {
	public void wwarehouse (List<warehouse> listawwarehouse) throws JRException {
		InputStream arq = warehouse.class.getResourceAsStream("/com.myacico.ui.frame.warehouse/warehouse.jrxml");
		JasperReport report = JasperCompileManager.compileReport(arq);
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(listawwarehouse));
		JasperViewer.viewReport(print,false);
	}
		

}
